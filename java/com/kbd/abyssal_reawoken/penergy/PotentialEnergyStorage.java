package com.kbd.abyssal_reawoken.penergy;

import net.minecraft.nbt.CompoundTag;
import java.util.function.IntSupplier;

public class PotentialEnergyStorage implements IPotentialEnergy {
    private int pe;
    private final IntSupplier maxPeSupplier;
    private final Runnable onChange;

    // ➡️ Uses IntSupplier to cleanly handle dynamic background method calls!
    public PotentialEnergyStorage(IntSupplier maxPeSupplier, Runnable onChange) {
        this.maxPeSupplier = maxPeSupplier;
        this.onChange = onChange;
        this.pe = 0;
    }

    @Override
    public int getPE() {
        return this.pe;
    }

    @Override
    public int getMaxPE() {
        return this.maxPeSupplier.getAsInt();
    }

    @Override
    public void setPE(int amount) {
        this.pe = Math.max(0, Math.min(amount, getMaxPE()));
        this.onChange.run();
    }

    public void save(CompoundTag tag) {
        tag.putInt("PE", this.pe);
    }

    public void load(CompoundTag tag) {
        this.pe = tag.getInt("PE");
    }
}
