package com.jaskarth.ecotones.world.worldgen.features.entity;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.util.FeatureContext;
import com.jaskarth.ecotones.world.worldgen.features.EcotonesFeature;

import java.util.List;

public class BeehiveFeature extends EcotonesFeature<DefaultFeatureConfig> {
    public static final List<BlockState> FLOWERS = ImmutableList.of(Blocks.ORANGE_TULIP.getDefaultState(), Blocks.PINK_TULIP.getDefaultState(), Blocks.RED_TULIP.getDefaultState(), Blocks.WHITE_TULIP.getDefaultState(), Blocks.OXEYE_DAISY.getDefaultState());

    public BeehiveFeature(Codec<DefaultFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(FeatureContext<DefaultFeatureConfig> context) {
        StructureWorldAccess world = context.getWorld();
        BlockPos pos = context.getOrigin();
        Random random = context.getRandom();

        BlockPos.Mutable mutable = pos.mutableCopy();

        // set beehive position
        int y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, mutable.getX(), mutable.getZ());
        mutable.setY(y);
        if (!canSpawnAt(world, mutable)) {
            return false;
        }

        world.setBlockState(mutable, Blocks.BEE_NEST.getDefaultState(), 3);

        // bee entities in hive
        BeehiveBlockEntity beehive = (BeehiveBlockEntity)world.getBlockEntity(mutable);
        int beeAmt = 1 + random.nextInt(3);

        if (beehive != null) {
            for (int i = 0; i < beeAmt; ++i) {
                NbtCompound nbt = new NbtCompound();
                nbt.putString("id", Registries.ENTITY_TYPE.getId(EntityType.BEE).toString());
                beehive.addBee(nbt, random.nextInt(600), false);
            }
        }

        // set surrounding flowers
        for (int i = 0; i < random.nextInt(4) + 12; i++) {
            mutable.set(pos, random.nextInt(8) - random.nextInt(8), 0, random.nextInt(8) - random.nextInt(8));
            y = world.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, mutable.getX(), mutable.getZ());
            mutable.setY(y);

            BlockState flower = FLOWERS.get(random.nextInt(FLOWERS.size()));

            if (flower.canPlaceAt(world, mutable) && canSpawnAt(world, mutable)) {
                world.setBlockState(mutable, flower, 3);
            }
        }

        return false;
    }

    private static boolean canSpawnAt(ServerWorldAccess world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return state.isAir();
    }
}
