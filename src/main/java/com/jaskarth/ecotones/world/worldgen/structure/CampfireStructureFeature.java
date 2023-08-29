package com.jaskarth.ecotones.world.worldgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.structure.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.SingleStateFeatureConfig;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;
import com.jaskarth.ecotones.world.worldgen.structure.gen.CampfireGenerator;

import java.util.Optional;
import java.util.Random;

public class CampfireStructureFeature extends Structure {
    public static final Codec<CampfireStructureFeature> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Structure.configCodecBuilder(instance),
            SingleStateFeatureConfig.CODEC.fieldOf("state_fc").forGetter(c -> c.state)
    ).apply(instance, CampfireStructureFeature::new));
    private final SingleStateFeatureConfig state;

    public CampfireStructureFeature(Structure.Config config, SingleStateFeatureConfig state) {
        super(config);

        this.state = state;
    }

    private void addPieces(StructurePiecesCollector collector, Context context) {
        ChunkPos pos = context.chunkPos();
        ChunkRandom random = context.random();

        CampfireGenerator.generate(new BlockPos(ChunkSectionPos.getBlockCoord(pos.x), 64, ChunkSectionPos.getBlockCoord(pos.z)),
                collector, new Random(random.nextLong()), this.state.state.getBlock());
    }

    @Override
    public Optional<StructurePosition> getStructurePosition(Context context) {
        return getStructurePosition(context, Heightmap.Type.OCEAN_FLOOR_WG, collector -> addPieces(collector, context));
    }

    @Override
    public StructureType<?> getType() {
        return EcotonesStructureTypes.CAMPFIRE;
    }
}
