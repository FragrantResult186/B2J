package fragrant.b2j.structure.overworld;

import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.StructurePos;

public class WoodlandMansion extends StructureGenerator {

    public static StructurePos getWoodlandMansion(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        StructurePos chunkPos = getFeatureChunkInRegion(config, mt, regX, regZ);
        StructurePos pos = getFeaturePos(config, regX, regZ, chunkPos);

        /* 0: West, 1: North, 2: East, 3: South */
        int rotation = mt.nextInt(4);
        String facing = switch (rotation) {
            case 0 -> "west";
            case 1 -> "north";
            case 2 -> "east";
            case 3 -> "south";
            default -> "unknown";
        };

        pos.setMeta("facing", facing);
        return pos;
    }

    public static String format(StructurePos pos) {
        String facing = pos.getMeta("facing", String.class);

        return String.format("[X=%d, Z=%d] (facing=%s)",
                pos.getX() + 8,
                pos.getZ() + 8,
                facing
        );
    }

}