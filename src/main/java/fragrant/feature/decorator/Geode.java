package fragrant.feature.decorator;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class Geode extends Decorator<Geode.Config, Geode.Data> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_17_0, new Config("minecraft:overworld_amethyst_geode_feature", 1, 53))
            .add(MCVersion.v1_18_0, new Config("minecraft:overworld_amethyst_geode_feature", 1, 24));

    public Geode(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public Geode(Config config, MCVersion version) {
        super(config, version);
        this.setChance(config.numerator, config.denominator);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Data getData(long structureSeed, int chunkX, int chunkZ, ChunkRand rand) {
        return null;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.DESERT;
    }

    public static String name() {
        return "amethyst_geode";
    }

    @Override
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        rand.setDecorationSeed(structureSeed, data.chunkX, data.chunkZ, this.getSalt(), this.getVersion());
        return this.shouldGenerate(rand);
    }

    private int getSalt() {
        return super.getHash(this.getFeatureName());
    }

    public String getFeatureName() {
        return this.getConfig().featureName;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    public Data at(int chunkX, int chunkZ) {
        return new Data(this, chunkX, chunkZ);
    }

    public static class Config extends Decorator.Config {
        public final String featureName;
        public final int numerator;
        public final int denominator;

        public Config(String featureName, int numerator, int denominator) {
            this.featureName = featureName;
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }

    public static class Data extends Decorator.Data<Geode> {
        public final int blockX;
        public final int blockZ;
        public final int offsetX;
        public final int offsetZ;

        public Data(Geode data, int chunkX, int chunkZ) {
            super(data, chunkX, chunkZ);
            this.blockX = chunkX >> 4;
            this.blockZ = chunkZ >> 4;
            this.offsetX = chunkX & 15;
            this.offsetZ = chunkZ & 15;
        }
    }

}
