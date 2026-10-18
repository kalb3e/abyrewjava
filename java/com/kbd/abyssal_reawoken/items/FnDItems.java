package com.kbd.abyssal_reawoken.items;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


@Mod(AbyssalReawoken.MODID)
public class FnDItems {
    public static final String MODID = "abyssal_reawoken";
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AbyssalReawoken.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);


    public static final DeferredItem<Item> ANTI_BEEF = ITEMS.register("anti_beef",
            () -> new Item(new Item.Properties().food(Foods.BEEF)));
    
    public static final DeferredItem<Item> ANTI_CHICKEN = ITEMS.register("anti_chicken",
            () -> new Item(new Item.Properties().food(Foods.CHICKEN)));
    
    public static final DeferredItem<Item> ANTI_PORKCHOP = ITEMS.register("anti_porkchop",
            () -> new Item(new Item.Properties().food(Foods.PORKCHOP)));

    public static final DeferredItem<Item> ROTTEN_ANTI_FLESH = ITEMS.register("rotten_anti_flesh",
            () -> new Item(new Item.Properties().food(Foods.ROTTEN_FLESH)));
    
    public static final DeferredItem<Item> ANTI_SPIDER_EYE = ITEMS.register("anti_spider_eye",
            () -> new Item(new Item.Properties().food(Foods.SPIDER_EYE)));
    
    public static final DeferredItem<Item> GENERIC_MEAT = ITEMS.register("generic_meat",
            () -> new Item(new Item.Properties().food(Foods.BEEF)));

    public static final DeferredItem<Item> COOKED_GENERIC_MEAT = ITEMS.register("cooked_generic_meat",
            () -> new Item(new Item.Properties().food(Foods.COOKED_BEEF)));


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
