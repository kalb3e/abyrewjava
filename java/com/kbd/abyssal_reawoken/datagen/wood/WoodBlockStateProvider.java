
package com.kbd.abyssal_reawoken.datagen.wood;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.fams.WoodFamily;

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

import static com.kbd.abyssal_reawoken.items.SnWItems.DARKWOOD;
import static com.kbd.abyssal_reawoken.items.SnWItems.DREADWOOD;

public class WoodBlockStateProvider extends BlockStateProvider {

    public WoodBlockStateProvider(
            PackOutput output,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, AbyssalReawoken.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        wood(DREADWOOD);
        wood(DARKWOOD);
    }

    private void wood(WoodFamily family) {
        ModelFile log = model(family.log.getId().getPath());
        ModelFile strippedLog = model(family.strippedLog.getId().getPath());
        ModelFile wood = model(family.wood.getId().getPath());
        ModelFile strippedWood = model(family.strippedWood.getId().getPath());
        ModelFile planks = model(family.planks.getId().getPath());
        ModelFile stairs = model(family.stairs.getId().getPath());
        ModelFile slab = model(family.slab.getId().getPath());
        ModelFile slabTop = model(family.slab.getId().getPath() + "_top");

        ModelFile fencePost = model(family.fence.getId().getPath());
        ModelFile fenceSide = model(family.fence.getId().getPath() + "_side");

        ModelFile fenceGate = model(family.fenceGate.getId().getPath());
        ModelFile fenceGateOpen = model(family.fenceGate.getId().getPath() + "_open");
        ModelFile fenceGateWall = model(family.fenceGate.getId().getPath() + "_wall");
        ModelFile fenceGateWallOpen = model(family.fenceGate.getId().getPath() + "_wall_open");

        ModelFile pressurePlate = model(family.pressurePlate.getId().getPath());
        ModelFile pressurePlateDown = model(family.pressurePlate.getId().getPath() + "_down");

        ModelFile button = model(family.button.getId().getPath());
        ModelFile buttonPressed = model(family.button.getId().getPath() + "_pressed");

        ModelFile sapling = model(family.sapling.getId().getPath());
        ModelFile leaves = model(family.leaves.getId().getPath());

        ModelFile doorBottomLeft = model(family.door.getId().getPath() + "_bottom_left");
        ModelFile doorBottomRight = model(family.door.getId().getPath() + "_bottom_right");
        ModelFile doorTopLeft = model(family.door.getId().getPath() + "_top_left");
        ModelFile doorTopRight = model(family.door.getId().getPath() + "_top_right");

        ModelFile trapdoorBottom = model(family.trapdoor.getId().getPath() + "_bottom");
        ModelFile trapdoorTop = model(family.trapdoor.getId().getPath() + "_top");
        ModelFile trapdoorOpen = model(family.trapdoor.getId().getPath() + "_open");

        // SIMPLE BLOCKS
        simpleState(family.planks.get(), planks);
        simpleState(family.wood.get(), wood);
        simpleState(family.strippedWood.get(), strippedWood);
        simpleState(family.sapling.get(), sapling);
        simpleState(family.leaves.get(), leaves);

        // LOGS
        axisState(family.log.get(), log);
        axisState(family.strippedLog.get(), strippedLog);

        // STAIRS
        getVariantBuilder(family.stairs.get()).forAllStates(state ->
                ConfiguredModel.builder()
                        .modelFile(stairs)
                        .rotationX(stairsRotationX(state))
                        .rotationY(stairsRotationY(state))
                        .uvLock(true)
                        .build()
        );

        // SLABS
        getVariantBuilder(family.slab.get()).forAllStates(state -> {
            SlabType type = state.getValue(BlockStateProperties.SLAB_TYPE);

            if (type == SlabType.TOP) {
                return ConfiguredModel.builder().modelFile(slabTop).build();
            }

            if (type == SlabType.DOUBLE) {
                return ConfiguredModel.builder().modelFile(planks).build();
            }

            return ConfiguredModel.builder().modelFile(slab).build();
        });

        // FENCES
        getMultipartBuilder(family.fence.get())
                .part().modelFile(fencePost).addModel().end()
                .part().modelFile(fenceSide).addModel()
                .condition(BlockStateProperties.NORTH, true).end()
                .part().modelFile(fenceSide).rotationY(90).addModel()
                .condition(BlockStateProperties.EAST, true).end()
                .part().modelFile(fenceSide).rotationY(180).addModel()
                .condition(BlockStateProperties.SOUTH, true).end()
                .part().modelFile(fenceSide).rotationY(270).addModel()
                .condition(BlockStateProperties.WEST, true).end();

        // FENCE GATES
        getVariantBuilder(family.fenceGate.get()).forAllStates(state -> {
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            boolean open = state.getValue(BlockStateProperties.OPEN);
            boolean inWall = state.getValue(BlockStateProperties.IN_WALL);

            ModelFile selected = inWall
                    ? (open ? fenceGateWallOpen : fenceGateWall)
                    : (open ? fenceGateOpen : fenceGate);

            return ConfiguredModel.builder()
                    .modelFile(selected)
                    .rotationY(facing.get2DDataValue() * 90)
                    .build();
        });

        // PRESSURE PLATES
        getVariantBuilder(family.pressurePlate.get()).forAllStates(state -> {
            boolean powered = state.getValue(BlockStateProperties.POWERED);

            return ConfiguredModel.builder()
                    .modelFile(powered ? pressurePlateDown : pressurePlate)
                    .build();
        });

        // BUTTONS
        getVariantBuilder(family.button.get()).forAllStates(state -> {
            boolean powered = state.getValue(BlockStateProperties.POWERED);
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);
            var face = state.getValue(BlockStateProperties.ATTACH_FACE);

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
                    .modelFile(powered ? buttonPressed : button)
                    .rotationX(x)
                    .rotationY(y)
                    .uvLock(true)
                    .build();
        });

