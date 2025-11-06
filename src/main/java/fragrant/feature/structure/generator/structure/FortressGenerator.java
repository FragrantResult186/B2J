package fragrant.feature.structure.generator.structure;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockDirection;
import fragrant.core.util.data.Pair;
import fragrant.core.util.pos.BPos;
import fragrant.core.util.pos.CPos;
import fragrant.core.util.pos.RPos;
import fragrant.core.version.MCVersion;
import fragrant.feature.structure.Fortress;
import fragrant.feature.structure.generator.Generator;
import fragrant.feature.structure.generator.piece.PieceWeight;
import fragrant.feature.structure.generator.piece.fortress.*;
import fragrant.terrain.TerrainGenerator;

import java.util.*;
import java.util.function.Predicate;

public class FortressGenerator extends Generator {

    private final List<PieceWeight<Fortress.Piece>> BRIDGE_WEIGHTS = Arrays.asList(
            new PieceWeight<>(BridgeStraight.class, 30, 0, true),
            new PieceWeight<>(BridgeCrossing.class, 10, 4),
            new PieceWeight<>(RoomCrossing.class, 10, 4),
            new PieceWeight<>(StairsRoom.class, 10, 3),
            new PieceWeight<>(MonsterThrone.class, 5, 2),
            new PieceWeight<>(CastleEntrance.class, 5, 1)
    );

    private final List<PieceWeight<Fortress.Piece>> CASTLE_WEIGHTS = Arrays.asList(
            new PieceWeight<>(CastleSmallCorridor.class, 25, 0, true),
            new PieceWeight<>(CastleSmallCorridorCrossing.class, 15, 5),
            new PieceWeight<>(CastleSmallCorridorRightTurn.class, 5, 10),
            new PieceWeight<>(CastleSmallCorridorLeftTurn.class, 5, 10),
            new PieceWeight<>(CastleCorridorStairs.class, 10, 3, true),
            new PieceWeight<>(CastleCorridorTBalcony.class, 7, 2),
            new PieceWeight<>(CastleStalkRoom.class, 5, 2)
    );

    protected List<PieceWeight<Fortress.Piece>> pieceWeights = null;
    protected List<PieceWeight<Fortress.Piece>> bridgeWeights = null;
    protected List<PieceWeight<Fortress.Piece>> castleWeights = null;
    protected Class<? extends Fortress.Piece> currentPiece = null;
    protected int totalWeight;

    public List<Fortress.Piece> pieceList = null;

    protected Predicate<Fortress.Piece> loopPredicate;
    protected boolean halted;

    public FortressGenerator(MCVersion version) {
        super(version);
    }

    @Override
    public boolean generate(TerrainGenerator generator, int chunkX, int chunkZ, ChunkRand rand) {
        if(generator == null) return false;
        return this.generate(generator.getWorldSeed(), chunkX, chunkZ, rand, piece -> true);
    }

    public boolean generate(long worldSeed, int chunkX, int chunkZ, ChunkRand rand, Predicate<Fortress.Piece> shouldContinue) {
        this.pieceList = new ArrayList<>();
        this.currentPiece = null;
        this.totalWeight = 0;
        this.loopPredicate = shouldContinue;
        this.halted = false;

        Start startPiece;

        this.bridgeWeights = new ArrayList<>(BRIDGE_WEIGHTS);
        this.castleWeights = new ArrayList<>(CASTLE_WEIGHTS);

        if (this.version.isNewerOrEqualTo(MCVersion.v1_16_0)) {
            RPos region = new CPos(chunkX, chunkZ).toRegionPos(30);
            rand.setRegionSeed(worldSeed, region.getX(), region.getZ(), 30084232, this.version);
            rand.nextInt(26);
            rand.nextInt(26);
            rand.nextInt(6);
        } else {
            rand.setFortressSeed(worldSeed, chunkX, chunkZ, this.version);
            rand.nextInt();
            rand.nextInt(3);
            rand.nextInt(8);
            rand.nextInt(8);
        }

        int x = (chunkX << 4) + 2;
        int z = (chunkZ << 4) + 2;
        startPiece = new Start(rand, x, z);
        this.pieceList.add(startPiece);

        startPiece.addChildren(this, startPiece, this.pieceList, rand);
        List<Fortress.Piece> pieces = startPiece.children;

        while (!pieces.isEmpty() && !this.halted) {
            int i = rand.nextInt(pieces.size());
            Fortress.Piece piece = pieces.remove(i);
            piece.addChildren(this, startPiece, this.pieceList, rand);
        }

        this.moveInsideHeights(this.pieceList, rand, 48, 70);

        return this.halted;
    }

