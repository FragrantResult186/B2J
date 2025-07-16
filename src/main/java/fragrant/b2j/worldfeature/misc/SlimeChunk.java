package fragrant.b2j.worldfeature.misc;

import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

public class SlimeChunk {

    public static FeaturePos getSlimeChunk(int chunkX, int chunkZ) {
        BedrockRandom mt = new BedrockRandom((chunkX * 522133279) ^ chunkZ);
        /* 1/10 chance */
        if (mt.nextInt(10) == 0) {
            return new FeaturePos(chunkX, chunkZ);
        }
        return null;
    }

}
