package com.kbd.abyssal_reawoken;

import com.kbd.abyssal_reawoken.client.gui.JEIPlugin;
import com.kbd.abyssal_reawoken.items.RitualItems;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class RitualRecipeCategory implements IRecipeCategory<RitualRecipe> {
    private final IDrawable background;
    private final IDrawable icon;

    // Maps each block item back to its true mathematical tier rank (1-6)
    private static final Map<net.minecraft.world.item.Item, Integer> BLOCK_TIERS = Map.ofEntries(
            Map.entry(RitualItems.COBBLESTONE_ALTAR_ITEM.get(), 1), Map.entry(RitualItems.COBBLESTONE_PEDESTAL_ITEM.get(), 1),
            Map.entry(RitualItems.DARKSTONE_COBBLESTONE_ALTAR_ITEM.get(), 1), Map.entry(RitualItems.DARKSTONE_COBBLESTONE_PEDESTAL_ITEM.get(), 1),

            Map.entry(RitualItems.ABYSSAL_COBBLESTONE_ALTAR_ITEM.get(), 2), Map.entry(RitualItems.ABYSSAL_COBBLESTONE_PEDESTAL_ITEM.get(), 2),
            Map.entry(RitualItems.CORALIUM_BRICK_ALTAR_ITEM.get(), 2), Map.entry(RitualItems.CORALIUM_BRICK_PEDESTAL_ITEM.get(), 2),

            Map.entry(RitualItems.DREADSTONE_BRICK_ALTAR_ITEM.get(), 3), Map.entry(RitualItems.DREADSTONE_BRICK_PEDESTAL_ITEM.get(), 3),
            Map.entry(RitualItems.ELYSIAN_BRICK_ALTAR_ITEM.get(), 4), Map.entry(RitualItems.ELYSIAN_BRICK_PEDESTAL_ITEM.get(), 4),
            Map.entry(RitualItems.ETHAXIUM_BRICK_ALTAR_ITEM.get(), 5), Map.entry(RitualItems.ETHAXIUM_BRICK_PEDESTAL_ITEM.get(), 5),
            Map.entry(RitualItems.DARK_ETHAXIUM_BRICK_ALTAR_ITEM.get(), 6), Map.entry(RitualItems.DARK_ETHAXIUM_BRICK_PEDESTAL_ITEM.get(), 6)
    );

    public RitualRecipeCategory(IGuiHelper guiHelper) {
        this.background = guiHelper.createBlankDrawable(175, 120);
        this.icon = guiHelper.createDrawableIngredient(
                VanillaTypes.ITEM_STACK,
                new ItemStack(RitualItems.COBBLESTONE_ALTAR_ITEM.get())
        );
    }

    @Override public RecipeType<RitualRecipe> getRecipeType() { return JEIPlugin.RITUAL_RECIPE_TYPE; }
    @Override public Component getTitle() { return Component.literal("Ritual"); }
    @Override public int getWidth() { return 175; }
    @Override public int getHeight() { return 120; }
    @Override public IDrawable getIcon() { return icon; }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, RitualRecipe recipe, IFocusGroup focuses) {
        // ➡️ FIXED FOCUS FILTER: Natively matching the exact Stream types and your version's TypedValue getIngredient paths
        boolean shouldHideRecipe = focuses.getFocuses(VanillaTypes.ITEM_STACK)
                .anyMatch(focus -> {
                    ItemStack focusedItem = focus.getTypedValue().getIngredient();
                    Integer catalystTier = BLOCK_TIERS.get(focusedItem.getItem());
                    return catalystTier != null && catalystTier < recipe.getRitualTier();
                });

        if (shouldHideRecipe) {
            builder.addSlot(RecipeIngredientRole.INPUT, -50, -50).addIngredients(Ingredient.EMPTY);
            return;
        }

        // Center Altar slot configuration
        builder.addSlot(RecipeIngredientRole.INPUT, 75, 45)
                .setSlotName("altar")
                .addIngredients(recipe.getAltarInput());

        // Full 8-pedestal circular coordinate nodes
        int[][] positions = {
                {75, 10},  // 0 - Top
                {98, 20},  // 1 - Upper-right
                {110, 45}, // 2 - Right
                {98, 70},  // 3 - Lower-right
                {75, 80},  // 4 - Bottom
                {52, 70},  // 5 - Lower-left
                {40, 45},  // 6 - Left
                {52, 20}   // 7 - Upper-left
        };

        List<Ingredient> pedestals = recipe.getPedestalInputs();
        for (int i = 0; i < pedestals.size() && i < 8; i++) {
            Ingredient ingredient = pedestals.get(i);
            List<ItemStack> itemOptions = Arrays.asList(ingredient.getItems());

            builder.addSlot(RecipeIngredientRole.INPUT, positions[i][0], positions[i][1])
                    .setSlotName("pedestal_" + i)
                    .addItemStacks(itemOptions);
        }

        // Output Result Slot placement
        builder.addSlot(RecipeIngredientRole.OUTPUT, 145, 45)
                .setSlotName("result")
                .addItemStack(recipe.getResult());

        // Required Necronomicon battery indicator slot
        ItemStack bookStack;
        if (recipe.getRitualTier() == 1) {
            bookStack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(AbyssalReawoken.MODID, "necronomicon")));
        } else {
            bookStack = new ItemStack(BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(AbyssalReawoken.MODID, "abyssal_wastelands_necronomicon")));
        }

        if (bookStack.isEmpty()) {
            bookStack = new ItemStack(Items.BOOK);
        }

        builder.addSlot(RecipeIngredientRole.CATALYST, 5, 45)
                .setSlotName("necronomicon")
                .addItemStack(bookStack);
    }

    @Override
    public void draw(RitualRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;

        String costText = recipe.getPeCost() + " PE";
        String tierName = switch (recipe.getRitualTier()) {
            case 1 -> "Tier 1: Cobble/Dark";
            case 2 -> "Tier 2: Aby/Coral";
            case 3 -> "Tier 3: Dread";
            case 4 -> "Tier 4: Elysian";
            case 5 -> "Tier 5: Ethaxium";
            case 6 -> "Tier 6: Dark Ethax";
            default -> "Tier " + recipe.getRitualTier();
        };

        guiGraphics.drawString(font, costText, 5, 105, 0x7A4B1A, false);
        guiGraphics.drawString(font, tierName, 70, 105, 0x55FFFF, false);
        guiGraphics.drawString(font, "Necronomicon", 2, 35, 0xAAAAAA, false);
    }
}
