package com.kbd.abyssal_reawoken.blockentity;

import com.kbd.abyssal_reawoken.*;
import com.kbd.abyssal_reawoken.penergy.IPotentialEnergy;
import com.kbd.abyssal_reawoken.penergy.PotentialEnergyCapability;
import com.kbd.abyssal_reawoken.penergy.PotentialEnergyStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import java.util.Map;

public class DisplayPedestalBlockEntity extends BlockEntity implements IPotentialEnergy {

    private static final Map<String, Integer> NECRONOMICON_CAPS = Map.of(
            "necronomicon", 5000,
            "abyssal_wastelands_necronomicon", 10000,
            "dreadlands_necronomicon", 20000,
            "omothol_necronomicon", 40000,
            "abyssalnomicon", 10000000
    );

    private ItemStack heldItem = ItemStack.EMPTY;
    private int tickCounter = 0;

    private final PotentialEnergyStorage energyStorage = new PotentialEnergyStorage(
            this::getDynamicMaxPE,
            () -> {
                this.setChanged();
                if (this.level != null) {
                    this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
                }
            }
    );

    public DisplayPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(AbyssalReawoken.RITUAL_PEDESTAL_BE.get(), pos, state);
    }

    public ItemStack getHeldItem() { return heldItem; }

    private static int getItemMaxCapacity(ItemStack stack) {
        if (stack.isEmpty()) return -1;
        ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
        if (!id.getNamespace().equals(AbyssalReawoken.MODID)) return -1;
        return NECRONOMICON_CAPS.getOrDefault(id.getPath(), -1);
    }

    public void setHeldItem(ItemStack item) {
        this.heldItem = item == null ? ItemStack.EMPTY : item.copy();
        this.setChanged();
        if (this.level != null) {
            this.level.sendBlockUpdated(this.worldPosition, this.getBlockState(), this.getBlockState(), 3);
        }
    }

    @Override public int getPE() { return this.energyStorage.getPE(); }
    @Override public int getMaxPE() { return this.energyStorage.getMaxPE(); }
    @Override public void setPE(int amount) { this.energyStorage.setPE(amount); }

    private int getDynamicMaxPE() {
        if (this.level == null) return 0;
        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(this.getBlockState().getBlock());
        if (blockId.getPath().equals("ritual_altar")) return 50000;
        if (blockId.getPath().equals("cthulhu_statue")) return 20000;
        return 5000;
    }

    public void tick(Level level, BlockPos pos, BlockState state) {
        if (level.isClientSide()) return;

        ResourceLocation blockId = BuiltInRegistries.BLOCK.getKey(state.getBlock());
        if (!blockId.getPath().equals("cthulhu_statue") && !blockId.getPath().equals("ritual_pedestal")) return;

        tickCounter++;
        if (tickCounter < 20) return; // Updates power transfer balances every second
        tickCounter = 0;

        addPE(5); // Internal passive charging loop

        ItemStack stack = getHeldItem();
        int itemMaxCap = getItemMaxCapacity(stack);

        // ➡️ TRANSFER ENERGY INTO DISPLAYED ITEM
        if (itemMaxCap != -1 && getPE() >= 5) {
            int currentItemPE = stack.getOrDefault(ModDataComponents.POTENTIAL_ENERGY.get(), 0);

            if (currentItemPE < itemMaxCap) {
                int transferAmount = Math.min(5, itemMaxCap - currentItemPE);
                if (consumePE(transferAmount)) {
                    stack.set(ModDataComponents.POTENTIAL_ENERGY.get(), currentItemPE + transferAmount);
                    setHeldItem(stack);
                    return;
                }
            }
        }

        /* Wireless broadcast backup routing */
        int radius = 3;
        for (int x = -radius; x <= radius; x++) {
            for (int z = -radius; z <= radius; z++) {
                if (x == 0 && z == 0) continue;
                BlockPos targetPos = pos.offset(x, 0, z);
                IPotentialEnergy targetEnergy = level.getCapability(PotentialEnergyCapability.BLOCK, targetPos, null);
                if (targetEnergy != null && targetEnergy.getPE() < targetEnergy.getMaxPE() && getPE() >= 5) {
                    targetEnergy.addPE(5);
                    consumePE(5);
                    return;
                }
            }
        }
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        energyStorage.save(tag);
        if (!heldItem.isEmpty()) tag.put("HeldItem", heldItem.save(registries));
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        energyStorage.load(tag);
        if (tag.contains("HeldItem") && !tag.getCompound("HeldItem").isEmpty()) {
            heldItem = ItemStack.parse(registries, tag.getCompound("HeldItem")).orElse(ItemStack.EMPTY);
        } else {
            heldItem = ItemStack.EMPTY;
        }
    }

    @Override public Packet<ClientGamePacketListener> getUpdatePacket() { return ClientboundBlockEntityDataPacket.create(this); }
    @Override public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = super.getUpdateTag(registries);
        energyStorage.save(tag);
        if (!heldItem.isEmpty()) tag.put("HeldItem", heldItem.save(registries));
        return tag;
    }
    @Override public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries) {
        super.onDataPacket(connection, packet, registries);
        loadAdditional(packet.getTag(), registries);
    }
}
