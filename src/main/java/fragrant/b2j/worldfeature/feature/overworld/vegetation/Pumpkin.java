package fragrant.b2j.worldfeature.feature.overworld.vegetation;

import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.worldfeature.BedrockFeatureHash;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;

public class Pumpkin extends BedrockFeatureGenerator {

    public static FeaturePos getPumpkin(long worldSeed, int chunkX, int chunkZ) {
        int featureSeed = BedrockFeatureHash.getFeatureSeed(worldSeed, chunkX, chunkZ, BedrockFeatureHash.overworld_after_surface_pumpkin_feature_rules);
        BedrockRandom mt = new BedrockRandom(featureSeed);

        /* 1/300 chance */
        if (mt.nextInt(300) == 0) {
            int blockX = chunkX << 4;
            int blockZ = chunkZ << 4;
            int offsetZ = mt.nextInt(16);
            int offsetX = mt.nextInt(16);
            return new FeaturePos(blockX + offsetX, blockZ + offsetZ);
        }

        return null;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getX(),
                pos.getZ()
        );
    }

}