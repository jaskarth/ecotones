package supercoder79.ecotones.world.biome;

import it.unimi.dsi.fastutil.HashCommon;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.BuiltinRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.ConfiguredStructureFeature;
import net.minecraft.world.gen.surfacebuilder.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.SurfaceConfig;
import supercoder79.ecotones.world.gen.BiomeGenData;

import java.util.HashMap;
import java.util.Map;

public class EcotonesBiomeBuilder {
    public static final Map<Biome, BiomeGenData> OBJ2DATA = new HashMap<>();
    private final Biome.Builder builder;
    private final SpawnSettings.Builder spawnSettings;
    private final GenerationSettings.Builder generationSettings;
    private final BiomeEffects.Builder biomeEffects;

    private double hilliness = 1.0;
    private double volatility = 1.0;

    public EcotonesBiomeBuilder() {
        this.builder = new Biome.Builder();
        this.spawnSettings = new SpawnSettings.Builder();
        this.generationSettings = new GenerationSettings.Builder();
        this.biomeEffects = new BiomeEffects.Builder();

        // Defaults
        this.biomeEffects.waterColor(4159204);
        this.biomeEffects.waterFogColor(329011);
        this.biomeEffects.fogColor(12638463);

        // TODO
        this.builder.category(Biome.Category.PLAINS);
    }

    protected void category(Biome.Category category) {
        this.builder.category(category);
    }

    protected void surfaceBuilder(ConfiguredSurfaceBuilder<?> surfaceBuilder) {
        this.generationSettings.surfaceBuilder(surfaceBuilder);
    }

    protected <C extends SurfaceConfig> void surfaceBuilder(SurfaceBuilder<C> surfaceBuilder, C config) {
        this.generationSettings.surfaceBuilder(surfaceBuilder.withConfig(config));
    }

    protected void depth(float depth) {
        this.builder.depth(depth);
    }

    protected void scale(float scale) {
        this.builder.scale(scale);
    }

    protected void hilliness(double hilliness) {
        this.hilliness = hilliness;
    }

    protected void volatility(double volatility) {
        this.volatility = volatility;
    }

    protected void downfall(float downfall) {
        this.builder.downfall(downfall);
    }

    protected void temperature(float temperature) {
        this.biomeEffects.skyColor(getSkyColor(temperature));
        this.builder.temperature(temperature);
    }

    protected void precipitation(Biome.Precipitation precipitation) {
        this.builder.precipitation(precipitation);
    }

    protected void addSpawn(SpawnGroup group, SpawnSettings.SpawnEntry entry) {
        this.spawnSettings.spawn(group, entry);
    }

    protected void playerSpawnFriendly() {
        this.spawnSettings.playerSpawnFriendly();
    }

    protected void grassColor(int grassColor) {
        this.biomeEffects.grassColor(grassColor);
    }

    protected void foliageColor(int foliageColor) {
        this.biomeEffects.foliageColor(foliageColor);
    }

    protected void skyColor(int skyColor) {
        this.biomeEffects.skyColor(skyColor);
    }

    protected void waterColor(int waterColor) {
        this.biomeEffects.waterColor(waterColor);
    }

    protected void waterFogColor(int waterFogColor) {
        this.biomeEffects.waterFogColor(waterFogColor);
    }

    protected void loopSound(SoundEvent loopSound) {
        this.biomeEffects.loopSound(loopSound);
    }

    protected void particleConfig(BiomeParticleConfig particleConfig) {
        this.biomeEffects.particleConfig(particleConfig);
    }

    protected void addFeature(GenerationStep.Feature step, ConfiguredFeature<?, ?> feature) {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, new Identifier("ecotones", "ecotones_auto_registered_" + feature.hashCode()), feature);
        this.generationSettings.feature(step, feature);
    }

    protected void addStructureFeature(ConfiguredStructureFeature<?, ?> structureFeature) {
        if (BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE.getRawId(structureFeature) == -1) {
            String path = "ecotones_auto_registed_structure_" + HashCommon.mix(structureFeature.hashCode());
            Registry.register(BuiltinRegistries.CONFIGURED_STRUCTURE_FEATURE, new Identifier("ecotones", path), structureFeature);
        }

        this.generationSettings.structureFeature(structureFeature);
    }

    public Biome build() {
        this.builder.effects(this.biomeEffects.build());
        this.builder.generationSettings(this.generationSettings.build());
        this.builder.spawnSettings(this.spawnSettings.build());

        Biome biome = builder.build();
        OBJ2DATA.put(biome, new BiomeGenData(this.volatility, this.hilliness));

        return biome;
    }

    public GenerationSettings.Builder getGenerationSettings() {
        return generationSettings;
    }

    public SpawnSettings.Builder getSpawnSettings() {
        return spawnSettings;
    }

    private static int getSkyColor(float temperature) {
        float f = temperature / 3.0F;
        f = MathHelper.clamp(f, -1.0F, 1.0F);
        return MathHelper.hsvToRgb(0.62222224F - f * 0.05F, 0.5F + f * 0.1F, 1.0F);
    }
}
