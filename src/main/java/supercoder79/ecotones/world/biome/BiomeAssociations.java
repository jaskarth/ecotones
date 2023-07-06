package supercoder79.ecotones.world.biome;

import net.minecraft.registry.tag.BiomeTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.world.biome.Biome;
import supercoder79.ecotones.util.compat.AurorasDecoCompat;

import java.util.Arrays;
import java.util.stream.Stream;

public class BiomeAssociations {
    public static final BiomeAssociation DEFAULT_STRUCTURES = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE);
    public static final BiomeAssociation DEFAULT = new BiomeAssociation(DEFAULT_STRUCTURES, BiomeTags.RUINED_PORTAL_STANDARD_HAS_STRUCTURE);
    public static final BiomeAssociation DESERT_LIKE = new BiomeAssociation(DEFAULT_STRUCTURES, BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE, BiomeTags.VILLAGE_DESERT_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_DESERT_HAS_STRUCTURE, BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE, AurorasDecoCompat.WAY_SIGN_DESERT);
    public static final BiomeAssociation LONELY_SAVANNA_LIKE = new BiomeAssociation(DEFAULT, BiomeTags.IS_SAVANNA);
    public static final BiomeAssociation SAVANNA_LIKE = new BiomeAssociation(LONELY_SAVANNA_LIKE, BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE, BiomeTags.VILLAGE_SAVANNA_HAS_STRUCTURE);
    public static final BiomeAssociation FOREST_LIKE = new BiomeAssociation(DEFAULT, BiomeTags.IS_FOREST);
    public static final BiomeAssociation JUNGLE_LIKE = new BiomeAssociation(DEFAULT_STRUCTURES, BiomeTags.RUINED_PORTAL_JUNGLE_HAS_STRUCTURE, BiomeTags.JUNGLE_TEMPLE_HAS_STRUCTURE, BiomeTags.IS_JUNGLE, BiomeTags.TRAIL_RUINS_HAS_STRUCTURE);
    public static final BiomeAssociation LONLEY_SPRUCE_LIKE = new BiomeAssociation(DEFAULT, BiomeTags.IS_TAIGA);
    public static final BiomeAssociation SPRUCE_LIKE = new BiomeAssociation(LONLEY_SPRUCE_LIKE, BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE, BiomeTags.VILLAGE_TAIGA_HAS_STRUCTURE, AurorasDecoCompat.WAY_SIGN_SPRUCE, BiomeTags.TRAIL_RUINS_HAS_STRUCTURE);
    public static final BiomeAssociation PLAINS_LIKE = new BiomeAssociation(DEFAULT, BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE, BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE, AurorasDecoCompat.WAY_SIGN_OAK);
    public static final BiomeAssociation LONELY_PLAINS_LIKE = new BiomeAssociation(DEFAULT);
    public static final BiomeAssociation MOUNTAIN_LIKE = new BiomeAssociation(DEFAULT_STRUCTURES, BiomeTags.IS_MOUNTAIN, BiomeTags.RUINED_PORTAL_MOUNTAIN_HAS_STRUCTURE);
    public static final BiomeAssociation SWAMP_LIKE = new BiomeAssociation(DEFAULT_STRUCTURES, BiomeTags.SWAMP_HUT_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_SWAMP_HAS_STRUCTURE, BiomeTags.SPAWNS_WARM_VARIANT_FROGS, BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS);
    public static final BiomeAssociation BEACH_LIKE = new BiomeAssociation(DEFAULT_STRUCTURES, BiomeTags.IS_BEACH, BiomeTags.BURIED_TREASURE_HAS_STRUCTURE, BiomeTags.SHIPWRECK_BEACHED_HAS_STRUCTURE);
    public static final BiomeAssociation MESA_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_MESA_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.IS_BADLANDS);

    // Partials
    public static final BiomeAssociation DARK_OAK = new BiomeAssociation(BiomeTags.WOODLAND_MANSION_HAS_STRUCTURE, AurorasDecoCompat.WAY_SIGN_OAK);
    public static final BiomeAssociation BIRCH = new BiomeAssociation(AurorasDecoCompat.WAY_SIGN_BIRCH);


    public record BiomeAssociation(TagKey<Biome>... tags) {
        public BiomeAssociation(Object... any) {
            this(map(any));
        }
        
        private static TagKey<Biome>[] map(Object... any) {
            return (TagKey<Biome>[]) Arrays.stream(any)
                    .flatMap(o -> {
                        if (o instanceof TagKey) {
                            return Stream.of((TagKey) o);
                        } else if (o instanceof BiomeAssociation) {
                            return Arrays.stream((TagKey[]) ((BiomeAssociation) o).tags());
                        } else {
                            throw new IllegalArgumentException("Invalid type: " + o.getClass());
                        }
                    }).toArray(TagKey[]::new);
        }

    }
}
