
package com.kbd.abyssal_reawoken.ritual;

import com.kbd.abyssal_reawoken.ModDataComponents;
import com.kbd.abyssal_reawoken.items.NecronomiconItems;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class RitualStructureTransformations {

    private RitualStructureTransformations() {}

    private static final int REQUIRED_PE = 10;
    private static final int OUTER_DISTANCE = 3;

    private record Transformation(
            Block material,
            Block altar,
            Block pedestal,
            int requiredBookTier,
            ResourceKey<Level> dimension
    ) {}

    private static final Map<Block, Transformation> TRANSFORMATIONS =
            new LinkedHashMap<>();

    public static void register(
            Block material,
            Block altar,
            Block pedestal,
            int requiredBookTier,
            ResourceKey<Level> dimension
    ) {
        TRANSFORMATIONS.put(
                material,
                new Transformation(
                        material,
                        altar,
                        pedestal,
                        requiredBookTier,
                        dimension
                )
        );
    }

    public static boolean tryTransform(
            ServerLevel level,
            BlockPos center,
            Player player,
            InteractionHand hand
    ) {
        ItemStack book = player.getItemInHand(hand);
        int bookTier = NecronomiconItems.getTier(book);

        if (bookTier <= 0) {
            return false;
        }

        BlockState centerState = level.getBlockState(center);
        Transformation transformation =
                TRANSFORMATIONS.get(centerState.getBlock());

        if (transformation == null) {
            return false;
        }

        // Tier 5 bypasses normal tier and dimension restrictions.
        if (bookTier != 5
                && bookTier < transformation.requiredBookTier()) {
            return false;
        }

        if (bookTier != 5
                && transformation.dimension() != null
                && !level.dimension().equals(transformation.dimension())) {
            return false;
        }

        int currentPE = book.getOrDefault(
                ModDataComponents.POTENTIAL_ENERGY, 0
        );

        if (currentPE < REQUIRED_PE) {
            return false;
        }

        List<BlockPos> outerPositions = new ArrayList<>(8);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                if (dx == 0 && dz == 0) {
                    continue;
                }

                BlockPos outer = center.offset(
                        dx * OUTER_DISTANCE,
                        0,
                        dz * OUTER_DISTANCE
                );

                if (level.getBlockState(outer).getBlock()
                        != transformation.material()) {
                    return false;
                }

                for (int distance = 1;
                     distance < OUTER_DISTANCE;
                     distance++) {

                    BlockPos gap = center.offset(
                            dx * distance,
                            0,
                            dz * distance
                    );

                    if (!level.getBlockState(gap).isAir()) {
                        return false;
                    }
                }

                outerPositions.add(outer);
            }
        }

        // All checks passed; only now consume PE and transform blocks.
        book.set(
                ModDataComponents.POTENTIAL_ENERGY,
                currentPE - REQUIRED_PE
        );

        for (BlockPos outer : outerPositions) {
            level.setBlock(
                    outer,
                    transformation.pedestal().defaultBlockState(),
                    Block.UPDATE_ALL
            );
        }

        level.setBlock(
                center,
                transformation.altar().defaultBlockState(),
                Block.UPDATE_ALL
        );

        return true;
    }
}
