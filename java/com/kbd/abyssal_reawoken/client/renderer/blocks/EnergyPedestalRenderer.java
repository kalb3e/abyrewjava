
package com.kbd.abyssal_reawoken.client.renderer.blocks;

import com.kbd.abyssal_reawoken.blockentity.EnergyPedestalBlockEntity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

import com.mojang.blaze3d.vertex.PoseStack;

public class EnergyPedestalRenderer
        implements BlockEntityRenderer<EnergyPedestalBlockEntity> {

    public EnergyPedestalRenderer(
            BlockEntityRendererProvider.Context context
    ) {
    }

    @Override
    public void render(
            EnergyPedestalBlockEntity blockEntity,
            float partialTick,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            int packedLight,
            int packedOverlay
    ) {
        ItemStack stack = blockEntity.getHeldItem();

        if (stack.isEmpty() || blockEntity.getLevel() == null) {
            return;
        }

        poseStack.pushPose();

        // Center the item above the pedestal.
        poseStack.translate(0.5D, 0.85D, 0.5D);
        poseStack.scale(0.65F, 0.65F, 0.65F);

        Minecraft.getInstance()
                .getItemRenderer()
                .renderStatic(
                        stack,
                        ItemDisplayContext.FIXED,
                        packedLight,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        bufferSource,
                        blockEntity.getLevel(),
                        (int) blockEntity.getBlockPos().asLong()
                );

        poseStack.popPose();
    }
}
