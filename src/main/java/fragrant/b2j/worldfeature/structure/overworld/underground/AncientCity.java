package fragrant.b2j.worldfeature.structure.overworld.underground;

import fragrant.b2j.worldfeature.BedrockFeatureConfig;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.Direction;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;

public class AncientCity extends BedrockFeatureGenerator {

    public static FeaturePos getAncientCity(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        int rotation = mt.nextInt(4); // Pure guess
        /* city_center_1..3 */
        int type = mt.nextInt(3); // Pure guess

        String facing = Direction.getFacing(rotation);
        String center_type = switch (type) {
            case 0 -> "city_center_1";
            case 1 -> "city_center_2";
            case 2 -> "city_center_3";
            default -> "unknown";
        };

        blockPos.setMeta("type", center_type);
        blockPos.setMeta("facing", facing);

        return blockPos;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} (%s, facing=%s) /tp %d -33 %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getMeta("type", String.class),
                pos.getMeta("facing", String.class),
                pos.getX(),
                pos.getZ()
        );
    }

}