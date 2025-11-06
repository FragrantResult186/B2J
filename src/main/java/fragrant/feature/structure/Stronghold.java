package fragrant.feature.structure;

import fragrant.biome.biome.Biome;
import fragrant.biome.source.BiomeSource;
import fragrant.core.state.Dimension;
import fragrant.core.util.pos.RPos;
import fragrant.feature.structure.generator.structure.StrongholdGenerator;
import fragrant.feature.structure.generator.piece.StructurePiece;
import fragrant.feature.structure.generator.piece.stronghold.Start;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.pos.BPos;
import fragrant.core.util.pos.CPos;
import fragrant.core.rand.BedrockRandom;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.version.VersionMap;
import fragrant.math.Mth;

import java.util.*;

public class Stronghold extends Structure<Stronghold.Config, Stronghold.Data> {

    public static final VersionMap<Config> CONFIGS = new VersionMap<Stronghold.Config>()
            .add(MCVersion.v1_0_0, new Stronghold.Config(200, 150, 3));

    public Stronghold(MCVersion version) {
        this(CONFIGS.getAsOf(version), version);
    }

    public Stronghold(Config config, MCVersion version) {
        super(config, version);
    }

    public static String name() {
        return "stronghold";
    }

    public int getSpacing() {
        return this.getConfig().spacing;
    }

    public int getSeparation() {
        return this.getConfig().separation;
    }

    public int getCount() {
        return this.getConfig().count;
    }

    public List<CPos> getAllVillageStrongholdStarts(BiomeSource source) {
        return this.getVillageStrongholds(source, this.getCount());
    }

    public List<CPos> getVillageStrongholds(BiomeSource source, int numberOfStronghold) {
        List<CPos> strongholds = new ArrayList<>(numberOfStronghold);
        ChunkRand rand = new ChunkRand();
        Village v = new Village(source.getVersion());

        rand.setSeed(source.getWorldSeed());
        float angle = rand.nextFloat() * Mth.TWO_PI;
        int dist = rand.nextInt(40, 56);

        int count = 0;
        while (count < numberOfStronghold) {
            int cx = (int) Math.floor(dist * Math.cos(angle));
            int cz = (int) Math.floor(dist * Math.sin(angle));

            boolean placed = false;
            for (int x = cx - 8; x < cx + 8; x++) {
                for (int z = cz - 8; z < cz + 8; z++) {
                    RegionStructure.Data<Village> data = new RegionStructure.Data<>(v, x, z);
                    if (v.canStart(data, source.getWorldSeed(), new ChunkRand())) {
                        if (source.getDimension() == null || v.canSpawn(x, z, source)) {
                            strongholds.add(new CPos(x, z));
                            placed = true;
                            count++;
                        }
                    }
                }
            }
            if (placed) {
                angle += 0.6F * Mth.PI;
                dist += 8;
            } else {
                angle += 0.25F * Mth.PI;
                dist += 4;
            }
        }

        return strongholds;
    }

    public List<CPos> getVillageStrongholdsFast(BiomeSource source, int numberOfStronghold) {
        List<CPos> strongholds = new ArrayList<>(numberOfStronghold);
        ChunkRand rand = new ChunkRand();
        Village v = new Village(source.getVersion());

        rand.setSeed(source.getWorldSeed());
        float angle = rand.nextFloat() * Mth.TWO_PI;
        int dist = rand.nextInt(40, 56);

        int count = 0;
        while (count < numberOfStronghold) {
            int cx = (int) Math.floor(dist * Math.cos(angle));
            int cz = (int) Math.floor(dist * Math.sin(angle));

            int regionX = cx >> 5;
            int regionZ = cz >> 5;
            boolean placed = false;

            outer:
            for (int rx = regionX - 1; rx < regionX + 1; rx++) {
                for (int rz = regionZ - 1; rz < regionZ + 1; rz++) {
                    CPos pos = v.getInRegion(source.getWorldSeed(), rx, rz, rand);
                    if (pos.isInChunk(cx - 8, cz - 8, cx + 8, cz + 8)) {
                        if (source.getDimension() == null || v.canSpawn(pos, source)) {
                            strongholds.add(pos);
                            placed = true;
                            count++;
                            break outer;
                        }
                    }
                }
            }
            if (placed) {
                angle += 0.6F * Mth.PI;
                dist += 8;
            } else {
                angle += 0.25F * Mth.PI;
                dist += 4;
            }
        }

        return strongholds;
    }

    public CPos getStaticStronghold(long worldSeed, int regionX, int regionZ) {
        ChunkRand rand = new ChunkRand();
        int a = this.getSpacing() * regionX + 100;
        int b = this.getSpacing() * regionZ + 100;
        rand.setStrongholdSeed(worldSeed, a, b, 97858791, this.getVersion());
        int x = rand.nextInt(-50, 50) + a;
        int z = rand.nextInt(-50, 50) + b;
        return rand.nextFloat() < 0.25F ? new CPos(x, z) : null;
    }

