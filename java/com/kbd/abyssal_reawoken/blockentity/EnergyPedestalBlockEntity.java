
package com.kbd.abyssal_reawoken.blockentity;

import com.kbd.abyssal_reawoken.items.EnergyItems;
import com.kbd.abyssal_reawoken.penergy.IPotentialEnergy;
import com.kbd.abyssal_reawoken.penergy.PotentialEnergyTransfer;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class EnergyPedestalBlockEntity extends BlockEntity
        implements IPotentialEnergy {

    private int pe;
    private ItemStack heldItem = ItemStack.EMPTY;

    public EnergyPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(EnergyItems.ENERGY_PEDESTAL_BE.get(), pos, state);
    }

    @Override
    public int getPE() {
        return pe;
    }

    @Override
    public int getMaxPE() {
        return EnergyItems.MAX_PE;
    }

    @Override
    public void setPE(int amount) {
        int clamped = Math.max(0, Math.min(amount, getMaxPE()));

        if (pe == clamped) {
            return;
        }

        pe = clamped;
        setChanged();
        syncToClient();
    }

    public ItemStack getHeldItem() {
        return heldItem;
    }

    public boolean hasHeldItem() {
        return !heldItem.isEmpty();
    }

    public void clearHeldItem() {
        setHeldItem(ItemStack.EMPTY);
    }

    public void setHeldItem(ItemStack stack) {
        heldItem = stack.isEmpty()
                ? ItemStack.EMPTY
                : stack.copyWithCount(1);

        // Transfer available PE once when an item is inserted or replaced.
        pe = PotentialEnergyTransfer.transferToItem(heldItem, pe);
        pe = Math.max(0, Math.min(pe, getMaxPE()));

        setChanged();
        syncToClient();
    }

    public int extractEnergy(int requestedPE) {
        if (requestedPE <= 0 || pe <= 0) {
            return 0;
        }

        int extracted = Math.min(requestedPE, pe);
        setPE(pe - extracted);
        return extracted;
    }

    private void syncToClient() {
        if (level != null && !level.isClientSide()) {
            level.sendBlockUpdated(
                    worldPosition,
                    getBlockState(),
                    getBlockState(),
                    3
            );
        }
    }

    @Override
    protected void saveAdditional(
            CompoundTag tag,
            HolderLookup.Provider registries
    ) {
        super.saveAdditional(tag, registries);

        tag.putInt("PotentialEnergy", pe);

        if (!heldItem.isEmpty()) {
            tag.put("HeldItem", heldItem.save(registries));
        }
    }

    @Override
    protected void loadAdditional(
            CompoundTag tag,
            HolderLookup.Provider registries
    ) {
        super.loadAdditional(tag, registries);

        pe = Math.max(
                0,
                Math.min(tag.getInt("PotentialEnergy"), getMaxPE())
        );

        heldItem = tag.contains("HeldItem")
                ? ItemStack.parse(registries, tag.getCompound("HeldItem"))
                .orElse(ItemStack.EMPTY)
                : ItemStack.EMPTY;
    }

    @Override
    public CompoundTag getUpdateTag(
            HolderLookup.Provider registries
    ) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
