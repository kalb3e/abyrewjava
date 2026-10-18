package com.kbd.abyssal_reawoken.portal;

import com.kbd.abyssal_reawoken.items.SnWItems;
import com.kbd.abyssal_reawoken.items.TerrainItems;
import com.kbd.abyssal_reawoken.worldgen.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Portal;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.portal.DimensionTransition;
import net.minecraft.world.phys.Vec3;

public class Abyssal_landsPortalBlock extends Block implements Portal {

    public static final EnumProperty<Direction.Axis> AXIS =
            BlockStateProperties.HORIZONTAL_AXIS;

    public Abyssal_landsPortalBlock(Properties properties) {
        super(properties);

        this.registerDefaultState(
                this.stateDefinition.any()
                        .setValue(AXIS, Direction.Axis.X)
        );
    }

    @Override
    protected void createBlockStateDefinition(
            StateDefinition.Builder<Block, BlockState> builder) {

        builder.add(AXIS);
    }

    @Override
    protected void entityInside(
            BlockState state,
            Level level,
            BlockPos pos,
            Entity entity) {

        if (entity.canUsePortal(false)) {
            entity.setAsInsidePortal(this, pos);
        }
    }

    @Override
    public int getPortalTransitionTime(
            ServerLevel level,
            Entity entity) {

        return 80;
    }

    @Override
    public DimensionTransition getPortalDestination(
            ServerLevel level,
            Entity entity,
            BlockPos pos) {

        ServerLevel destination;

        if (level.dimension().equals(
                ModDimensions.ABYSSAL_LANDS_LEVEL_KEY)) {

            destination = level.getServer().overworld();

        } else {

            destination = level.getServer().getLevel(
                    ModDimensions.ABYSSAL_LANDS_LEVEL_KEY
            );
        }

        if (destination == null) {
            return null;
        }

        /*
         * Keep X/Z coordinates because Abyssal_lands
         * currently has coordinate_scale = 1.0.
         */
        int x = (int) Math.floor(entity.getX());
        int z = (int) Math.floor(entity.getZ());

        /*
         * Find a safe surface position.
         */
        BlockPos target = findSafePosition(
                destination,
                x,
                z
        );

        if (target == null) {
            return null;
        }

        /*
         * If there isn't already a portal there,
         * create one.
         */
        if (!isPortalNearby(destination, target)) {
            createPortal(destination, target);
        }

        return new DimensionTransition(
                destination,
                new Vec3(
                        target.getX() + 0.5,
                        target.getY(),
                        target.getZ() + 0.5
                ),
                Vec3.ZERO,
                entity.getYRot(),
                entity.getXRot(),
                false,
                DimensionTransition.PLAY_PORTAL_SOUND
        );
    }

    private BlockPos findSafePosition(
            ServerLevel level,
            int x,
            int z) {

        for (int y = level.getMaxBuildHeight() - 3;
             y > level.getMinBuildHeight();
             y--) {

            BlockPos ground =
                    new BlockPos(x, y, z);

            BlockPos feet =
                    ground.above();

            BlockPos head =
                    feet.above();

            if (!level.getBlockState(ground).isAir()
                    && level.getBlockState(feet).isAir()
                    && level.getBlockState(head).isAir()) {

                return feet;
            }
        }

        return null;
    }

    private boolean isPortalNearby(
            ServerLevel level,
            BlockPos center) {

        int radius = 16;

        for (int x = -radius; x <= radius; x++) {
            for (int y = -radius; y <= radius; y++) {
                for (int z = -radius; z <= radius; z++) {

                    BlockPos check =
                            center.offset(x, y, z);

                    if (level.getBlockState(check).is(
                            TerrainItems.ABYSSAL_LANDS_PORTAL.get()
                    )) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private void createPortal(
            ServerLevel level,
            BlockPos center) {

        BlockPos bottomLeft =
                center.below();

        Direction right = Direction.EAST;

        /*
         * D D D D
         * D P P D
         * D P P D
         * D P P D
         * D D D D
         */

        for (int x = 0; x < 4; x++) {

            level.setBlock(
                    bottomLeft.relative(right, x),
                    SnWItems.ABYSSAL
                            .stone.get()
                            .defaultBlockState(),
                    Block.UPDATE_ALL
            );

            level.setBlock(
                    bottomLeft
                            .relative(right, x)
                            .above(4),
                    SnWItems.ABYSSAL
                            .stone.get()
                            .defaultBlockState(),
                    Block.UPDATE_ALL
            );
        }

        for (int y = 0; y < 5; y++) {

            level.setBlock(
                    bottomLeft.above(y),
                    SnWItems.ABYSSAL
                            .stone.get()
                            .defaultBlockState(),
                    Block.UPDATE_ALL
            );

            level.setBlock(
                    bottomLeft
                            .relative(right, 3)
                            .above(y),
                    SnWItems.ABYSSAL
                            .stone.get()
                            .defaultBlockState(),
                    Block.UPDATE_ALL
            );
        }

        for (int x = 1; x <= 2; x++) {

            for (int y = 1; y <= 3; y++) {

                BlockPos portalPos =
                        bottomLeft
                                .relative(right, x)
                                .above(y);

                level.setBlock(
                        portalPos,
                        TerrainItems.ABYSSAL_LANDS_PORTAL
                                .get()
                                .defaultBlockState()
                                .setValue(
                                        AXIS,
                                        Direction.Axis.X
                                ),
                        Block.UPDATE_ALL
                );
            }
        }
    }
}