package com.kbd.abyssal_reawoken.items;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.fams.StoneFamily;
import com.kbd.abyssal_reawoken.fams.WoodFamily;
import net.minecraft.world.level.block.grower.TreeGrower;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.DeferredRegister;

@Mod(AbyssalReawoken.MODID)
public class SnWItems {
    public static final String MODID = "abyssal_reawoken";
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(AbyssalReawoken.MODID);
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);

    public static final WoodFamily DREADWOOD =
            new WoodFamily(
                    "dreadwood",
                    MapColor.COLOR_RED,
                    MapColor.COLOR_RED,
                    TreeGrower.OAK
            );

    public static final WoodFamily DARKWOOD =
            new WoodFamily(
                    "darkwood",
                    MapColor.COLOR_BLACK,
                    MapColor.COLOR_BLUE,
                    TreeGrower.DARK_OAK
            );

    public static final StoneFamily DARKSTONE =
            new StoneFamily(
                    "darkstone",
                    MapColor.COLOR_BLACK,
                    true,   // CBP
                    false   // BI
            );

    public static final StoneFamily ABYSSAL =
            new StoneFamily(
                    "abyssal",
                    MapColor.COLOR_GREEN,
                    true,   // CBP
                    false   // BI
            );

    public static final StoneFamily DREADSTONE =
            new StoneFamily(
                    "dreadstone",
                    MapColor.COLOR_RED,
                    true,   // CBP
                    false   // BI
            );

    public static final StoneFamily ELYSIAN =
            new StoneFamily(
                    "elysian",
                    MapColor.COLOR_BLUE,
                    true,   // CBP
                    false   // BI
            );

    public static final StoneFamily CORALIUM =
            new StoneFamily(
                    "coralium",
                    MapColor.COLOR_CYAN,
                    true,   // CBP
                    false   // BI
            );

    public static final StoneFamily ETHAXIUM =
            new StoneFamily(
                    "ethaxium",
                    MapColor.COLOR_LIGHT_GRAY,
                    false,   // CBP
                    false   // BI
            );

    public static final StoneFamily OMOTHOL =
            new StoneFamily(
                    "omothol",
                    MapColor.COLOR_BLACK,
                    false,   // CBP
                    false   // BI
            );

    public static final StoneFamily MONOLITH =
            new StoneFamily(
                    "monolith",
                    MapColor.COLOR_GRAY,
                    false,   // CBP
                    false   // BI
            );

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }
}