package fragrant.b2j.finder;

import fragrant.b2j.worldfeature.BedrockFeature;
import fragrant.b2j.worldfeature.BedrockFeatureType;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

import java.util.concurrent.atomic.AtomicLong;

public class UndergroundGiantRP {
    public static void main(String[] args) {
        int chunkX = 0;
        int chunkZ = 0;
        int version = BedrockVersion.MC_1_21_9;

        AtomicLong seedsChecked = new AtomicLong(0);
        long seed = 0;
        int fnd = 0;
        while (fnd < 100) {
            if (check(seed, chunkX, chunkZ, version)) {
                fnd++;
                System.out.printf("%d%n", seed);
            }
            seed++;
            seedsChecked.incrementAndGet();
        }
    }

    private static boolean check(long worldSeed, int chunkX, int chunkZ, int version) {
        FeaturePos rp = BedrockFeature.isFeatureChunk(BedrockFeatureType.RUINED_PORTAL_O, version, worldSeed, chunkX, chunkZ, true);
        if (rp == null) return false;
        return rp.getMeta("giant", Boolean.class) &&
               rp.getMeta("underground", Boolean.class) &&
               rp.getMeta("variant", Integer.class) == 3 &&
               !rp.getMeta("mirror", Boolean.class) &&
               rp.getMeta("rotation", Integer.class) == 1;
    }

}