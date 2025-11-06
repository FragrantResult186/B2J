package fragrant.feature;

import fragrant.biome.biome.Biome;
import fragrant.biome.source.BiomeSource;
import fragrant.core.util.pos.CPos;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;

public abstract class BiomeValidatedFeature<C extends Feature.Config, D extends Feature.Data<?>> extends Feature<C, D> {

    protected Biome biome;

    protected BiomeValidatedFeature(C config, MCVersion version) {
        super(config, version);
    }

    public Biome getBiome() {
        return biome;
    }

    @Override
    public final boolean canSpawn(D data, BiomeSource source) {
        return canSpawn(data.chunkX, data.chunkZ, source);
    }

    public boolean canSpawn(CPos pos, BiomeSource source) {
        return canSpawn(pos.getX(), pos.getZ(), source);
    }

    public boolean canSpawn(int chunkX, int chunkZ, BiomeSource source) {
        this.biome = getBiomeForChunk(chunkX, chunkZ, source);
        return this.biome != null && isValidBiome(this.biome);
    }

    protected Biome getBiomeForChunk(int chunkX, int chunkZ, BiomeSource source) {
        if (source == null || source.getDimension() == null) {
            return null;
        }

        if (getVersion().isOlderThan(MCVersion.v1_16_0)) {
            return source.getBiome(
                    (chunkX << 4) + 9,
                    0,
                    (chunkZ << 4) + 9
            );
        } else {
            return source.getBiomeForNoiseGen(
                    (chunkX << 2) + 2,
                    0,
                    (chunkZ << 2) + 2
            );
        }
    }

    public abstract boolean isValidBiome(Biome biome);
}
