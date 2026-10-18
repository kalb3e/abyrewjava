
package com.kbd.abyssal_reawoken.datagen.wood;

import com.kbd.abyssal_reawoken.AbyssalReawoken;
import com.kbd.abyssal_reawoken.fams.WoodFamily;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import static com.kbd.abyssal_reawoken.items.SnWItems.DARKWOOD;
import static com.kbd.abyssal_reawoken.items.SnWItems.DREADWOOD;

public class WoodBlockModelProvider extends BlockModelProvider {

    public WoodBlockModelProvider(
            PackOutput output,
            ExistingFileHelper existingFileHelper
    ) {
        super(output, AbyssalReawoken.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        wood(DREADWOOD);
        wood(DARKWOOD);
    }

    private void wood(WoodFamily family) {
        String name = family.planks.getId().getPath()
                .replace("_planks", "");

        String planksTexture =
                name + "/blocks/" + name + "_planks";

        String logTexture =
                name + "/blocks/" + name + "_log";

        String strippedLogTexture =
                name + "/blocks/stripped_" + name + "_log";

        String logTopTexture =
                name + "/blocks/" + name + "_log_top";

        String strippedLogTopTexture =
                name + "/blocks/stripped_" + name + "_log_top";

        String saplingTexture =
                name + "/blocks/" + name + "_sapling";

        String leavesTexture =
                name + "/blocks/" + name + "_leaves";


        // LOG
        cubeColumn(
                family.log.getId().getPath(),
                modLoc(logTexture),
                modLoc(logTopTexture)
        );

        // STRIPPED LOG
        cubeColumn(
                family.strippedLog.getId().getPath(),
                modLoc(strippedLogTexture),
                modLoc(strippedLogTopTexture)
        );

        // WOOD
        cubeColumn(
                family.wood.getId().getPath(),
                modLoc(logTexture),
                modLoc(logTexture)
        );

        // STRIPPED WOOD
        cubeColumn(
                family.strippedWood.getId().getPath(),
                modLoc(strippedLogTexture),
                modLoc(strippedLogTexture)
        );

        // PLANKS
        cubeAll(
                family.planks.getId().getPath(),
                modLoc(planksTexture)
        );

        // STAIRS
        stairs(
                family.stairs.getId().getPath(),
                modLoc(planksTexture),
                modLoc(planksTexture),
                modLoc(planksTexture)
        );

        // SLAB
        slab(
                family.slab.getId().getPath(),
                modLoc(planksTexture),
                modLoc(planksTexture),
                modLoc(planksTexture)
        );

        // FENCE
        fencePost(
                family.fence.getId().getPath(),
                modLoc(planksTexture)
        );

        fenceSide(
                family.fence.getId().getPath() + "_side",
                modLoc(planksTexture)
        );

        // FENCE GATE
        fenceGate(
                family.fenceGate.getId().getPath(),
                modLoc(planksTexture)
        );

        fenceGateOpen(
                family.fenceGate.getId().getPath() + "_open",
                modLoc(planksTexture)
        );

        fenceGateWall(
                family.fenceGate.getId().getPath() + "_wall",
                modLoc(planksTexture)
        );

        fenceGateWallOpen(
                family.fenceGate.getId().getPath() + "_wall_open",
                modLoc(planksTexture)
        );

        // PRESSURE PLATE
        pressurePlate(
                family.pressurePlate.getId().getPath(),
                modLoc(planksTexture)
        );

        // BUTTON
        button(
                family.button.getId().getPath(),
                modLoc(planksTexture)
        );

        buttonPressed(
                family.button.getId().getPath() + "_pressed",
                modLoc(planksTexture)
        );

        // TRAPDOOR
        trapdoorBottom(
                family.trapdoor.getId().getPath() + "_bottom",
                modLoc(planksTexture)
        );

        trapdoorTop(
                family.trapdoor.getId().getPath() + "_top",
                modLoc(planksTexture)
        );

        trapdoorOpen(
                family.trapdoor.getId().getPath() + "_open",
                modLoc(planksTexture)
        );

        // DOOR
        doorBottomLeft(
                family.door.getId().getPath() + "_bottom_left",
                modLoc(name + "/blocks/" + name + "_door"),
                modLoc(name + "/blocks/" + name + "_door")
        );

        doorBottomRight(
                family.door.getId().getPath() + "_bottom_right",
                modLoc(name + "/blocks/" + name + "_door"),
                modLoc(name + "/blocks/" + name + "_door")
        );

        doorTopLeft(
                family.door.getId().getPath() + "_top_left",
                modLoc(name + "/blocks/" + name + "_door"),
                modLoc(name + "/blocks/" + name + "_door")
        );

        doorTopRight(
                family.door.getId().getPath() + "_top_right",
                modLoc(name + "/blocks/" + name + "_door"),
                modLoc(name + "/blocks/" + name + "_door")
        );

        // SAPLING
        cross(
                family.sapling.getId().getPath(),
                modLoc(saplingTexture)
        ).renderType("minecraft:cutout");

        // LEAVES
        leaves(
                family.leaves.getId().getPath(),
                modLoc(leavesTexture)
        );
    }
}
