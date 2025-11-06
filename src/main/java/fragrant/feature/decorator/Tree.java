package fragrant.feature.decorator;

import fragrant.biome.biome.Biome;
import fragrant.core.rand.ChunkRand;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.BPos;
import fragrant.core.util.pos.CPos;
import fragrant.core.version.MCVersion;
import fragrant.feature.decorator.generator.tree.TreeFeature;

public class Tree extends Decorator<Tree.Config, Tree.Data> {

    public Tree(MCVersion version) {
        super(new Config(), version);
    }

    public Tree(Config config, MCVersion version) {
        super(config, version);
    }

    @Override
    public String getName() {
        return name();
    }

    @Override
    public Data getData(long structureSeed, int chunkX, int chunkZ, ChunkRand rand) {
        return null;
    }

    public static String name() {
        return "tree";
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        //TreeBiome.fromBiome(biome);
        return true;
    }

    @Override
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        return true;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    public Data at(int chunkX, int chunkZ, BPos treePos, TreeFeature type) {
        return new Data(this, chunkX, chunkZ, treePos, type);
    }

    public static class Config extends Decorator.Config {
    }

    public static class Data extends Decorator.Data<Tree> {
        private final BPos pos;
        private final TreeFeature type;
        private int height;
        private boolean generated = false;

        private boolean fallen = false;
        private boolean vines = false;
        private boolean beehive = false;
        private boolean cocoa = false; // jungle trees

        private String mushroom;

        private int leavesHeight = 0;
        private int leavesPattern = 0;

        public Data(Tree decorator, int chunkX, int chunkZ, BPos pos, TreeFeature type) {
            super(decorator, chunkX, chunkZ);
            this.pos = pos;
            this.type = type;
        }

        public BPos getPos() {
            return pos;
        }

        public CPos getChunkPos() {
            return pos.toChunkPos();
        }

        public int getX() {
            return pos.getX();
        }

        public int getZ() {
            return pos.getZ();
        }

        public TreeFeature getType() {
            return type;
        }

        public int getHeight() {
            return height;
        }

        public boolean isGenerated() {
            return generated;
        }

        public boolean isFallen() {
            return fallen;
        }

        public String getMushroom() {
            return mushroom;
        }

        public boolean hasVines() {
            return vines;
        }

        public boolean hasBeehive() {
            return beehive;
        }

        public boolean hasCocoa() {
            return cocoa;
        }

        public int getLeavesHeight() {
            return leavesHeight;
        }

        public int getLeavesPattern() {
            return leavesPattern;
        }

        public void generated(boolean generated) {
            this.generated = generated;
        }

        public void height(int height) {
            this.height = height;
        }

        public void fallen(boolean fallen) {
            this.fallen = fallen;
        }

        public void mushroom(String mushroom) {
            this.mushroom = mushroom;
        }

        public void vines(boolean vines) {
            this.vines = vines;
        }

        public void beehive(boolean beehive) {
            this.beehive = beehive;
        }

        public void cocoa(boolean cocoa) {
            this.cocoa = cocoa;
        }

        public void leavesHeight(int leavesHeight) {
            this.leavesHeight = leavesHeight;
        }

        public void leavesPattern(int leavesPattern) {
            this.leavesPattern = leavesPattern;
        }
    }
}