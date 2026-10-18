package com.kbd.abyssal_reawoken.items;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.portal.DreadlandsPortalBlock;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;


@Mod(AbyssalReawoken.MODID)
public class TerrainItems {
    public static final String MODID = "abyssal_reawoken";
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AbyssalReawoken.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);


    public static final DeferredBlock<Block> DREADLANDS_PORTAL = BLOCKS.register(
            "dreadlands_portal",
            () -> new DreadlandsPortalBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
                            .noLootTable()
                            .noOcclusion()));

    public static final DeferredBlock<Block> DREADED_GRASS_BLOCK = BLOCKS.registerSimpleBlock("dreaded_grass_block",
            BlockBehaviour.Properties.ofFullCopy(Blocks.GRASS_BLOCK)
                    .mapColor(MapColor.COLOR_RED)
                    .requiresCorrectToolForDrops());
    public static final DeferredItem<BlockItem> DREADED_GRASS_BLOCK_ITEM = ITEMS.register("dreaded_grass_block",
            () -> new BlockItem(DREADED_GRASS_BLOCK.get(), new Item.Properties()));
    
    public static final DeferredBlock<Block> DREADED_DIRT = BLOCKS.registerSimpleBlock("dreaded_dirt",
            BlockBehaviour.Properties.ofFullCopy(Blocks.DIRT)
                    .mapColor(MapColor.COLOR_RED)
                    .requiresCorrectToolForDrops());
    public static final DeferredItem<BlockItem> DREADED_DIRT_ITEM = ITEMS.register("dreaded_dirt",
            () -> new BlockItem(DREADED_DIRT.get(), new Item.Properties()));


    public static final DeferredBlock<Block> ABYSSAL_LANDS_PORTAL = BLOCKS.register(
            "abyssal_lands_portal",
            () -> new DreadlandsPortalBlock(
                    BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_PORTAL)
                            .noLootTable()
                            .noOcclusion()
            )
    );


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
