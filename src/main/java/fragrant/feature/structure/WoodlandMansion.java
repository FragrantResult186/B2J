package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.biome.source.BiomeSource;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class WoodlandMansion extends TriangularStructure<WoodlandMansion> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_11_0, new Config(80, 20, 10387319));

    public WoodlandMansion(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public WoodlandMansion(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "woodland_mansion";
    }

    @Override
    public boolean canSpawn(int chunkX, int chunkZ, BiomeSource source) {
        if (!super.canSpawn(chunkX, chunkZ, source)) return false;
        return source.iterateUniqueBiomes((chunkX << 4) + 9, (chunkZ << 4) + 9, 32, this::isValidBiome);
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.DARK_FOREST || biome == Biomes.DARK_FOREST_HILLS;
    }
}
