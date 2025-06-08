package fragrant.b2j.feature.structure;

import fragrant.b2j.feature.loot.LootConfig;
import fragrant.b2j.feature.loot.LootFactory;
import fragrant.b2j.feature.loot.LootType;
import fragrant.b2j.feature.BedrockFeatureConfig;
import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

import java.util.*;

public class EndCity extends BedrockFeatureGenerator {

    private static final LootConfig LOOT_CONFIG = new LootConfig(
            "data/loot_tables/chests/end_city_treasure.json", 1, 2
    );

    private static final PieceInfo[] PIECE_INFO = {
            new PieceInfo(9, 3, 9, "base_floor"),
            new PieceInfo(11, 1, 11, "base_roof"),
            new PieceInfo(4, 5, 1, "bridge_end"),
            new PieceInfo(4, 6, 7, "bridge_gentle_stairs"),
            new PieceInfo(4, 5, 3, "bridge_piece"),
            new PieceInfo(4, 6, 3, "bridge_steep_stairs"),
            new PieceInfo(12, 3, 12, "fat_tower_base"),
            new PieceInfo(12, 7, 12, "fat_tower_middle"),
            new PieceInfo(16, 5, 16, "fat_tower_top"),
            new PieceInfo(11, 7, 11, "second_floor_1"),
            new PieceInfo(11, 7, 11, "second_floor_2"),
            new PieceInfo(13, 1, 13, "second_roof"),
            new PieceInfo(12, 23, 28, "ship"),
            new PieceInfo(13, 7, 13, "third_floor_1"),
            new PieceInfo(13, 7, 13, "third_floor_2"),
            new PieceInfo(15, 1, 15, "third_roof"),
            new PieceInfo(6, 6, 6, "tower_base"),
            new PieceInfo(6, 3, 6, "tower_floor"),
            new PieceInfo(6, 3, 6, "tower_piece"),
            new PieceInfo(8, 4, 8, "tower_top"),
    };

    private static final int BASE_FLOOR = 0;
    private static final int BASE_ROOF = 1;
    private static final int BRIDGE_END = 2;
    private static final int BRIDGE_GENTLE_STAIRS = 3;
    private static final int BRIDGE_PIECE = 4;
    private static final int BRIDGE_STEEP_STAIRS = 5;
    private static final int FAT_TOWER_BASE = 6;
    private static final int FAT_TOWER_MIDDLE = 7;
    private static final int FAT_TOWER_TOP = 8;
    private static final int SECOND_FLOOR_1 = 9;
    private static final int SECOND_FLOOR_2 = 10;
    private static final int SECOND_ROOF = 11;
    private static final int END_SHIP = 12;
    private static final int THIRD_FLOOR_1 = 13;
    private static final int THIRD_FLOOR_2 = 14;
    private static final int THIRD_ROOF = 15;
    private static final int TOWER_BASE = 16;
    private static final int TOWER_FLOOR = 17;
    private static final int TOWER_PIECE = 18;
    private static final int TOWER_TOP = 19;

    public static FeaturePos getEndCity(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        if (Math.hypot(blockPos.getX(), blockPos.getZ()) >= 1024L) {
            EndShip ship = getEndCityPieces(mt, blockPos.getX() >> 4, blockPos.getZ() >> 4);
            blockPos.setMeta("hasShip", ship.hasShip);

            if (ship.hasShip && ship.shipPosition != null) {
                blockPos.setMeta("shipX", ship.shipPosition.x);
                blockPos.setMeta("shipZ", ship.shipPosition.z);
                blockPos.setMeta("shipRotation", ship.shipRotation);

                List<ShipPosition> chestPos = getShipChestPositions(ship.shipPosition, ship.shipRotation);
                blockPos.setMeta("chestPositions", chestPos);

                for (int i = 0; i < chestPos.size(); i++) {
                    ShipPosition pos = chestPos.get(i);
                    blockPos.setMeta("chest" + i + "X", pos.x);
                    blockPos.setMeta("chest" + i + "Z", pos.z);
                }
            }
            return blockPos;
        }
        return null;
    }

