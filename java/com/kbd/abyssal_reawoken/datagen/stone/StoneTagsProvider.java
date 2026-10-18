package com.kbd.abyssal_reawoken.datagen.stone;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.fams.StoneFamily;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.kbd.abyssal_reawoken.items.SnWItems.*;

public class StoneTagsProvider extends BlockTagsProvider {

    public StoneTagsProvider(
            PackOutput output,
            CompletableFuture<HolderLookup.Provider> lookupProvider,
            ExistingFileHelper existingFileHelper
    ) {
        super(
                output,
                lookupProvider,
                AbyssalReawoken.MODID,
                existingFileHelper
        );
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        stone(DARKSTONE);
        stone(ABYSSAL);
        stone(DREADSTONE);
        stone(ELYSIAN);
        stone(CORALIUM);
        stone(ETHAXIUM);
        stone(OMOTHOL);
        stone(MONOLITH);
    }

    private void stone(StoneFamily family) {

        /*
         * BASE STONE
         *
         * Makes it behave as a normal stone-type block for
         * vanilla systems that check the base-stone tag.
         */
        tag(BlockTags.BASE_STONE_OVERWORLD)
                .add(family.stone.get());

        tag(BlockTags.STONE_ORE_REPLACEABLES)
                .add(family.stone.get());

        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(
                        family.stone.get(),
                        family.stairs.get(),
                        family.slab.get(),
                        family.bricks.get(),
                        family.crackedBricks.get(),
                        family.brickStairs.get(),
                        family.brickSlab.get(),
                        family.brickWall.get(),
                        family.brickFence.get(),
                        family.chiseledBricks.get()
                );

        tag(BlockTags.NEEDS_STONE_TOOL)
                .add(
                        family.stone.get(),
                        family.stairs.get(),
                        family.slab.get(),
                        family.bricks.get(),
                        family.crackedBricks.get(),
                        family.brickStairs.get(),
                        family.brickSlab.get(),
                        family.brickWall.get(),
                        family.brickFence.get(),
                        family.chiseledBricks.get()
                );

        /*
         * STONE
         *
         * NeoForge's STONES tag is specifically for blocks that
         * can be used as a stone ingredient.
         */
        tag(Tags.Blocks.STONES)
                .add(
                        family.stone.get(),
                        family.bricks.get(),
                        family.crackedBricks.get(),
                        family.chiseledBricks.get()
                );

        /*
         * STONE BRICKS
         */
        tag(BlockTags.STONE_BRICKS)
                .add(
                        family.bricks.get(),
                        family.crackedBricks.get(),
                        family.chiseledBricks.get()
                );

        /*
         * STAIRS
         */
        tag(BlockTags.STAIRS)
                .add(
                        family.stairs.get(),
                        family.brickStairs.get()
                );

        /*
         * SLABS
         */
        tag(BlockTags.SLABS)
                .add(
                        family.slab.get(),
                        family.brickSlab.get()
                );

        /*
         * WALLS
         */
        tag(BlockTags.WALLS)
                .add(
                        family.brickWall.get()
                );

        /*
         * NORMAL WALL
         *
         * Only exists when CBP is disabled.
         */
        if (family.wall != null) {
            tag(BlockTags.WALLS)
                    .add(family.wall.get());

            tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(family.wall.get());

            tag(BlockTags.NEEDS_STONE_TOOL)
                    .add(family.wall.get());
        }

        /*
         * BRICK FENCE
         */
        tag(BlockTags.FENCES)
                .add(family.brickFence.get());

        /*
         * CBP
         *
         * Cobblestone variants only exist when CBP is enabled.
         */
        if (family.cobblestone != null) {

            tag(Tags.Blocks.COBBLESTONES)
                    .add(family.cobblestone.get());

            tag(Tags.Blocks.COBBLESTONES_NORMAL)
                    .add(family.cobblestone.get());

            tag(BlockTags.MINEABLE_WITH_PICKAXE)
                    .add(
                            family.cobblestone.get(),
                            family.cobblestoneStairs.get(),
                            family.cobblestoneSlab.get(),
                            family.cobblestoneWall.get(),
                            family.pressurePlate.get(),
                            family.button.get()
                    );

            tag(BlockTags.NEEDS_STONE_TOOL)
                    .add(
                            family.cobblestone.get(),
                            family.cobblestoneStairs.get(),
                            family.cobblestoneSlab.get(),
                            family.cobblestoneWall.get(),
                            family.pressurePlate.get(),
                            family.button.get()
                    );

            /*
             * Cobblestone behaves like vanilla cobblestone.
             */
            tag(BlockTags.STAIRS)
                    .add(family.cobblestoneStairs.get());

            tag(BlockTags.SLABS)
                    .add(family.cobblestoneSlab.get());

            tag(BlockTags.WALLS)
                    .add(family.cobblestoneWall.get());

            /*
             * Pressure plate
             */
            tag(BlockTags.STONE_PRESSURE_PLATES)
                    .add(family.pressurePlate.get());

            /*
             * Button
             */
            tag(BlockTags.STONE_BUTTONS)
                    .add(family.button.get());

            tag(BlockTags.BUTTONS)
                    .add(family.button.get());
        }
    }
}