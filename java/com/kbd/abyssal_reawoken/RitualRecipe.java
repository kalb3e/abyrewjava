package com.kbd.abyssal_reawoken;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import java.util.ArrayList;
import java.util.List;

public class RitualRecipe implements Recipe<RitualRecipeInput> {
    private final int ritualTier;
    private final Ingredient altarInput;
    private final List<Ingredient> pedestalInputs;
    private final int peCost;
    private final ItemStack result;

    public RitualRecipe(int ritualTier, Ingredient altarInput, List<Ingredient> pedestalInputs, int peCost, ItemStack result) {
        this.ritualTier = ritualTier;
        this.altarInput = altarInput;
        this.pedestalInputs = pedestalInputs;
        this.peCost = peCost;
        this.result = result;
    }

    public int getRitualTier() { return ritualTier; }
    public Ingredient getAltarInput() { return altarInput; }
    public List<Ingredient> getPedestalInputs() { return pedestalInputs; }
    public int getPeCost() { return peCost; }
    public ItemStack getResult() { return result; }

    @Override
    public boolean matches(RitualRecipeInput input, Level level) {
        // 1. Verify available PE satisfies the cost criteria
        if (input.availablePE() < this.peCost) return false;

        // 2. Match the center altar slot item
        if (!this.altarInput.test(input.altarItem())) return false;

        // 3. Match the surrounding pedestals order-independently (0-8 range)
        List<ItemStack> worldPedestals = new ArrayList<>(input.pedestalItems().stream().filter(s -> !s.isEmpty()).toList());
        if (worldPedestals.size() != this.pedestalInputs.size()) return false;

        for (Ingredient targetIngredient : this.pedestalInputs) {
            boolean ingredientFound = false;
            for (int i = 0; i < worldPedestals.size(); i++) {
                if (targetIngredient.test(worldPedestals.get(i))) {
                    worldPedestals.remove(i);
                    ingredientFound = true;
                    break;
                }
            }
            if (!ingredientFound) return false;
        }
        return true;
    }

    @Override
    public ItemStack assemble(RitualRecipeInput input, HolderLookup.Provider registries) {
        return this.result.copy();
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

    @Override
    public ItemStack getResultItem(HolderLookup.Provider registries) {
        return this.result;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.RITUAL_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return ModRecipes.RITUAL_TYPE.get();
    }

    // Modern 1.21.1 Disk Loading Mapping Parser (Matches template names)
    public static final MapCodec<RitualRecipe> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            Codec.INT.fieldOf("ritual_tier").forGetter(RitualRecipe::getRitualTier),
            Ingredient.CODEC.fieldOf("altar_input").forGetter(RitualRecipe::getAltarInput),
            Ingredient.CODEC.listOf().fieldOf("pedestal_inputs").forGetter(RitualRecipe::getPedestalInputs),
            Codec.INT.fieldOf("pe_cost").forGetter(RitualRecipe::getPeCost),
            ItemStack.CODEC.fieldOf("result").forGetter(RitualRecipe::getResult)
    ).apply(instance, RitualRecipe::new));

    // Modern 1.21.1 Packets Stream Synchronizer
    public static final StreamCodec<RegistryFriendlyByteBuf, RitualRecipe> STREAM_CODEC = StreamCodec.of(
            (buf, recipe) -> {
                buf.writeInt(recipe.ritualTier);
                Ingredient.CONTENTS_STREAM_CODEC.encode(buf, recipe.altarInput);

                // FIXED: Wrapped the generic list inside an explicit collection call configuration
                ByteBufCodecs.collection(ArrayList::new, Ingredient.CONTENTS_STREAM_CODEC)
                        .encode(buf, new ArrayList<>(recipe.pedestalInputs));

                buf.writeInt(recipe.peCost);
                ItemStack.STREAM_CODEC.encode(buf, recipe.result);
            },
            buf -> {
                int tier = buf.readInt();
                Ingredient altar = Ingredient.CONTENTS_STREAM_CODEC.decode(buf);

                // FIXED: Decoded collection cast directly back to standard interface list array handles
                List<Ingredient> pedestals = new ArrayList<>(ByteBufCodecs.collection(ArrayList::new, Ingredient.CONTENTS_STREAM_CODEC).decode(buf));

                int pe = buf.readInt();
                ItemStack resultStack = ItemStack.STREAM_CODEC.decode(buf);
                return new RitualRecipe(tier, altar, pedestals, pe, resultStack);
            }
    );
}
