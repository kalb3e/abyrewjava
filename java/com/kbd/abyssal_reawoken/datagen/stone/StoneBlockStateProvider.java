package com.kbd.abyssal_reawoken.datagen.stone;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.fams.StoneFamily;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.kbd.abyssal_reawoken.items.SnWItems.*;

public class StoneBlockStateProvider extends BlockStateProvider {

    public StoneBlockStateProvider(
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
    protected void registerStatesAndModels() {
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

        // ============================================================
        // MODEL REFERENCES
        // ============================================================

        ModelFile stone =
                model(family.stone.getId().getPath());

        ModelFile stairs =
                model(family.stairs.getId().getPath());

        ModelFile slab =
                model(family.slab.getId().getPath());

        ModelFile slabTop =
                model(family.slab.getId().getPath() + "_top");


        ModelFile bricks =
                model(family.bricks.getId().getPath());

        ModelFile crackedBricks =
                model(family.crackedBricks.getId().getPath());

        ModelFile brickStairs =
                model(family.brickStairs.getId().getPath());

        ModelFile brickSlab =
                model(family.brickSlab.getId().getPath());

        ModelFile brickSlabTop =
                model(family.brickSlab.getId().getPath() + "_top");

        ModelFile brickWall =
                model(family.brickWall.getId().getPath());

        ModelFile brickWallSide =
                model(family.brickWall.getId().getPath() + "_side");

        ModelFile brickWallSideTall =
                model(family.brickWall.getId().getPath() + "_side_tall");

        ModelFile brickFence =
                model(family.brickFence.getId().getPath());

        ModelFile brickFenceSide =
                model(family.brickFence.getId().getPath() + "_side");

        ModelFile chiseledBricks =
                model(family.chiseledBricks.getId().getPath());


        // ============================================================
        // STONE
        // ============================================================

        simpleState(
                family.stone.get(),
                stone
        );


        // ============================================================
        // STONE STAIRS
        // ============================================================

        stairsState(
                family.stairs.get(),
                stairs
        );


        // ============================================================
        // STONE SLAB
        // ============================================================

        slabState(
                family.slab.get(),
                slab,
                slabTop,
                stone
        );


        // ============================================================
        // NORMAL STONE WALL
        //
        // ONLY EXISTS WHEN CBP IS OFF
        // ============================================================

        if (family.wall != null) {

            ModelFile wall =
                    model(family.wall.getId().getPath());

            ModelFile wallSide =
                    model(family.wall.getId().getPath() + "_side");

            ModelFile wallSideTall =
                    model(family.wall.getId().getPath() + "_side_tall");

            wallState(
                    family.wall.get(),
                    wall,
                    wallSide,
                    wallSideTall
            );
        }


        // ============================================================
        // BRICKS
        // ============================================================

        simpleState(
                family.bricks.get(),
                bricks
        );


        // ============================================================
        // CRACKED BRICKS
        // ============================================================

        simpleState(
                family.crackedBricks.get(),
                crackedBricks
        );


        // ============================================================
        // BRICK STAIRS
        // ============================================================

        stairsState(
                family.brickStairs.get(),
                brickStairs
        );


        // ============================================================
        // BRICK SLAB
        // ============================================================

        slabState(
                family.brickSlab.get(),
                brickSlab,
                brickSlabTop,
                bricks
        );


        // ============================================================
        // BRICK WALL
        // ============================================================

        wallState(
                family.brickWall.get(),
                brickWall,
                brickWallSide,
                brickWallSideTall
        );


        // ============================================================
        // BRICK FENCE
        // ============================================================

        fenceState(
                family.brickFence.get(),
                brickFence,
                brickFenceSide
        );


        // ============================================================
        // CHISELED BRICKS
        // ============================================================

        simpleState(
                family.chiseledBricks.get(),
                chiseledBricks
        );


        // ============================================================
        // CBP
        // ============================================================

        if (family.cobblestone != null) {

            ModelFile cobblestone =
                    model(family.cobblestone.getId().getPath());

            ModelFile cobblestoneStairs =
                    model(family.cobblestoneStairs.getId().getPath());

            ModelFile cobblestoneSlab =
                    model(family.cobblestoneSlab.getId().getPath());

            ModelFile cobblestoneSlabTop =
                    model(family.cobblestoneSlab.getId().getPath() + "_top");

            ModelFile cobblestoneWall =
                    model(family.cobblestoneWall.getId().getPath());

            ModelFile cobblestoneWallSide =
                    model(family.cobblestoneWall.getId().getPath() + "_side");

            ModelFile cobblestoneWallSideTall =
                    model(family.cobblestoneWall.getId().getPath() + "_side_tall");

            ModelFile pressurePlate =
                    model(family.pressurePlate.getId().getPath());

            ModelFile pressurePlateDown =
                    model(family.pressurePlate.getId().getPath() + "_down");

            ModelFile button =
                    model(family.button.getId().getPath());

            ModelFile buttonPressed =
                    model(family.button.getId().getPath() + "_pressed");


            // --------------------------------------------------------
            // COBBLESTONE
            // --------------------------------------------------------

            simpleState(
                    family.cobblestone.get(),
                    cobblestone
            );


            // --------------------------------------------------------
            // COBBLESTONE STAIRS
            // --------------------------------------------------------

            stairsState(
                    family.cobblestoneStairs.get(),
                    cobblestoneStairs
            );


            // --------------------------------------------------------
            // COBBLESTONE SLAB
            // --------------------------------------------------------

            slabState(
                    family.cobblestoneSlab.get(),
                    cobblestoneSlab,
                    cobblestoneSlabTop,
                    cobblestone
            );


            // --------------------------------------------------------
            // COBBLESTONE WALL
            // --------------------------------------------------------

            wallState(
                    family.cobblestoneWall.get(),
                    cobblestoneWall,
                    cobblestoneWallSide,
                    cobblestoneWallSideTall
            );


            // --------------------------------------------------------
            // PRESSURE PLATE
            // --------------------------------------------------------

            pressurePlateState(
                    family.pressurePlate.get(),
                    pressurePlate,
                    pressurePlateDown
            );


            // --------------------------------------------------------
            // BUTTON
            // --------------------------------------------------------

            buttonState(
                    family.button.get(),
                    button,
                    buttonPressed
            );
        }
    }


    // ================================================================
    // MODEL REFERENCES
    // ================================================================

    private ModelFile model(String path) {

        return new ModelFile.UncheckedModelFile(
                modLoc("block/" + path)
        );
    }


    // ================================================================
    // SIMPLE BLOCK
    // ================================================================

    private void simpleState(
            Block block,
            ModelFile model
    ) {

        getVariantBuilder(block)
                .forAllStates(state ->
                        ConfiguredModel.builder()
                                .modelFile(model)
                                .build()
                );
    }


    // ================================================================
    // STAIRS
    // ================================================================

    private void stairsState(
            Block block,
            ModelFile model
    ) {

        getVariantBuilder(block)
                .forAllStates(state ->
                        ConfiguredModel.builder()
                                .modelFile(model)
                                .rotationX(stairsRotationX(state))
                                .rotationY(stairsRotationY(state))
                                .uvLock(true)
                                .build()
                );
    }


    // ================================================================
    // SLAB
    // ================================================================

    private void slabState(
            Block block,
            ModelFile slab,
            ModelFile slabTop,
            ModelFile doubleSlab
    ) {

        getVariantBuilder(block)
                .forAllStates(state -> {

                    SlabType type =
                            state.getValue(
                                    BlockStateProperties.SLAB_TYPE
                            );

                    if (type == SlabType.TOP) {

                        return ConfiguredModel.builder()
                                .modelFile(slabTop)
                                .build();
                    }

                    if (type == SlabType.DOUBLE) {

                        return ConfiguredModel.builder()
                                .modelFile(doubleSlab)
                                .build();
                    }

                    return ConfiguredModel.builder()
                            .modelFile(slab)
                            .build();
                });
    }


    // ================================================================
    // WALL
    // ================================================================

    private void wallState(
            Block block,
            ModelFile post,
            ModelFile side,
            ModelFile sideTall
    ) {

        getMultipartBuilder(block)

                // Post
                .part()
                .modelFile(post)
                .addModel()
                .end()

                // North
                .part()
                .modelFile(side)
                .addModel()
                .condition(
                        BlockStateProperties.NORTH,
                        true
                )
                .end()

                // East
                .part()
                .modelFile(side)
                .rotationY(90)
                .addModel()
                .condition(
                        BlockStateProperties.EAST,
                        true
                )
                .end()

                // South
                .part()
                .modelFile(side)
                .rotationY(180)
                .addModel()
                .condition(
                        BlockStateProperties.SOUTH,
                        true
                )
                .end()

                // West
                .part()
                .modelFile(side)
                .rotationY(270)
                .addModel()
                .condition(
                        BlockStateProperties.WEST,
                        true
                )
                .end();
    }


    // ================================================================
    // FENCE
    // ================================================================

    private void fenceState(
            Block block,
            ModelFile post,
            ModelFile side
    ) {

        getMultipartBuilder(block)

                // Post
                .part()
                .modelFile(post)
                .addModel()
                .end()

                // North
                .part()
                .modelFile(side)
                .addModel()
                .condition(
                        BlockStateProperties.NORTH,
                        true
                )
                .end()

                // East
                .part()
                .modelFile(side)
                .rotationY(90)
                .addModel()
                .condition(
                        BlockStateProperties.EAST,
                        true
                )
                .end()

                // South
                .part()
                .modelFile(side)
                .rotationY(180)
                .addModel()
                .condition(
                        BlockStateProperties.SOUTH,
                        true
                )
                .end()

                // West
                .part()
                .modelFile(side)
                .rotationY(270)
                .addModel()
                .condition(
                        BlockStateProperties.WEST,
                        true
                )
                .end();
    }


    // ================================================================
    // PRESSURE PLATE
    // ================================================================

    private void pressurePlateState(
            Block block,
            ModelFile normal,
            ModelFile pressed
    ) {

        getVariantBuilder(block)
                .forAllStates(state -> {

                    boolean powered =
                            state.getValue(
                                    BlockStateProperties.POWERED
                            );

                    return ConfiguredModel.builder()
                            .modelFile(
                                    powered
                                            ? pressed
                                            : normal
                            )
                            .build();
                });
    }


    // ================================================================
    // BUTTON
    // ================================================================

    private void buttonState(
            Block block,
            ModelFile normal,
            ModelFile pressed
    ) {

        getVariantBuilder(block)
                .forAllStates(state -> {

                    boolean powered =
                            state.getValue(
                                    BlockStateProperties.POWERED
                            );

                    Direction facing =
                            state.getValue(
                                    BlockStateProperties.HORIZONTAL_FACING
                            );

                    var face =
                            state.getValue(
                                    BlockStateProperties.ATTACH_FACE
                            );

                    int x;
                    int y;

                    switch (face) {

                        case FLOOR -> {
                            x = 0;
                            y = facing.get2DDataValue() * 90;
                        }

                        case CEILING -> {
                            x = 180;
                            y = facing.get2DDataValue() * 90;
                        }

                        case WALL -> {
                            x = 90;
                            y = facing.get2DDataValue() * 90 + 180;
                        }

                        default -> {
                            x = 0;
                            y = 0;
                        }
                    }

                    return ConfiguredModel.builder()
                            .modelFile(
                                    powered
                                            ? pressed
                                            : normal
                            )
                            .rotationX(x)
                            .rotationY(y)
                            .uvLock(true)
                            .build();
                });
    }


    // ================================================================
    // STAIRS ROTATION
    // ================================================================

    private int stairsRotationX(BlockState state) {

        return switch (
                state.getValue(
                        BlockStateProperties.HALF
                )
                ) {

            case TOP -> 180;

            case BOTTOM -> 0;
        };
    }


    private int stairsRotationY(BlockState state) {

        return switch (
                state.getValue(
                        BlockStateProperties.HORIZONTAL_FACING
                )
                ) {

            case EAST -> 0;

            case SOUTH -> 90;

            case WEST -> 180;

            case NORTH -> 270;

            default -> 0;
        };
    }
}