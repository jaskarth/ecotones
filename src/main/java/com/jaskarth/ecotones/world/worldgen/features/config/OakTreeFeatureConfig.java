package com.jaskarth.ecotones.world.worldgen.features.config;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.feature.FeatureConfig;

public class OakTreeFeatureConfig implements FeatureConfig {
    public static final Codec<OakTreeFeatureConfig> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Codec.INT.fieldOf("height").forGetter(config -> config.height),
            Codec.INT.fieldOf("random_height").forGetter(config -> config.randomHeight)
    ).apply(instance, OakTreeFeatureConfig::new));

    private int height;
    private int randomHeight;
    private int branchCount;
    private int randomBranchCount;
    private int branchLength;
    private int randomBranchLength;

    public OakTreeFeatureConfig(int height, int randomHeight) {
        this();
        this.height = height;
        this.randomHeight = randomHeight;
    }

    public OakTreeFeatureConfig() {
        this.height = 9;
        this.randomHeight = 4;
        this.branchCount = 2;
        this.randomBranchCount = 3;
        this.branchLength = 3;
        this.randomBranchLength = 3;
    }

    public OakTreeFeatureConfig height(int height) {
        this.height = height;

        return this;
    }

    public OakTreeFeatureConfig randomHeight(int randomHeight) {
        this.randomHeight = randomHeight;

        return this;
    }

    public OakTreeFeatureConfig branchCount(int branchCount) {
        this.branchCount = branchCount;

        return this;
    }

    public OakTreeFeatureConfig randomBranchCount(int randomBranchCount) {
        this.randomBranchCount = randomBranchCount;

        return this;
    }

    public OakTreeFeatureConfig branchLength(int branchLength) {
        this.branchLength = branchLength;

        return this;
    }

    public OakTreeFeatureConfig randomBranchLength(int randomBranchLength) {
        this.randomBranchLength = randomBranchLength;

        return this;
    }

    public int getHeight() {
        return height;
    }

    public int getRandomHeight() {
        return randomHeight;
    }

    public int getBranchCount() {
        return branchCount;
    }

    public int getRandomBranchCount() {
        return randomBranchCount;
    }

    public int getBranchLength() {
        return branchLength;
    }

    public int getRandomBranchLength() {
        return randomBranchLength;
    }
}
