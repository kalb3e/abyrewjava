package com.kbd.abyssal_reawoken.portal;

import com.kbd.abyssal_reawoken.items.SnWItems;
import com.kbd.abyssal_reawoken.items.TerrainItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public class DreadlandsPortal {

    public static boolean tryCreatePortal(Level level, BlockPos pos) {
        for (Direction.Axis axis : new Direction.Axis[]{
                Direction.Axis.X,
                Direction.Axis.Z
        }) {
            if (tryCreatePortal(level, pos, axis)) {
                return true;
            }
        }

        return false;
    }

    private static boolean tryCreatePortal(
            Level level,
            BlockPos pos,
            Direction.Axis axis) {

        Direction right = axis == Direction.Axis.X
                ? Direction.EAST
                : Direction.SOUTH;

        Direction up = Direction.UP;

        // Bottom-left corner of the possible frame
        BlockPos bottomLeft = pos;

        // Check frame width: 4 blocks
        for (int x = 0; x < 4; x++) {
            if (!isFrameBlock(level, bottomLeft.relative(right, x))) {
                return false;
            }
        }

        // Check frame height: 5 blocks
        for (int y = 0; y < 5; y++) {
            if (!isFrameBlock(level, bottomLeft.above(y))) {
                return false;
            }

            if (!isFrameBlock(
                    level,
                    bottomLeft.relative(right, 3).above(y))) {
                return false;
            }
        }

        // Top frame
        for (int x = 0; x < 4; x++) {
            if (!isFrameBlock(
                    level,
                    bottomLeft.relative(right, x).above(4))) {
                return false;
            }
        }

        // Fill the inside: 2 × 3
        for (int x = 1; x <= 2; x++) {
            for (int y = 1; y <= 3; y++) {

                BlockPos portalPos =
                        bottomLeft.relative(right, x).above(y);

                level.setBlock(
                        portalPos,
                        TerrainItems.DREADLANDS_PORTAL
                                .get()
                                .defaultBlockState()
                                .setValue(
                                        DreadlandsPortalBlock.AXIS,
                                        axis
                                ),
                        Block.UPDATE_ALL
                );
            }
        }

        return true;
    }

    private static boolean isFrameBlock(
            Level level,
            BlockPos pos) {

        return level.getBlockState(pos).is(
                SnWItems.DREADSTONE.stone.get()
        );
    }
}