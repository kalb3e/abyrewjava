
package com.kbd.abyssal_reawoken.datagen.epedestal;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.items.EnergyItems;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class EnergyPedestalItemModelProvider extends ItemModelProvider {

    public EnergyPedestalItemModelProvider(
            PackOutput output,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, AbyssalReawoken.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(
                "energy_pedestal",
                modLoc("block/epedestal")
        );
    }
}
