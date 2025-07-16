package fragrant.b2j.worldfeature.feature.overworld.underground;

import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.worldfeature.BedrockFeatureHash;
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
            int val = coal_mt.nextInt(4) + 1;
            String type = "(coal, skull_" + val + " or spine_" + val + ")"; // Pure guess
            FeaturePos pos = new FeaturePos(chunkX << 4, chunkZ << 4);
            pos.setType(type);
            return pos;
        }
        /* diamond 1/64 chance */
        if (diamond_mt.nextInt(64) == 0) {
            int val = coal_mt.nextInt(4) + 1;
            String type = "(diamond, skull_" + val + " or spine_" + val + ")"; // Pure guess
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