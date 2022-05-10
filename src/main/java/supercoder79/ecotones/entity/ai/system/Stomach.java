package supercoder79.ecotones.entity.ai.system;

import net.minecraft.nbt.NbtCompound;

public final class Stomach {
    private double food;
    private double energy;
    private final double maxFood;
    private final double maxEnergy;
    private double metabolismRate;

    public Stomach(double food, double energy, double maxFood, double maxEnergy, double metabolismRate) {
        this.food = food;
        this.energy = energy;
        this.maxFood = maxFood;
        this.maxEnergy = maxEnergy;
        this.metabolismRate = metabolismRate;
    }

    public void tick() {
        if (this.metabolismRate > 0) {
            if (this.energy < this.maxEnergy) {
                if (this.food > this.metabolismRate) {
                    if (famish(this.metabolismRate)) {
                        this.energy += this.metabolismRate;
                    }
                }
            }
        }
    }

    public NbtCompound serialize(NbtCompound nbt) {
        nbt.putDouble("Food", this.food);
        nbt.putDouble("Energy", this.energy);
        nbt.putDouble("Metabolism", this.metabolismRate);

        return nbt;
    }

    public void deserialize(NbtCompound nbt) {
        this.food = nbt.getDouble("Food");
        this.energy = nbt.getDouble("Energy");
        this.metabolismRate = nbt.getDouble("Metabolism");
    }

    public boolean eat(double food) {
        if (this.food + food < this.maxFood) {
            this.food += food;
            return true;
        }

        return false;
    }

    public boolean famish(double food) {
        if (this.food - food >= 0) {
            this.food -= food;
            return true;
        }

        return false;
    }

    public boolean energize(double energy) {
        if (this.energy + energy < this.maxEnergy) {
            this.energy += energy;
            return true;
        }

        return false;
    }

    public boolean exhaust(double energy) {
        if (this.energy - energy >= 0) {
            this.energy -= energy;
            return true;
        }

        return false;
    }

    public double getFood() {
        return food;
    }

    public double getEnergy() {
        return energy;
    }

    public double getMetabolismRate() {
        return metabolismRate;
    }

    public void setMetabolismRate(double metabolismRate) {
        this.metabolismRate = metabolismRate;
    }
}
