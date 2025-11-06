package fragrant.feature.decorator;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class IceSpike extends Decorator<IceSpike.Config, IceSpike.Data> {

    public static final VersionMap<IceSpike.Config> CONFIGS = new VersionMap<IceSpike.Config>()
            .add(MCVersion.v1_13_0, new IceSpike.Config("minecraft:ice_plains_spikes_first_ice_spike_feature", 3));

    public IceSpike(MCVersion version) {
        super(CONFIGS.getAsOf(version), version);
    }

    public IceSpike(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "ice_spike";
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Data getData(long structureSeed, int chunkX, int chunkZ, ChunkRand rand) {
        return null;
    }

    public int getSalt() {
        return super.getHash(this.getFeatureName());
    }

    public String getFeatureName() {
        return this.getConfig().featureName;
    }

    public int getIterations() {
        return this.getConfig().iterations;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.ICE_PLAINS /*|| biome == Biomes.MUTATED_ICE_FLATS*/;
    }

    @Override
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        return true;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    public static class Config extends Decorator.Config {
        public final String featureName;
        public final int iterations;

        public Config(String featureName, int iterations) {
            this.featureName = featureName;
            this.iterations = iterations;
        }

    }

    public static class Data extends Decorator.Data<IceSpike> {
        public final int blockX;
        public final int blockZ;
        public final int offsetX;
        public final int offsetZ;

        public Data(IceSpike feature, int blockX, int blockZ) {
            super(feature, blockX >> 4, blockZ >> 4);
            this.blockX = blockX;
            this.blockZ = blockZ;
            this.offsetX = blockX & 15;
            this.offsetZ = blockZ & 15;
        }
    }
}