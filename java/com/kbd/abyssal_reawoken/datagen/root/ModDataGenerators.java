package com.kbd.abyssal_reawoken.datagen.root;

import com.kbd.abyssal_reawoken.datagen.stone.*;
import com.kbd.abyssal_reawoken.datagen.wood.*;
import net.minecraft.data.DataProvider;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.List;
import java.util.Set;

public class ModDataGenerators {

    // ============================================================
    // SELECT WHAT DATAGEN GENERATES
    // ============================================================

    private static final DatagenType SELECTED = DatagenType.WOOD;

    private enum DatagenType {
        WOOD,
        STONE
    }

    public static void gatherData(GatherDataEvent event) {

        var generator = event.getGenerator();
        var packOutput = generator.getPackOutput();
        var existingFileHelper = event.getExistingFileHelper();

        // ============================================================
        // CLIENT
        // ============================================================

        if (event.includeClient()) {

            switch (SELECTED) {

                // ====================================================
                // WOOD
                // ====================================================

                case WOOD -> {

                    // Block models
                    generator.addProvider(
                            true,
                            new WoodBlockModelProvider(
                                    packOutput,
                                    existingFileHelper
                            )
                    );

                    // Blockstates
                    generator.addProvider(
                            true,
                            new WoodBlockStateProvider(
                                    packOutput,
                                    existingFileHelper
                            )
                    );

                    // Item models
                    generator.addProvider(
                            true,
                            new WoodItemModelProvider(
                                    packOutput,
                                    existingFileHelper
                            )
                    );
                }

                // ====================================================
                // STONE
                // ====================================================

                case STONE -> {

                    // Block models
                    generator.addProvider(
                            true,
                            new StoneBlockModelProvider(
                                    packOutput,
                                    existingFileHelper
                            )
                    );

                    // Blockstates
                    generator.addProvider(
                            true,
                            new StoneBlockStateProvider(
                                    packOutput,
                                    existingFileHelper
                            )
                    );

                    // Item models
                    generator.addProvider(
                            true,
                            new StoneItemModelProvider(
                                    packOutput,
                                    existingFileHelper
                            )
                    );
                }
            }
        }

        // ============================================================
        // SERVER
        // ============================================================

        if (event.includeServer()) {

            switch (SELECTED) {

                // ====================================================
                // WOOD
                // ====================================================

                case WOOD -> {

                    // Loot tables
                    generator.addProvider(
                            true,
                            (DataProvider.Factory<DataProvider>) output ->
                                    new LootTableProvider(
                                            output,
                                            Set.of(),
                                            List.of(
                                                    new LootTableProvider.SubProviderEntry(
                                                            WoodBlockLootProvider::new,
                                                            LootContextParamSets.BLOCK
                                                    )
                                            ),
                                            event.getLookupProvider()
                                    )
                    );

                    // Tags
                    generator.addProvider(
                            true,
                            new WoodTagsProvider(
                                    packOutput,
                                    event.getLookupProvider(),
                                    existingFileHelper
                            )
                    );
                }

                // ====================================================
                // STONE
                // ====================================================

                case STONE -> {

                    // Loot tables
                    generator.addProvider(
                            true,
                            (DataProvider.Factory<DataProvider>) output ->
                                    new LootTableProvider(
                                            output,
                                            Set.of(),
                                            List.of(
                                                    new LootTableProvider.SubProviderEntry(
                                                            StoneBlockLootProvider::new,
                                                            LootContextParamSets.BLOCK
                                                    )
                                            ),
                                            event.getLookupProvider()
                                    )
                    );

                    // Tags
                    generator.addProvider(
                            true,
                            new StoneTagsProvider(
                                    packOutput,
                                    event.getLookupProvider(),
                                    existingFileHelper
                            )
                    );
                }
            }
        }
    }
}