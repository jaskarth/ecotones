package supercoder79.ecotones.world.biome;

import net.fabricmc.fabric.api.tag.convention.v1.ConventionalBiomeTags;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.world.biome.Biome;

public class BiomeAssociations {
    public static final BiomeAssociation DEFAULT = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_STANDARD_HAS_STRUCTURE);
    public static final BiomeAssociation DESERT_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE, BiomeTags.VILLAGE_DESERT_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_DESERT_HAS_STRUCTURE, BiomeTags.DESERT_PYRAMID_HAS_STRUCTURE);
    public static final BiomeAssociation SAVANNA_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_STANDARD_HAS_STRUCTURE, BiomeTags.IS_SAVANNA, BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE, BiomeTags.VILLAGE_SAVANNA_HAS_STRUCTURE);
    public static final BiomeAssociation LONELY_SAVANNA_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_STANDARD_HAS_STRUCTURE, BiomeTags.IS_SAVANNA);
    public static final BiomeAssociation FOREST_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_STANDARD_HAS_STRUCTURE, BiomeTags.IS_FOREST);
    public static final BiomeAssociation JUNGLE_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_JUNGLE_HAS_STRUCTURE, BiomeTags.JUNGLE_TEMPLE_HAS_STRUCTURE, BiomeTags.IS_JUNGLE);
    public static final BiomeAssociation SPRUCE_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_STANDARD_HAS_STRUCTURE, BiomeTags.IS_TAIGA, BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE, BiomeTags.VILLAGE_TAIGA_HAS_STRUCTURE);
    public static final BiomeAssociation LONLEY_SPRUCE_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_STANDARD_HAS_STRUCTURE, BiomeTags.IS_TAIGA);
    public static final BiomeAssociation PLAINS_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_STANDARD_HAS_STRUCTURE, BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE, BiomeTags.VILLAGE_PLAINS_HAS_STRUCTURE);
    public static final BiomeAssociation LONELY_PLAINS_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_STANDARD_HAS_STRUCTURE);
    public static final BiomeAssociation MOUNTAIN_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.IS_MOUNTAIN, BiomeTags.RUINED_PORTAL_MOUNTAIN_HAS_STRUCTURE);
    public static final BiomeAssociation SWAMP_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.SWAMP_HUT_HAS_STRUCTURE, BiomeTags.RUINED_PORTAL_SWAMP_HAS_STRUCTURE, BiomeTags.SPAWNS_WARM_VARIANT_FROGS, BiomeTags.ALLOWS_SURFACE_SLIME_SPAWNS);
    public static final BiomeAssociation BEACH_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.IS_BEACH, BiomeTags.BURIED_TREASURE_HAS_STRUCTURE, BiomeTags.SHIPWRECK_BEACHED_HAS_STRUCTURE);
    public static final BiomeAssociation MESA_LIKE = new BiomeAssociation(BiomeTags.IS_OVERWORLD, BiomeTags.MINESHAFT_MESA_HAS_STRUCTURE, BiomeTags.STRONGHOLD_HAS_STRUCTURE, BiomeTags.IS_BADLANDS);

    // Partials
    public static final BiomeAssociation DARK_OAK = new BiomeAssociation(BiomeTags.WOODLAND_MANSION_HAS_STRUCTURE);


    public record BiomeAssociation(TagKey<Biome>... tags) {

    }
}