    public static Map<Integer, List<LootType.LootItem>> getShipLoot(long worldSeed, FeaturePos pos) {
        Map<Integer, List<LootType.LootItem>> allLoot = new HashMap<>();
        Boolean hasShip = pos.getMeta("hasShip", Boolean.class);
        if (hasShip == null || !hasShip) return allLoot;

        @SuppressWarnings("unchecked")
        List<ShipPosition> chestPositions = (List<ShipPosition>) pos.getMeta("chestPositions", List.class);
        if (chestPositions == null || chestPositions.isEmpty()) return allLoot;

        for (int i = 0; i < chestPositions.size(); i++) {
            ShipPosition chestPos = chestPositions.get(i);
            FeaturePos chestFeaturePos = new FeaturePos(chestPos.x, chestPos.y, chestPos.z);
            chestFeaturePos.setMeta("chestIndex", i);
            Map<Integer, List<LootType.LootItem>> chestLoot = getLoot(worldSeed, chestFeaturePos);
            int finalI = i;
            chestLoot.forEach((chestIndex, items) -> allLoot.computeIfAbsent(finalI, k -> new ArrayList<>()).addAll(items));
        }
        return allLoot;
    }

    public static Map<Integer, List<LootType.LootItem>> getLoot(long worldSeed, FeaturePos pos) {
        int chestIndex = pos.getMeta("chestIndex", Integer.class);
        int skipCount = (chestIndex == 0) ? 2 : 3;
        LootConfig config = new LootConfig(
                LOOT_CONFIG.lootTablePath(),
                LOOT_CONFIG.chestCount(),
                skipCount
        );
        return LootFactory.getLoot(config, worldSeed, pos.getX() >> 4, pos.getZ() >> 4);
    }

    public static List<ShipPosition> getShipChestPositions(ShipPosition shipPosition, int rotation) {
        if (shipPosition == null) return Collections.emptyList();

        int x = shipPosition.x;
        int y = shipPosition.y;
        int z = shipPosition.z;
        int[][] relativePositions = {{5, 4, 7}, {7, 4, 7}};
        List<ShipPosition> chests = new ArrayList<>();

        for (int i = 0; i < relativePositions.length; i++) {
            int[] rel = relativePositions[i];
            ShipPosition chestPos = calculateRotatedPosition(x, y, z, rotation, rel[0], rel[1], rel[2]);
            chestPos.chestIndex = i;
            chests.add(chestPos);
        }
        return chests;
    }

    public static EndShip getEndCityPieces(BedrockRandom mt, int chunkX, int chunkZ) {
        int rot = new BedrockRandom(chunkZ * 10387313 + chunkX).nextInt(4);
        boolean[] ship = {false};
        PieceList pieces = new PieceList();
        PieceEnv env = new PieceEnv(pieces, mt, ship);
        int x = (chunkX << 4) + 8;
        int z = (chunkZ << 4) + 8;

        Piece base = addEndCityPiece(env, null, rot, x, 0, z, BASE_FLOOR);
        base = addEndCityPiece(env, base, rot, -1, 0, -1, SECOND_FLOOR_1);
        base = addEndCityPiece(env, base, rot, -1, 4, -1, THIRD_FLOOR_1);
        base = addEndCityPiece(env, base, rot, -1, 8, -1, THIRD_ROOF);
        genPiecesRecursively(env, base, 1, "TOWER");

        EndShip result = new EndShip();
        Piece shipPiece = null;

        for (Piece piece : pieces.pieces) {
            if (piece.type == END_SHIP) {
                shipPiece = piece;
                break;
            }
        }

        if (shipPiece != null) {
            result.hasShip = true;
            result.shipPosition = new ShipPosition(shipPiece.pos.x, shipPiece.pos.y, shipPiece.pos.z);
            result.shipRotation = shipPiece.rot;
        } else {
            result.hasShip = false;
        }

        result.pieces = pieces.pieces;
        return result;
    }

