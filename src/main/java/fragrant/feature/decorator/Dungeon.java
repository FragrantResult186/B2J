package fragrant.feature.decorator;

import fragrant.biome.biome.Biome;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.BPos;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;
import fragrant.feature.decorator.generator.DungeonGenerator;

import java.util.ArrayList;
import java.util.List;

public class Dungeon extends Decorator<Dungeon.Config, Dungeon.Data> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_18_0, new Config(8));

    public Dungeon(MCVersion version) {
        super(CONFIGS.getAsOf(version), version);
    }

    public Dungeon(Config config) {
        super(config, null);
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
        return true;
    }

    public static String name() {
        return "monster_room";
    }

    @Override
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        return true;
    }

    public int getChance() {
        return this.getConfig().chance;
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

    public static class Data extends Decorator.Data<Dungeon> {
        public final int blockX;
        public final int blockZ;
        public final int offsetX;
        public final int offsetZ;

        public Data(Dungeon data, int chunkX, int chunkZ) {
            super(data, chunkX, chunkZ);
            this.blockX = chunkX >> 4;
            this.blockZ = chunkZ >> 4;
            this.offsetX = chunkX & 15;
            this.offsetZ = chunkZ & 15;
        }
    }

}