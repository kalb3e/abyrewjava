
package com.kbd.abyssal_reawoken.client;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.client.renderer.blocks.EnergyPedestalRenderer;
import com.kbd.abyssal_reawoken.items.EnergyItems;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(
        modid = AbyssalReawoken.MODID,
        bus = EventBusSubscriber.Bus.MOD,
        value = Dist.CLIENT
)
public final class EnergyPedestalClientEvents {

    private EnergyPedestalClientEvents() {
    }

    @SubscribeEvent
    public static void registerRenderers(
            EntityRenderersEvent.RegisterRenderers event
    ) {
        event.registerBlockEntityRenderer(
                EnergyItems.ENERGY_PEDESTAL_BE.get(),
                EnergyPedestalRenderer::new
        );
    }
}
