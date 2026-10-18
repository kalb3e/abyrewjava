package com.kbd.abyssal_reawoken.client.gui;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.penergy.IPotentialEnergy;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

@WailaPlugin
public class AbyssalJadePlugin implements IWailaPlugin {
    @Override
    public void registerClient(IWailaClientRegistration registration) {
        // Registers a tooltip injector for ANY block that implements IPotentialEnergy
        registration.registerBlockComponent(new IBlockComponentProvider() {
            @Override
            public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
                if (accessor.getBlockEntity() instanceof IPotentialEnergy pe) {
                    // Adds a clean info line directly inside Jade's overlay HUD
                    tooltip.add(Component.literal("PE: " + pe.getPE() + " / " + pe.getMaxPE()));
                }
            }

            @Override
            public ResourceLocation getUid() {
                return ResourceLocation.fromNamespaceAndPath(AbyssalReawoken.MODID, "potential_energy");
            }
        }, Block.class);
    }
}
