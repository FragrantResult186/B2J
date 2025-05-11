package fragrant.b2j;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.BedrockStructure;
import fragrant.b2j.generator.structure.BedrockStructureType;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.Position;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Example1 {
    static int MAX = 100;

    public static void main(String[] args) {
        test(1234567890987654321L, 0, 0, 2000, BedrockVersion.MC_1_21, false);
    }

    private static void test(long worldSeed, int centerChunkX, int centerChunkZ, int radiusChunk, int version, boolean skipBiomeCheck) {
        System.out.println("\u001b[34m" + "MC version: " + version + "\u001b[0m");
        List<Integer> structureTypes = Arrays.asList(
//                BedrockStructureType.ANCIENT_CITY,
//                BedrockStructureType.DESERT_PYRAMID,
//                BedrockStructureType.IGLOO,
//                BedrockStructureType.JUNGLE_TEMPLE,
//                BedrockStructureType.SWAMP_HUT,
//                BedrockStructureType.WOODLAND_MANSION,
//                BedrockStructureType.OCEAN_MONUMENT,
//                BedrockStructureType.OCEAN_RUINS,
//                BedrockStructureType.PILLAGER_OUTPOST,
//                BedrockStructureType.SHIPWRECK,
//                BedrockStructureType.DESERT_WELL,
//                BedrockStructureType.FOSSIL_O,
//                BedrockStructureType.AMETHYST_GEODE,
//                BedrockStructureType.MINESHAFT,
//                BedrockStructureType.TRAIL_RUINS,
//                BedrockStructureType.TRIAL_CHAMBERS,
//                BedrockStructureType.VILLAGE,
//                BedrockStructureType.BURIED_TREASURE,
//                BedrockStructureType.BASTION_REMNANT,
//                BedrockStructureType.NETHER_FORTRESS,
//                BedrockStructureType.RUINED_PORTAL_O,
//                BedrockStructureType.RUINED_PORTAL_N,
//                BedrockStructureType.END_CITY,
//                BedrockStructureType.RAVINE,
                BedrockStructureType.STRONGHOLD
        );

        try {
            List<Position.Pos> allStructures = BedrockStructure.getBedrockStructuresRadius(
                    structureTypes, version, worldSeed, centerChunkX, centerChunkZ, radiusChunk, skipBiomeCheck
            );

            Map<Integer, List<Position.Pos>> structuresByType = allStructures.stream()
                    .collect(Collectors.groupingBy(Position.Pos::getStructureType)
                    );

            for (int structureType : structureTypes) {
                BedrockStructureConfig config = BedrockStructureConfig.getForType(structureType, version);
                if (config == null) {
                    System.out.println("*Skipping " + BedrockStructureType.toString(structureType) + " (not available in version)");
                    continue;
                }

                List<Position.Pos> structures = structuresByType.getOrDefault(structureType, List.of());
                printStructures(structureType, structures);
            }
            System.out.println();
        } catch (Exception e) {
            System.err.println("Error occurred during structure generation:");
            e.printStackTrace();
        }
    }

    private static void printStructures(int structureType, List<Position.Pos> foundStructures) {
        int count = foundStructures.size();
        String structureName = BedrockStructureType.toString(structureType);
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