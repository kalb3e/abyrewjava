package com.kbd.abyssal_reawoken.portal;

import com.kbd.abyssal_reawoken.items.SnWItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;

public class DreadlandsPortalEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        Level level = event.getLevel();

        if (level.isClientSide()) {
            return;
        }

        if (event.getItemStack().is(Items.FLINT_AND_STEEL)) {
            BlockPos clickedPos = event.getPos();
            BlockState clickedState = level.getBlockState(clickedPos);

            if (clickedState.is(SnWItems.DREADSTONE.stone.get())) {
                if (DreadlandsPortal.tryCreatePortal(level, clickedPos)) {
                    event.getItemStack().hurtAndBreak(
                            1,
                            event.getEntity(),
                            net.minecraft.world.entity.EquipmentSlot.MAINHAND
                    );

                    event.setCanceled(true);
                }
            }
        }
    }
}