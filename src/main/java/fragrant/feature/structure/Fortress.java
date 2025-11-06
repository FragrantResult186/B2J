package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.biome.source.BiomeSource;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.CPos;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.version.UnsupportedVersion;
import fragrant.core.version.VersionMap;
import fragrant.feature.structure.generator.piece.StructurePiece;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.pos.BPos;
import fragrant.feature.structure.generator.piece.fortress.Start;
import fragrant.feature.structure.generator.structure.FortressGenerator;

import java.util.Iterator;
import java.util.List;

public class Fortress extends UniformStructure<Fortress> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Config>()
            .add(MCVersion.v1_8_0, new Config(16, 8, -1))
            .add(MCVersion.v1_16_0, new Config(30, 26, 30084232));

    public Fortress(MCVersion version) {
        super(CONFIGS.getAsOf(version), version);
    }

    public Fortress(Config config, MCVersion version) {
        super(config, version);

        if (this.getVersion().isOlderThan(MCVersion.v1_16_0)) {
            throw new UnsupportedVersion(this.getVersion(), "fortress regions");
        }
    }

    public static String name() {
        return "fortress";
    }

    @Override
    public boolean canStart(Data<Fortress> data, long structureSeed, ChunkRand rand) {
        if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_16_0)) {
            return super.canStart(data, structureSeed, rand) && rand.nextInt(6) < 2;
        } else {
            rand.setFortressSeed(structureSeed, data.chunkX, data.chunkZ, this.getVersion());
            rand.nextInt(); // burn a one call
            if (rand.nextInt(3) != 0) return false;
            if (data.chunkX != (data.chunkX & ~15) + rand.nextInt(8) + 4) return false;
            return data.chunkZ == (data.chunkZ & ~15) + rand.nextInt(8) + 4;
        }
    }

    @Override
    public CPos getInRegion(long structureSeed, int regionX, int regionZ, ChunkRand rand) {
        if (this.getVersion().isNewerOrEqualTo(MCVersion.v1_16_0)) {
            CPos fortress = super.getInRegion(structureSeed, regionX, regionZ, rand);
            return rand.nextInt(6) < 2 ? fortress : null;
        } else {
            rand.setFortressSeed(structureSeed, regionX << 4, regionZ << 4, this.getVersion());
            rand.nextInt(); // burn a one call
            if (rand.nextInt(3) != 0) return null;
            CPos fortress = super.getInRegion(structureSeed, regionX, regionZ, rand);
            return new CPos(fortress.getX() + 4, fortress.getZ() + 4);
        }
    }

    @Override
    public boolean canSpawn(int chunkX, int chunkZ, BiomeSource source) {
        int x = this.getVersion().isOlderThan(MCVersion.v1_16_0) ? (chunkX << 4) + 9 : (chunkX << 2) + 2;
        int z = this.getVersion().isOlderThan(MCVersion.v1_16_0) ? (chunkZ << 4) + 9 : (chunkZ << 2) + 2;
        return this.isValidBiome(this.getVersion().isOlderThan(MCVersion.v1_16_0)
                ? source.getBiome(x, 0, z) : source.getBiomeForNoiseGen(x, 0, z));
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.NETHER;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return biome == Biomes.BASALT_DELTAS || biome == Biomes.CRIMSON_FOREST || biome == Biomes.NETHER_WASTES
                || biome == Biomes.SOUL_SAND_VALLEY || biome == Biomes.WARPED_FOREST;
    }

    public static abstract class Piece extends StructurePiece<Piece> {
        public BPos chest;
        public BPos spawner;

        public Piece(int pieceId) {
            super(pieceId);
        }

        protected static Piece getNextIntersectingPiece(List<Piece> pieces, BlockBox box) {
            Iterator<Piece> var2 = pieces.iterator();

            Piece piece;

            do {
                if (!var2.hasNext()) {
                    return null;
                }

                piece = var2.next();
            } while (piece.getBoundingBox() == null || !piece.getBoundingBox().intersects(box));

            return piece;
        }

        protected static boolean isHighEnough(BlockBox box) {
            return box != null && box.minY > 10;
        }

        public abstract void addChildren(FortressGenerator gen, Start start, List<Fortress.Piece> pieces, ChunkRand rand);

        public boolean process(ChunkRand rand, BPos pos) {
            return false;
        }

        protected Fortress.Piece generateChildrenForward(FortressGenerator gen, Start start, List<Fortress.Piece> pieces, ChunkRand rand, int int_1, int int_2, boolean isCastle) {
            BlockDirection facing = this.getFacing();

            if (facing == null) {
                return null;
            } else if (facing == BlockDirection.NORTH) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + int_1, this.boundingBox.minY + int_2, this.boundingBox.minZ - 1, facing, this.pieceId, isCastle);
            } else if (facing == BlockDirection.SOUTH) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + int_1, this.boundingBox.minY + int_2, this.boundingBox.maxZ + 1, facing, this.pieceId, isCastle);
            } else if (facing == BlockDirection.WEST) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX - 1, this.boundingBox.minY + int_2, this.boundingBox.minZ + int_1, facing, this.pieceId, isCastle);
            } else if (facing == BlockDirection.EAST) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + int_2, this.boundingBox.minZ + int_1, facing, this.pieceId, isCastle);
            }

            return null;
        }

        protected Fortress.Piece generateChildrenLeft(FortressGenerator gen, Start start, List<Fortress.Piece> pieces, ChunkRand rand, int int_1, int int_2, boolean isCastle) {
            BlockDirection facing = this.getFacing();

            if (facing == null) {
                return null;
            } else if (facing == BlockDirection.NORTH) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX - 1, this.boundingBox.minY + int_1, this.boundingBox.minZ + int_2, BlockDirection.WEST, this.pieceId, isCastle);
            } else if (facing == BlockDirection.SOUTH) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX - 1, this.boundingBox.minY + int_1, this.boundingBox.minZ + int_2, BlockDirection.WEST, this.pieceId, isCastle);
            } else if (facing == BlockDirection.WEST) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + int_2, this.boundingBox.minY + int_1, this.boundingBox.minZ - 1, BlockDirection.NORTH, this.pieceId, isCastle);
            } else if (facing == BlockDirection.EAST) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + int_2, this.boundingBox.minY + int_1, this.boundingBox.minZ - 1, BlockDirection.NORTH, this.pieceId, isCastle);
            }

            return null;
        }

        protected Fortress.Piece generateChildrenRight(FortressGenerator gen, Start start, List<Fortress.Piece> pieces, ChunkRand rand, int int_1, int int_2, boolean isCastle) {
            BlockDirection facing = this.getFacing();

            if (facing == null) {
                return null;
            } else if (facing == BlockDirection.NORTH) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + int_1, this.boundingBox.minZ + int_2, BlockDirection.EAST, this.pieceId, isCastle);
            } else if (facing == BlockDirection.SOUTH) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + int_1, this.boundingBox.minZ + int_2, BlockDirection.EAST, this.pieceId, isCastle);
            } else if (facing == BlockDirection.WEST) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + int_2, this.boundingBox.minY + int_1, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, this.pieceId, isCastle);
            } else if (facing == BlockDirection.EAST) {
                return gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + int_2, this.boundingBox.minY + int_1, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, this.pieceId, isCastle);
            }

            return null;
        }
    }
}