
package com.kbd.abyssal_reawoken.fams;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.entities.GhoulEntity;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GhoulFamily {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(
                    Registries.ENTITY_TYPE,
                    AbyssalReawoken.MODID
            );

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(AbyssalReawoken.MODID);

    private static final List<GhoulFamily> FAMILIES = new ArrayList<>();

    public final String name;
    public final float sizeScale;
    public final float healthScale;
    public final float damageScale;

    public final DeferredHolder<EntityType<?>, EntityType<GhoulEntity>> entity;
    public final DeferredHolder<Item, Item> flesh;

    private final List<ResourceLocation> additionalTargets = new ArrayList<>();
    private final List<ResourceLocation> killVictims = new ArrayList<>();

    private ResourceLocation killReplacement;
    private float killChance;

    public GhoulFamily(
            String name,
            float sizeScale,
            float healthScale,
            float damageScale
    ) {
        this.name = Objects.requireNonNull(name);

        if (name.isBlank()) {
            throw new IllegalArgumentException(
                    "Ghoul family name cannot be blank."
            );
        }

        if (sizeScale <= 0.0F
                || healthScale <= 0.0F
                || damageScale < 0.0F) {
            throw new IllegalArgumentException(
                    "Invalid ghoul family multipliers."
            );
        }

        this.sizeScale = sizeScale;
        this.healthScale = healthScale;
        this.damageScale = damageScale;

        String entityName = name.equals("ghoul")
                ? "ghoul"
                : name + "_ghoul";

        String fleshName = name.equals("ghoul")
                ? "ghoul_flesh"
                : name + "_ghoul_flesh";

        entity = ENTITIES.register(
                entityName,
                () -> EntityType.Builder.of(
                                GhoulEntity::new,
                                MobCategory.MONSTER
                        )
                        .sized(0.6F, 1.95F)
                        .build(entityName)
        );

        flesh = ITEMS.registerSimpleItem(fleshName);

        FAMILIES.add(this);
    }

    public GhoulFamily targets(String... entityIds) {
        for (String entityId : entityIds) {
            additionalTargets.add(ResourceLocation.parse(entityId));
        }

        return this;
    }

    public List<ResourceLocation> getAdditionalTargets() {
        return List.copyOf(additionalTargets);
    }

    public GhoulFamily transformOnKill(
            String replacementEntityId,
            float chance,
            String... victimEntityIds
    ) {
        if (chance < 0.0F || chance > 1.0F) {
            throw new IllegalArgumentException(
                    "Chance must be between 0 and 1."
            );
        }

        if (victimEntityIds.length == 0) {
            throw new IllegalArgumentException(
                    "At least one victim entity must be provided."
            );
        }

        killVictims.clear();

        for (String victimEntityId : victimEntityIds) {
            killVictims.add(ResourceLocation.parse(victimEntityId));
        }

        killReplacement = ResourceLocation.parse(replacementEntityId);
        killChance = chance;

        return this;
    }

    public List<ResourceLocation> getKillVictims() {
        return List.copyOf(killVictims);
    }

    public ResourceLocation getKillReplacement() {
        return killReplacement;
    }

    public float getKillChance() {
        return killChance;
    }

    public static List<GhoulFamily> getFamilies() {
        return List.copyOf(FAMILIES);
    }

    public static GhoulFamily getFamily(EntityType<?> type) {
        for (GhoulFamily family : FAMILIES) {
            if (family.entity.get() == type) {
                return family;
            }
        }

        return null;
    }

    public static void registerAttributes(
            EntityAttributeCreationEvent event
    ) {
        for (GhoulFamily family : FAMILIES) {
            AttributeSupplier attributes =
                    GhoulEntity.createAttributes(family).build();

            event.put(family.entity.get(), attributes);
        }
    }

    public static void register(IEventBus eventBus) {
        ENTITIES.register(eventBus);
        ITEMS.register(eventBus);
    }
}
