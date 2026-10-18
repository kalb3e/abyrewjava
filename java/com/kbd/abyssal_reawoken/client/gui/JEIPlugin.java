package com.kbd.abyssal_reawoken.client.gui;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.ModRecipes;
import com.kbd.abyssal_reawoken.RitualRecipe;
import com.kbd.abyssal_reawoken.RitualRecipeCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeManager;
import java.util.List;

@JeiPlugin
public class JEIPlugin implements IModPlugin {
    public static final RecipeType<RitualRecipe> RITUAL_RECIPE_TYPE =
            RecipeType.create(AbyssalReawoken.MODID, "ritual", RitualRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return ResourceLocation.fromNamespaceAndPath(AbyssalReawoken.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new RitualRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();

        List<RitualRecipe> recipes = recipeManager.getAllRecipesFor(ModRecipes.RITUAL_TYPE.get())
                .stream()
                .map(holder -> holder.value())
                .toList();

        registration.addRecipes(RITUAL_RECIPE_TYPE, recipes);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        // ➡️ FIXED: Handled dynamically inside the recipe class loops now to prevent global cross-contamination listings!
    }
}