    private static Piece addEndCityPiece(PieceEnv env, Piece prev, int rot, int px, int py, int pz, int typ) {
        Piece p = new Piece();
        p.name = PIECE_INFO[typ].name;
        p.rot = rot;
        p.depth = 0;
        p.type = typ;
        Pos3 pos = new Pos3(px, py, pz);

        if (prev != null) {
            pos = new Pos3(prev.pos.x, prev.pos.y, prev.pos.z);
        }

        p.pos = new Pos3(pos.x, pos.y, pos.z);
        p.bb0 = new Pos3(pos.x, pos.y, pos.z);
        p.bb1 = new Pos3(pos.x, pos.y + PIECE_INFO[typ].sy, pos.z);

        switch (rot) {
            case 0:
                p.bb1.x += PIECE_INFO[typ].sx;
                p.bb1.z += PIECE_INFO[typ].sz;
                break;
            case 1:
                p.bb0.x -= PIECE_INFO[typ].sz;
                p.bb1.z += PIECE_INFO[typ].sx;
                break;
            case 2:
                p.bb0.x -= PIECE_INFO[typ].sx;
                p.bb0.z -= PIECE_INFO[typ].sz;
                break;
            case 3:
                p.bb1.x += PIECE_INFO[typ].sz;
                p.bb0.z -= PIECE_INFO[typ].sx;
                break;
        }

        if (prev != null) {
            int dx = 0, dz = 0;
            switch (prev.rot) {
                case 0: dx += px; dz += pz; break;
                case 1: dx -= pz; dz += px; break;
                case 2: dx -= px; dz -= pz; break;
                case 3: dx += pz; dz -= px; break;
            }
            p.pos.x += dx; p.pos.y += py; p.pos.z += dz;
            p.bb0.x += dx; p.bb0.y += py; p.bb0.z += dz;
            p.bb1.x += dx; p.bb1.y += py; p.bb1.z += dz;
        }

        env.pieces.add(p);
        return p;
    }

    private static boolean genPiecesRecursively(PieceEnv env, Piece current, int depth, String genType) {
        if (depth > 8) return false;

        PieceList localPieces = new PieceList();
        PieceEnv localEnv = new PieceEnv(localPieces, env.rng, env.ship);

        boolean success = switch (genType) {
            case "TOWER" -> genTower(localEnv, current, depth);
            case "BRIDGE" -> genBridge(localEnv, current, depth);
            case "HOUSE_TOWER" -> genHouseTower(localEnv, current, depth);
            case "FAT_TOWER" -> genFatTower(localEnv, current, depth);
            default -> false;
        };

        if (!success) return false;

        int genDepth = env.rng.nextInt(32);
        boolean hasCollision = false;

        for (Piece newPiece : localPieces.pieces) {
            newPiece.depth = genDepth;
            for (Piece existingPiece : env.pieces.pieces) {
                if (isColliding(existingPiece, newPiece)) {
                    if (current.depth != existingPiece.depth) {
                        hasCollision = true;
                        break;
                    }
                }
            }
            if (hasCollision) break;
        }

        if (hasCollision) return false;

        env.pieces.addAll(localPieces.pieces);

        if (localEnv.ship[0]) {
            env.ship[0] = true;
        }

        return true;
    }

    private static boolean isColliding(Piece a, Piece b) {
        return a.bb1.x >= b.bb0.x && a.bb0.x <= b.bb1.x &&
                a.bb1.z >= b.bb0.z && a.bb0.z <= b.bb1.z &&
                a.bb1.y >= b.bb0.y && a.bb0.y <= b.bb1.y;
    }

