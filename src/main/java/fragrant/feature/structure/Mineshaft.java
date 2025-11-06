package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.core.rand.BedrockRandom;
import fragrant.core.state.Dimension;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.pos.BPos;
import fragrant.feature.Feature;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;
import fragrant.feature.structure.generator.piece.StructurePiece;
import fragrant.feature.structure.generator.piece.mineshaft.MineshaftRoom;
import fragrant.feature.structure.generator.structure.MineshaftGenerator;

import java.util.Iterator;
import java.util.List;

public class Mineshaft extends Structure<Mineshaft.Config, Feature.Data<?>> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_8_0, new Config(0.004F));

    public Mineshaft(Type type, MCVersion version) {
        this(type, CONFIGS.getAsOf(version), version);
    }

    public Mineshaft(Type type, Config config, MCVersion version) {
        super(config, version);
        this.type = type;
    }

    public static String name() {
        return "mineshaft";
    }

    private double getChance() {
        return this.getConfig().chance;
    }

    public final Type type;

    public enum Type {
        NORMAL,
        MESA
    }

    @Override
    public boolean canStart(Data<?> data, long structureSeed, ChunkRand rand) {
        if (getVersion().isNewerOrEqualTo(MCVersion.v1_11_0)) {
            rand.setCarverSeed(structureSeed, data.chunkX, data.chunkZ, this.getVersion());
        } else {
            rand.setSeed(structureSeed ^ data.chunkZ ^ data.chunkX);
        }
        rand.nextInt(); // burn a one call
        if (rand.nextFloat() < this.getChance()) {
            return rand.nextInt(80) < Math.max(Math.abs(data.chunkX), Math.abs(data.chunkZ));
        }
        return false;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome.getCategory() == Biome.Category.OCEAN
                || biome == Biomes.BAMBOO_JUNGLE || biome == Biomes.BAMBOO_JUNGLE_HILLS || biome == Biomes.BIRCH_FOREST
                || biome == Biomes.BIRCH_FOREST_HILLS || biome == Biomes.DARK_FOREST || biome == Biomes.DARK_FOREST_HILLS
                || biome == Biomes.DESERT || biome == Biomes.DESERT_HILLS || biome == Biomes.DESERT_LAKES
                || biome == Biomes.FLOWER_FOREST || biome == Biomes.FOREST || biome == Biomes.GIANT_SPRUCE_TAIGA
                || biome == Biomes.GIANT_SPRUCE_TAIGA_HILLS || biome == Biomes.GIANT_TREE_TAIGA || biome == Biomes.GIANT_TREE_TAIGA_HILLS
                || biome == Biomes.GRAVELLY_MOUNTAINS || biome == Biomes.ICE_SPIKES || biome == Biomes.JUNGLE
                || biome == Biomes.JUNGLE_EDGE || biome == Biomes.JUNGLE_HILLS || biome == Biomes.MODIFIED_GRAVELLY_MOUNTAINS
                || biome == Biomes.MODIFIED_JUNGLE || biome == Biomes.MODIFIED_JUNGLE_EDGE || biome == Biomes.MOUNTAIN_EDGE
                || biome == Biomes.MOUNTAINS || biome == Biomes.MUSHROOM_FIELDS || biome == Biomes.MUSHROOM_FIELD_SHORE
                || biome == Biomes.PLAINS || biome == Biomes.SAVANNA || biome == Biomes.SAVANNA_PLATEAU
                || biome == Biomes.SHATTERED_SAVANNA || biome == Biomes.SHATTERED_SAVANNA_PLATEAU || biome == Biomes.SNOWY_MOUNTAINS
                || biome == Biomes.SNOWY_TAIGA || biome == Biomes.SNOWY_TAIGA_HILLS || biome == Biomes.SNOWY_TAIGA_MOUNTAINS
                || biome == Biomes.SNOWY_TUNDRA || biome == Biomes.STONE_SHORE || biome == Biomes.SUNFLOWER_PLAINS
                || biome == Biomes.TAIGA || biome == Biomes.TAIGA_HILLS || biome == Biomes.TAIGA_MOUNTAINS
                || biome == Biomes.TALL_BIRCH_FOREST || biome == Biomes.TALL_BIRCH_HILLS || biome == Biomes.WOODED_HILLS
                || biome == Biomes.WOODED_MOUNTAINS || biome.getCategory() == Biome.Category.MESA
                || biome == Biomes.BEACH || biome == Biomes.FROZEN_RIVER || biome == Biomes.RIVER
                || biome == Biomes.SNOWY_BEACH || biome == Biomes.SWAMP || biome == Biomes.SWAMP_HILLS;
    }

    public Data<Mineshaft> at(int chunkX, int chunkZ) {
        return new Data<>(this, chunkX, chunkZ);
    }

    public static class Config extends Feature.Config {
        public final double chance;

        public Config(double chance) {
            this.chance = chance;
        }
    }

    public static abstract class Piece extends StructurePiece<Mineshaft.Piece> {
        public Piece(int pieceId) {
            super(pieceId);
        }

        protected static Mineshaft.Piece getNextIntersectingPiece(List<Mineshaft.Piece> pieces, BlockBox box) {
            Iterator<Mineshaft.Piece> var2 = pieces.iterator();

            Mineshaft.Piece piece;

            do {
                if (!var2.hasNext()) {
                    return null;
                }

                piece = var2.next();
            } while (piece.getBoundingBox() == null || !piece.getBoundingBox().intersects(box));

            return piece;
        }

        public abstract void addChildren(MineshaftGenerator gen, MineshaftRoom start, Mineshaft.Piece pieces, ChunkRand randomSource);
    }

}