    @Override
    public boolean canStart(Data data, long structureSeed, ChunkRand rand) {
        throw new UnsupportedOperationException("stronghold start depends on biomes");
    }

    @Override
    public boolean canSpawn(int chunkX, int chunkZ, BiomeSource source) {
        //village SH
        for (CPos start : this.getAllVillageStrongholdStarts(source)) {
            if (start.getX() == chunkX && start.getZ() == chunkZ) return true;
        }
        //static SH
        RPos rPos = new CPos(chunkX, chunkZ).toRegionPos(this.getSpacing());
        CPos start = this.getStaticStronghold(source.getWorldSeed(), rPos.getX(), rPos.getZ());
        return start.getX() == chunkX && start.getZ() == chunkZ;
    }

    @Override
    public Dimension getValidDimension() {
        return Dimension.OVERWORLD;
    }

    @Override
    public boolean isValidBiome(Biome biome) {
        return true;
    }

    public static class Config extends Structure.Config {
        public final int spacing;
        public final int separation;
        public final int count;

        public Config(int spacing, int separation, int count) {
            this.spacing = spacing;
            this.separation = separation;
            this.count = count;
        }
    }

    public static class Data extends Structure.Data<Stronghold> {
        public Data(Stronghold stronghold, int chunkX, int chunkZ) {
            super(stronghold, chunkX, chunkZ);
        }
    }

    public static abstract class Piece extends StructurePiece<Piece> {
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

        public abstract void addChildren(StrongholdGenerator gen, Start start, List<Piece> pieces, ChunkRand rand);

        public boolean process(BedrockRandom rand, BPos pos) {
            return false;
        }

        protected Piece generateChildrenForward(StrongholdGenerator gen, Start start, List<Piece> pieces, ChunkRand rand, int a, int b) {
            BlockDirection facing = this.getFacing();

            if (facing == null) {
                return null;
            }

            Piece newPiece = null;

            if (facing == BlockDirection.NORTH) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + a, this.boundingBox.minY + b, this.boundingBox.minZ - 1, facing, this.pieceId);
            } else if (facing == BlockDirection.SOUTH) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + a, this.boundingBox.minY + b, this.boundingBox.maxZ + 1, facing, this.pieceId);
            } else if (facing == BlockDirection.WEST) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX - 1, this.boundingBox.minY + b, this.boundingBox.minZ + a, facing, this.pieceId);
            } else if (facing == BlockDirection.EAST) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + b, this.boundingBox.minZ + a, facing, this.pieceId);
            }

            if (newPiece != null) {
                this.setNextPiece(newPiece);
            }

            return newPiece;
        }

        protected Piece generateChildrenLeft(StrongholdGenerator gen, Start start, List<Piece> pieces, ChunkRand rand, int a, int b) {
            BlockDirection facing = this.getFacing();

            if (facing == null) {
                return null;
            }

            Piece newPiece = null;

            if (facing == BlockDirection.NORTH) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX - 1, this.boundingBox.minY + a, this.boundingBox.minZ + b, BlockDirection.WEST, this.pieceId);
            } else if (facing == BlockDirection.SOUTH) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX - 1, this.boundingBox.minY + a, this.boundingBox.minZ + b, BlockDirection.WEST, this.pieceId);
            } else if (facing == BlockDirection.WEST) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + b, this.boundingBox.minY + a, this.boundingBox.minZ - 1, BlockDirection.NORTH, this.pieceId);
            } else if (facing == BlockDirection.EAST) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + b, this.boundingBox.minY + a, this.boundingBox.minZ - 1, BlockDirection.NORTH, this.pieceId);
            }

            if (newPiece != null) {
                this.setNextPiece(newPiece);
            }

            return newPiece;
        }

        protected Piece generateChildrenRight(StrongholdGenerator gen, Start start, List<Piece> pieces, ChunkRand rand, int a, int b) {
            BlockDirection facing = this.getFacing();

            if (facing == null) {
                return null;
            }

            Piece newPiece = null;

            if (facing == BlockDirection.NORTH) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + a, this.boundingBox.minZ + b, BlockDirection.EAST, this.pieceId);
            } else if (facing == BlockDirection.SOUTH) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.maxX + 1, this.boundingBox.minY + a, this.boundingBox.minZ + b, BlockDirection.EAST, this.pieceId);
            } else if (facing == BlockDirection.WEST) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + b, this.boundingBox.minY + a, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, this.pieceId);
            } else if (facing == BlockDirection.EAST) {
                newPiece = gen.generateAndAddPiece(start, pieces, rand, this.boundingBox.minX + b, this.boundingBox.minY + a, this.boundingBox.maxZ + 1, BlockDirection.SOUTH, this.pieceId);
            }

            if (newPiece != null) {
                this.setNextPiece(newPiece);
            }

            return newPiece;
        }
    }

}
