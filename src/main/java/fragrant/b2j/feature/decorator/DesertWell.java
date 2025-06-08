package fragrant.b2j.feature.decorator;

import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.feature.BedrockFeatureHash;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;

public class DesertWell extends BedrockFeatureGenerator {

    public static FeaturePos getDesertWell(long worldSeed, int chunkX, int chunkZ) {
        int featureSeed = BedrockFeatureHash.getFeatureSeed(worldSeed, chunkX, chunkZ, BedrockFeatureHash.desert_after_surface_desert_well_feature);
        BedrockRandom mt = new BedrockRandom(featureSeed);

        /* 1/500 chance */
        if (mt.nextInt(500) == 0) {
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