package fragrant.feature.decorator;

import fragrant.biome.biome.Biome;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class LavaLake extends Decorator<LavaLake.Config, LavaLake.Data> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_18_0, new Config(8));

    public LavaLake(MCVersion version) {
        super(CONFIGS.getAsOf(version), version);
    }

    public LavaLake(Config config) {
        super(config, null);
    }

    public static String name() {
        return "lava_lake";
    }

    @Override
    public String getName() {
        return name();
    }

    public int getChance() {
        return this.getConfig().chance;
    }

    @Override
    public Data getData(long structureSeed, int chunkX, int chunkZ, ChunkRand rand) {
        return null;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome.getCategory() == Biome.Category.PLAINS || biome.getCategory() == Biome.Category.DESERT ||
                biome.getCategory() == Biome.Category.BADLANDS_PLATEAU || biome.getCategory() == Biome.Category.BEACH ||
                biome.getCategory() == Biome.Category.MESA || biome.getCategory() == Biome.Category.FOREST ||
                biome.getCategory() == Biome.Category.EXTREME_HILLS || biome.getCategory() == Biome.Category.ICY ||
                biome.getCategory() == Biome.Category.JUNGLE || biome.getCategory() == Biome.Category.MUSHROOM ||
                biome.getCategory() == Biome.Category.SAVANNA || biome.getCategory() == Biome.Category.TAIGA;
    }

    @Override
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        rand.setPopulationSeed(structureSeed, data.chunkX, data.chunkZ, getVersion());
        if (rand.nextInt(this.getChance()) != 0) return false;
        int x = rand.nextInt(16);
        int i = rand.nextInt(120) + 8;
        int y = rand.nextInt(i);
        if (y < 60) return false;
        if (y < 64) return true;
        int z = rand.nextInt(16);
        return rand.nextInt(10) == 0;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    public Data at(int chunkX, int chunkZ) {
        return new Data(this, chunkX, chunkZ);
    }

    public static class Config extends Decorator.Config {
        public final int chance;

        public Config(int chance) {
            this.chance = chance;
        }
    }

    public static class Data extends Decorator.Data<LavaLake> {
        public final int blockX;
        public final int blockZ;
        public final int offsetX;
        public final int offsetZ;

        public Data(LavaLake data, int chunkX, int chunkZ) {
            super(data, chunkX, chunkZ);
            this.blockX = chunkX >> 4;
            this.blockZ = chunkZ >> 4;
            this.offsetX = chunkX & 15;
            this.offsetZ = chunkZ & 15;
        }
    }

}