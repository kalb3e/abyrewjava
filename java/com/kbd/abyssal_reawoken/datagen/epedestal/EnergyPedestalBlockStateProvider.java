
package com.kbd.abyssal_reawoken.datagen.epedestal;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.blocks.EnergyPedestalBlock;
import com.kbd.abyssal_reawoken.items.EnergyItems;

import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class EnergyPedestalBlockStateProvider extends BlockStateProvider {

    public EnergyPedestalBlockStateProvider(
            PackOutput output,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, AbyssalReawoken.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        getVariantBuilder(EnergyItems.ENERGY_PEDESTAL.get())
                .forAllStates(state -> {
                    Direction facing =
                            state.getValue(BlockStateProperties.HORIZONTAL_FACING);

                    int yRotation = switch (facing) {
                        case SOUTH -> 180;
                        case WEST -> 270;
                        case EAST -> 90;
                        default -> 0;
                    };

                    return ConfiguredModel.builder()
                            .modelFile(
                                    models().getExistingFile(
                                            modLoc("block/epedestal")
                                    )
                            )
                            .rotationY(yRotation)
                            .build();
                });
    }
}
