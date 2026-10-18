
package com.kbd.abyssal_reawoken.events;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.items.EnergyItems;

import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber(
        modid = AbyssalReawoken.MODID,
        bus = EventBusSubscriber.Bus.MOD
)
public final class EnergyPedestalCreativeEvents {

    private EnergyPedestalCreativeEvents() {
    }

    @SubscribeEvent
    public static void addEnergyPedestals(
            BuildCreativeModeTabContentsEvent event
    ) {
        if (event.getTabKey() != CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            return;
        }

        // Normal pedestal: 0 PE.
        event.accept(EnergyItems.ENERGY_PEDESTAL_ITEM.get());

        // Fully charged stack: same item ID, with maximum PE stored.
        event.accept(EnergyItems.createFullyChargedStack());
    }
}
