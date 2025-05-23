package fragrant.b2j.structure.nether;

import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.StructurePos;

public class Bastion extends StructureGenerator {

    public static StructurePos getBastion(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        StructurePos chunkPos = getFeatureChunkInRegion(config, mt, regX, regZ);
        StructurePos structurePos = getFeaturePos(config, regX, regZ, chunkPos);

        /* isBastion (66% chance) */
        boolean isBastion = mt.nextInt(6) >= 2;
        /* Type */
        if (isBastion)
        {
            mt.nextInt(4); // Skip
            String type = switch (mt.nextInt(4)) {
                /* Bedrock is the opposite */
                case 0 -> "bridge";
                case 1 -> "treasure_room";
                case 2 -> "hoglin_stables";
                case 3 -> "housing_units";
                default -> "null";
            };
            structurePos.setType(type);
            return structurePos;
        }

        return null;
    }

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Z=%d] (%s)",
                pos.getX(),
                pos.getZ(),
                pos.getType()
        );
    }

}