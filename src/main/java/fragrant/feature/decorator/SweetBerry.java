package fragrant.feature.decorator;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class SweetBerry extends Decorator<SweetBerry.Config, SweetBerry.Data> {

    public static final VersionMap<Config> TAIGA_CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_18_0, new Config("minecraft:taiga_after_surface_sweet_berry_bush_feature_rules", 1, 32));

    public static final VersionMap<Config> COLD_TAIGA_CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_18_0, new Config("minecraft:cold_taiga_after_surface_sweet_berry_bush_feature_rules", 1, 384));

    private final Type type;

    public enum Type {
        TAIGA,
        COLD_TAIGA
    }

    public SweetBerry(Type type, MCVersion version) {
        this(type, getConfigs(type).getAsOf(version), version);
    }

    public SweetBerry(Type type, Config config, MCVersion version) {
        super(config, version);
        this.type = type;
        this.setChance(config.numerator, config.denominator);
    }

    public static String name() {
        return "sweet_berry";
    }

    public static VersionMap<Config> getConfigs(Type type) {
        switch(type) {
            case TAIGA: return TAIGA_CONFIGS;
            case COLD_TAIGA: return COLD_TAIGA_CONFIGS;
        }
        return new VersionMap<>();
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
        switch(type) {
            case TAIGA: return biome == Biomes.TAIGA;
            case COLD_TAIGA: return biome == Biomes.SNOWY_TAIGA;
        }
        return false;
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

    public static class Data extends Decorator.Data<SweetBerry> {
        public final int blockX;
        public final int blockZ;
        public final int offsetX;
        public final int offsetZ;

        public Data(SweetBerry data, int chunkX, int chunkZ) {
            super(data, chunkX, chunkZ);
            this.blockX = chunkX >> 4;
            this.blockZ = chunkZ >> 4;
            this.offsetX = chunkX & 15;
            this.offsetZ = chunkZ & 15;
        }
    }

}