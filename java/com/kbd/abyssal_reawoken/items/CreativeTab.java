package com.kbd.abyssal_reawoken.items;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import static com.kbd.abyssal_reawoken.items.SnWItems.*;

public class CreativeTab {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AbyssalReawoken.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, AbyssalReawoken.MODID);

    public static final DeferredItem<BlockItem> RITUAL_PEDESTAL_ITEM = ITEMS.registerSimpleBlockItem("ritual_pedestal",
            AbyssalReawoken.RITUAL_PEDESTAL);

    public static final DeferredItem<BlockItem> CTHULHU_STATUE_ITEM = ITEMS.registerSimpleBlockItem("cthulhu_statue",
            AbyssalReawoken.CTHULHU_STATUE);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ABYSSAL_REAWOKEN_BLOCKS_TAB = CREATIVE_MODE_TABS.register("abyssal_reawoken_blocks_tab", () -> CreativeModeTab.builder()
            .title(Component.literal("Abyssal Reawoken Blocks"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> (RitualItems.NECRONOMICON.get().getDefaultInstance()))
            .displayItems((parameters, output) -> {
                output.accept(TerrainItems.DREADED_GRASS_BLOCK_ITEM.get());
                output.accept(TerrainItems.DREADED_DIRT_ITEM.get());

                output.accept(RitualItems.NECRONOMICON.get());
                output.accept(RitualItems.ABYSSAL_WASTELAND_NECRONOMICON.get());

                output.accept(RitualItems.COBBLESTONE_ALTAR_ITEM.get());
                output.accept(RitualItems.COBBLESTONE_PEDESTAL_ITEM.get());;
                output.accept(RitualItems.DARKSTONE_COBBLESTONE_ALTAR_ITEM.get());
                output.accept(RitualItems.DARKSTONE_COBBLESTONE_PEDESTAL_ITEM.get());;
                output.accept(RitualItems.ABYSSAL_COBBLESTONE_ALTAR_ITEM.get());
                output.accept(RitualItems.ABYSSAL_COBBLESTONE_PEDESTAL_ITEM.get());;
                output.accept(RitualItems.CORALIUM_BRICK_ALTAR_ITEM.get());
                output.accept(RitualItems.CORALIUM_BRICK_PEDESTAL_ITEM.get());;
                output.accept(RitualItems.ELYSIAN_BRICK_ALTAR_ITEM.get());
                output.accept(RitualItems.ELYSIAN_BRICK_PEDESTAL_ITEM.get());;
                output.accept(RitualItems.ETHAXIUM_BRICK_ALTAR_ITEM.get());
                output.accept(RitualItems.ETHAXIUM_BRICK_PEDESTAL_ITEM.get());;
                output.accept(RitualItems.DARK_ETHAXIUM_BRICK_ALTAR_ITEM.get());
                output.accept(RitualItems.DARK_ETHAXIUM_BRICK_PEDESTAL_ITEM.get());

                DARKSTONE.items().forEach(item -> output.accept(item.get()));
                ABYSSAL.items().forEach(item -> output.accept(item.get()));
                DREADSTONE.items().forEach(item -> output.accept(item.get()));
                ELYSIAN.items().forEach(item -> output.accept(item.get()));
                CORALIUM.items().forEach(item -> output.accept(item.get()));

                ETHAXIUM.items().forEach(item -> output.accept(item.get()));
                OMOTHOL.items().forEach(item -> output.accept(item.get()));
                MONOLITH.items().forEach(item -> output.accept(item.get()));

                DREADWOOD.items().forEach(item -> output.accept(item.get()));
                DARKWOOD.items().forEach(item -> output.accept(item.get()));

                output.accept(RITUAL_PEDESTAL_ITEM.get());
                output.accept(CTHULHU_STATUE_ITEM.get());

            }).build());

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ABYSSAL_REAWOKEN_FOOD_DROPS_TAB = CREATIVE_MODE_TABS.register("abyssal_reawoken_food_drops_tab", () -> CreativeModeTab.builder()
            .title(Component.literal("Abyssal Reawoken Food&Drops"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> (RitualItems.NECRONOMICON.get().getDefaultInstance()))
            .displayItems((parameters, output) -> {
                output.accept(FnDItems.ANTI_BEEF.get());
                output.accept(FnDItems.ANTI_CHICKEN.get());
                output.accept(FnDItems.ANTI_PORKCHOP.get());
                output.accept(FnDItems.ROTTEN_ANTI_FLESH.get());
                output.accept(FnDItems.ANTI_SPIDER_EYE.get());
                output.accept(FnDItems.GENERIC_MEAT.get());
                output.accept(FnDItems.COOKED_GENERIC_MEAT.get());

            }).build());

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
