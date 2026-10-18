package com.kbd.abyssal_reawoken.datagen.stone;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.fams.StoneFamily;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.kbd.abyssal_reawoken.items.SnWItems.*;

public class StoneItemModelProvider extends ItemModelProvider {

    public StoneItemModelProvider(
            PackOutput output,
            ExistingFileHelper existingFileHelper
    ) {
        super(
                output,
                AbyssalReawoken.MODID,
                existingFileHelper
        );
    }

    @Override
    protected void registerModels() {
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

        String name =
                family.stone.getId().getPath();


        // ============================================================
        // TEXTURES
        // ============================================================

        var stoneTexture =
                modLoc(
                        "block/"
                                + name
                                + "/"
                                + name
                );

        var bricksTexture =
                modLoc(
                        "block/"
                                + name
                                + "/"
                                + name
                                + "_bricks"
                );


        // ============================================================
        // NORMAL STONE
        // ============================================================

        blockItem(family.stone.getId().getPath());

        blockItem(family.stairs.getId().getPath());

        blockItem(family.slab.getId().getPath());


        // ============================================================
        // NORMAL WALL
        //
        // Only exists when CBP is OFF.
        // ============================================================

        if (family.wall != null) {

            wallInventory(
                    family.wall.getId().getPath(),
                    stoneTexture
            );
        }


        // ============================================================
        // BRICKS
        // ============================================================

        blockItem(
                family.bricks.getId().getPath()
        );

        blockItem(
                family.crackedBricks.getId().getPath()
        );

        blockItem(
                family.brickStairs.getId().getPath()
        );

        blockItem(
                family.brickSlab.getId().getPath()
        );


        // ============================================================
        // BRICK WALL
        // ============================================================

        wallInventory(
                family.brickWall.getId().getPath(),
                bricksTexture
        );


        // ============================================================
        // BRICK FENCE
        // ============================================================

        fenceInventory(
                family.brickFence.getId().getPath(),
                bricksTexture
        );


        // ============================================================
        // CHISELED BRICKS
        // ============================================================

        blockItem(
                family.chiseledBricks.getId().getPath()
        );


        // ============================================================
        // BRICK ITEM
        //
        // Only exists when BI is ON.
        // ============================================================

        if (family.brickItem != null) {
            getBuilder(family.brickItem.getId().getPath())
                    .parent(
                            new ModelFile.UncheckedModelFile(
                                    mcLoc("item/generated")
                            )
                    )
                    .texture(
                            "layer0",
                            modLoc("block/" + name + "/" + name + "_brick")
                    );
        }


        // ============================================================
        // CBP
        //
        // Only exists when CBP is ON.
        // ============================================================

        if (family.cobblestone != null) {

            var cobblestoneTexture =
                    modLoc(
                            "block/"
                                    + name
                                    + "/"
                                    + name
                                    + "_cobblestone"
                    );


            // --------------------------------------------------------
            // COBBLESTONE
            // --------------------------------------------------------

            blockItem(
                    family.cobblestone.getId().getPath()
            );


            // --------------------------------------------------------
            // COBBLESTONE STAIRS
            // --------------------------------------------------------

            blockItem(
                    family.cobblestoneStairs.getId().getPath()
            );


            // --------------------------------------------------------
            // COBBLESTONE SLAB
            // --------------------------------------------------------

            blockItem(
                    family.cobblestoneSlab.getId().getPath()
            );


            // --------------------------------------------------------
            // COBBLESTONE WALL
            // --------------------------------------------------------

            wallInventory(
                    family.cobblestoneWall.getId().getPath(),
                    cobblestoneTexture
            );


            // --------------------------------------------------------
            // PRESSURE PLATE
            // --------------------------------------------------------

            blockItem(
                    family.pressurePlate.getId().getPath()
            );


            // --------------------------------------------------------
            // BUTTON
            // --------------------------------------------------------

            buttonInventory(
                    family.button.getId().getPath(),
                    stoneTexture
            );
        }
    }


    // ================================================================
    // NORMAL BLOCK ITEM
    // ================================================================

    private void blockItem(String name) {

        getBuilder(name)
                .parent(
                        new net.neoforged.neoforge.client.model.generators.ModelFile.UncheckedModelFile(
                                modLoc("block/" + name)
                        )
                );
    }
}