package fragrant.feature.decorator;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class DesertWell extends Decorator<DesertWell.Config, DesertWell.Data> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_18_0, new Config("minecraft:desert_after_surface_desert_well_feature", 1, 500));

    public DesertWell(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public DesertWell(Config config, MCVersion version) {
        super(config, version);
        this.setChance(config.numerator, config.denominator);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Data getData(long structureSeed, int chunkX, int chunkZ, ChunkRand rand) {
        rand.setDecorationSeed(structureSeed, chunkX, chunkZ, this.getSalt(), this.getVersion());
        if (!this.shouldGenerate(rand)) return null;
        int blockX = rand.nextInt(16) + chunkX * 16;
        int blockZ = rand.nextInt(16) + chunkZ * 16;
        return new DesertWell.Data(this, blockX, blockZ);
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.DESERT;
    }

    public static String name() {
        return "desert_well";
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

    public static class Data extends Decorator.Data<DesertWell> {
        public final int blockX;
        public final int blockZ;
        public final int offsetX;
        public final int offsetZ;

        public Data(DesertWell data, int chunkX, int chunkZ) {
            super(data, chunkX, chunkZ);
            this.blockX = chunkX >> 4;
            this.blockZ = chunkZ >> 4;
            this.offsetX = chunkX & 15;
            this.offsetZ = chunkZ & 15;
        }
    }

}
