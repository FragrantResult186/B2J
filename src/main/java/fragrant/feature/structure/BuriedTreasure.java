package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.state.Dimension;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class BuriedTreasure extends TriangularStructure<BuriedTreasure> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_0_0, new Config(4, 2, 16842397));

    public BuriedTreasure(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public BuriedTreasure(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "buried_treasure";
    }

    @Override
    public boolean canStart(Data<BuriedTreasure> data, long structureSeed, ChunkRand rand) {
        return super.canStart(data, structureSeed, rand);
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.BEACH || biome == Biomes.SNOWY_BEACH || biome == Biomes.STONE_SHORE;
    }

    @Override
    public Data<BuriedTreasure> at(int chunkX, int chunkZ) {
        return new Data<>(this, chunkX, chunkZ);
    }

}
