package fragrant.b2j.worldfeature.structure.overworld.underwater;

import fragrant.b2j.worldfeature.BedrockFeatureConfig;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

public class OceanMonument extends BedrockFeatureGenerator {

    public static FeaturePos getOceanMonument(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
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