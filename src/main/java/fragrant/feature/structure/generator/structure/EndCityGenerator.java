package fragrant.feature.structure.generator.structure;

import fragrant.core.rand.ChunkRand;
import fragrant.core.util.block.BlockBox;
import fragrant.core.util.block.BlockMirror;
import fragrant.core.util.block.BlockRotation;
import fragrant.core.util.pos.BPos;
import fragrant.core.util.pos.CPos;
import fragrant.core.util.pos.RPos;
import fragrant.core.version.MCVersion;
import fragrant.feature.structure.EndCity;
import fragrant.feature.structure.generator.Generator;
import fragrant.feature.structure.generator.piece.endcity.*;
import fragrant.terrain.TerrainGenerator;

import java.util.*;
import java.util.function.Predicate;

public class EndCityGenerator extends Generator {

    public List<EndCity.Piece> pieceList = null;

    protected Predicate<EndCity.Piece> loopPredicate;
    protected boolean halted;

    private boolean hasShip = false;
    private int depth;

    private static final int[][] TOWER_BRIDGE_DATA = {
            {0, 1, 0},   // NONE
            {1, 6, 1},   // CLOCKWISE_90
            {3, 0, 5},   // COUNTERCLOCKWISE_90
            {2, 5, 6}    // CLOCKWISE_180
    };

    private static final int[][] FAT_TOWER_BRIDGE_DATA = {
            {0, 4, 0},
            {1, 12, 4},
            {3, 0, 8},
            {2, 8, 12}
    };

    public EndCityGenerator(MCVersion version) {
        super(version);
    }

    @Override
    public boolean generate(TerrainGenerator generator, int chunkX, int chunkZ, ChunkRand rand) {
        if(generator == null) return false;
        return this.generate(generator.getWorldSeed(), chunkX, chunkZ, rand, piece -> true);
    }

    public boolean generate(long worldSeed, int chunkX, int chunkZ, ChunkRand rand, Predicate<EndCity.Piece> shouldContinue) {
        this.pieceList = new ArrayList<>();
        this.halted = false;
        this.loopPredicate = shouldContinue;
        this.hasShip = false;
        this.depth = 0;

        RPos region = new CPos(chunkX, chunkZ).toRegionPos(20);
        rand.setRegionSeed(worldSeed, region.getX(), region.getZ(), 10387313, this.version);
        rand.nextInt(9);
        rand.nextInt(9);
        rand.nextInt(9);
        rand.nextInt(9);

        int x = (chunkX << 4) + 8;
        int z = (chunkZ << 4) + 8;
        EndCity.Piece base = new BaseFloor(0, new ChunkRand(chunkZ * 10387313 + chunkX), x, z);

        base.pieceClass = base.getClass();
        this.pieceList.add(base);

        base = this.addPiece(base, base.rotation, -1, 0, -1, new SecondFloor1());
        base = this.addPiece(base, base.rotation, -1, 4, -1, new ThirdFloor1());
        base = this.addPiece(base, base.rotation, -1, 8, -1, new ThirdRoof());

        this.recursiveGenerate(base, rand, SectionType.TOWER, 1);

        return this.halted;
    }

    private EndCity.Piece addPiece(EndCity.Piece prev, BlockRotation rotation, int px, int py, int pz, EndCity.Piece piece) {
        piece.rotation = rotation;
        piece.pieceClass = piece.getClass();

        BPos anchor = (prev != null) ? prev.pos : new BPos(px, py, pz);
        BPos offset = BPos.ORIGIN;
        if (prev != null) {
            offset = applyRotation(prev.rotation, px, py, pz);
        }

        piece.pos = anchor.add(offset);
        piece.boundingBox = BlockBox.getBoundingBox(piece.pos, rotation, BPos.ORIGIN, BlockMirror.NONE, piece.getSize());

        if (canPlace(piece)) {
            this.pieceList.add(piece);
            piece.setChest();
        }

        return piece;
    }

    private BPos applyRotation(BlockRotation rotation, int x, int y, int z) {
        return rotation.rotate(new BPos(x, y, z), BPos.ORIGIN);
    }

    private boolean canPlace(EndCity.Piece piece) {
        for (EndCity.Piece exist : this.pieceList) {
            if (exist.boundingBox.intersects(piece.boundingBox)) {
                return false;
            }
        }
        return true;
    }

    private boolean recursiveGenerate(EndCity.Piece piece, ChunkRand rand, SectionType type, int genDepth) {
        if (genDepth > 8 || this.hasShip || this.halted) {
            return false;
        }

        this.depth = genDepth;

        boolean res;
        switch (type) {
            case TOWER:
                res = this.generateTower(piece, rand);
                break;
            case BRIDGE:
                res = this.generateBridge(piece, rand);
                break;
            case HOUSE_TOWER:
                res = this.generateHouseTower(piece, rand);
                break;
            case FAT_TOWER:
                res = this.generateFatTower(piece, rand);
                break;
            default:
                res = false;
                break;
        }

        int depth = rand.nextInt();// skip

        return res;
    }

