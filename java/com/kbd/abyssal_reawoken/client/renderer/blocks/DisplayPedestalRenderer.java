package com.kbd.abyssal_reawoken.client.renderer.blocks;

import com.kbd.abyssal_reawoken.blockentity.DisplayPedestalBlockEntity;
import com.kbd.abyssal_reawoken.blockentity.RitualAltarBlockEntity;
import com.kbd.abyssal_reawoken.blockentity.RitualPedestalBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;

// ➡️ FIXED: Changed from <DisplayPedestalBlockEntity> to <BlockEntity> using generic class wildcards
public class DisplayPedestalRenderer<T extends BlockEntity> implements BlockEntityRenderer<T> {
    private final ItemRenderer itemRenderer;

    public DisplayPedestalRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(T entity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int combinedLight, int combinedOverlay) {
        ItemStack stack = ItemStack.EMPTY;

        // ➡️ Dynamically query the stack depending on what type of block entity is placed down in the world
        if (entity instanceof DisplayPedestalBlockEntity pedestal) {
            stack = pedestal.getHeldItem();
        } else if (entity instanceof RitualPedestalBlockEntity tierPedestal) {
            stack = tierPedestal.getHeldItem();
        } else if (entity instanceof RitualAltarBlockEntity altar) {
            stack = altar.getHeldItem();
        }

        if (stack.isEmpty()) {
            return;
        }

        poseStack.pushPose();
        poseStack.translate(0.5D, 1.15D, 0.5D);
        poseStack.scale(0.9F, 0.9F, 0.9F);

        long time = entity.getLevel() != null ? entity.getLevel().getGameTime() : 0;
        float angle = (time + partialTick) * 2.0F;
        poseStack.mulPose(Axis.YP.rotationDegrees(angle));

        this.itemRenderer.renderStatic(stack, ItemDisplayContext.GROUND, combinedLight, combinedOverlay, poseStack, buffer, entity.getLevel(), 0);
        poseStack.popPose();
    }
}
