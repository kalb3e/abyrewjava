
package com.kbd.abyssal_reawoken.client.renderer.entity;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.entities.GhoulEntity;
import com.kbd.abyssal_reawoken.fams.GhoulFamily;

import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.monster.Drowned;

public class GhoulRenderer extends DrownedRenderer {

    private static final ResourceLocation FALLBACK_TEXTURE =
            ResourceLocation.withDefaultNamespace(
                    "textures/entity/zombie/drowned.png"
            );

    public GhoulRenderer(EntityRendererProvider.Context context) {
        super(context);
    }

    @Override
    public ResourceLocation getTextureLocation(Drowned entity) {
        if (!(entity instanceof GhoulEntity ghoul)) {
            return FALLBACK_TEXTURE;
        }

        GhoulFamily family = ghoul.getFamily();
        if (family == null) {
            return FALLBACK_TEXTURE;
        }

        return ResourceLocation.fromNamespaceAndPath(
                AbyssalReawoken.MODID,
                "textures/ghouls/" + family.name + "_ghoul.png"
        );
    }
}