    private boolean generateTower(EndCity.Piece piece, ChunkRand rand) {
        int x = 3 + rand.nextInt(2);
        int z = 3 + rand.nextInt(2);

        EndCity.Piece base;
        base = this.addPiece(piece, piece.rotation, x, -3, z, new TowerBase());
        base = this.addPiece(base, piece.rotation, 0, 7, 0, new TowerPiece());
        EndCity.Piece bridgePiece = (rand.nextInt(3) == 0) ? base : null;

        int floorCount = 1 + rand.nextInt(3);
        for (int i = 0; i < floorCount; i++) {
            base = this.addPiece(base, piece.rotation, 0, 4, 0, new TowerPiece());
            if (i < floorCount - 1 && rand.nextBoolean()) {
                bridgePiece = base;
            }
        }

        if (bridgePiece != null) {
            for (int i = 0; i < 4; i++) {
                if (rand.nextBoolean()) {
                    int[] data = TOWER_BRIDGE_DATA[i];
                    BlockRotation bridgeRot = piece.rotation.rotateBy(data[0]);
                    EndCity.Piece bridge = this.addPiece(bridgePiece, bridgeRot, data[1], -1, data[2], new BridgeEnd());
                    this.recursiveGenerate(bridge, rand, SectionType.BRIDGE, this.depth + 1);
                }
            }
            this.addPiece(base, piece.rotation, -1, 4, -1, new TowerTop());
        } else {
            if (this.depth != 7) {
                this.recursiveGenerate(base, rand, SectionType.FAT_TOWER, this.depth + 1);
            }
            this.addPiece(base, piece.rotation, -1, 4, -1, new TowerTop());
        }

        return true;
    }

    private boolean generateBridge(EndCity.Piece piece, ChunkRand rand) {
        int bridgeLength = rand.nextInt(4) + 1;
        EndCity.Piece base = this.addPiece(piece, piece.rotation, 0, 0, -4, new BridgePiece());
        base.pieceId = -1; // setGenDepth(-1)

        int y = 0;
        for (int i = 0; i < bridgeLength; i++) {
            if (rand.nextBoolean()) {
                base = this.addPiece(base, piece.rotation, 0, y, -4, new BridgePiece());
                y = 0;
            } else {
                if (rand.nextBoolean()) {
                    base = this.addPiece(base, piece.rotation, 0, y, -4, new BridgeSteepStairs());
                } else {
                    base = this.addPiece(base, piece.rotation, 0, y, -8, new BridgeGentleStairs());
                }
                y = 4;
            }
        }

        if (!this.hasShip && rand.nextInt(10 - this.depth) == 0) {
            int x = -8 + rand.nextInt(8);
            int z = -70 + rand.nextInt(10);
            this.addPiece(base, piece.rotation, x, y, z, new Ship(rand, piece.boundingBox, piece.rotation.getDirection().getOpposite()));
            this.hasShip = true;
        } else {
            if (!this.recursiveGenerate(base, rand, SectionType.HOUSE_TOWER, this.depth + 1)) {
                return false;
            }
        }

        EndCity.Piece endBridge = this.addPiece(base, piece.rotation.getOpposite(), 4, y, 0, new BridgeEnd());
        endBridge.pieceId = -1; // setGenDepth(-1)

        return true;
    }

    private boolean generateHouseTower(EndCity.Piece piece, ChunkRand rand) {
        if (this.depth > 8) return false;

        EndCity.Piece base = this.addPiece(piece, piece.rotation, -3, 1, -11, new BaseFloor());

        int choice = rand.nextInt(3);
        switch (choice) {
            case 0:
                this.addPiece(base, piece.rotation, -1, 4, -1, new BaseRoof());
                return true;
            case 1:
                base = this.addPiece(base, piece.rotation, -1, 0, -1, new SecondFloor2());
                base = this.addPiece(base, piece.rotation, -1, 8, -1, new SecondRoof());
                this.recursiveGenerate(base, rand, SectionType.TOWER, this.depth + 1);
                break;
            case 2:
                base = this.addPiece(base, piece.rotation, -1, 0, -1, new SecondFloor2());
                base = this.addPiece(base, piece.rotation, -1, 4, -1, new ThirdFloor2(rand, piece.boundingBox, piece.rotation.getDirection()));
                base = this.addPiece(base, piece.rotation, -1, 8, -1, new ThirdRoof());
                this.recursiveGenerate(base, rand, SectionType.TOWER, this.depth + 1);
                break;
        }

        return true;
    }

    private boolean generateFatTower(EndCity.Piece piece, ChunkRand rand) {
        EndCity.Piece base = this.addPiece(piece, piece.rotation, -3, 4, -3, new FatTowerBase());
        base = this.addPiece(base, piece.rotation, 0, 4, 0, new FatTowerMiddle());

        for (int layer = 0; layer < 2 && rand.nextInt(3) != 0; layer++) {
            base = this.addPiece(base, piece.rotation, 0, 8, 0, new FatTowerMiddle());

            for (int i = 0; i < 4; i++) {
                if (rand.nextBoolean()) {
                    int[] data = FAT_TOWER_BRIDGE_DATA[i];
                    BlockRotation bridgeRot = piece.rotation.rotateBy(data[0]);
                    EndCity.Piece bridge = this.addPiece(base, bridgeRot, data[1], -1, data[2], new BridgeEnd());
                    this.recursiveGenerate(bridge, rand, SectionType.BRIDGE, this.depth + 1);
                }
            }
        }

        this.addPiece(base, piece.rotation, -2, 8, -2, new FatTowerTop(rand, piece.boundingBox, piece.rotation.getDirection()));
        return true;
    }

    private enum SectionType {
        TOWER,
        BRIDGE,
        HOUSE_TOWER,
        FAT_TOWER
    }
}