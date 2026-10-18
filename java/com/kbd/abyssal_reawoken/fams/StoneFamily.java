package com.kbd.abyssal_reawoken.fams;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.WallBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;

public class StoneFamily {

    // ------------------------------------------------------------
    // CENTRAL REGISTRIES
    // ------------------------------------------------------------

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(AbyssalReawoken.MODID);

    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(AbyssalReawoken.MODID);


    // ------------------------------------------------------------
    // FAMILY SETTINGS
    // ------------------------------------------------------------

    private final String name;
    private final MapColor mapColor;
    private final boolean cbp;
    private final boolean bi;


    // ------------------------------------------------------------
    // REGISTERED BLOCKS
    // ------------------------------------------------------------

    public final DeferredBlock<Block> stone;
    public final DeferredBlock<StairBlock> stairs;
    public final DeferredBlock<SlabBlock> slab;

    // Only exists when CBP is OFF
    public final DeferredBlock<WallBlock> wall;

    public final DeferredBlock<Block> bricks;
    public final DeferredBlock<Block> crackedBricks;
    public final DeferredBlock<StairBlock> brickStairs;
    public final DeferredBlock<SlabBlock> brickSlab;
    public final DeferredBlock<WallBlock> brickWall;
    public final DeferredBlock<FenceBlock> brickFence;
    public final DeferredBlock<Block> chiseledBricks;

    // CBP blocks
    public final DeferredBlock<Block> cobblestone;
    public final DeferredBlock<StairBlock> cobblestoneStairs;
    public final DeferredBlock<SlabBlock> cobblestoneSlab;
    public final DeferredBlock<WallBlock> cobblestoneWall;
    public final DeferredBlock<PressurePlateBlock> pressurePlate;
    public final DeferredBlock<ButtonBlock> button;


    // ------------------------------------------------------------
    // REGISTERED ITEMS
    // ------------------------------------------------------------

    public final DeferredItem<BlockItem> stoneItem;
    public final DeferredItem<BlockItem> stairsItem;
    public final DeferredItem<BlockItem> slabItem;

    // Only exists when CBP is OFF
    public final DeferredItem<BlockItem> wallItem;

    public final DeferredItem<BlockItem> bricksItem;
    public final DeferredItem<BlockItem> crackedBricksItem;
    public final DeferredItem<BlockItem> brickStairsItem;
    public final DeferredItem<BlockItem> brickSlabItem;
    public final DeferredItem<BlockItem> brickWallItem;
    public final DeferredItem<BlockItem> brickFenceItem;
    public final DeferredItem<BlockItem> chiseledBricksItem;

    // Brick item
    public final DeferredItem<Item> brickItem;

    // CBP items
    public final DeferredItem<BlockItem> cobblestoneItem;
    public final DeferredItem<BlockItem> cobblestoneStairsItem;
    public final DeferredItem<BlockItem> cobblestoneSlabItem;
    public final DeferredItem<BlockItem> cobblestoneWallItem;
    public final DeferredItem<BlockItem> pressurePlateItem;
    public final DeferredItem<BlockItem> buttonItem;


    // ------------------------------------------------------------
    // CONSTRUCTOR
    // ------------------------------------------------------------

    public StoneFamily(
            String name,
            MapColor mapColor,
            boolean cbp,
            boolean bi
    ) {
        this.name = name;
        this.mapColor = mapColor;
        this.cbp = cbp;
        this.bi = bi;


        // --------------------------------------------------------
        // BASE STONE
        // --------------------------------------------------------

        stone = BLOCKS.registerSimpleBlock(
                name,
                BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                        .mapColor(mapColor)
                        .requiresCorrectToolForDrops()
        );

        stoneItem = ITEMS.registerSimpleBlockItem(stone);


        // --------------------------------------------------------
        // STONE STAIRS
        // --------------------------------------------------------

        stairs = BLOCKS.register(
                name + "_stairs",
                () -> new StairBlock(
                        stone.get().defaultBlockState(),
                        BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                                .mapColor(mapColor)
                                .requiresCorrectToolForDrops()
                )
        );

        stairsItem = ITEMS.registerSimpleBlockItem(stairs);


        // --------------------------------------------------------
        // STONE SLAB
        // --------------------------------------------------------

        slab = BLOCKS.register(
                name + "_slab",
                () -> new SlabBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                                .mapColor(mapColor)
                                .requiresCorrectToolForDrops()
                )
        );

