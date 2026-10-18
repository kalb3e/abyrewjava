
package com.kbd.abyssal_reawoken.ritual;

import com.kbd.abyssal_reawoken.AbyssalReawoken;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

@EventBusSubscriber(
        modid = AbyssalReawoken.MODID,
        bus = EventBusSubscriber.Bus.GAME
)
public final class RitualMaterialInteraction {

    private RitualMaterialInteraction() {}

    @SubscribeEvent
    public static void onRightClickBlock(
            PlayerInteractEvent.RightClickBlock event
    ) {
        Player player = event.getEntity();

        if (!player.isShiftKeyDown()
                || player.level().isClientSide()
                || !(player.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        InteractionHand hand = event.getHand();

        if (!RitualStructureTransformations.tryTransform(
                serverLevel,
                event.getPos(),
                player,
                hand
        )) {
            return;
        }

        event.setCanceled(true);
        event.setCancellationResult(InteractionResult.SUCCESS);
    }
}
