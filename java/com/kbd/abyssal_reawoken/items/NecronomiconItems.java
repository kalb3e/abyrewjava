
package com.kbd.abyssal_reawoken.items;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.ModDataComponents;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class NecronomiconItems {

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(AbyssalReawoken.MODID);

    private NecronomiconItems() {}

    public record Book(
            DeferredItem<Item> item,
            int tier,
            int maxPE
    ) {}

    public static final Book NECRONOMICON =
            registerBook("necronomicon", 1, 5_000);

    public static final Book ABYSSAL_WASTELAND_NECRONOMICON =
            registerBook("abyssal_wasteland_necronomicon", 2, 10_000);

    public static final Book DREADLANDS_NECRONOMICON =
            registerBook("dreadlands_necronomicon", 3, 20_000);

    public static final Book OMOTHOL_NECRONOMICON =
            registerBook("omothol_necronomicon", 4, 40_000);

    public static final Book ABYSSALNOMICON =
            registerBook("abyssalnomicon", 5, 10_000_000);

    private static Book registerBook(
            String id,
            int tier,
            int maxPE
    ) {
        DeferredItem<Item> item = ITEMS.register(
                id,
                () -> new Item(new Item.Properties().stacksTo(1))
        );

        return new Book(item, tier, maxPE);
    }

    public static ItemStack createStack(Book book, int pe) {
        ItemStack stack = new ItemStack(book.item().get());

        stack.set(
                ModDataComponents.POTENTIAL_ENERGY,
                Math.max(0, Math.min(pe, book.maxPE()))
        );

        return stack;
    }

    public static ItemStack createFullyChargedStack(Book book) {
        return createStack(book, book.maxPE());
    }

    public static int getTier(ItemStack stack) {
        for (Book book : getAllBooks()) {
            if (stack.is(book.item().get())) {
                return book.tier();
            }
        }

        return 0;
    }

    public static int getMaxPE(ItemStack stack) {
        for (Book book : getAllBooks()) {
            if (stack.is(book.item().get())) {
                return book.maxPE();
            }
        }

        return 0;
    }

    public static boolean isNecronomicon(ItemStack stack) {
        return getTier(stack) > 0;
    }

    public static boolean isFullyCharged(ItemStack stack) {
        int maxPE = getMaxPE(stack);

        return maxPE > 0
                && stack.getOrDefault(
                ModDataComponents.POTENTIAL_ENERGY,
                0
        ) >= maxPE;
    }

    public static Book[] getAllBooks() {
        return new Book[] {
                NECRONOMICON,
                ABYSSAL_WASTELAND_NECRONOMICON,
                DREADLANDS_NECRONOMICON,
                OMOTHOL_NECRONOMICON,
                ABYSSALNOMICON
        };
    }

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }
}
