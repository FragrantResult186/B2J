package fragrant.b2j;

import fragrant.b2j.worldfeature.BedrockFeatureConfig;
import fragrant.b2j.worldfeature.BedrockFeature;
import fragrant.b2j.worldfeature.BedrockFeatureType;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Example1 {
    static int MAX = 1000;

    public static void main(String[] args) {

        test(1234567890L, 0>>4, 0>>4, 100, BedrockVersion.MC_1_21, false);

    }

    private static void test(long worldSeed, int centerChunkX, int centerChunkZ, int radiusChunk, int version, boolean skipBiomeCheck) {
        System.out.println("\u001b[34m" + "MC version: " + version + "\u001b[0m");
        List<Integer> featureTypes = Arrays.asList(
//                BedrockFeatureType.ANCIENT_CITY,
//                BedrockFeatureType.DESERT_PYRAMID
//                BedrockFeatureType.IGLOO,
//                BedrockFeatureType.JUNGLE_TEMPLE,
//                BedrockFeatureType.SWAMP_HUT,
//                BedrockFeatureType.WOODLAND_MANSION,
//                BedrockFeatureType.OCEAN_MONUMENT,
//                BedrockFeatureType.OCEAN_RUINS,
//                BedrockFeatureType.PILLAGER_OUTPOST,
//                BedrockFeatureType.SHIPWRECK,
//                BedrockFeatureType.MINESHAFT,
//                BedrockFeatureType.TRAIL_RUINS,
//                BedrockFeatureType.TRIAL_CHAMBERS,
//                BedrockFeatureType.VILLAGE
//                BedrockFeatureType.BURIED_TREASURE,
                BedrockFeatureType.BASTION_REMNANT,
//                BedrockFeatureType.VILLAGE_STRONGHOLD,
//                BedrockFeatureType.STATIC_STRONGHOLD,
//                BedrockFeatureType.NETHER_FORTRESS,
//                BedrockFeatureType.RUINED_PORTAL_O
//                BedrockFeatureType.RUINED_PORTAL_N,
//                BedrockFeatureType.END_CITY,
//                BedrockFeatureType.AMETHYST_GEODE,
//                BedrockFeatureType.DESERT_WELL,
//                BedrockFeatureType.FOSSIL_O,
                BedrockFeatureType.FOSSIL_N
//                BedrockFeatureType.PUMPKIN,
//                BedrockFeatureType.RAVINE,
//                BedrockFeatureType.SWEET_BERRY,
//                BedrockFeatureType.LAVA_LAKE
//                BedrockFeatureType.END_ISLAND
        );

        try {
            List<FeaturePos> allStructures = getFeaturesWithWorldSeed(
                    featureTypes, version, worldSeed, centerChunkX, centerChunkZ, radiusChunk, skipBiomeCheck
            );

            Map<Integer, List<FeaturePos>> featureByType = allStructures.stream()
                    .filter(pos -> pos.getFeatureType() != null)
                    .collect(Collectors.groupingBy(FeaturePos::getFeatureType));

            for (int featureType : featureTypes) {
                BedrockFeatureConfig config = BedrockFeatureConfig.getForType(featureType, version);
                if (config == null) {
                    System.out.println("*Skipping " + BedrockFeatureType.toString(featureType) + " (not available in version)");
                    continue;
                }

                List<FeaturePos> structures = featureByType.getOrDefault(featureType, List.of());
                print(featureType, structures);
            }
            System.out.println();
        } catch (Exception e) {
            System.err.println("Error occurred during feature generation:");
            e.printStackTrace();
        }
    }

    private static List<FeaturePos> getFeaturesWithWorldSeed(
            List<Integer> structureTypes, int version, long worldSeed,
            int centerChunkX, int centerChunkZ, int radiusChunk, boolean skipBiomeCheck) {

        List<FeaturePos> features = BedrockFeature.getBedrockFeaturesRadius(
                structureTypes, version, worldSeed, centerChunkX, centerChunkZ, radiusChunk, skipBiomeCheck
        );

        for (FeaturePos pos : features) {
            pos.setMeta("worldSeed", worldSeed);
        }

        return features;
    }

    private static void print(int structureType, List<FeaturePos> foundStructures) {
        int count = foundStructures.size();
        String structureName = BedrockFeatureType.toString(structureType);
        System.out.println("\u001b[33m" + structureName + "\u001b[0m");

        if (count == 0) {
            System.out.println("Not found");
            return;
        }

        foundStructures.stream()
                .limit(MAX)
                .forEach(pos -> System.out.println(pos.format()));

        if (count > MAX) {
            System.out.printf("...%d more\n", count - MAX);
        }
    }

}