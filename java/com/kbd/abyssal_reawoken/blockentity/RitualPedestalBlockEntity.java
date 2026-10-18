
package com.kbd.abyssal_reawoken.blockentity;

import com.kbd.abyssal_reawoken.items.RitualItems;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

public class RitualPedestalBlockEntity extends DisplayPedestalBlockEntity {

    public RitualPedestalBlockEntity(BlockPos pos, BlockState state) {
        super(RitualItems.RITUAL_PEDESTAL_BE.get(), pos, state);
    }

    @Override
    protected void saveAdditional(
            CompoundTag tag,
            HolderLookup.Provider registries
    ) {
        super.saveAdditional(tag, registries);

        ItemStack stack = getHeldItem();
        if (!stack.isEmpty()) {
            tag.put("RitualHeldItem", stack.save(registries));
        }
    }

    @Override
    protected void loadAdditional(
            CompoundTag tag,
            HolderLookup.Provider registries
    ) {
        super.loadAdditional(tag, registries);

        if (tag.contains("RitualHeldItem")) {
            ItemStack stack = ItemStack.parse(
                    registries,
                    tag.getCompound("RitualHeldItem")
            ).orElse(ItemStack.EMPTY);

            setHeldItem(stack);
        } else {
            setHeldItem(ItemStack.EMPTY);
        }
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
