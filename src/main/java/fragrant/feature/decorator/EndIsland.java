package fragrant.feature.decorator;

import fragrant.biome.biome.Biome;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;

public class EndIsland extends Decorator<EndIsland.Config, EndIsland.Data> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_18_0, new Config(14));

    public EndIsland(MCVersion version) {
        super(CONFIGS.getAsOf(version), version);
    }

    public EndIsland(Config config) {
        super(config, null);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Data getData(long structureSeed, int chunkX, int chunkZ, ChunkRand rand) {
        if (Math.hypot(chunkX, chunkZ) < 64L) return null;
        rand.setPopulationSeed(structureSeed, chunkX, chunkZ, getVersion());
        if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_18_0)) {
            rand.nextInt(); // burn a one call
        }
        if (rand.nextInt(this.getChance()) != 0) return null;
        int x = rand.nextInt(16) + chunkX * 16 + 8;
        int y = rand.nextInt(16) + 55;
        int z = rand.nextInt(16) + chunkZ * 16 + 8;
        return new EndIsland.Data(this, x, y, z);
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome.getCategory() == Biome.Category.THE_END;
    }

    public static String name() {
        return "end_island";
    }

    @Override
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        if (Math.hypot(data.chunkX, data.chunkZ) < 64L) return false;
        rand.setPopulationSeed(structureSeed, data.chunkX, data.chunkZ, getVersion());
        if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_18_0)) {
            rand.nextInt(); // burn a one call
        }
        return rand.nextInt(this.getChance()) == 0;
    }

    public int getChance() {
        return this.getConfig().chance;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.END;
    }

    public EndIsland.Data at(int blockX, int blockY, int blockZ) {
        return new Data(this, blockX, blockY, blockZ);
    }

    public static class Config extends Decorator.Config {
        public final int chance;

        public Config(int chance) {
            this.chance = chance;
        }
    }

    public static class Data extends Decorator.Data<EndIsland> {
        public final int blockX;
        public final int blockY;
        public final int blockZ;
        public final int offsetX;
        public final int offsetZ;

        public Data(EndIsland feature, int blockX, int blockY, int blockZ) {
            super(feature, blockX >> 4, blockZ >> 4);
            this.blockX = blockX;
            this.blockY = blockY;
            this.blockZ = blockZ;
            this.offsetX = this.blockX & 15;
            this.offsetZ = this.blockZ & 15;
        }
    }

}