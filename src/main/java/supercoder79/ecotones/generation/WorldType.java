package supercoder79.ecotones.generation;

import com.google.common.collect.Maps;
import com.mojang.datafixers.Dynamic;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorOptions;
import net.minecraft.world.level.LevelGeneratorType;
import supercoder79.ecotones.Ecotones;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.BiFunction;

public class WorldType<T extends ChunkGenerator<?>> {
    public static final Map<LevelGeneratorType, WorldType<?>> LGT_TO_WT_MAP = Maps.newHashMap();
    public static final Map<String, WorldType<?>> STR_TO_WT_MAP = Maps.newHashMap();

    public LevelGeneratorType generatorType = null;
    public final WorldTypeChunkGeneratorFactory<T> chunkGenSupplier;

    public WorldType(String name, WorldTypeChunkGeneratorFactory<T> chunkGenSupplier) {
        Constructor<LevelGeneratorType> constructor = null;
        try {
            constructor = LevelGeneratorType.class.getDeclaredConstructor(int.class, String.class, BiFunction.class);
            constructor.setAccessible(true);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

        try {
            this.generatorType = constructor.newInstance(13, name, (BiFunction<LevelGeneratorType, Dynamic<?>, LevelGeneratorOptions>) LevelGenUtil::makeChunkGenerator);
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException e) {
            System.out.println("oh god oh frick");
            e.printStackTrace();
        }
        generatorType.setCustomizable(false);

        this.chunkGenSupplier = chunkGenSupplier;

        if (this.generatorType == null) {
            throw new NullPointerException("An old world type has a null generator type: " + name + "!");
        }

        LGT_TO_WT_MAP.put(generatorType, this);
        STR_TO_WT_MAP.put(name, this);
    }

    public static final WorldType<EcotonesChunkGenerator> ECOTONES = new WorldType<>("ecotones", (world) -> {
        OverworldChunkGeneratorConfig chunkGenConfig = new OverworldChunkGeneratorConfig();
        EcotonesBiomeSourceConfig biomeSourceConfig = new EcotonesBiomeSourceConfig(world.getSeed());

        return Ecotones.WORLDGEN_TYPE.create(world, new EcotonesBiomeSource(biomeSourceConfig), chunkGenConfig);
    });

    public interface WorldTypeChunkGeneratorFactory<T extends ChunkGenerator<?>> {
        T create(World world);
    }
}