package com.jaskarth.ecotones.world.worldgen.biome.cave;

import net.minecraft.registry.tag.BlockTags;
import net.minecraft.structure.rule.TagMatchRuleTest;
import net.minecraft.world.gen.YOffset;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import com.jaskarth.ecotones.api.CaveBiome;
import com.jaskarth.ecotones.world.blocks.EcotonesBlocks;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesConfiguredFeature;

public class LimestoneCaveBiome extends CaveBiome {
    public static LimestoneCaveBiome INSTANCE;

    public static void init() {
        INSTANCE = new LimestoneCaveBiome();
    }

    protected LimestoneCaveBiome() {
        this.addFeature(EcotonesConfiguredFeature.wrap(Feature.ORE,
                        new OreFeatureConfig(new TagMatchRuleTest(BlockTags.BASE_STONE_OVERWORLD), EcotonesBlocks.LIMESTONE.getDefaultState(), 33))
                    .uniformRange(YOffset.fixed(0), YOffset.fixed(48))
                    .spreadHorizontally()
                    .repeat(15).placed());
    }
}
