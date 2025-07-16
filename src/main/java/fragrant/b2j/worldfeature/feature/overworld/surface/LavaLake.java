package fragrant.b2j.worldfeature.feature.overworld.surface;

import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

public class LavaLake extends BedrockFeatureGenerator {

    public static FeaturePos getLavaLake(long worldSeed, int chunkX, int chunkZ) {
        long seed = BedrockFeatureGenerator.chunkGenerateRnd(worldSeed, chunkX, chunkZ);
        BedrockRandom mt = new BedrockRandom(seed);

        /* 1/8 chance */
        if (mt.nextInt(8) == 0) {
            int x = chunkX * 16 + mt.nextInt(9);
            int r = mt.nextInt(120);
            int y = mt.nextInt(r + 8);
            if (y < 60) return null;
            int z = chunkZ * 16 + mt.nextInt(9);
            /* more check */
            if (y >= 64) {
                /* 1/10 chance */
                if (mt.nextInt(10) != 0) {
                    return null;
                }
            }
            FeaturePos pos = new FeaturePos(x, y, z);
            pos.setMeta("x", x);
            pos.setMeta("y", y);
            pos.setMeta("z", z);
            return pos;
        }
        return null;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} /tp %d %d %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getX(),
                pos.getY(),
                pos.getZ()
        );
    }

}