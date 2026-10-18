
package com.kbd.abyssal_reawoken.fams;

import com.kbd.abyssal_reawoken.AbyssalReawoken;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class WoodFamily {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AbyssalReawoken.MODID);

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(AbyssalReawoken.MODID);


    // ------------------------------------------------------------
    // BLOCKS
    // ------------------------------------------------------------

    public final DeferredBlock<RotatedPillarBlock> log;
    public final DeferredBlock<RotatedPillarBlock> strippedLog;

    public final DeferredBlock<RotatedPillarBlock> wood;
    public final DeferredBlock<RotatedPillarBlock> strippedWood;

    public final DeferredBlock<Block> planks;
    public final DeferredBlock<StairBlock> stairs;
    public final DeferredBlock<SlabBlock> slab;
    public final DeferredBlock<FenceBlock> fence;
    public final DeferredBlock<FenceGateBlock> fenceGate;

    public final DeferredBlock<DoorBlock> door;
    public final DeferredBlock<TrapDoorBlock> trapdoor;

    public final DeferredBlock<PressurePlateBlock> pressurePlate;
    public final DeferredBlock<ButtonBlock> button;

    public final DeferredBlock<Block> sapling;
    public final DeferredBlock<Block> leaves;


    // ------------------------------------------------------------
    // ITEMS
    // ------------------------------------------------------------

    public final DeferredItem<BlockItem> logItem;
    public final DeferredItem<BlockItem> strippedLogItem;

    public final DeferredItem<BlockItem> woodItem;
    public final DeferredItem<BlockItem> strippedWoodItem;

    public final DeferredItem<BlockItem> planksItem;
    public final DeferredItem<BlockItem> stairsItem;
    public final DeferredItem<BlockItem> slabItem;
    public final DeferredItem<BlockItem> fenceItem;
    public final DeferredItem<BlockItem> fenceGateItem;

    public final DeferredItem<BlockItem> doorItem;
    public final DeferredItem<BlockItem> trapdoorItem;

    public final DeferredItem<BlockItem> pressurePlateItem;
    public final DeferredItem<BlockItem> buttonItem;

    public final DeferredItem<BlockItem> saplingItem;
    public final DeferredItem<BlockItem> leavesItem;


    // ------------------------------------------------------------
    // CONSTRUCTOR
    // ------------------------------------------------------------

    public WoodFamily(
            String name,
            MapColor woodColor,
            MapColor leavesColor,
            TreeGrower treeGrower
    ) {

        // Logs
        log = BLOCKS.register(
                name + "_log",
                () -> new RotatedPillarBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)
                                .mapColor(woodColor)
                )
        );

        strippedLog = BLOCKS.register(
                "stripped_" + name + "_log",
                () -> new RotatedPillarBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)
                                .mapColor(woodColor)
                )
        );


        // Wood
        wood = BLOCKS.register(
                name + "_wood",
                () -> new RotatedPillarBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WOOD)
                                .mapColor(woodColor)
                )
        );

        strippedWood = BLOCKS.register(
                "stripped_" + name + "_wood",
                () -> new RotatedPillarBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)
                                .mapColor(woodColor)
                )
        );


        // Planks
        planks = BLOCKS.registerSimpleBlock(
                name + "_planks",
                BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                        .mapColor(woodColor)
        );


        // Stairs
        stairs = BLOCKS.register(
                name + "_stairs",
                () -> new StairBlock(
                        planks.get().defaultBlockState(),
                        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                                .mapColor(woodColor)
                )
        );


        // Slabs
        slab = BLOCKS.register(
                name + "_slab",
                () -> new SlabBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                                .mapColor(woodColor)
                )
        );


        // Fences
        fence = BLOCKS.register(
                name + "_fence",
                () -> new FenceBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)
                                .mapColor(woodColor)
                )
        );


        // Fence gates
        fenceGate = BLOCKS.register(
                name + "_fence_gate",
                () -> new FenceGateBlock(
                        WoodType.OAK,
                        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)
                                .mapColor(woodColor)
                )
        );


        // Doors
        door = BLOCKS.register(
                name + "_door",
                () -> new DoorBlock(
                        BlockSetType.OAK,
                        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)
                                .mapColor(woodColor)
                )
        );


        // Trapdoors
        trapdoor = BLOCKS.register(
                name + "_trapdoor",
                () -> new TrapDoorBlock(
                        BlockSetType.OAK,
                        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)
                                .mapColor(woodColor)
                )
        );


        // Pressure plates
        pressurePlate = BLOCKS.registerBlock(
                name + "_pressure_plate",
                properties -> new PressurePlateBlock(
                        BlockSetType.OAK,
                        properties
                                .mapColor(woodColor)
                                .forceSolidOn()
                                .noOcclusion()
                                .strength(0.5F)
                                .pushReaction(PushReaction.DESTROY)
                )
        );


        // Buttons
        button = BLOCKS.registerBlock(
                name + "_button",
                properties -> new ButtonBlock(
                        BlockSetType.OAK,
                        20,
                        properties
                                .mapColor(woodColor)
                                .noOcclusion()
                                .strength(0.5F)
                                .pushReaction(PushReaction.DESTROY)
                )
        );


        // Saplings
        sapling = BLOCKS.register(
                name + "_sapling",
                () -> new SaplingBlock(
                        treeGrower,
                        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)
                                .mapColor(woodColor)
                )
        );


        // Leaves
        leaves = BLOCKS.register(
                name + "_leaves",
                () -> new LeavesBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LEAVES)
                                .mapColor(leavesColor)
                )
        );


        // --------------------------------------------------------
        // REGISTER ITEMS
        // --------------------------------------------------------

        logItem = registerItem(name + "_log", log);
        strippedLogItem = registerItem("stripped_" + name + "_log", strippedLog);

        woodItem = registerItem(name + "_wood", wood);
        strippedWoodItem = registerItem("stripped_" + name + "_wood", strippedWood);

        planksItem = registerItem(name + "_planks", planks);
        stairsItem = registerItem(name + "_stairs", stairs);
        slabItem = registerItem(name + "_slab", slab);
        fenceItem = registerItem(name + "_fence", fence);
        fenceGateItem = registerItem(name + "_fence_gate", fenceGate);

        doorItem = registerItem(name + "_door", door);
        trapdoorItem = registerItem(name + "_trapdoor", trapdoor);

        pressurePlateItem = registerItem(
                name + "_pressure_plate",
                pressurePlate
        );

        buttonItem = registerItem(
                name + "_button",
                button
        );

        saplingItem = registerItem(name + "_sapling", sapling);
        leavesItem = registerItem(name + "_leaves", leaves);
    }


    // ------------------------------------------------------------
    // BLOCK ITEM REGISTRATION
    // ------------------------------------------------------------

    private DeferredItem<BlockItem> registerItem(
            String id,
            DeferredBlock<? extends Block> block
    ) {
        return ITEMS.register(
                id,
                () -> new BlockItem(
                        block.get(),
                        new Item.Properties()
                )
        );
    }


    // ------------------------------------------------------------
    // CREATIVE TAB ITEMS
    // ------------------------------------------------------------

    public List<DeferredItem<? extends Item>> items() {

        List<DeferredItem<? extends Item>> items = new ArrayList<>();

        items.add(logItem);
        items.add(strippedLogItem);

        items.add(woodItem);
        items.add(strippedWoodItem);

        items.add(planksItem);
        items.add(stairsItem);
        items.add(slabItem);
        items.add(fenceItem);
        items.add(fenceGateItem);

        items.add(doorItem);
        items.add(trapdoorItem);

        items.add(pressurePlateItem);
        items.add(buttonItem);

        items.add(saplingItem);
        items.add(leavesItem);

        return items;
    }


    // ------------------------------------------------------------
    // REGISTRATION
    // ------------------------------------------------------------

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}