        // DOORS
        getVariantBuilder(family.door.get()).forAllStates(state -> {
            boolean upper = state.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF)
                    == net.minecraft.world.level.block.state.properties.DoubleBlockHalf.UPPER;
            boolean right = state.getValue(BlockStateProperties.DOOR_HINGE)
                    == net.minecraft.world.level.block.state.properties.DoorHingeSide.RIGHT;
            boolean open = state.getValue(BlockStateProperties.OPEN);
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            ModelFile selected;

            if (!upper) {
                selected = right ? doorBottomRight : doorBottomLeft;
            } else {
                selected = right ? doorTopRight : doorTopLeft;
            }

            int rotation = facing.get2DDataValue() * 90;

            if (open) {
                rotation = (rotation + (right ? -90 : 90) + 360) % 360;
            }

            return ConfiguredModel.builder()
                    .modelFile(selected)
                    .rotationY(rotation)
                    .build();
        });

        // TRAPDOORS
        getVariantBuilder(family.trapdoor.get()).forAllStates(state -> {
            boolean open = state.getValue(BlockStateProperties.OPEN);
            boolean top = state.getValue(BlockStateProperties.HALF)
                    == net.minecraft.world.level.block.state.properties.Half.TOP;
            Direction facing = state.getValue(BlockStateProperties.HORIZONTAL_FACING);

            ModelFile selected = open
                    ? trapdoorOpen
                    : (top ? trapdoorTop : trapdoorBottom);

            int rotation = facing.get2DDataValue() * 90;

            return ConfiguredModel.builder()
                    .modelFile(selected)
                    .rotationY(rotation)
                    .build();
        });
    }

    private ModelFile model(String path) {
        return new ModelFile.UncheckedModelFile(modLoc("block/" + path));
    }

    private void simpleState(Block block, ModelFile model) {
        getVariantBuilder(block).forAllStates(state ->
                ConfiguredModel.builder().modelFile(model).build()
        );
    }

    private void axisState(Block block, ModelFile model) {
        getVariantBuilder(block).forAllStates(state -> {
            Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);

            return switch (axis) {
                case X -> ConfiguredModel.builder()
                        .modelFile(model).rotationX(90).rotationY(90).build();
                case Y -> ConfiguredModel.builder()
                        .modelFile(model).build();
                case Z -> ConfiguredModel.builder()
                        .modelFile(model).rotationX(90).build();
            };
        });
    }

    private int stairsRotationX(BlockState state) {
        return switch (state.getValue(BlockStateProperties.HALF)) {
            case TOP -> 180;
            case BOTTOM -> 0;
        };
    }

    private int stairsRotationY(BlockState state) {
        return switch (state.getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case EAST -> 0;
            case SOUTH -> 90;
            case WEST -> 180;
            case NORTH -> 270;
            default -> 0;
        };
    }
}
