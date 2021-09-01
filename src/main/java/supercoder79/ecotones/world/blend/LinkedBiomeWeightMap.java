package supercoder79.ecotones.world.blend;

import net.minecraft.world.biome.Biome;

public final class LinkedBiomeWeightMap {
    private Biome biome;
    private double[] weights;
    private LinkedBiomeWeightMap next;
    
    public LinkedBiomeWeightMap(Biome biome, LinkedBiomeWeightMap next) {
        this.biome = biome;
        this.next = next;
    }
    
    public LinkedBiomeWeightMap(Biome biome, int chunkColumnCount, LinkedBiomeWeightMap next) {
        this.biome = biome;
        this.weights = new double[chunkColumnCount];
        this.next = next;
    }
    
    public Biome getBiome() {
        return biome;
    }
    
    public double[] getWeights() {
        return weights;
    }
    
    public void setWeights(double[] weights) {
        this.weights = weights;
    }
    
    public LinkedBiomeWeightMap getNext() {
        return next;
    }
}