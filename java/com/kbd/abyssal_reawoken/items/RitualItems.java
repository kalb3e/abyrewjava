
package com.kbd.abyssal_reawoken.items;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.blocks.RitualAltarBlock;
import com.kbd.abyssal_reawoken.blocks.RitualPedestalBlock;
import com.kbd.abyssal_reawoken.blockentity.RitualAltarBlockEntity;
import com.kbd.abyssal_reawoken.blockentity.RitualPedestalBlockEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class RitualItems {

    private RitualItems() {}

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AbyssalReawoken.MODID);

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(AbyssalReawoken.MODID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    AbyssalReawoken.MODID
            );

    public record RitualPair(
            DeferredBlock<RitualAltarBlock> altar,
            DeferredHolder<Item, BlockItem> altarItem,
            DeferredBlock<RitualPedestalBlock> pedestal,
            DeferredHolder<Item, BlockItem> pedestalItem,
            int tierHalfSteps
    ) {
        public double tier() {
            return tierHalfSteps / 2.0;
        }
    }

    private static RitualPair registerPair(
            String id,
            int tierHalfSteps,
            BlockBehaviour.Properties properties
    ) {
        DeferredBlock<RitualAltarBlock> altar = BLOCKS.register(
                id + "_altar",
                () -> new RitualAltarBlock(properties, tierHalfSteps)
        );

        DeferredHolder<Item, BlockItem> altarItem = ITEMS.register(
                id + "_altar",
                () -> new BlockItem(altar.get(), new Item.Properties())
        );

        DeferredBlock<RitualPedestalBlock> pedestal = BLOCKS.register(
                id + "_ritual_pedestal",
                () -> new RitualPedestalBlock(properties, tierHalfSteps)
        );

        DeferredHolder<Item, BlockItem> pedestalItem = ITEMS.register(
                id + "_ritual_pedestal",
                () -> new BlockItem(pedestal.get(), new Item.Properties())
        );

        return new RitualPair(
                altar, altarItem, pedestal, pedestalItem, tierHalfSteps
        );
    }

    private static final BlockBehaviour.Properties STONE =
            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS);

    public static final RitualPair COBBLESTONE = registerPair(
            "cobblestone", 2,
            BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
    );

    public static final RitualPair DARKSTONE_COBBLESTONE =
            registerPair("darkstone_cobblestone", 2, STONE);

    public static final RitualPair ABYSSAL_COBBLESTONE =
            registerPair("abyssal_cobblestone", 4, STONE);

    public static final RitualPair CORALIUM_BRICK =
            registerPair("coralium_brick", 4, STONE);

    public static final RitualPair DREADSTONE_BRICK =
            registerPair("dreadstone_brick", 6, STONE);

    public static final RitualPair ELYSIAN_BRICK =
            registerPair("elysian_brick", 7, STONE);

    public static final RitualPair ETHAXIUM_BRICK =
            registerPair("ethaxium_brick", 10, STONE);

    public static final RitualPair DARK_ETHAXIUM_BRICK =
            registerPair("dark_ethaxium_brick", 12, STONE);

    public static final DeferredHolder<
            BlockEntityType<?>, BlockEntityType<RitualAltarBlockEntity>
            > ALTAR_BE = BLOCK_ENTITIES.register(
            "ritual_altar",
            () -> BlockEntityType.Builder.of(
                    RitualAltarBlockEntity::new,
                    COBBLESTONE.altar().get(),
                    DARKSTONE_COBBLESTONE.altar().get(),
                    ABYSSAL_COBBLESTONE.altar().get(),
                    CORALIUM_BRICK.altar().get(),
                    DREADSTONE_BRICK.altar().get(),
                    ELYSIAN_BRICK.altar().get(),
                    ETHAXIUM_BRICK.altar().get(),
                    DARK_ETHAXIUM_BRICK.altar().get()
            ).build(null)
    );

    public static final DeferredHolder<
            BlockEntityType<?>, BlockEntityType<RitualPedestalBlockEntity>
            > RITUAL_PEDESTAL_BE = BLOCK_ENTITIES.register(
            "ritual_pedestal",
            () -> BlockEntityType.Builder.of(
                    RitualPedestalBlockEntity::new,
                    COBBLESTONE.pedestal().get(),
                    DARKSTONE_COBBLESTONE.pedestal().get(),
                    ABYSSAL_COBBLESTONE.pedestal().get(),
                    CORALIUM_BRICK.pedestal().get(),
                    DREADSTONE_BRICK.pedestal().get(),
                    ELYSIAN_BRICK.pedestal().get(),
                    ETHAXIUM_BRICK.pedestal().get(),
                    DARK_ETHAXIUM_BRICK.pedestal().get()
            ).build(null)
    );

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
    }
}