    private static boolean genTower(PieceEnv env, Piece current, int depth) {
        int rot = current.rot;
        int x = env.rng.nextInt(2) + 3;
        int z = env.rng.nextInt(2) + 3;
        Piece base = current;
        base = addEndCityPiece(env, base, rot, x, -3, z, TOWER_BASE);
        base = addEndCityPiece(env, base, rot, 0, 7, 0, TOWER_PIECE);
        Piece floor = env.rng.nextInt(3) == 0 ? base : null;
        int floorcnt = 1 + env.rng.nextInt(3);

        for (int i = 0; i < floorcnt; i++) {
            base = addEndCityPiece(env, base, rot, 0, 4, 0, TOWER_PIECE);
            if (i < floorcnt - 1 && env.rng.nextBoolean())
                floor = base;
        }

        if (floor != null) {
            int[][] binfo = {
                    {0, 1, -1, 0},
                    {1, 6, -1, 1},
                    {3, 0, -1, 5},
                    {2, 5, -1, 6},
            };
            for (int i = 0; i < 4; i++) {
                if (!env.rng.nextBoolean()) continue;
                int brot = (rot + binfo[i][0]) & 3;
                Piece bridge = addEndCityPiece(env, base, brot, binfo[i][1], binfo[i][2], binfo[i][3], BRIDGE_END);
                genPiecesRecursively(env, bridge, depth + 1, "BRIDGE");
            }
        } else if (depth != 7) {
            return genPiecesRecursively(env, base, depth + 1, "FAT_TOWER");
        }

        addEndCityPiece(env, base, rot, -1, 4, -1, TOWER_TOP);
        return true;
    }

    private static boolean genBridge(PieceEnv env, Piece current, int depth) {
        int rot = current.rot;
        int floorcnt = 1 + env.rng.nextInt(4);
        Piece base = current;
        base = addEndCityPiece(env, base, rot, 0, 0, -4, BRIDGE_PIECE);
        base.depth = -1;

        int y = 0;
        for (int i = 0; i < floorcnt; i++) {
            if (env.rng.nextBoolean()) {
                base = addEndCityPiece(env, base, rot, 0, y, -4, BRIDGE_PIECE);
                y = 0;
                continue;
            }
            if (env.rng.nextBoolean()) {
                base = addEndCityPiece(env, base, rot, 0, y, -4, BRIDGE_STEEP_STAIRS);
            } else {
                base = addEndCityPiece(env, base, rot, 0, y, -8, BRIDGE_GENTLE_STAIRS);
            }
            y = 4;
        }

        if (!env.ship[0] && env.rng.nextInt(10 - depth) == 0) {
            int x = -8 + env.rng.nextInt(8);
            int z = -70 + env.rng.nextInt(10);

            PieceList tempPieces = new PieceList();
            PieceEnv tempEnv = new PieceEnv(tempPieces, env.rng, env.ship);

            Piece shipPiece = addEndCityPiece(tempEnv, base, rot, x, y, z, END_SHIP);

            boolean canPlace = true;
            for (Piece existingPiece : env.pieces.pieces) {
                if (isColliding(existingPiece, shipPiece)) {
                    canPlace = false;
                    break;
                }
            }

            if (canPlace) {
                env.pieces.add(shipPiece);
                env.ship[0] = true;
            } else {
                env.ship[0] = false;
                if (!genPiecesRecursively(env, base, depth + 1, "HOUSE_TOWER"))
                    return false;
            }
        } else {
            if (!genPiecesRecursively(env, base, depth + 1, "HOUSE_TOWER"))
                return false;
        }

        base = addEndCityPiece(env, base, (rot + 2) & 3, 4, y, 0, BRIDGE_END);
        base.depth = -1;
        return true;
    }

    private static boolean genHouseTower(PieceEnv env, Piece current, int depth) {
        if (depth > 8) return false;

        int rot = current.rot;
        Piece base = current;
        base = addEndCityPiece(env, base, rot, -3, 1, -11, BASE_FLOOR);
        int size = env.rng.nextInt(3);

        if (size == 0) {
            addEndCityPiece(env, base, rot, -1, 4, -1, BASE_ROOF);
            return true;
        }

        base = addEndCityPiece(env, base, rot, -1, 0, -1, SECOND_FLOOR_2);
        if (size == 1) {
            base = addEndCityPiece(env, base, rot, -1, 8, -1, SECOND_ROOF);
        } else {
            base = addEndCityPiece(env, base, rot, -1, 4, -1, THIRD_FLOOR_2);
            base = addEndCityPiece(env, base, rot, -1, 8, -1, THIRD_ROOF);
        }

        genPiecesRecursively(env, base, depth + 1, "TOWER");
        return true;
    }

