
package com.kbd.abyssal_reawoken.blocks;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class StatueBlock extends Block {

    public static final MapCodec<StatueBlock> CODEC =
            simpleCodec(properties -> new StatueBlock(true, properties));

    public static final DirectionProperty FACING =
            BlockStateProperties.HORIZONTAL_FACING;

    private static final ResourceLocation MONOLITH_WALL_ID =
            ResourceLocation.fromNamespaceAndPath(
                    "abyssal_reawoken",
                    "monolith_wall"
            );

    private final boolean active;

    public StatueBlock(
            boolean active,
            BlockBehaviour.Properties properties
    ) {
        super(properties.noOcclusion());
        this.active = active;

        registerDefaultState(
                stateDefinition.any()
                        .setValue(FACING, Direction.NORTH)
        );
    }

    @Override
    public MapCodec<? extends StatueBlock> codec() {
        return CODEC;
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder
    ) {
        builder.add(FACING);
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return defaultBlockState().setValue(
                FACING,
                context.getHorizontalDirection().getOpposite()
        );
    }

    public boolean isActiveStatue() {
        return active;
    }

    /**
     * Checks whether a position is inside this active statue's range.
     *
     * A monolith_wall directly underneath adds one horizontal block
     * of range and one downward block of range, but never upward range.
     */
    public boolean isWithinRange(
            Level level,
            BlockPos statuePos,
            BlockPos targetPos,
            int baseRange
    ) {
        if (!active || baseRange < 0) {
            return false;
        }

        Block monolithWall =
                BuiltInRegistries.BLOCK.get(MONOLITH_WALL_ID);

        boolean hasBonus = level.getBlockState(statuePos.below())
                .is(monolithWall);

        int horizontalRange = baseRange + (hasBonus ? 1 : 0);
        int downwardRange = baseRange + (hasBonus ? 1 : 0);

        int dx = Math.abs(targetPos.getX() - statuePos.getX());
        int dz = Math.abs(targetPos.getZ() - statuePos.getZ());
        int dy = targetPos.getY() - statuePos.getY();

        return dx <= horizontalRange
                && dz <= horizontalRange
                && dy <= baseRange
                && dy >= -downwardRange;
    }
}
