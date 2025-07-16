package fragrant.b2j.worldfeature.feature.overworld.underground;

import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.worldfeature.BedrockFeatureHash;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

public class AmethystGeode extends BedrockFeatureGenerator {

    public static FeaturePos getGeode(long worldSeed, int chunkX, int chunkZ, int version) {
        int featureSeed = BedrockFeatureHash.getFeatureSeed(worldSeed, chunkX, chunkZ, BedrockFeatureHash.overworld_amethyst_geode_feature);
        BedrockRandom mt = new BedrockRandom(featureSeed);

        int spawnChance;
        int[] yRange;
        if (version >= BedrockVersion.MC_1_18) {
            spawnChance = 24;
            yRange = new int[]{-58, 30};
        } else {
            spawnChance = 53;
            yRange = new int[]{6, 47};
        }

        /* 1/24 chance 1.18+, 1/53 chance 1.17- */
        if (mt.nextInt(spawnChance) == 0) {
            int y = mt.nextInt(yRange[0], yRange[1]);
            return new FeaturePos(chunkX << 4, y, chunkZ << 4);
        }

        return null;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} /tp %d %d %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getX() + 4,
                pos.getY() + 4,
                pos.getZ() + 4
        );
    }

}