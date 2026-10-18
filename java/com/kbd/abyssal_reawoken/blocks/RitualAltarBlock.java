
package com.kbd.abyssal_reawoken.blocks;

import com.kbd.abyssal_reawoken.blockentity.RitualAltarBlockEntity;
import com.mojang.serialization.MapCodec;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class RitualAltarBlock extends BaseEntityBlock {

    public static final MapCodec<RitualAltarBlock> CODEC =
            simpleCodec(properties -> new RitualAltarBlock(properties, 2));

    private final int tierHalfSteps;

    public RitualAltarBlock(Properties properties, int tierHalfSteps) {
        super(properties.noOcclusion());
        this.tierHalfSteps = tierHalfSteps;
    }

    public int getTierHalfSteps() {
        return tierHalfSteps;
    }

    public double getTier() {
        return tierHalfSteps / 2.0;
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new RitualAltarBlockEntity(pos, state);
    }
}
