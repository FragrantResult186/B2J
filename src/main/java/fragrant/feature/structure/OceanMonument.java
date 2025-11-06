package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.biome.source.BiomeSource;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class OceanMonument extends TriangularStructure<OceanMonument> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_8_0, new Config(32, 27, 10387313));

    public OceanMonument(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public OceanMonument(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "ocean_monument";
    }

    @Override
    public boolean canSpawn(int chunkX, int chunkZ, BiomeSource source) {
        if (!super.canSpawn(chunkX, chunkZ, source)) return false;

        if (!source.iterateUniqueBiomes((chunkX << 4) + 9, (chunkZ << 4) + 9, 16, this::isValidBiome)) {
            return false;
        }

        return source.iterateUniqueBiomes((chunkX << 4) + 9, (chunkZ << 4) + 9, 29, this::isOceanOrRiver);
    }

    public boolean isOceanOrRiver(Biome biome) {
        return biome.getCategory() == Biome.Category.OCEAN || biome.getCategory() == Biome.Category.RIVER;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.DEEP_COLD_OCEAN || biome == Biomes.DEEP_FROZEN_OCEAN || biome == Biomes.DEEP_LUKEWARM_OCEAN
                || biome == Biomes.DEEP_OCEAN || biome == Biomes.DEEP_WARM_OCEAN;
    }

}
