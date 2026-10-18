
package com.kbd.abyssal_reawoken.blocks;

import com.kbd.abyssal_reawoken.blockentity.RitualPedestalBlockEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RitualPedestalBlock extends DisplayPedestalBlock {

    public static final MapCodec<RitualPedestalBlock> CODEC =
            simpleCodec(properties -> new RitualPedestalBlock(properties, 2));

    private final int tierHalfSteps;

    public RitualPedestalBlock(Properties properties, int tierHalfSteps) {
        super(properties);
        this.tierHalfSteps = tierHalfSteps;
    }

    public int getTierHalfSteps() {
        return tierHalfSteps;
    }

    public double getTier() {
        return tierHalfSteps / 2.0;
    }

    @Override
    protected MapCodec<? extends RitualPedestalBlock> codec() {
        return CODEC;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RitualPedestalBlockEntity(pos, state);
    }
}
