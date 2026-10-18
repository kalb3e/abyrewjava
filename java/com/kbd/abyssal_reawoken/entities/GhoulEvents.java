
package com.kbd.abyssal_reawoken.entities;

import com.kbd.abyssal_reawoken.fams.GhoulFamily;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;

public class GhoulEvents {

    @SubscribeEvent
    public void onLivingDeath(LivingDeathEvent event) {
        if (!(event.getSource().getEntity() instanceof GhoulEntity ghoul)) {
            return;
        }

        if (!(ghoul.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        GhoulFamily family = ghoul.getFamily();
        if (family == null) {
            return;
        }

        if (family.getKillReplacement() == null
                || family.getKillVictims().isEmpty()) {
            return;
        }

        ResourceLocation victimId =
                BuiltInRegistries.ENTITY_TYPE.getKey(
                        event.getEntity().getType()
                );

        if (!family.getKillVictims().contains(victimId)) {
            return;
        }

        if (serverLevel.getRandom().nextFloat()
                >= family.getKillChance()) {
            return;
        }

        GhoulEntity.replaceEntity(
                serverLevel,
                event.getEntity(),
                family.getKillReplacement()
        );
    }
}
