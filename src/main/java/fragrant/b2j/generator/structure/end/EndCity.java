package fragrant.b2j.generator.structure.end;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.Position;

public class EndCity extends StructureGenerator {

    public static Position.Pos getEndCity(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature endCity = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        Position.Pos pos = getFeaturePos(config, regX, regZ, endCity.position());

        /* No spawns within 63*63 chunks (1008 blocks) */
        if (Math.hypot(pos.getX(), pos.getZ()) >= 1008) {

            /* hasShip */
            EndShip ship = new EndShip();
//            System.out.println("add BASE_FLOOR");
//            System.out.println("add SECOND_FLOOR_1");
//            System.out.println("add THIRD_FLOOR_1");
//            System.out.println("add THIRD_ROOF");
            generatePieces("TOWER_GENERATOR", 1, endCity.mt(), ship);

            pos.setMeta("hasShip", ship.hasShip);

            return pos;
        }
        return null;
    }

    private static class EndShip {
        boolean hasShip = false;
    }

    private static boolean generatePieces(String tower, int depth, BedrockRandom mt, EndShip ship) {
        if (depth > 8 || ship.hasShip) {
            return false;
        }

        boolean type = switch (tower) {
            case "TOWER_GENERATOR"        -> TOWER_GENERATOR(depth, mt, ship);
            case "TOWER_BRIDGE_GENERATOR" -> TOWER_BRIDGE_GENERATOR(depth, mt, ship);
            case "HOUSE_TOWER_GENERATOR"  -> HOUSE_TOWER_GENERATOR(depth, mt, ship);
            case "FAT_TOWER_GENERATOR"    -> FAT_TOWER_GENERATOR(depth, mt, ship);
            default -> false;
        };

        if (!type) {
            return false;
        }

        mt.nextInt();
        return true;
    }

    private static boolean TOWER_GENERATOR(int depth, BedrockRandom mt, EndShip shipData) {
        int x = 3 + mt.nextInt(2);
        int z = 3 + mt.nextInt(2);
        boolean currentFloor = mt.nextInt(3) == 0;
//        System.out.println("add TOWER_BASE");
//        System.out.println("add TOWER_PIECE");
        int size = mt.nextInt(3) + 1;
        for (int floor = 0; floor < size - 1; floor++) {
//            System.out.println("add TOWER_PIECE");
            if (mt.nextBoolean()) {
                currentFloor = true;
            }
        }

        if (currentFloor) {
            for (int i = 0; i < 4; i++) {
                if (!mt.nextBoolean()) continue;
//                System.out.println("add BRIDGE_END");
                generatePieces("TOWER_BRIDGE_GENERATOR", depth + 1, mt, shipData);
            }
        } else if (depth != 7) {
            return generatePieces("FAT_TOWER_GENERATOR", depth + 1, mt, shipData);
        }
//        System.out.println("add TOWER_TOP");
        return true;
    }

    private static boolean TOWER_BRIDGE_GENERATOR(int depth, BedrockRandom mt, EndShip ship) {
        int size = mt.nextInt(4) + 1;
//        System.out.println("add BRIDGE_PIECE");
        for (int floor = 0; floor < size; floor++) {
            if (mt.nextBoolean()) {
//                System.out.println("add BRIDGE_PIECE");
                continue;
            }
            if (mt.nextBoolean()) {
//                System.out.println("add BRIDGE_STEEP_STAIRS");
            } else {
//                System.out.println("add BRIDGE_GENTLE_STAIRS");
            }
        }
//        System.out.println("add BRIDGE_END");

        if (ship.hasShip || 0 != mt.nextInt(10 - depth)) {
            if (!generatePieces("HOUSE_TOWER_GENERATOR", depth + 1, mt, ship))
                return false;
        } else {
            int x = -8 + mt.nextInt(8);
            int z = -70 + mt.nextInt(10);
//            System.out.println("add END_SHIP");
            ship.hasShip = true;
        }
        return true;
    }

    private static boolean HOUSE_TOWER_GENERATOR(int depth, BedrockRandom mt, EndShip ship) {
        if (depth > 8) return false;

//        System.out.println("add BASE_FLOOR");
        int size = mt.nextInt(3);
        if (size == 0) {
//            System.out.println("add BASE_ROOF");
            return true;
        }
        if (size == 1) {
//            System.out.println("add SECOND_FLOOR_2");
//            System.out.println("add SECOND_ROOF");
        }
        else {
//            System.out.println("add SECOND_FLOOR_2");
//            System.out.println("add THIRD_FLOOR_2");
//            System.out.println("add THIRD_ROOF");
        }
        generatePieces("TOWER_GENERATOR", depth + 1, mt, ship);
        return true;
    }

    private static boolean FAT_TOWER_GENERATOR(int depth, BedrockRandom mt, EndShip ship) {
//        System.out.println("add FAT_TOWER_BASE");
//        System.out.println("add FAT_TOWER_MIDDLE");
        for (int floor = 0; floor < 2 && mt.nextInt(3) != 0; floor++) {
//            System.out.println("add FAT_TOWER_MIDDLE");
            for (int i = 0; i < 4; i++) {
                if (!mt.nextBoolean()) continue;
//                System.out.println("add BRIDGE_END");
                generatePieces("TOWER_BRIDGE_GENERATOR", depth + 1, mt, ship);
            }
        }
//        System.out.println("add FAT_TOWER_TOP");
        return true;
    }

    public static String format(Position.Pos pos) {
        String ship = Boolean.TRUE.equals(pos.getMeta("hasShip", Boolean.class)) ? "(with ship)" : "";

        return String.format("[X=%d, Z=%d] %s",
                pos.getX() + 8,
                pos.getZ() + 8,
                ship
        );
    }

}