package fragrant.feature.misc;

import fragrant.core.state.Dimension;
import fragrant.biome.source.BiomeSource;
import fragrant.feature.Feature;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class SlimeChunk extends Feature<SlimeChunk.Config, SlimeChunk.Data> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_8_0, new Config(10));

    public SlimeChunk(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public SlimeChunk(Config config, MCVersion version) {
        super(config, version);
    }

    @Override
    public String getName() {
        return name();
    }

    public static String name() {
        return "slime_chunk";
    }

    public int getChance() {
        return this.getConfig().chance;
    }

    @Override
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        return this.canStart(data.chunkX, data.chunkZ, rand) == data.isSlime;
    }

    public boolean canStart(int chunkX, int chunkZ) {
        return this.canStart(chunkX, chunkZ, new ChunkRand());
    }

    public boolean canStart(int chunkX, int chunkZ, ChunkRand rand) {
        rand.seedSlimeChunk(chunkX, chunkZ);
        return (rand.nextInt(this.getChance()) == 0);
    }

    @Override
    public boolean canSpawn(Data data, BiomeSource source) {
        return true;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    public Data at(int chunkX, int chunkZ, boolean isSlime) {
        return new Data(this, chunkX, chunkZ, isSlime);
    }

    public static class Config extends Feature.Config {
        public final int chance;

        public Config(int chance) {
            this.chance = chance;
        }
    }

    public static class Data extends Feature.Data<SlimeChunk> {
        public final boolean isSlime;

        public Data(SlimeChunk feature, int chunkX, int chunkZ, boolean isSlime) {
            super(feature, chunkX, chunkZ);
            this.isSlime = isSlime;
        }
    }

}
