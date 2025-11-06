package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.biome.source.BiomeSource;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.CPos;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class AncientCity extends TriangularStructure<AncientCity> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_9_0, new Config(24, 16, 20083232));

    public AncientCity(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public AncientCity(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "ancient_city";
    }

    @Override
    public boolean canStart(Data<AncientCity> data, long structureSeed, ChunkRand rand) {
        return super.canStart(data, structureSeed, rand);
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.END;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.DEEP_DARK;
    }

    @Override
    public boolean canSpawn(CPos cPos, BiomeSource source) {
        return this.canSpawn(cPos.getX(), cPos.getZ(), source);
    }

    @Override
    public boolean canSpawn(int chunkX, int chunkZ, BiomeSource source) {
        return true;
    }
}
