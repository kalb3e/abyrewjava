
package com.kbd.abyssal_reawoken.items;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.blocks.StatueBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.LinkedHashMap;
import java.util.Map;

public final class StatueItems {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AbyssalReawoken.MODID);

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(AbyssalReawoken.MODID);

    public record StatuePair(
            DeferredBlock<StatueBlock> activeBlock,
            DeferredItem<BlockItem> activeItem,
            DeferredBlock<StatueBlock> deactivatedBlock,
            DeferredItem<BlockItem> deactivatedItem
    ) {}

    private static final Map<String, StatuePair> STATUES =
            new LinkedHashMap<>();

    private StatueItems() {}

    /**
     * Registers a statue and its decorative deactivated variant.
     *
     * Example name: "cthulhu"
     *
     * Produces:
     * cthulhu_statue
     * deactivated_cthulhu_statue
     *
     * Both use the same block properties. Their block models should
     * reference the same model JSON.
     */
    public static StatuePair registerStatue(
            String name,
            BlockBehaviour.Properties properties
    ) {
        if (STATUES.containsKey(name)) {
            throw new IllegalArgumentException(
                    "Duplicate statue registration: " + name
            );
        }

        DeferredBlock<StatueBlock> activeBlock = BLOCKS.register(
                name + "_statue",
                () -> new StatueBlock(true, properties)
        );

        DeferredItem<BlockItem> activeItem = ITEMS.register(
                name + "_statue",
                () -> new BlockItem(
                        activeBlock.get(),
                        new Item.Properties()
                )
        );

        DeferredBlock<StatueBlock> deactivatedBlock = BLOCKS.register(
                "deactivated_" + name + "_statue",
                () -> new StatueBlock(
                        false,
                        BlockBehaviour.Properties.ofFullCopy(
                                activeBlock.get()
                        )
                )
        );

        DeferredItem<BlockItem> deactivatedItem = ITEMS.register(
                "deactivated_" + name + "_statue",
                () -> new BlockItem(
                        deactivatedBlock.get(),
                        new Item.Properties()
                )
        );

        StatuePair pair = new StatuePair(
                activeBlock,
                activeItem,
                deactivatedBlock,
                deactivatedItem
        );

        STATUES.put(name, pair);
        return pair;
    }

    public static StatuePair getStatue(String name) {
        return STATUES.get(name);
    }

    public static Map<String, StatuePair> getStatues() {
        return Map.copyOf(STATUES);
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
    }
}
