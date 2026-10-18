package com.kbd.abyssal_reawoken;

import net.minecraft.network.chat.Component;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;

@EventBusSubscriber(modid = AbyssalReawoken.MODID)
public class ModItemTooltipEvents {

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        // Fetch your custom data component directly from the target stack
        Integer peValue = event.getItemStack().get(ModDataComponents.POTENTIAL_ENERGY.get());

        if (peValue != null) {
            event.getToolTip().add(Component.literal("Potential Energy: " + peValue + " PE")
                    .withStyle(net.minecraft.ChatFormatting.GRAY));
        }
    }
}
