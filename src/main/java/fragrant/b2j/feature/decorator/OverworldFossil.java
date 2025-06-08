package fragrant.b2j.feature.decorator;

import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.feature.BedrockFeatureHash;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;

public class OverworldFossil extends BedrockFeatureGenerator {

    public static FeaturePos getOverworldFossil(long worldSeed, int chunkX, int chunkZ) {
        long coal_featureSeed = BedrockFeatureHash.getFeatureSeed(worldSeed, chunkX, chunkZ, BedrockFeatureHash.desert_or_swamp_after_surface_fossil_feature);
        long diamond_featureSeed = BedrockFeatureHash.getFeatureSeed(worldSeed, chunkX, chunkZ, BedrockFeatureHash.desert_or_swamp_after_surface_fossil_deepslate_feature);
        BedrockRandom coal_mt = new BedrockRandom(coal_featureSeed);
        BedrockRandom diamond_mt = new BedrockRandom(diamond_featureSeed);

        /* coal 1/64 chance */
        if (coal_mt.nextInt(64) == 0) {
            String type = switch (coal_mt.nextInt(4)) { // Pure guess
                case 0 -> "(coal, skull_1 or spine_1)";
                case 1 -> "(coal, skull_2 or spine_2)";
                case 2 -> "(coal, skull_3 or spine_3)";
                case 3 -> "(coal, skull_4 or spine_4)";
                default -> "null";
            };
            FeaturePos pos = new FeaturePos(chunkX << 4, chunkZ << 4);
            pos.setType(type);
            return pos;
        }
        /* diamond 1/64 chance */
        if (diamond_mt.nextInt(64) == 0) {
            String type = switch (diamond_mt.nextInt(4)) {
                case 0 -> "(diamond, skull_1 or spine_1)";
                case 1 -> "(diamond, skull_2 or spine_2)";
                case 2 -> "(diamond, skull_3 or spine_3)";
                case 3 -> "(diamond, skull_4 or spine_4)";
                default -> "null";
            };
            FeaturePos pos = new FeaturePos(chunkX << 4, chunkZ << 4);
            pos.setType(type);
            return pos;
        }
        return null;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} %s /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getType(),
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}