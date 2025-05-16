package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.Position;

public class WoodlandMansion extends StructureGenerator {

    public static Position.Pos getWoodlandMansion(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature mansion = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        Position.Pos pos = getFeaturePos(config, regX, regZ, mansion.position());
        BedrockRandom mt = mansion.mt();

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

    public static String format(Position.Pos pos) {
        String facing = pos.getMeta("facing", String.class);

        return String.format("[X=%d, Z=%d] (facing=%s)",
                pos.getX() + 8,
                pos.getZ() + 8,
                facing
        );
    }

}
