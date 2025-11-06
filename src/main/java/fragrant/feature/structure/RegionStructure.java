package fragrant.feature.structure;

import fragrant.core.util.pos.CPos;
import fragrant.feature.Feature;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;

public abstract class RegionStructure<C extends RegionStructure.Config, D extends RegionStructure.Data<?>> extends Structure<C, D> {

    public RegionStructure(C config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "region_structure";
    }

    public int getSpacing() {
        return getConfig().spacing;
    }

    public int getSeparation() {
        return getConfig().separation;
    }

    public int getSalt() {
        return getConfig().salt;
    }

    public RegionStructure.Data<?> at(int chunkX, int chunkZ) {
        return new RegionStructure.Data<>(this, chunkX, chunkZ);
    }

    public CPos getInRegion(long structureSeed, int regionX, int regionZ) {
        return getInRegion(structureSeed, regionX, regionZ, new ChunkRand());
    }

    public abstract CPos getInRegion(long structureSeed, int regionX, int regionZ, ChunkRand rand);

    public static class Config extends Feature.Config {
        public final int spacing;
        public final int separation;
        public final int salt;

        public Config(int spacing, int separation, int salt) {
            this.spacing = spacing;
            this.separation = separation;
            this.salt = salt;
        }
    }

    public static class Data<T extends RegionStructure<?, ?>> extends Feature.Data<T> {
        public final int regionX;
        public final int regionZ;
        public final int offsetX;
        public final int offsetZ;
        public final long baseRegionSeed;

        public Data(T structure, int chunkX, int chunkZ) {
            super(structure, chunkX, chunkZ);

            int adjustedX = adjustForNegative(this.chunkX, this.feature.getSpacing());
            int adjustedZ = adjustForNegative(this.chunkZ, this.feature.getSpacing());

            this.regionX = adjustedX / this.feature.getSpacing();
            this.regionZ = adjustedZ / this.feature.getSpacing();

            this.offsetX = this.chunkX - this.regionX * this.feature.getSpacing();
            this.offsetZ = this.chunkZ - this.regionZ * this.feature.getSpacing();

            this.baseRegionSeed = new ChunkRand().setRegionSeed(
                    0L,
                    this.regionX,
                    this.regionZ,
                    this.feature.getSalt(),
                    this.feature.getVersion()
            );
        }

        private static int adjustForNegative(int coord, int spacing) {
            return coord < 0 ? coord - spacing + 1 : coord;
        }

        @Override
        public String toString() {
            return String.format(
                    "Data{regionX=%d, regionZ=%d, offsetX=%d, offsetZ=%d, baseRegionSeed=%d}",
                    regionX, regionZ, offsetX, offsetZ, baseRegionSeed
            );
        }
    }
}
