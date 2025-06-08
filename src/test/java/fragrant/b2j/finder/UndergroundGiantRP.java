package fragrant.b2j.finder;

import fragrant.b2j.feature.BedrockFeatureConfig;
import fragrant.b2j.feature.BedrockFeatureType;
import fragrant.b2j.feature.structure.RuinedPortal;
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
            if (checkSeed(seed, chunkX, chunkZ, version)) {
                fnd++;
                System.out.printf("%d%n", seed);
            }
            seed++;
            seedsChecked.incrementAndGet();
        }
    }

    private static boolean checkSeed(long worldSeed, int chunkX, int chunkZ, int version) {
        try {
            BedrockFeatureConfig c = BedrockFeatureConfig.getForType(BedrockFeatureType.RUINED_PORTAL_O, version);
            if (c == null) return false;
            int regionSize = c.getSpacing();
            int regX = Math.floorDiv(chunkX, regionSize);
            int regZ = Math.floorDiv(chunkZ, regionSize);
            FeaturePos p = RuinedPortal.getOverworldRuinedPortal(c, (int) worldSeed, regX, regZ);
            int portalChunkX = p.getX() >> 4;
            int portalChunkZ = p.getZ() >> 4;
            if (portalChunkX != chunkX || portalChunkZ != chunkZ) return false;
            Boolean isGiant = p.getMeta("giant", Boolean.class);
            Boolean isUnderground = p.getMeta("underground", Boolean.class);
            return isGiant != null && isGiant && isUnderground != null && isUnderground;
        } catch (Exception e) {
            return false;
        }
    }

}