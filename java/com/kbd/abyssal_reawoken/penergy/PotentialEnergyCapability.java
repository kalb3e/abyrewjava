package com.kbd.abyssal_reawoken.penergy;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;

public class PotentialEnergyCapability {
    // The unique global ID for your Potential Energy system
    public static final BlockCapability<IPotentialEnergy, Void> BLOCK = BlockCapability.createVoid(
            ResourceLocation.fromNamespaceAndPath(AbyssalReawoken.MODID, "potential_energy"),
            IPotentialEnergy.class
    );
}
