package fragrant.biome.source;

import fragrant.biome.biome.Biome;
import fragrant.biome.layer.IntBiomeLayer;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.BPos;
import fragrant.core.version.MCVersion;

public class FakeBiomeSource extends LayeredBiomeSource<IntBiomeLayer> {
    public FakeBiomeSource(MCVersion version, long worldSeed) {
        super(version, worldSeed);
    }

    @Override
    public Dimension getDimension() {
        return null;
    }

    @Override
    public Biome getBiome(BPos bpos) {
        return null;
    }

    @Override
    public Biome getBiome(int x, int y, int z) {
        return null;
    }

    @Override
    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        return null;
    }
}
