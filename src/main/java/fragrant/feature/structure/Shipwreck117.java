package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.state.Dimension;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class Shipwreck117 extends TriangularStructure<Shipwreck117> {
    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_13_0, new Config(10, 5, 165745295));

    public Shipwreck117(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public Shipwreck117(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "shipwreck";
    }

    @Override
    public boolean canStart(Data<Shipwreck117> data, long structureSeed, ChunkRand rand) {
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
