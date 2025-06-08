package fragrant.b2j.feature.structure;

import fragrant.b2j.feature.BedrockFeatureConfig;
import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.Direction;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;

public class WoodlandMansion extends BedrockFeatureGenerator {

    public static FeaturePos getWoodlandMansion(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        int rotation = mt.nextInt(4);
        String facing = Direction.getFacing(rotation);

        blockPos.setMeta("facing", facing);
        return blockPos;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} (facing=%s) /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getMeta("facing", String.class),
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}