package fragrant.b2j.worldfeature.structure.nether;

import fragrant.b2j.terrain.nether.NetherUtil;
import fragrant.b2j.worldfeature.BedrockFeatureConfig;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;

public class Bastion extends BedrockFeatureGenerator {

    public static FeaturePos getBastion(BedrockFeatureConfig config, long worldSeed, int regX, int regZ, boolean skipBiomeCheck) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        /*  2/6 chance */
        boolean isBastion = mt.nextInt(6) >= 2;
        /* Type */
        if (isBastion) {
            if (!skipBiomeCheck) {
                int biomeId = NetherUtil.getBiome(worldSeed, blockPos.getX(), blockPos.getZ());
                String biomeName = NetherUtil.getBiomeName(biomeId);
                if ("basalt_deltas".equals(biomeName)) return null;
            }

            int rotation = mt.nextInt(4);
            int bastionType = mt.nextInt(4);
            String type = switch (bastionType) {
                /* Bedrock is the opposite */
                case 0 -> "bridge";
                case 1 -> "treasure_room";
                case 2 -> "hoglin_stables";
                case 3 -> "housing_units";
                default -> "null";
            };
            int offsetX = 0;
            int offsetZ = 0;
            int y = 0;
            switch (type) {
                case "bridge" -> {
                    switch (rotation) {
                        case 0: offsetX =  2; offsetZ =  16; break;
                        case 1: offsetX = -2; offsetZ =  16; break;
                        case 2: offsetX = -2; offsetZ = -16; break;
                        case 3: offsetX =  2; offsetZ = -16; break;
                    }
                    y = 69;
                }
                case "treasure_room" -> {
                    switch (rotation) {
                        case 0: offsetX =  20; offsetZ =  20; break;
                        case 1: offsetX = -20; offsetZ =  20; break;
                        case 2: offsetX = -20; offsetZ = -20; break;
                        case 3: offsetX =  20; offsetZ = -20; break;
                    }
                    y = 37;
                }
                case "hoglin_stables" -> {
                    switch (rotation) {
                        case 0: offsetX =  12; offsetZ =  22; break;
                        case 1: offsetX = -12; offsetZ =  22; break;
                        case 2: offsetX = -12; offsetZ = -22; break;
                        case 3: offsetX =  12; offsetZ = -22; break;
                    }
                    y = 34;
                }
                case "housing_units" -> {
                    switch (rotation) {
                        case 0: offsetX =  20; offsetZ =  20; break;
                        case 1: offsetX = -20; offsetZ =  20; break;
                        case 2: offsetX = -20; offsetZ = -20; break;
                        case 3: offsetX =  20; offsetZ = -20; break;
                    }
                    y = 38;
                }
            }

            blockPos.setMeta("startX", blockPos.getX());
            blockPos.setMeta("startZ", blockPos.getZ());
            blockPos.setMeta("x", blockPos.getX() + offsetX);
            blockPos.setMeta("y", y);
            blockPos.setMeta("z", blockPos.getZ() + offsetZ);
            blockPos.setMeta("bastion_type", type);

            return blockPos;
        }

        return null;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} (%s) /tp %d %d %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getMeta("bastion_type", String.class),
                pos.getMeta("x", Integer.class),
                pos.getMeta("y", Integer.class),
                pos.getMeta("z", Integer.class)
        );
    }

}