    public Fortress.Piece generateAndAddPiece(Start startPiece, List<Fortress.Piece> pieces, ChunkRand rand,
                                              int x, int y, int z, BlockDirection facing, int pieceId, boolean isCastle) {
        if (pieceId > 30) {
            Fortress.Piece piece = BridgeEndFiller.createPiece(pieceList, rand, x, y, z, facing, pieceId);
            if (piece != null) {
                pieces.add(piece);
                startPiece.children.add(piece);
            }
            return piece;
        } else if (Math.abs(x - startPiece.getBoundingBox().minX) <= 112 && Math.abs(z - startPiece.getBoundingBox().minZ) <= 112) {
            Fortress.Piece piece = this.getNextStructurePiece(startPiece, pieces, rand, x, y, z, facing, pieceId + 1, isCastle);

            if (piece != null) {
                pieces.add(piece);

                if (!this.loopPredicate.test(piece)) {
                    this.halted = true;
                }

                startPiece.children.add(piece);
            }

            return piece;
        } else {
            Fortress.Piece piece = BridgeEndFiller.createPiece(pieceList, rand, x, y, z, facing, pieceId);
            if (piece != null) {
                pieces.add(piece);
                startPiece.children.add(piece);
            }
            return piece;
        }
    }

    private Fortress.Piece getNextStructurePiece(Start startPiece, List<Fortress.Piece> pieceList, ChunkRand rand,
                                                 int x, int y, int z, BlockDirection facing, int pieceId, boolean isCastle) {
        this.pieceWeights = isCastle ? this.castleWeights : this.bridgeWeights;

        if (!this.canAddStructurePieces()) {
            return BridgeEndFiller.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else {
            if (this.currentPiece != null) {
                Fortress.Piece piece = classToPiece(this.currentPiece, pieceList, rand, x, y, z, facing, pieceId);
                this.currentPiece = null;

                if (piece != null) {
                    return piece;
                }
            }

            int int_5 = 0;

            while (int_5 < 5) {
                ++int_5;
                int int_6 = rand.nextInt(this.totalWeight);
                Iterator<PieceWeight<Fortress.Piece>> pieceWeightsIterator = this.pieceWeights.iterator();

                while (pieceWeightsIterator.hasNext()) {
                    PieceWeight<Fortress.Piece> pieceWeight = pieceWeightsIterator.next();
                    int_6 -= pieceWeight.pieceWeight;

                    if (int_6 < 0) {
                        if (!pieceWeight.consecutive && pieceWeight == startPiece.pieceWeight) {
                            break;
                        }

                        Fortress.Piece piece = classToPiece(pieceWeight.pieceClass, pieceList, rand, x, y, z, facing, pieceId);

                        if (piece != null) {
                            ++pieceWeight.instancesSpawned;
                            startPiece.pieceWeight = pieceWeight;

                            if (!pieceWeight.canSpawnMoreStructures()) {
                                pieceWeightsIterator.remove();
                            }

                            return piece;
                        }
                    }
                }
            }

            return BridgeEndFiller.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        }
    }

    private static Fortress.Piece classToPiece(Class<? extends Fortress.Piece> pieceClass,
                                               List<Fortress.Piece> pieceList, ChunkRand rand,
                                               int x, int y, int z, BlockDirection facing, int pieceId) {
        Fortress.Piece piece = null;

        if (pieceClass == BridgeStraight.class) {
            piece = BridgeStraight.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == BridgeCrossing.class) {
            piece = BridgeCrossing.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == RoomCrossing.class) {
            piece = RoomCrossing.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == StairsRoom.class) {
            piece = StairsRoom.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == MonsterThrone.class) {
            piece = MonsterThrone.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == CastleEntrance.class) {
            piece = CastleEntrance.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == CastleSmallCorridor.class) {
            piece = CastleSmallCorridor.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == CastleSmallCorridorCrossing.class) {
            piece = CastleSmallCorridorCrossing.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == CastleSmallCorridorRightTurn.class) {
            piece = CastleSmallCorridorRightTurn.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == CastleSmallCorridorLeftTurn.class) {
            piece = CastleSmallCorridorLeftTurn.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == CastleCorridorStairs.class) {
            piece = CastleCorridorStairs.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == CastleCorridorTBalcony.class) {
            piece = CastleCorridorTBalcony.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == CastleStalkRoom.class) {
            piece = CastleStalkRoom.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        }

        return piece;
    }

    private boolean canAddStructurePieces() {
        boolean flag = false;
        this.totalWeight = 0;

        for (PieceWeight<Fortress.Piece> pieceWeight : this.pieceWeights) {
            if (pieceWeight.instancesLimit > 0 && pieceWeight.instancesSpawned < pieceWeight.instancesLimit) {
                flag = true;
            }

            this.totalWeight += pieceWeight.pieceWeight;
        }

        return flag;
    }
}