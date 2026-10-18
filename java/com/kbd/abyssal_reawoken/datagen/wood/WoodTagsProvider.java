package com.kbd.abyssal_reawoken.datagen.wood;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.fams.WoodFamily;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

import static com.kbd.abyssal_reawoken.items.SnWItems.DARKWOOD;
import static com.kbd.abyssal_reawoken.items.SnWItems.DREADWOOD;

public class WoodTagsProvider extends BlockTagsProvider {

    public WoodTagsProvider(
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
        wood(DREADWOOD);
        wood(DARKWOOD);
    }

    private void wood(WoodFamily family) {

        // Logs
        tag(BlockTags.LOGS)
                .add(
                        family.log.get(),
                        family.strippedLog.get(),
                        family.wood.get(),
                        family.strippedWood.get()
                );

        tag(BlockTags.LOGS_THAT_BURN)
                .add(
                        family.log.get(),
                        family.strippedLog.get(),
                        family.wood.get(),
                        family.strippedWood.get()
                );

        // Axe mineable
        tag(BlockTags.MINEABLE_WITH_AXE)
                .add(
                        family.log.get(),
                        family.strippedLog.get(),
                        family.wood.get(),
                        family.strippedWood.get(),
                        family.planks.get(),
                        family.stairs.get(),
                        family.slab.get(),
                        family.fence.get(),
                        family.pressurePlate.get(),
                        family.button.get()
                );

        // Wooden stairs
        tag(BlockTags.WOODEN_STAIRS)
                .add(family.stairs.get());

        // Wooden slabs
        tag(BlockTags.WOODEN_SLABS)
                .add(family.slab.get());

        // Wooden fences
        tag(BlockTags.WOODEN_FENCES)
                .add(family.fence.get());

        // Wooden buttons
        tag(BlockTags.WOODEN_BUTTONS)
                .add(family.button.get());

        // Wooden pressure plates
        tag(BlockTags.WOODEN_PRESSURE_PLATES)
                .add(family.pressurePlate.get());

        // Saplings
        tag(BlockTags.SAPLINGS)
                .add(family.sapling.get());

        // Leaves
        tag(BlockTags.LEAVES)
                .add(family.leaves.get());

        // Mineable with hoe
        tag(BlockTags.MINEABLE_WITH_HOE)
                .add(family.leaves.get());
    }
}