package fragrant.b2j.feature.structure;

import fragrant.b2j.feature.BedrockFeatureConfig;
import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

public class Fortress extends BedrockFeatureGenerator {

    public static FeaturePos getFortress(BedrockFeatureConfig config, long worldSeed, int regX, int regZ, int version) {
        /* 1.14 - 1.15 */
        if (BedrockVersion.isAtMost(version, BedrockVersion.MC_1_16)) {
            int x = regX >> 4;
            int z = regZ >> 4 << 4;
            long seed = x ^ z ^ worldSeed;
            BedrockRandom mt = new BedrockRandom(seed);

            mt.nextInt(); // Skip

            /* 1/3 chance */
            if (mt.nextInt(3) == 0) {
                int expectX = 4 + (x << 4) + mt.nextInt(8);
                int expectZ = 4 + (z << 4) + mt.nextInt(8);

                if (regX == expectX && regZ == expectZ) {
                    return new FeaturePos(regX << 4, regZ << 4);
                }
            }
        } else { // 1.16 +
            BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
            FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

            /* 4/6 chance */
            boolean isFortress = mt.nextInt(6) < 2;

            return isFortress ? blockPos : null;
        }
        return null;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getX() + 11,
                pos.getZ() + 11
        );
    }

}