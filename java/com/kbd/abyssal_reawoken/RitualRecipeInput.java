package com.kbd.abyssal_reawoken;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import java.util.List;

public record RitualRecipeInput(
        ItemStack altarItem,
        List<ItemStack> pedestalItems,
        int availablePE
) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        if (index == 0) return altarItem;
        int pIndex = index - 1;
        if (pIndex >= 0 && pIndex < pedestalItems.size()) {
            return pedestalItems.get(pIndex);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public int size() {
        return 1 + pedestalItems.size();
    }
}
