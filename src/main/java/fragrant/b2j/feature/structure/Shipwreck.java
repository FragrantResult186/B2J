package fragrant.b2j.feature.structure;

import fragrant.b2j.feature.BedrockFeatureConfig;
import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

public class Shipwreck extends BedrockFeatureGenerator {

    public static FeaturePos getShipwreck(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        return blockPos;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}