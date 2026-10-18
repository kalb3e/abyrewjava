package com.kbd.abyssal_reawoken.datagen.stone;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.fams.StoneFamily;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.kbd.abyssal_reawoken.items.SnWItems.*;

public class StoneBlockModelProvider extends BlockModelProvider {

    public StoneBlockModelProvider(
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

        String name = family.stone.getId().getPath();

        // --------------------------------------------------------
        // TEXTURES
        // --------------------------------------------------------

        String stoneTexture =
                "block/" + name + "/" + name;

        String bricksTexture =
                "block/" + name + "/" + name + "_bricks";

        String crackedBricksTexture =
                "block/" + name + "/cracked_" + name + "_bricks";


        // --------------------------------------------------------
        // STONE
        // --------------------------------------------------------

        cubeAll(
                family.stone.getId().getPath(),
                modLoc(stoneTexture)
        );


        // --------------------------------------------------------
        // STONE STAIRS
        // --------------------------------------------------------

        stairs(
                family.stairs.getId().getPath(),
                modLoc(stoneTexture),
                modLoc(stoneTexture),
                modLoc(stoneTexture)
        );


        // --------------------------------------------------------
        // STONE SLAB
        // --------------------------------------------------------

        slab(
                family.slab.getId().getPath(),
                modLoc(stoneTexture),
                modLoc(stoneTexture),
                modLoc(stoneTexture)
        );


        // --------------------------------------------------------
        // NORMAL STONE WALL
        //
        // Only exists when CBP is OFF.
        // --------------------------------------------------------

        if (family.wall != null) {

            wallPost(
                    family.wall.getId().getPath(),
                    modLoc(stoneTexture)
            );

            wallSide(
                    family.wall.getId().getPath() + "_side",
                    modLoc(stoneTexture)
            );

            wallSideTall(
                    family.wall.getId().getPath() + "_side_tall",
                    modLoc(stoneTexture)
            );
        }


        // --------------------------------------------------------
        // BRICKS
        // --------------------------------------------------------

        cubeAll(
                family.bricks.getId().getPath(),
                modLoc(bricksTexture)
        );


        // --------------------------------------------------------
        // CRACKED BRICKS
        // --------------------------------------------------------

        cubeAll(
                family.crackedBricks.getId().getPath(),
                modLoc(crackedBricksTexture)
        );


        // --------------------------------------------------------
        // BRICK STAIRS
        // --------------------------------------------------------

        stairs(
                family.brickStairs.getId().getPath(),
                modLoc(bricksTexture),
                modLoc(bricksTexture),
                modLoc(bricksTexture)
        );


        // --------------------------------------------------------
        // BRICK SLAB
        // --------------------------------------------------------

        slab(
                family.brickSlab.getId().getPath(),
                modLoc(bricksTexture),
                modLoc(bricksTexture),
                modLoc(bricksTexture)
        );


        // --------------------------------------------------------
        // BRICK WALL
        // --------------------------------------------------------

        wallPost(
                family.brickWall.getId().getPath(),
                modLoc(bricksTexture)
        );

        wallSide(
                family.brickWall.getId().getPath() + "_side",
                modLoc(bricksTexture)
        );

        wallSideTall(
                family.brickWall.getId().getPath() + "_side_tall",
                modLoc(bricksTexture)
        );


        // --------------------------------------------------------
        // BRICK FENCE
        // --------------------------------------------------------

        fencePost(
                family.brickFence.getId().getPath(),
                modLoc(bricksTexture)
        );

        fenceSide(
                family.brickFence.getId().getPath() + "_side",
                modLoc(bricksTexture)
        );


        // --------------------------------------------------------
        // CHISELED BRICKS
        // --------------------------------------------------------

        cubeAll(
                family.chiseledBricks.getId().getPath(),
                modLoc(bricksTexture)
        );


        // ========================================================
        // CBP
        // ========================================================

        if (family.cobblestone != null) {

            String cobblestoneTexture =
                    "block/" + name + "/" + name + "_cobblestone";


            // ----------------------------------------------------
            // COBBLESTONE
            // ----------------------------------------------------

            cubeAll(
                    family.cobblestone.getId().getPath(),
                    modLoc(cobblestoneTexture)
            );


            // ----------------------------------------------------
            // COBBLESTONE STAIRS
            // ----------------------------------------------------

            stairs(
                    family.cobblestoneStairs.getId().getPath(),
                    modLoc(cobblestoneTexture),
                    modLoc(cobblestoneTexture),
                    modLoc(cobblestoneTexture)
            );


            // ----------------------------------------------------
            // COBBLESTONE SLAB
            // ----------------------------------------------------

            slab(
                    family.cobblestoneSlab.getId().getPath(),
                    modLoc(cobblestoneTexture),
                    modLoc(cobblestoneTexture),
                    modLoc(cobblestoneTexture)
            );


            // ----------------------------------------------------
            // COBBLESTONE WALL
            // ----------------------------------------------------

            wallPost(
                    family.cobblestoneWall.getId().getPath(),
                    modLoc(cobblestoneTexture)
            );

            wallSide(
                    family.cobblestoneWall.getId().getPath() + "_side",
                    modLoc(cobblestoneTexture)
            );

            wallSideTall(
                    family.cobblestoneWall.getId().getPath() + "_side_tall",
                    modLoc(cobblestoneTexture)
            );


            // ----------------------------------------------------
            // PRESSURE PLATE
            // ----------------------------------------------------

            pressurePlate(
                    family.pressurePlate.getId().getPath(),
                    modLoc(stoneTexture)
            );


            // ----------------------------------------------------
            // BUTTON
            // ----------------------------------------------------

            button(
                    family.button.getId().getPath(),
                    modLoc(stoneTexture)
            );

            buttonPressed(
                    family.button.getId().getPath() + "_pressed",
                    modLoc(stoneTexture)
            );
        }
    }
}