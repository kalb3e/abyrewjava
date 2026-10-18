package com.kbd.abyssal_reawoken.worldgen;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public class ModDimensions {

    public static final ResourceKey<Level> DREADLANDS_LEVEL_KEY =
            ResourceKey.create(
                    Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(
                            "abyssal_reawoken",
                            "dreadlands"
                    )
            );

    public static final ResourceKey<Level> ABYSSAL_LANDS_LEVEL_KEY =
            ResourceKey.create(
                    Registries.DIMENSION,
                    ResourceLocation.fromNamespaceAndPath(
                            "abyssal_reawoken",
                            "abyssal_lands"
                    )
            );
}