        slabItem = ITEMS.registerSimpleBlockItem(slab);


        // --------------------------------------------------------
        // STONE WALL
        //
        // If CBP is ON:
        //     NO normal wall is registered.
        //
        // If CBP is OFF:
        //     Registers <name>_wall normally.
        // --------------------------------------------------------

        if (!cbp) {

            wall = BLOCKS.register(
                    name + "_wall",
                    () -> new WallBlock(
                            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE)
                                    .mapColor(mapColor)
                                    .requiresCorrectToolForDrops()
                    )
            );

            wallItem = ITEMS.registerSimpleBlockItem(wall);

        } else {

            wall = null;
            wallItem = null;
        }


        // --------------------------------------------------------
        // BRICKS
        // --------------------------------------------------------

        bricks = BLOCKS.registerSimpleBlock(
                name + "_bricks",
                BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
                        .mapColor(mapColor)
                        .requiresCorrectToolForDrops()
        );

        bricksItem = ITEMS.registerSimpleBlockItem(bricks);


        // --------------------------------------------------------
        // CRACKED BRICKS
        // --------------------------------------------------------

        crackedBricks = BLOCKS.registerSimpleBlock(
                "cracked_" + name + "_bricks",
                BlockBehaviour.Properties.ofFullCopy(Blocks.CRACKED_STONE_BRICKS)
                        .mapColor(mapColor)
                        .requiresCorrectToolForDrops()
        );

        crackedBricksItem = ITEMS.registerSimpleBlockItem(crackedBricks);


        // --------------------------------------------------------
        // BRICK STAIRS
        // --------------------------------------------------------

        brickStairs = BLOCKS.register(
                name + "_brick_stairs",
                () -> new StairBlock(
                        bricks.get().defaultBlockState(),
                        BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
                                .mapColor(mapColor)
                                .requiresCorrectToolForDrops()
                )
        );

        brickStairsItem = ITEMS.registerSimpleBlockItem(brickStairs);


        // --------------------------------------------------------
        // BRICK SLAB
        // --------------------------------------------------------

        brickSlab = BLOCKS.register(
                name + "_brick_slab",
                () -> new SlabBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
                                .mapColor(mapColor)
                                .requiresCorrectToolForDrops()
                )
        );

        brickSlabItem = ITEMS.registerSimpleBlockItem(brickSlab);


        // --------------------------------------------------------
        // BRICK WALL
        // --------------------------------------------------------

        brickWall = BLOCKS.register(
                name + "_brick_wall",
                () -> new WallBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
                                .mapColor(mapColor)
                                .requiresCorrectToolForDrops()
                )
        );

        brickWallItem = ITEMS.registerSimpleBlockItem(brickWall);


        // --------------------------------------------------------
        // BRICK FENCE
        // --------------------------------------------------------

        brickFence = BLOCKS.register(
                name + "_brick_fence",
                () -> new FenceBlock(
                        BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS)
                                .mapColor(mapColor)
                                .requiresCorrectToolForDrops()
                )
        );

        brickFenceItem = ITEMS.registerSimpleBlockItem(brickFence);


        // --------------------------------------------------------
        // CHISELED BRICKS
        // --------------------------------------------------------

        chiseledBricks = BLOCKS.registerSimpleBlock(
                "chiseled_" + name + "_bricks",
                BlockBehaviour.Properties.ofFullCopy(Blocks.CHISELED_STONE_BRICKS)
                        .mapColor(mapColor)
                        .requiresCorrectToolForDrops()
        );

        chiseledBricksItem = ITEMS.registerSimpleBlockItem(chiseledBricks);


        // --------------------------------------------------------
        // BRICK ITEM
        // --------------------------------------------------------

        if (bi) {
            brickItem = ITEMS.registerSimpleItem(
                    name + "_brick"
            );
        } else {
            brickItem = null;
        }


        // --------------------------------------------------------
        // CBP
        // --------------------------------------------------------

        if (cbp) {

            // Cobblestone
            cobblestone = BLOCKS.registerSimpleBlock(
                    name + "_cobblestone",
                    BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
                            .mapColor(mapColor)
                            .requiresCorrectToolForDrops()
            );

            cobblestoneItem = ITEMS.registerSimpleBlockItem(cobblestone);


            // Cobblestone stairs
            cobblestoneStairs = BLOCKS.register(
                    name + "_cobblestone_stairs",
                    () -> new StairBlock(
                            cobblestone.get().defaultBlockState(),
                            BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
                                    .mapColor(mapColor)
                                    .requiresCorrectToolForDrops()
                    )
            );

            cobblestoneStairsItem = ITEMS.registerSimpleBlockItem(cobblestoneStairs);


            // Cobblestone slab
            cobblestoneSlab = BLOCKS.register(
                    name + "_cobblestone_slab",
                    () -> new SlabBlock(
                            BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
                                    .mapColor(mapColor)
                                    .requiresCorrectToolForDrops()
                    )
            );

            cobblestoneSlabItem = ITEMS.registerSimpleBlockItem(cobblestoneSlab);


            // Cobblestone wall
            cobblestoneWall = BLOCKS.register(
                    name + "_cobblestone_wall",
                    () -> new WallBlock(
                            BlockBehaviour.Properties.ofFullCopy(Blocks.COBBLESTONE)
                                    .mapColor(mapColor)
                                    .requiresCorrectToolForDrops()
                    )
            );

            cobblestoneWallItem = ITEMS.registerSimpleBlockItem(cobblestoneWall);


            // Pressure plate
            pressurePlate = BLOCKS.register(
                    name + "_pressure_plate",
                    () -> new PressurePlateBlock(
                            BlockSetType.STONE,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_PRESSURE_PLATE)
                                    .mapColor(mapColor)
                                    .forceSolidOn()
                                    .noOcclusion()
                                    .strength(0.5F)
                                    .pushReaction(PushReaction.DESTROY)
                    )
            );

            pressurePlateItem = ITEMS.registerSimpleBlockItem(pressurePlate);


            // Button
            button = BLOCKS.register(
                    name + "_button",
                    () -> new ButtonBlock(
                            BlockSetType.STONE,
                            20,
                            BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BUTTON)
                                    .mapColor(mapColor)
                                    .noOcclusion()
                                    .strength(0.5F)
                                    .pushReaction(PushReaction.DESTROY)
                    )
            );

            buttonItem = ITEMS.registerSimpleBlockItem(button);

        } else {

            cobblestone = null;
            cobblestoneItem = null;

            cobblestoneStairs = null;
            cobblestoneStairsItem = null;

            cobblestoneSlab = null;
            cobblestoneSlabItem = null;

            cobblestoneWall = null;
            cobblestoneWallItem = null;

            pressurePlate = null;
            pressurePlateItem = null;

            button = null;
            buttonItem = null;
        }
    }


    // ------------------------------------------------------------
    // ITEMS
    // ------------------------------------------------------------

    public List<DeferredItem<? extends Item>> items() {

        List<DeferredItem<? extends Item>> items = new ArrayList<>();

        items.add(stoneItem);
        items.add(stairsItem);
        items.add(slabItem);

        // Normal wall only exists when CBP is OFF
        if (wallItem != null) {
            items.add(wallItem);
        }

        items.add(bricksItem);
        items.add(crackedBricksItem);
        items.add(brickStairsItem);
        items.add(brickSlabItem);
        items.add(brickWallItem);
        items.add(brickFenceItem);
        items.add(chiseledBricksItem);

        if (brickItem != null) {
            items.add(brickItem);
        }

        if (cbp) {
            items.add(cobblestoneItem);
            items.add(cobblestoneStairsItem);
            items.add(cobblestoneSlabItem);
            items.add(cobblestoneWallItem);
            items.add(pressurePlateItem);
            items.add(buttonItem);
        }

        return items;
    }


    // ------------------------------------------------------------
    // REGISTER
    // ------------------------------------------------------------

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}