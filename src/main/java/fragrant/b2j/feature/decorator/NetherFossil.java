package fragrant.b2j.feature.decorator;

import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.feature.BedrockFeatureHash;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

public class NetherFossil extends BedrockFeatureGenerator {

    public static FeaturePos getNetherFossil(long worldSeed, int chunkX, int chunkZ) {
        int featureSeed = BedrockFeatureHash.getFeatureSeed(worldSeed, chunkX, chunkZ, BedrockFeatureHash.soulsand_valley_fossil_surface_feature);
        BedrockRandom mt = new BedrockRandom(featureSeed);

        /* 2/6 chance */
        if (mt.nextInt(6) < 2) // It may not generate because heightmap isn't used
        {
            int blockX = chunkX << 4;
            int blockZ = chunkZ << 4;
            int offsetX = mt.nextInt(16);
            int offsetZ = mt.nextInt(16);
            String type = switch (mt.nextInt(14)) { // Pure guess
                case 0 -> "fossil_1";
                case 1 -> "fossil_2";
                case 2 -> "fossil_3";
                case 3 -> "fossil_4";
                case 4 -> "fossil_5";
                case 5 -> "fossil_6";
                case 6 -> "fossil_7";
                case 7 -> "fossil_8";
                case 8 -> "fossil_9";
                case 9 -> "fossil_10";
                case 10 -> "fossil_11";
                case 11 -> "fossil_12";
                case 12 -> "fossil_13";
                case 13 -> "fossil_14";
                default -> "null";
            };
            FeaturePos pos = new FeaturePos(blockX + offsetX, blockZ + offsetZ, type);
            pos.setType(type);
            return pos;
        }

        return null;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} (%s) /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getType(),
                pos.getX(),
                pos.getZ()
        );
    }

}