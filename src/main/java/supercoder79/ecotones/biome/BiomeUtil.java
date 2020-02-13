package supercoder79.ecotones.biome;

import com.terraformersmc.terraform.biome.builder.TerraformBiome;
import net.fabricmc.fabric.api.biomes.v1.FabricBiomes;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.layers.generation.MountainLayer;

public class BiomeUtil {
    public static Biome register(Identifier name, TerraformBiome.Builder builder) {
        Integer[] ids = new Integer[2];
        Biome ret = Registry.register(Registry.BIOME, name, builder.build());
        ids[0] = Registry.BIOME.getRawId(Registry.register(Registry.BIOME,
                new Identifier(name.getNamespace(), name.getPath().concat("_hilly")),
                builder.depth(ret.getDepth()+0.75f).scale(ret.getScale()+0.2f).temperature(ret.getTemperature()-0.15f).build()));

        ids[1] = Registry.BIOME.getRawId(Registry.register(Registry.BIOME,
                new Identifier(name.getNamespace(), name.getPath().concat("_mountainous")),
                builder.depth(ret.getDepth()+1.5f).scale(ret.getScale()+0.6f).temperature(ret.getTemperature()-0.3f).build()));

        MountainLayer.Biome2MountainBiomeMap.put(Registry.BIOME.getRawId(ret), ids);

        FabricBiomes.addSpawnBiome(ret);
        return ret;
    }
}
