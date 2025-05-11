package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.BedrockStructureType;
import fragrant.b2j.biome.BiomeCache;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.Position;
import nl.jellejurre.biomesampler.minecraft.Biome;
import nl.kallestruik.noisesampler.minecraft.Dimension;

import java.util.HashMap;
import java.util.Map;

public class OceanRuins extends StructureGenerator {

    public static Position.Pos getOceanRuins(BedrockStructureConfig config, long worldSeed, int regX, int regZ, int version, boolean skipBiomeCheck) {
        Feature ruins = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        Position.Pos blockPos = getFeaturePos(config, regX, regZ, ruins.position());

        /* Cold or Warm */
        Ruin ruinConfig = new Ruin("null", 0, 0);
        if (!skipBiomeCheck) {
            BiomeCache biomeCache = new BiomeCache(worldSeed);
            Biome biome = biomeCache.getBiomeAt(blockPos.getX(), 60, blockPos.getZ(), Dimension.OVERWORLD);
            if (BIOME_CONFIG.containsKey(biome)) {
                ruinConfig = BIOME_CONFIG.get(biome);
            }
        }

        /* Region Random (ocean monument) */
        BedrockStructureConfig monumentConfig = BedrockStructureConfig.getForType(BedrockStructureType.OCEAN_MONUMENT, version);
        int newRegX = Math.floorDiv(ruins.position().getX(), monumentConfig.getSpacing());
        int newRegZ = Math.floorDiv(ruins.position().getZ(), monumentConfig.getSpacing());
        BedrockRandom mt = StructureGenerator.setRegionSeed(monumentConfig, worldSeed, newRegX, newRegZ);
        mt.skip(4); // Skip Triangular distribution rng
        mt.nextInt(); // Skip

        /* isLarge */
        if (mt.nextFloat() < ruinConfig.largeProb()) {
            blockPos.setMeta("isLarge", true);
            mt.nextInt(); // Skip
            /* isCluster */
            if (mt.nextFloat() <= ruinConfig.clusterProb()) {
                mt.skip(16); // Skip *16
                int clusterSize = 4 + mt.nextInt(5); // 4 ~ 8
                blockPos.setMeta("clusterSize", clusterSize);
            }
        }
        blockPos.setMeta("ruinType", ruinConfig.type());

        return blockPos;
    }

    private record Ruin(String type, float largeProb, float clusterProb) {}
    private static final Map<Biome, Ruin> BIOME_CONFIG = new HashMap<>();
    static {
        /* COLD OCEANS */
        BIOME_CONFIG.put(Biome.FROZEN_OCEAN, new Ruin("cold", 0.3f, 0.25f));
        BIOME_CONFIG.put(Biome.COLD_OCEAN, new Ruin("cold", 0.3f, 0.25f));
        BIOME_CONFIG.put(Biome.OCEAN, new Ruin("cold", 0.3f, 0.25f));

        /* DEEP COLD OCEANS */
        BIOME_CONFIG.put(Biome.DEEP_FROZEN_OCEAN, new Ruin("cold", 0.5f, 0.4f));
        BIOME_CONFIG.put(Biome.DEEP_COLD_OCEAN, new Ruin("cold", 0.5f, 0.4f));
        BIOME_CONFIG.put(Biome.DEEP_OCEAN, new Ruin("cold", 0.5f, 0.4f));

        /* WARM OCEANS */
        BIOME_CONFIG.put(Biome.WARM_OCEAN, new Ruin("warm", 0.3f, 0.5f));
        BIOME_CONFIG.put(Biome.LUKEWARM_OCEAN, new Ruin("warm", 0.3f, 0.5f));
        BIOME_CONFIG.put(Biome.DEEP_LUKEWARM_OCEAN, new Ruin("warm", 0.3f, 0.5f));
    }

    public static String format(Position.Pos pos) {
        Boolean isLarge = pos.getMeta("isLarge", Boolean.class);
        String sizeLabel = isLarge != null && isLarge ? "large" : "small";
        Integer clusterSize = pos.getMeta("clusterSize", Integer.class);
        String clusterInfo = clusterSize != null && clusterSize > 0 ? ", cluster*" + clusterSize : "";
        String ruinType = pos.getMeta("ruinType", String.class);

        return String.format(
                "[X=%d, Z=%d] (%s, %s%s)",
                pos.getX() + 8,
                pos.getZ() + 8,
                sizeLabel,
                ruinType,
                clusterInfo
        );
    }

}