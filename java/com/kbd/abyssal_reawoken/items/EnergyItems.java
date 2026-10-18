
package com.kbd.abyssal_reawoken.items;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.ModDataComponents;
import com.kbd.abyssal_reawoken.blocks.EnergyPedestalBlock;
import com.kbd.abyssal_reawoken.blockentity.EnergyPedestalBlockEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class EnergyItems {
    private EnergyItems() {}

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AbyssalReawoken.MODID);

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(AbyssalReawoken.MODID);

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(
                    BuiltInRegistries.BLOCK_ENTITY_TYPE,
                    AbyssalReawoken.MODID
            );

    public static final DeferredBlock<EnergyPedestalBlock> ENERGY_PEDESTAL =
            BLOCKS.register("energy_pedestal",
                    () -> new EnergyPedestalBlock(
                            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
                    ));

    public static final DeferredHolder<BlockEntityType<?>,
            BlockEntityType<EnergyPedestalBlockEntity>> ENERGY_PEDESTAL_BE =
            BLOCK_ENTITIES.register("energy_pedestal",
                    () -> BlockEntityType.Builder.of(
                            EnergyPedestalBlockEntity::new,
                            ENERGY_PEDESTAL.get()
                    ).build(null));

    public static final DeferredHolder<
            net.minecraft.world.item.Item, BlockItem> ENERGY_PEDESTAL_ITEM =
            ITEMS.register("energy_pedestal",
                    () -> new BlockItem(ENERGY_PEDESTAL.get(),
                            new Item.Properties()));

    public static final int MAX_PE = 5000;

    public static ItemStack createStack(int pe) {
        ItemStack stack = new ItemStack(ENERGY_PEDESTAL_ITEM.get());
        stack.set(ModDataComponents.POTENTIAL_ENERGY,
                Math.max(0, Math.min(pe, MAX_PE)));
        return stack;
    }

    public static ItemStack createFullyChargedStack() {
        return createStack(MAX_PE);
    }

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);
    }
}
