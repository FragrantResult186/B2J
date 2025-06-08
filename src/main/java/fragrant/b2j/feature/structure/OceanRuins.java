package fragrant.b2j.feature.structure;

import de.rasmusantons.cubiomes.BiomeID;
import de.rasmusantons.cubiomes.Dimension;
import fragrant.b2j.biome.Biome;
import fragrant.b2j.feature.BedrockFeatureConfig;
import fragrant.b2j.feature.BedrockFeatureType;
import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;

import java.util.HashMap;
import java.util.Map;

public class OceanRuins extends BedrockFeatureGenerator {

    public static FeaturePos getOceanRuins(BedrockFeatureConfig config, long worldSeed, int regX, int regZ, int version, boolean skipBiomeCheck) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        /* Cold or Warm */
        Ruin ruinConfig = new Ruin("null", 0, 0);
        if (!skipBiomeCheck) {
            Biome bedrockBiome = Biome.getBiomeCache(worldSeed, version);
            BiomeID biome = bedrockBiome.getBiomeAt(blockPos.getX(), 60, blockPos.getZ(), Dimension.DIM_OVERWORLD);
            if (BIOME_CONFIG.containsKey(biome)) {
                ruinConfig = BIOME_CONFIG.get(biome);
            }
        }

        /* Region Random (ocean monument) */
        BedrockFeatureConfig monumentConfig = BedrockFeatureConfig.getForType(BedrockFeatureType.OCEAN_MONUMENT, version);
        int newRegX = Math.floorDiv(blockPos.getX() >> 4, monumentConfig.getSpacing());
        int newRegZ = Math.floorDiv(blockPos.getZ() >> 4, monumentConfig.getSpacing());

        mt = BedrockFeatureGenerator.setRegionSeed(monumentConfig, worldSeed, newRegX, newRegZ);

        mt.skipNextInt(4); // Skip Triangular distribution rng
        mt.nextInt(4); // Skip

        /* isLarge */
        if (mt.nextFloat() < ruinConfig.largeProb()) {
            blockPos.setMeta("isLarge", true);

            mt.nextInt(); // Skip

            /* isCluster */
            if (mt.nextFloat() <= ruinConfig.clusterProb()) {
                mt.skipNextInt(16); // Skip *16

                int size = 4 + mt.nextInt(5); // 4 ~ 8
                blockPos.setMeta("clusterSize", size);
            }
        }
        blockPos.setMeta("ruinType", ruinConfig.type());

        return blockPos;
    }

    private record Ruin(String type, float largeProb, float clusterProb) {
    }

    private static final Map<BiomeID, Ruin> BIOME_CONFIG = new HashMap<>();

    static {
        /* COLD OCEANS */
        BIOME_CONFIG.put(BiomeID.frozen_ocean, new Ruin("cold", 0.3f, 0.25f));
        BIOME_CONFIG.put(BiomeID.cold_ocean, new Ruin("cold", 0.3f, 0.25f));
        BIOME_CONFIG.put(BiomeID.ocean, new Ruin("cold", 0.3f, 0.25f));

        /* DEEP COLD OCEANS */
        BIOME_CONFIG.put(BiomeID.deep_frozen_ocean, new Ruin("cold", 0.5f, 0.4f));
        BIOME_CONFIG.put(BiomeID.deep_cold_ocean, new Ruin("cold", 0.5f, 0.4f));
        BIOME_CONFIG.put(BiomeID.deep_ocean, new Ruin("cold", 0.5f, 0.4f));

        /* WARM OCEANS */
        BIOME_CONFIG.put(BiomeID.warm_ocean, new Ruin("warm", 0.3f, 0.5f));
        BIOME_CONFIG.put(BiomeID.lukewarm_ocean, new Ruin("warm", 0.3f, 0.5f));
        BIOME_CONFIG.put(BiomeID.deep_lukewarm_ocean, new Ruin("warm", 0.3f, 0.5f));
    }

    public static String format(FeaturePos pos) {
        Boolean isLarge = pos.getMeta("isLarge", Boolean.class);
        String size = isLarge != null && isLarge ? "large" : "small";
        Integer clusterSize = pos.getMeta("clusterSize", Integer.class);
        String clusterCnt = clusterSize != null && clusterSize > 0 ? ", small ruins*" + clusterSize : "";
        String type = pos.getMeta("ruinType", String.class);

        return String.format("chunkPos{X=%d, Z=%d} (%s, %s%s) /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                size,
                type,
                clusterCnt,
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}