
package com.kbd.abyssal_reawoken.datagen.wood;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.fams.WoodFamily;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.kbd.abyssal_reawoken.items.SnWItems.DARKWOOD;
import static com.kbd.abyssal_reawoken.items.SnWItems.DREADWOOD;

public class WoodItemModelProvider extends ItemModelProvider {

    public WoodItemModelProvider(
            PackOutput output,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, AbyssalReawoken.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        wood(DREADWOOD);
        wood(DARKWOOD);
    }

    private void wood(WoodFamily family) {
        String name = family.planks.getId().getPath()
                .replace("_planks", "");

        String planksTexture =
                name + "/blocks/" + name + "_planks";

        // LOGS AND WOOD
        blockItem(family.log.getId().getPath());
        blockItem(family.strippedLog.getId().getPath());
        blockItem(family.wood.getId().getPath());
        blockItem(family.strippedWood.getId().getPath());

        // PLANKS AND BUILDING BLOCKS
        blockItem(family.planks.getId().getPath());
        blockItem(family.stairs.getId().getPath());
        blockItem(family.slab.getId().getPath());
        blockItem(family.leaves.getId().getPath());

        // FENCE
        fenceInventory(
                family.fence.getId().getPath(),
                modLoc(planksTexture)
        );

        // FENCE GATE
        blockItem(family.fenceGate.getId().getPath());

        // DOOR ITEM
        withExistingParent(
                family.door.getId().getPath(),
                mcLoc("item/generated")
        ).texture(
                "layer0",
                modLoc(name + "/items/" + name + "_door")
        );

        // TRAPDOOR
        blockItem(family.trapdoor.getId().getPath());

        // PRESSURE PLATE
        pressurePlate(
                family.pressurePlate.getId().getPath(),
                modLoc(planksTexture)
        );

        // BUTTON
        buttonInventory(
                family.button.getId().getPath(),
                modLoc(planksTexture)
        );

        // SAPLING
        withExistingParent(
                family.sapling.getId().getPath(),
                mcLoc("item/generated")
        ).texture(
                "layer0",
                modLoc(name + "/blocks/" + name + "_sapling")
        );
    }

    private void blockItem(String name) {
        getBuilder(name)
                .parent(new ModelFile.UncheckedModelFile(
                        modLoc("block/" + name)
                ));
    }
}
