package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.biome.source.BiomeSource;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class PillagerOutpost extends TriangularStructure<PillagerOutpost> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_14_0, new Config(80, 56, 165745296));

    public PillagerOutpost(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public PillagerOutpost(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "pillager_outpost";
    }

    @Override
    public boolean canSpawn(int chunkX, int chunkZ, BiomeSource source) {
        return super.canSpawn(chunkX, chunkZ, source);
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.PLAINS || biome == Biomes.DESERT || biome == Biomes.SAVANNA
                || biome == Biomes.TAIGA || biome == Biomes.SNOWY_TUNDRA;
    }

}
