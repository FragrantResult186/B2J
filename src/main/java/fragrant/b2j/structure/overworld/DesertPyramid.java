package fragrant.b2j.structure.overworld;

import fragrant.b2j.loot.LootType;
import fragrant.b2j.loot.Loot;
import fragrant.b2j.loot.LootGenerator;
import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.position.StructurePos;
import fragrant.b2j.util.random.BedrockRandom;

public class DesertPyramid extends StructureGenerator {
    private static final int CHEST_COUNT = 4;

    public static StructurePos getDesertPyramid(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        StructurePos pyramid = getFeatureChunkInRegion(config, mt, regX, regZ);
        StructurePos pos = getFeaturePos(config, regX, regZ, pyramid);

        return pos;
    }

    public static class LootSeedGenerator extends LootGenerator.LootSeedGenerator {
        public LootSeedGenerator() {
            super(CHEST_COUNT);
        }
    }

    public static class LootTable implements Loot.LootTable {
        private static final String LOOT_TABLE_PATH = "data/loot_tables/chests/desert_pyramid.json";
        private final LootType.LootTable lootTable;

        public LootTable() {
            try {
                java.net.URL url = getClass().getClassLoader().getResource(LOOT_TABLE_PATH);
                if (url == null) {
                    throw new RuntimeException("Loot table resource not found: " + LOOT_TABLE_PATH);
                }
                this.lootTable = fragrant.b2j.loot.LootTableLoader.loadFromUrl(url);
            } catch (java.io.IOException e) {
                throw new RuntimeException("Failed to load loot table: " + LOOT_TABLE_PATH, e);
            }
        }

        @Override
        public LootType.LootTable getLootTable() {
            return lootTable;
        }
    }

    public static String getDirectionName(int index) {
        return switch (index) {
            case 0 -> "North";
            case 1 -> "West";
            case 2 -> "South";
            case 3 -> "East";
            default -> "Unknown";
        };
    }

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 10,
                pos.getZ() + 10
        );
    }

}