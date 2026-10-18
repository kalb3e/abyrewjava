package com.kbd.abyssal_reawoken;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(BuiltInRegistries.RECIPE_SERIALIZER, AbyssalReawoken.MODID);

    public static final DeferredRegister<RecipeType<?>> TYPES =
            DeferredRegister.create(BuiltInRegistries.RECIPE_TYPE, AbyssalReawoken.MODID);

    // Registers the deserializer pipeline cleanly
    public static final DeferredHolder<RecipeSerializer<?>, RecipeSerializer<RitualRecipe>> RITUAL_SERIALIZER =
            SERIALIZERS.register("ritual", () -> new RecipeSerializer<>() {
                @Override
                public MapCodec<RitualRecipe> codec() {
                    return RitualRecipe.CODEC;
                }

                @Override
                public StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> streamCodec() {
                    return RitualRecipe.STREAM_CODEC;
                }
            });

    // Registers the recipe lookup type token: "abyssal_reawoken:ritual"
    public static final DeferredHolder<RecipeType<?>, RecipeType<RitualRecipe>> RITUAL_TYPE =
            TYPES.register("ritual", () -> new RecipeType<>() {
                @Override
                public String toString() {
                    return "ritual";
                }
            });

    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
        TYPES.register(eventBus);
    }
}
