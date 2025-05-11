package fragrant.b2j.generator.structure.nether;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.Position;

public class Bastion extends StructureGenerator {

    public static Position.Pos getBastion(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature bastion = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        Position.Pos blockPos = getFeaturePos(config, regX, regZ, bastion.position());

        /* isBastion (66% chance) */
        BedrockRandom mt = bastion.mt();
        boolean isBastion = mt.nextInt(6) >= 2;

        /* Type */
        if (isBastion) {
            mt.nextInt(); // Skip
            String type = switch (mt.nextInt(4)) {
                /* Bedrock is the opposite */
                case 0 -> "bridge";
                case 1 -> "treasure_room";
                case 2 -> "hoglin_stables";
                case 3 -> "housing_units";
                default -> "null";
            };
            blockPos.setType(type);
            return blockPos;
        }

        return null;
    }

    public static String format(Position.Pos pos) {
        return String.format("[X=%d, Z=%d] (%s)",
                pos.getX(),
                pos.getZ(),
                pos.getType()
        );
    }

}