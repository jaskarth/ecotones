package supercoder79.ecotones.features;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;
import supercoder79.ecotones.features.config.ShrubConfig;

import java.util.Random;
import java.util.function.Function;

public class ShrubFeature extends Feature<ShrubConfig> {
    private final BlockState woodState = Blocks.ACACIA_LOG.getDefaultState();
    private final BlockState leafState = Blocks.OAK_LEAVES.getDefaultState().with(Properties.DISTANCE_1_7, 1);

    public ShrubFeature(Function<Dynamic<?>, ? extends ShrubConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(IWorld world, StructureAccessor accessor, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, ShrubConfig config) {
        if (world.getBlockState(pos.down()) != Blocks.GRASS_BLOCK.getDefaultState()) return true;
        int height = random.nextInt(2) + 1;
        for (int i = 0; i < height; i++) {
            world.setBlockState(pos.add(0, i, 0), config.woodState, 2);
        }

        setIfAir(world, pos.add(0, height, 0), config.leafState);

        setIfAir(world, pos.add(0, height-1, 1), config.leafState);
        setIfAir(world, pos.add(0, height-1, -1), config.leafState);
        setIfAir(world, pos.add(-1, height-1, 0), config.leafState);
        setIfAir(world, pos.add(1, height-1, 0), config.leafState);
        return true;
    }

    protected void setIfAir(IWorld world, BlockPos pos, BlockState state) {
        if (world.getBlockState(pos).isAir()) world.setBlockState(pos, state, 2);
    }
}
