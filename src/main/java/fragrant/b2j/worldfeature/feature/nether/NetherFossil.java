package fragrant.b2j.worldfeature.feature.nether;

import fragrant.b2j.terrain.nether.NetherUtil;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.worldfeature.BedrockFeatureHash;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

public class NetherFossil extends BedrockFeatureGenerator {

    public static FeaturePos getNetherFossil(long worldSeed, int chunkX, int chunkZ, boolean skipBiomeCheck) {
        int featureSeed = BedrockFeatureHash.getFeatureSeed(worldSeed, chunkX, chunkZ, BedrockFeatureHash.soulsand_valley_fossil_surface_feature);
        BedrockRandom mt = new BedrockRandom(featureSeed);

        /* 2/6 chance */
        if (mt.nextInt(6) < 2) // It may not generate because heightmap isn't used
        {
            int blockX = chunkX << 4;
            int blockZ = chunkZ << 4;
            if (!skipBiomeCheck) {
                int biomeId = NetherUtil.getBiome(worldSeed,blockX, blockZ);
                String biomeName = NetherUtil.getBiomeName(biomeId);
                if (!"soul_sand_valley".equals(biomeName)) return null;
            }
            int offsetX = mt.nextInt(16);
            int offsetZ = mt.nextInt(16);
            String type = "fossil_" + (mt.nextInt(14) + 1); // Pure guess
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