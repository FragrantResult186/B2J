package fragrant.feature.decorator;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.CPos;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class Fossil extends Decorator<Fossil.Config, Fossil.Data> {

    private static final VersionMap<Config> COAL_CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_16_0, new Config("minecraft:desert_or_swamp_after_surface_fossil_feature", 1, 64));

    private static final VersionMap<Config> DIAMONDS_CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_16_0, new Config("minecraft:desert_or_swamp_after_surface_fossil_deepslate_feature", 1, 64));

    private static final VersionMap<Config> NETHER_CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_16_0, new Config("minecraft:soulsand_valley_fossil_surface_feature", 2, 6))
            .add(MCVersion.v1_21_90, new Config("minecraft:soulsand_valley_fossil_surface_feature", 1, 2));

    private final Type type;

    public enum Type {
        COAL,
        DIAMONDS,
        NETHER
    }

    public Fossil(Type type, MCVersion version) {
        this(type, getConfigsForType(type).getAsOf(version), version);
    }

    public Fossil(Type type, Config config, MCVersion version) {
        super(config, version);
        this.type = type;
        this.setChance(config.numerator, config.denominator);
    }

    public static String name() {
        return "fossil";
    }

    @Override
    public String getName() {
        return name();
    }

    public Type getType() {
        return type;
    }

    public boolean hasDriedGhast(long structureSeed, CPos cPos, ChunkRand rand) {
        if (type != Type.NETHER && !getVersion().isOlderThan(MCVersion.v1_21_90)) return false;
        rand.setDecorationSeed(structureSeed, cPos.getX(), cPos.getZ(), getSalt(), getVersion());
        int rotation = rand.nextInt(4);
        int variant = rand.nextInt(14);
        return rand.nextFloat() < 0.5F;
    }

    @Override
    public Data getData(long structureSeed, int chunkX, int chunkZ, ChunkRand rand) {
        rand.setDecorationSeed(structureSeed, chunkX, chunkZ, getSalt(), getVersion());
        if (!this.shouldGenerate(rand)) return null;
        switch (type) {
            case COAL:
            case DIAMONDS:
                return this.generateOverworldFossil(chunkX, chunkZ, rand);
            case NETHER:
                return this.generateNetherFossil(structureSeed, chunkX, chunkZ, rand);
            default:
                return null;
        }
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        switch (type) {
            case COAL:
            case DIAMONDS:
                return biome == Biomes.DESERT || biome == Biomes.SWAMP;
            case NETHER:
                return biome == Biomes.SOUL_SAND_VALLEY;
            default:
                return false;
        }
    }

    @Override
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        rand.setDecorationSeed(structureSeed, data.chunkX, data.chunkZ, getSalt(), getVersion());
        return this.shouldGenerate(rand);
    }

    @Override
    public Dimension getValidDimension() {
        return type == Type.NETHER ? Dimension.NETHER : Dimension.OVERWORLD;
    }

    public Data at(int blockX, int blockZ) {
        return new Data(this, blockX, blockZ, -1, -1);
    }

    private static VersionMap<Config> getConfigsForType(Type type) {
        switch (type) {
            case COAL:
                return COAL_CONFIGS;
            case DIAMONDS:
                return DIAMONDS_CONFIGS;
            case NETHER:
                return NETHER_CONFIGS;
            default:
                return new VersionMap<>();
        }
    }

    private Data generateOverworldFossil(int chunkX, int chunkZ, ChunkRand rand) {
        int x = chunkX * 16 + 8;
        int z = chunkZ * 16 + 8;
        int rotation = rand.nextInt(4);
        int variant = rand.nextInt(8);
        return new Data(this, x, z, variant, rotation);
    }

    private Data generateNetherFossil(long structureSeed, int chunkX, int chunkZ, ChunkRand rand) {
        int x = rand.nextInt(16) + chunkX * 16;
        int z = rand.nextInt(16) + chunkZ * 16;
        int y = rand.nextInt(124);// ?
        rand.setDecorationSeed(structureSeed, chunkX, chunkZ, getSalt(), getVersion());
        int rotation = rand.nextInt(4);
        int variant = rand.nextInt(14);
        boolean placeDriedGhast = rand.nextFloat() < 0.5F;
        return new Data(this, x, y, z, variant, rotation, placeDriedGhast);
    }

    private int getSalt() {
        return getHash(getConfig().featureName);
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

    public static class Data extends Decorator.Data<Fossil> {
        public final int blockX;
        public final int blockY;
        public final int blockZ;
        public final int offsetX;
        public final int offsetZ;
        public final int variant;
        public final int rotation;
        public final boolean placeDriedGhast;

        public Data(Fossil feature, int blockX, int blockZ, int variant, int rotation) {
            this(feature, blockX, 0, blockZ, variant, rotation, false);
        }

        public Data(Fossil feature, int blockX, int blockY, int blockZ, int variant, int rotation, boolean placeDriedGhast) {
            super(feature, blockX >> 4, blockZ >> 4);
            this.blockX = blockX;
            this.blockY = blockY;
            this.blockZ = blockZ;
            this.offsetX = blockX & 15;
            this.offsetZ = blockZ & 15;
            this.variant = variant;
            this.rotation = rotation;
            this.placeDriedGhast = placeDriedGhast;
        }
    }
}