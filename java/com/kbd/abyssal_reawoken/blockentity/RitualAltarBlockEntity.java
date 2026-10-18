
package com.kbd.abyssal_reawoken.blockentity;

import com.kbd.abyssal_reawoken.ModDataComponents;
import com.kbd.abyssal_reawoken.blocks.RitualAltarBlock;
import com.kbd.abyssal_reawoken.items.RitualItems;
import com.kbd.abyssal_reawoken.items.NecronomiconItems;
import com.kbd.abyssal_reawoken.recipe.ModRecipes;
import com.kbd.abyssal_reawoken.recipe.RitualRecipe;
import com.kbd.abyssal_reawoken.recipe.RitualRecipeInput;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RitualAltarBlockEntity extends BlockEntity {

    private ItemStack heldItem = ItemStack.EMPTY;

    public RitualAltarBlockEntity(BlockPos pos, BlockState state) {
        super(RitualItems.ALTAR_BE.get(), pos, state);
    }

    public ItemStack getHeldItem() {
        return heldItem;
    }

    public boolean hasHeldItem() {
        return !heldItem.isEmpty();
    }

    public void setHeldItem(ItemStack stack) {
        heldItem = stack.isEmpty() ? ItemStack.EMPTY : stack.copyWithCount(1);
        setChanged();
        syncToClient();
    }

    public void clearHeldItem() {
        setHeldItem(ItemStack.EMPTY);
    }

    public int getTierHalfSteps() {
        if (getBlockState().getBlock() instanceof RitualAltarBlock altar) {
            return altar.getTierHalfSteps();
        }
        return 0;
    }

    public double getTier() {
        return getTierHalfSteps() / 2.0;
    }

    public boolean tryTriggerRitual() {
        if (level == null || level.isClientSide()) {
            return false;
        }

        if (heldItem.isEmpty() || !NecronomiconItems.isNecronomicon(heldItem)) {
            return false;
        }

        int bookPE = heldItem.getOrDefault(
                ModDataComponents.POTENTIAL_ENERGY, 0
        );

        List<ItemStack> pedestalItems = new ArrayList<>();

        // Gather items from eligible pedestals in the altar's horizontal
        // radius. The altar itself is excluded.
        for (BlockPos pos : BlockPos.betweenClosed(
                worldPosition.offset(-4, 0, -4),
                worldPosition.offset(4, 0, 4)
        )) {
            if (pos.equals(worldPosition)) {
                continue;
            }

            BlockEntity nearby = level.getBlockEntity(pos);

            if (nearby instanceof RitualPedestalBlockEntity pedestal) {
                if (pedestal.getBlockState().getBlock()
                        instanceof com.kbd.abyssal_reawoken.blocks.RitualPedestalBlock block
                        && block.getTierHalfSteps() <= getTierHalfSteps()
                        && pedestal.hasHeldItem()) {
                    pedestalItems.add(pedestal.getHeldItem().copy());
                }
            } else if (nearby instanceof DisplayPedestalBlockEntity display
                    && display.hasHeldItem()) {
                pedestalItems.add(display.getHeldItem().copy());
            }
        }

        RitualRecipeInput input = new RitualRecipeInput(
                heldItem,
                pedestalItems,
                bookPE
        );

        Optional<net.minecraft.world.item.crafting.RecipeHolder<RitualRecipe>>
                match = level.getRecipeManager().getRecipesFor(
                        ModRecipes.RITUAL_TYPE.get(),
                        input,
                        level
                ).stream()
                .filter(holder ->
                        getTierHalfSteps()
                                >= holder.value().getTierHalfSteps()
                )
                .filter(holder -> bookPE >= holder.value().getRequiredPE())
                .findFirst();

        if (match.isEmpty()) {
            return false;
        }

        RitualRecipe recipe = match.get().value();
        int requiredPE = recipe.getRequiredPE();

        // Recheck before consuming anything.
        if (heldItem.getOrDefault(
                ModDataComponents.POTENTIAL_ENERGY, 0
        ) < requiredPE) {
            return false;
        }

        ItemStack result = recipe.assemble(input, level.registryAccess());

        if (result.isEmpty()) {
            return false;
        }

        heldItem.set(
                ModDataComponents.POTENTIAL_ENERGY,
                bookPE - requiredPE
        );

        // Consume pedestal items only after a valid recipe and result exist.
        for (BlockPos pos : BlockPos.betweenClosed(
                worldPosition.offset(-4, 0, -4),
                worldPosition.offset(4, 0, 4)
        )) {
            if (pos.equals(worldPosition)) {
                continue;
            }

            BlockEntity nearby = level.getBlockEntity(pos);

            if (nearby instanceof RitualPedestalBlockEntity pedestal
                    && pedestal.hasHeldItem()) {
                pedestal.clearHeldItem();
            } else if (nearby instanceof DisplayPedestalBlockEntity display
                    && display.hasHeldItem()) {
                display.clearHeldItem();
            }
        }

        heldItem = result.copyWithCount(1);
        setChanged();
        syncToClient();
        return true;
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

        heldItem = tag.contains("HeldItem")
                ? ItemStack.parse(registries, tag.getCompound("HeldItem"))
                .orElse(ItemStack.EMPTY)
                : ItemStack.EMPTY;
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return saveWithoutMetadata(registries);
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
