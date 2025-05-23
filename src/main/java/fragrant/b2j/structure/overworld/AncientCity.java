package fragrant.b2j.structure.overworld;

import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.StructurePos;

public class AncientCity extends StructureGenerator {

    public static StructurePos getAncientCity(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        StructurePos city = getFeatureChunkInRegion(config, mt, regX, regZ);
        StructurePos pos = getFeaturePos(config, regX, regZ, city);

        /* 0: West, 1: North, 2: East, 3: South */
        int rotation = mt.nextInt(4);
        /* city_center_1..3 */
        int type = mt.nextInt(3);

        String facing = switch (rotation) {
            case 0 -> "west";
            case 1 -> "north";
            case 2 -> "east";
            case 3 -> "south";
            default -> "unknown";
        };

        pos.setMeta("type", type);
        pos.setMeta("facing", facing);

        return pos;
    }

    public static String format(StructurePos pos) {
        Integer center = pos.getMeta("center", Integer.class);
        String facing = pos.getMeta("facing", String.class);

        return String.format("[X=%d, Y=-33, Z=%d] (city_center_%d, facing=%s)",
                pos.getX(),
                pos.getZ(),
                center + 1,
                facing
        );
    }

}