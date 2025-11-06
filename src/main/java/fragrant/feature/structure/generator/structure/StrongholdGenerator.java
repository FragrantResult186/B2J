package fragrant.feature.structure.generator.structure;

import fragrant.core.util.data.Pair;
import fragrant.core.util.pos.BPos;
import fragrant.feature.structure.Stronghold;
import fragrant.feature.structure.generator.ChunkRngTracker;
import fragrant.feature.structure.generator.Generator;
import fragrant.feature.structure.generator.piece.PieceWeight;
import fragrant.feature.structure.generator.piece.stronghold.*;
import fragrant.core.util.block.BlockBox;
import fragrant.core.rand.BedrockRandom;
import fragrant.core.rand.ChunkRand;
import fragrant.core.version.MCVersion;
import fragrant.core.util.block.BlockDirection;
import fragrant.terrain.TerrainGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

public class StrongholdGenerator extends Generator {

    private final List<PieceWeight<Stronghold.Piece>> PIECE_WEIGHTS = Arrays.asList(
            new PieceWeight<>(Corridor.class, 40, 0),
            new PieceWeight<>(PrisonHall.class, 5, 5),
            new PieceWeight<>(LeftTurn.class, 20, 0),
            new PieceWeight<>(RightTurn.class, 20, 0),
            new PieceWeight<>(RoomCrossing.class, 10, 6),
            new PieceWeight<>(Stairs.class, 5, 5),
            new PieceWeight<>(SpiralStaircase.class, 5, 5),
            new PieceWeight<>(FiveWayCrossing.class, 5, 4),
            new PieceWeight<>(ChestCorridor.class, 5, 4),
            new PieceWeight<>(Library.class, 10, 2) {
                @Override
                public boolean canSpawnMoreStructuresOfType(int placedPieces) {
                    return super.canSpawnMoreStructuresOfType(placedPieces) && placedPieces > 4;
                }
            },
            new PieceWeight<>(PortalRoom.class, 10, 1) {
                @Override
                public boolean canSpawnMoreStructuresOfType(int placedPieces) {
                    return super.canSpawnMoreStructuresOfType(placedPieces) && placedPieces > 5;
                }
            }
    );

    protected TerrainGenerator generator;

    protected List<PieceWeight<Stronghold.Piece>> pieceWeights = null;
    public Class<? extends Stronghold.Piece> currentPiece = null;
    protected int totalWeight;

    public List<Stronghold.Piece> pieceList = null;
    public BlockBox strongholdBox = null;
    public ChunkRngTracker<Stronghold.Piece> chunkRngTracker = null;
    public long worldSeed;
    public MCVersion version;

    private Predicate<Stronghold.Piece> loopPredicate;
    private boolean halted;

    public StrongholdGenerator(MCVersion version) {
        super(version);
        this.version = version;
    }

    @Override
    public boolean generate(TerrainGenerator generator, int chunkX, int chunkZ, ChunkRand rand) {
        this.generator = generator;
        return this.generate(generator.getWorldSeed(), chunkX, chunkZ, piece -> true);
    }

    public boolean generate(long worldSeed, int chunkX, int chunkZ, Predicate<Stronghold.Piece> shouldContinue) {
        this.worldSeed = worldSeed;
        this.pieceList = new ArrayList<>();
        this.currentPiece = null;
        this.totalWeight = 0;
        this.halted = false;
        this.loopPredicate = shouldContinue;
        this.chunkRngTracker = new ChunkRngTracker<>(StrongholdChunkRng::getRngCountForChunk);

        Start startPiece;

        this.pieceWeights = new ArrayList<>(PIECE_WEIGHTS);

        ChunkRand rand = new ChunkRand();
        rand.setPopulationSeed(worldSeed, chunkX, chunkZ, this.version);
        rand.nextInt(); // burn a one call

        int x = (chunkX << 4) + 2;
        int z = (chunkZ << 4) + 2;
        startPiece = new Start(rand, x, z);
        this.pieceList.add(startPiece);
        this.chunkRngTracker.addPiece(startPiece);

        startPiece.addChildren(this, startPiece, this.pieceList, rand);
        List<Stronghold.Piece> pieces = startPiece.children;

        while (!pieces.isEmpty() && !this.halted) {
            int i = rand.nextInt(pieces.size());
            Stronghold.Piece piece = pieces.remove(i);
            piece.addChildren(this, startPiece, this.pieceList, rand);
        }

        if (startPiece.portalRoom == null && this.version.isNewerOrEqualTo(MCVersion.v1_20_71)) {
            this.forceAddPortalRoom(pieceList.getLast(), this.pieceList);
        }

        int offset = 5;
        if (this.version.isNewerOrEqualTo(MCVersion.v1_18_0)) offset = 10;
        moveBelowSeaLevel(rand, offset);

        return this.halted;
    }

