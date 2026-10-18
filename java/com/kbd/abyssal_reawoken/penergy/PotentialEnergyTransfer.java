
package com.kbd.abyssal_reawoken.penergy;

import com.kbd.abyssal_reawoken.ModDataComponents;
import com.kbd.abyssal_reawoken.items.NecronomiconItems;

import net.minecraft.world.item.ItemStack;

public final class PotentialEnergyTransfer {

    private PotentialEnergyTransfer() {}

    /**
     * Transfers stored PE into a supported item.
     *
     * @param stack the item receiving PE
     * @param storedPE PE currently stored by the source
     * @return PE remaining in the source
     */
    public static int transferToItem(ItemStack stack, int storedPE) {
        int remaining = Math.max(0, storedPE);

        if (stack.isEmpty() || remaining == 0) {
            return remaining;
        }

        int maxPE = NecronomiconItems.getMaxPE(stack);

        if (maxPE <= 0) {
            return remaining;
        }

        int currentPE = Math.max(
                0,
                stack.getOrDefault(ModDataComponents.POTENTIAL_ENERGY, 0)
        );

        currentPE = Math.min(currentPE, maxPE);

        int accepted = Math.min(remaining, maxPE - currentPE);

        if (accepted <= 0) {
            return remaining;
        }

        stack.set(
                ModDataComponents.POTENTIAL_ENERGY,
                currentPE + accepted
        );

        return remaining - accepted;
    }

    /**
     * Extracts as much PE as possible from a supported item.
     *
     * @param stack the item losing PE
     * @param requestedPE requested amount
     * @return amount actually extracted
     */
    public static int extractFromItem(ItemStack stack, int requestedPE) {
        if (stack.isEmpty() || requestedPE <= 0) {
            return 0;
        }

        int maxPE = NecronomiconItems.getMaxPE(stack);

        if (maxPE <= 0) {
            return 0;
        }

        int currentPE = Math.max(
                0,
                Math.min(
                        stack.getOrDefault(
                                ModDataComponents.POTENTIAL_ENERGY, 0
                        ),
                        maxPE
                )
        );

        int extracted = Math.min(currentPE, requestedPE);

        stack.set(
                ModDataComponents.POTENTIAL_ENERGY,
                currentPE - extracted
        );

        return extracted;
    }
}
