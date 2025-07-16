package fragrant.b2j.worldfeature.feature.overworld.vegetation;

import de.rasmusantons.cubiomes.BiomeID;
import de.rasmusantons.cubiomes.Dimension;
import fragrant.b2j.biome.Biome;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.worldfeature.BedrockFeatureHash;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;

public class SweetBerry extends BedrockFeatureGenerator {

    public static FeaturePos getSweetBerry(long worldSeed, int chunkX, int chunkZ, boolean skipBiomeCheck) {
        int blockX = chunkX << 4;
        int blockZ = chunkZ << 4;

        long sweet_berry_featureSeed = BedrockFeatureHash.getFeatureSeed(worldSeed, chunkX, chunkZ, BedrockFeatureHash.taiga_after_surface_sweet_berry_bush_feature_rules);
        long cold_sweet_berry_featureSeed = BedrockFeatureHash.getFeatureSeed(worldSeed, chunkX, chunkZ, BedrockFeatureHash.cold_taiga_after_surface_sweet_berry_bush_feature_rules);
        BedrockRandom sweet_berry_mt = new BedrockRandom(sweet_berry_featureSeed);
        BedrockRandom cold_sweet_berry_mt = new BedrockRandom(cold_sweet_berry_featureSeed);

        /* taiga 1/32 chance */
        if (sweet_berry_mt.nextInt(32) == 0) {
            int offsetZ = sweet_berry_mt.nextInt(16);
            int offsetX = sweet_berry_mt.nextInt(16);

            if (skipBiomeCheck) {
                return new FeaturePos(blockX + offsetX, blockZ + offsetZ);
            }

            Biome bedrockBiome = Biome.getBiomeCache(worldSeed, BedrockVersion.MC_1_21_9);
            BiomeID biome = bedrockBiome.getBiomeAt(blockX + offsetX, 128, blockZ + offsetZ, Dimension.DIM_OVERWORLD);
            if (biome == BiomeID.taiga) {
                return new FeaturePos(blockX + offsetX, blockZ + offsetZ);
            }
        }
        /* snowy_taiga 1/384 chance */
        if (cold_sweet_berry_mt.nextInt(384) == 0) {
            int offsetZ = cold_sweet_berry_mt.nextInt(16);
            int offsetX = cold_sweet_berry_mt.nextInt(16);

            if (skipBiomeCheck) {
                return new FeaturePos(blockX + offsetX, blockZ + offsetZ);
            }

            Biome bedrockBiome = Biome.getBiomeCache(worldSeed, BedrockVersion.MC_1_21_9);
            BiomeID biome = bedrockBiome.getBiomeAt(blockX + offsetX, 128, blockZ + offsetZ, Dimension.DIM_OVERWORLD);
            if (biome == BiomeID.snowy_taiga) {
                return new FeaturePos(blockX + offsetX, blockZ + offsetZ);
            }
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