    // TODO
    public void forceAddPortalRoom(Stronghold.Piece lastPiece, List<Stronghold.Piece> pieces) {
        if (lastPiece == null || pieces == null || pieces.isEmpty()) {
            return;
        }

        BlockBox lastBox = lastPiece.getBoundingBox();
        if (lastBox == null) {
            return;
        }

        int x, z;

        switch (lastPiece.getFacing()) {
            case NORTH:
                x = (lastBox.maxX + lastBox.minX) / 2 + 1;
                z = lastBox.maxZ;
                break;
            case EAST:
                x = lastBox.minX;
                z = (lastBox.maxZ + lastBox.minZ) / 2 - 1;
                break;
            case SOUTH:
                x = (lastBox.maxX + lastBox.minX) / 2 - 1;
                z = lastBox.minZ;
                break;
            case WEST:
                x = lastBox.maxX;
                z = (lastBox.maxZ + lastBox.minZ) / 2 + 1;
                break;
            default:
                return;
        }
        BlockBox box = BlockBox.rotated(x, 0, z, -4, -1, 0, 11, 8, 16, lastPiece.getFacing().getRotation());
        PortalRoom portalRoom = new PortalRoom(lastPiece.pieceId + 1, box, lastPiece.getFacing());
        pieces.add(portalRoom);

        portalRoom.addChildren(this, (Start) pieces.getFirst(), pieces, null);
        this.chunkRngTracker.addPiece(portalRoom);
    }

    private void moveBelowSeaLevel(BedrockRandom rand, int offset) {
        moveBelowSeaLevel(
                this.pieceList,
                rand,
                this.generator.getSeaLevel(),
                this.generator.getMinWorldHeight(),
                offset
        );

        PortalRoom portalRoom = this.getPortalRoom();
        if (portalRoom != null) {
            portalRoom.updateFramePositions();
        }
    }

    public PortalRoom getPortalRoom() {
        for (Stronghold.Piece piece : this.pieceList) {
            if (piece instanceof PortalRoom) {
                return (PortalRoom) piece;
            }
        }
        return null;
    }

    public Stronghold.Piece generateAndAddPiece(Start startPiece, List<Stronghold.Piece> pieces, ChunkRand rand,
                                                int x, int y, int z, BlockDirection facing, int pieceId) {
        if (pieceId > 50) {
            return null;
        } else if (Math.abs(x - startPiece.getBoundingBox().minX) <= 112 && Math.abs(z - startPiece.getBoundingBox().minZ) <= 112) {
            ChunkRand newRand = new ChunkRand(rand);

            Stronghold.Piece piece = this.getNextStructurePiece(startPiece, pieces, newRand, x, y, z, facing, pieceId + 1);

            if (piece != null) {
                pieces.add(piece);
                this.chunkRngTracker.addPiece(piece);

                if (!this.loopPredicate.test(piece)) {
                    this.halted = true;
                }

                startPiece.children.add(piece);
            }

            return piece;
        } else {
            return null;
        }
    }

    private Stronghold.Piece getNextStructurePiece(Start startPiece, List<Stronghold.Piece> pieceList, ChunkRand rand,
                                                   int x, int y, int z, BlockDirection facing, int pieceId) {
        if (!this.canAddStructurePieces()) {
            return null;
        } else {
            if (this.currentPiece != null) {
                Stronghold.Piece piece = classToPiece(this.currentPiece, pieceList, rand, x, y, z, facing, pieceId);
                this.currentPiece = null;

                if (piece != null) {
                    return piece;
                }
            }

            int int_5 = 0;

            while (int_5 < 5) {
                ++int_5;
                int int_6 = rand.nextInt(this.totalWeight);
                Iterator<PieceWeight<Stronghold.Piece>> pieceWeightsIterator = this.pieceWeights.iterator();

                while (pieceWeightsIterator.hasNext()) {
                    PieceWeight<Stronghold.Piece> pieceWeight = pieceWeightsIterator.next();
                    int_6 -= pieceWeight.pieceWeight;

                    if (int_6 < 0) {
                        if (!pieceWeight.canSpawnMoreStructuresOfType(pieceId) || pieceWeight == startPiece.pieceWeight) {
                            break;
                        }

                        Stronghold.Piece piece = classToPiece(pieceWeight.pieceClass, pieceList, rand, x, y, z, facing, pieceId);

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

            BlockBox boundingBox = SmallCorridor.createBox(pieceList, rand, x, y, z, facing);

            if (boundingBox != null && boundingBox.minY > 0) {
                return new SmallCorridor(pieceId, rand, boundingBox, facing);
            } else {
                return null;
            }
        }
    }

    private static Stronghold.Piece classToPiece(Class<? extends Stronghold.Piece> pieceClass,
                                                 List<Stronghold.Piece> pieceList, ChunkRand rand,
                                                 int x, int y, int z, BlockDirection facing, int pieceId) {
        Stronghold.Piece piece = null;

        if (pieceClass == Corridor.class) {
            piece = Corridor.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == PrisonHall.class) {
            piece = PrisonHall.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == LeftTurn.class) {
            piece = LeftTurn.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == RightTurn.class) {
            piece = RightTurn.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == RoomCrossing.class) {
            piece = RoomCrossing.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == Stairs.class) {
            piece = Stairs.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == SpiralStaircase.class) {
            piece = SpiralStaircase.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == FiveWayCrossing.class) {
            piece = FiveWayCrossing.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == ChestCorridor.class) {
            piece = ChestCorridor.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == Library.class) {
            piece = Library.createPiece(pieceList, rand, x, y, z, facing, pieceId);
        } else if (pieceClass == PortalRoom.class) {
            piece = PortalRoom.createPiece(pieceList, x, y, z, facing, pieceId);
        }

        return piece;
    }

    private boolean canAddStructurePieces() {
        boolean flag = false;
        this.totalWeight = 0;

        for (PieceWeight<Stronghold.Piece> pieceWeight : this.pieceWeights) {
            if (pieceWeight.instancesLimit > 0 && pieceWeight.instancesSpawned < pieceWeight.instancesLimit) {
                flag = true;
            }

            totalWeight += pieceWeight.pieceWeight;
        }

        return flag;
    }
}