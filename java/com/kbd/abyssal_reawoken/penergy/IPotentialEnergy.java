
package com.kbd.abyssal_reawoken.penergy;

public interface IPotentialEnergy {

    int getPE();

    int getMaxPE();

    void setPE(int amount);

    default boolean isFull() {
        return getPE() >= getMaxPE();
    }

    default boolean isEmpty() {
        return getPE() <= 0;
    }

    default int addPE(int amount) {
        if (amount <= 0) {
            return 0;
        }

        int accepted = Math.min(
                amount,
                Math.max(0, getMaxPE() - getPE())
        );

        if (accepted > 0) {
            setPE(getPE() + accepted);
        }

        return accepted;
    }

    default int extractPE(int amount) {
        if (amount <= 0) {
            return 0;
        }

        int extracted = Math.min(amount, Math.max(0, getPE()));

        if (extracted > 0) {
            setPE(getPE() - extracted);
        }

        return extracted;
    }
}
