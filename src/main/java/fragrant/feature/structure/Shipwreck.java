package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.state.Dimension;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class Shipwreck extends UniformStructure<Shipwreck> {
    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_17_0, new Config(24, 20, 165745295));

    public Shipwreck(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public Shipwreck(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "shipwreck";
    }

    @Override
    public boolean canStart(Data<Shipwreck> data, long structureSeed, ChunkRand rand) {
        return super.canStart(data, structureSeed, rand);
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome.getCategory() == Biome.Category.OCEAN || biome == Biomes.BEACH || biome == Biomes.SNOWY_BEACH;
    }
}
