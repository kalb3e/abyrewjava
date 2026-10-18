package com.kbd.abyssal_reawoken.datagen.wood;

import com.kbd.abyssal_reawoken.fams.WoodFamily;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.data.loot.BlockLootSubProvider;

import java.util.Set;

import static com.kbd.abyssal_reawoken.items.SnWItems.DARKWOOD;
import static com.kbd.abyssal_reawoken.items.SnWItems.DREADWOOD;

public class WoodBlockLootProvider extends BlockLootSubProvider {

    private static final float[] NORMAL_LEAVES_SAPLING_CHANCES = {
            0.05F,
            0.0625F,
            0.083333336F,
            0.1F
    };

    public WoodBlockLootProvider(HolderLookup.Provider lookupProvider) {
        super(
                Set.of(),
                FeatureFlags.DEFAULT_FLAGS,
                lookupProvider
        );
    }

    @Override
    protected void generate() {
        wood(DREADWOOD);
        wood(DARKWOOD);
    }

    private void wood(WoodFamily family) {
        // Logs
        dropSelf(family.log.get());
        dropSelf(family.strippedLog.get());

        // Wood
        dropSelf(family.wood.get());
        dropSelf(family.strippedWood.get());

        // Planks and stairs
        dropSelf(family.planks.get());
        dropSelf(family.stairs.get());

        // Slab
        add(family.slab.get(), createSlabItemTable(family.slab.get()));

        // Fence and fence gate
        dropSelf(family.fence.get());
        dropSelf(family.fenceGate.get());

        // Door
        add(family.door.get(), createDoorTable(family.door.get()));

        // Trapdoor
        dropSelf(family.trapdoor.get());

        // Redstone blocks
        dropSelf(family.pressurePlate.get());
        dropSelf(family.button.get());

        // Sapling
        dropSelf(family.sapling.get());

        // Leaves
        add(
                family.leaves.get(),
                createLeavesDrops(
                        family.leaves.get(),
                        family.sapling.get(),
                        NORMAL_LEAVES_SAPLING_CHANCES
                )
        );
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return Set.of(
                // Dreadwood
                DREADWOOD.log.get(),
                DREADWOOD.strippedLog.get(),
                DREADWOOD.wood.get(),
                DREADWOOD.strippedWood.get(),
                DREADWOOD.planks.get(),
                DREADWOOD.stairs.get(),
                DREADWOOD.slab.get(),
                DREADWOOD.fence.get(),
                DREADWOOD.fenceGate.get(),
                DREADWOOD.door.get(),
                DREADWOOD.trapdoor.get(),
                DREADWOOD.pressurePlate.get(),
                DREADWOOD.button.get(),
                DREADWOOD.sapling.get(),
                DREADWOOD.leaves.get(),

                // Darkwood
                DARKWOOD.log.get(),
                DARKWOOD.strippedLog.get(),
                DARKWOOD.wood.get(),
                DARKWOOD.strippedWood.get(),
                DARKWOOD.planks.get(),
                DARKWOOD.stairs.get(),
                DARKWOOD.slab.get(),
                DARKWOOD.fence.get(),
                DARKWOOD.fenceGate.get(),
                DARKWOOD.door.get(),
                DARKWOOD.trapdoor.get(),
                DARKWOOD.pressurePlate.get(),
                DARKWOOD.button.get(),
                DARKWOOD.sapling.get(),
                DARKWOOD.leaves.get()
        );
    }
}