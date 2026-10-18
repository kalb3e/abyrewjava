package com.kbd.abyssal_reawoken;

import com.kbd.abyssal_reawoken.blockentity.DisplayPedestalBlockEntity;
import com.kbd.abyssal_reawoken.blockentity.RitualAltarBlockEntity;
import com.kbd.abyssal_reawoken.blockentity.RitualPedestalBlockEntity;
import com.kbd.abyssal_reawoken.blocks.DisplayPedestalBlock;
import com.kbd.abyssal_reawoken.client.renderer.blocks.*;
import com.kbd.abyssal_reawoken.datagen.root.ModDataGenerators;
import com.kbd.abyssal_reawoken.fams.*;
import com.kbd.abyssal_reawoken.items.*;
import com.kbd.abyssal_reawoken.penergy.PotentialEnergyCapability;
import com.kbd.abyssal_reawoken.portal.Abyssal_landsPortalEvents;
import com.kbd.abyssal_reawoken.portal.DreadlandsPortalEvents;
import com.kbd.abyssal_reawoken.ritual.RitualStructureTransformations;
import net.minecraft.world.level.block.*;
import org.slf4j.Logger;
import com.mojang.logging.LogUtils;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.common.NeoForge;
import com.kbd.abyssal_reawoken.entities.GhoulEvents;
import com.kbd.abyssal_reawoken.client.renderer.entity.GhoulRenderer;

@Mod(AbyssalReawoken.MODID)
public class AbyssalReawoken {
    public static final String MODID = "abyssal_reawoken";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, MODID);

    public static final DeferredBlock<Block> RITUAL_PEDESTAL = BLOCKS.register("ritual_pedestal", () -> new DisplayPedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion()));
    public static final DeferredBlock<Block> CTHULHU_STATUE = BLOCKS.register("cthulhu_statue", () -> new DisplayPedestalBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STONE_BRICKS).noOcclusion()));

    // 1. Core PE Ticking Pedestals (Only items that hold energy)
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<DisplayPedestalBlockEntity>> RITUAL_PEDESTAL_BE =
            BLOCK_ENTITIES.register("ritual_pedestal", () -> BlockEntityType.Builder.of(
                    DisplayPedestalBlockEntity::new,
                    RITUAL_PEDESTAL.get(),
                    CTHULHU_STATUE.get()
            ).build(null));

    // 2. ➡️ FIXED ISOLATION: 8 Structural Tier Pedestals (Passive, completely blocks Jade PE tooltips)
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RitualPedestalBlockEntity>> TIER_PEDESTAL_BE =
            BLOCK_ENTITIES.register("tier_pedestal", () -> BlockEntityType.Builder.of(
                    RitualPedestalBlockEntity::new,
                    RitualItems.COBBLESTONE_PEDESTAL.get(),
                    RitualItems.DARKSTONE_COBBLESTONE_PEDESTAL.get(),
                    RitualItems.ABYSSAL_COBBLESTONE_PEDESTAL.get(),
                    RitualItems.CORALIUM_BRICK_PEDESTAL.get(),
                    RitualItems.DREADSTONE_BRICK_PEDESTAL.get(),
                    RitualItems.ELYSIAN_BRICK_PEDESTAL.get(),
                    RitualItems.ETHAXIUM_BRICK_PEDESTAL.get(),
                    RitualItems.DARK_ETHAXIUM_BRICK_PEDESTAL.get()
            ).build(null));

    // 3. Structural Tier Altars (Passive recipe handlers)
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<RitualAltarBlockEntity>> RITUAL_ALTAR_BE =
            BLOCK_ENTITIES.register("ritual_altar", () -> BlockEntityType.Builder.of(
                    RitualAltarBlockEntity::new,
                    RitualItems.COBBLESTONE_ALTAR.get(),
                    RitualItems.DARKSTONE_COBBLESTONE_ALTAR.get(),
                    RitualItems.ABYSSAL_COBBLESTONE_ALTAR.get(),
                    RitualItems.CORALIUM_BRICK_ALTAR.get(),
                    RitualItems.DREADSTONE_BRICK_ALTAR.get(),
                    RitualItems.ELYSIAN_BRICK_ALTAR.get(),
                    RitualItems.ETHAXIUM_BRICK_ALTAR.get(),
                    RitualItems.DARK_ETHAXIUM_BRICK_ALTAR.get()
            ).build(null));

    public AbyssalReawoken(IEventBus modEventBus, ModContainer modContainer) {
        BLOCKS.register(modEventBus);
        BLOCK_ENTITIES.register(modEventBus);

        ModDataComponents.register(modEventBus);
        ModRecipes.register(modEventBus);
        RitualItems.register(modEventBus);

        StoneFamily.register(modEventBus);
        WoodFamily.register(modEventBus);
        GhoulFamily.register(modEventBus);

        GhoulFamilies.initialize();
        SnWItems.register(modEventBus);
        FnDItems.register(modEventBus);
        TerrainItems.register(modEventBus);
        StatueItems.register(modEventBus);
        NecronomiconItems.register(modEventBus);
        EnergyItems.register(modEventBus);
        CreativeTab.register(modEventBus);

        modEventBus.addListener(GhoulFamily::registerAttributes);
        NeoForge.EVENT_BUS.register(new GhoulEvents());

        modEventBus.addListener(ModDataGenerators::gatherData);

        RitualStructureTransformations.register(modEventBus);
        modEventBus.addListener(this::registerRenderers);
        modEventBus.addListener(this::registerCapabilities);
        NeoForge.EVENT_BUS.register(DreadlandsPortalEvents.class);
        NeoForge.EVENT_BUS.register(Abyssal_landsPortalEvents.class);
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(RITUAL_PEDESTAL_BE.get(), DisplayPedestalRenderer::new);
        event.registerBlockEntityRenderer(TIER_PEDESTAL_BE.get(), DisplayPedestalRenderer::new);
        event.registerBlockEntityRenderer(RITUAL_ALTAR_BE.get(), DisplayPedestalRenderer::new);
        for (GhoulFamily family : GhoulFamily.getFamilies()) {
            event.registerEntityRenderer(
                    family.entity.get(),
                    GhoulRenderer::new
            );
        }
    }

    private void registerCapabilities(RegisterCapabilitiesEvent event) {
        // Only the core Cthulhu Statue/Display Pedestal layout tracks capability power
        event.registerBlockEntity(
                PotentialEnergyCapability.BLOCK,
                RITUAL_PEDESTAL_BE.get(),
                (blockEntity, context) -> blockEntity
        );
    }
}