    private static boolean genFatTower(PieceEnv env, Piece current, int depth) {
        int rot = current.rot;
        Piece base = current;
        base = addEndCityPiece(env, base, rot, -3, 4, -3, FAT_TOWER_BASE);
        base = addEndCityPiece(env, base, rot, 0, 4, 0, FAT_TOWER_MIDDLE);

        int[][] binfo = {
                {0,  4, -1,  0},
                {1, 12, -1,  4},
                {3,  0, -1,  8},
                {2,  8, -1, 12},
        };

        for (int j = 0; j < 2 && env.rng.nextInt(3) != 0; j++) {
            base = addEndCityPiece(env, base, rot, 0, 8, 0, FAT_TOWER_MIDDLE);
            for (int i = 0; i < 4; i++) {
                if (!env.rng.nextBoolean()) continue;
                int brot = (rot + binfo[i][0]) & 3;
                Piece bridge = addEndCityPiece(env, base, brot, binfo[i][1], binfo[i][2], binfo[i][3], BRIDGE_END);
                genPiecesRecursively(env, bridge, depth + 1, "BRIDGE");
            }
        }

        addEndCityPiece(env, base, rot, -2, 8, -2, FAT_TOWER_TOP);
        return true;
    }

    private static ShipPosition calculateRotatedPosition(int baseX, int baseY, int baseZ, int rotation, int relX, int relY, int relZ) {
        return switch (rotation) {
            case 0 -> new ShipPosition(baseX + relX, baseY + relY, baseZ + relZ);
            case 1 -> new ShipPosition(baseX - relZ, baseY + relY, baseZ + relX);
            case 2 -> new ShipPosition(baseX - relX, baseY + relY, baseZ - relZ);
            case 3 -> new ShipPosition(baseX + relZ, baseY + relY, baseZ - relX);
            default -> new ShipPosition(baseX + relX, baseY + relY, baseZ + relZ);
        };
    }

    private record PieceInfo(int sx, int sy, int sz, String name) {}

    private static class Pos3 {
        int x, y, z;

        Pos3(int x, int y, int z) {
            this.x = x; this.y = y; this.z = z;
        }
    }

    private static class Piece {
        String name;
        int rot;
        int depth;
        int type;
        Pos3 pos;
        Pos3 bb0, bb1;
    }

    private static class PieceList {
        List<Piece> pieces = new ArrayList<>();

        void add(Piece piece) {
            pieces.add(piece);
        }

        void addAll(List<Piece> otherPieces) {
            pieces.addAll(otherPieces);
        }
    }

    private static class PieceEnv {
        PieceList pieces;
        BedrockRandom rng;
        boolean[] ship;

        PieceEnv(PieceList pieces, BedrockRandom rng, boolean[] ship) {
            this.pieces = pieces;
            this.rng = rng;
            this.ship = ship;
        }
    }

    public static class EndShip {
        public boolean hasShip = false;
        public ShipPosition shipPosition = null;
        public int shipRotation = 0;
        public List<Piece> pieces = new ArrayList<>();
    }

    public static class ShipPosition {
        public int x, y, z;
        public int chestIndex;

        public ShipPosition(int x, int y, int z) {
            this.x = x; this.y = y; this.z = z;
        }
    }

    public static String format(FeaturePos pos) {
        String ship = "";
        Boolean hasShip = pos.getMeta("hasShip", Boolean.class);

        if (Boolean.TRUE.equals(hasShip)) {
            Integer shipX = pos.getMeta("shipX", Integer.class);
            Integer shipZ = pos.getMeta("shipZ", Integer.class);

            if (shipX != null && shipZ != null) {
                ship = String.format(" (with ship at %d ~ %d)", shipX, shipZ);
            } else {
                ship = " (with ship)";
            }
        }

        return String.format("chunkPos{X=%d, Z=%d}%s /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                ship,
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}