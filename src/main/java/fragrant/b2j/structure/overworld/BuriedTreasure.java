package fragrant.b2j.structure.overworld;

import fragrant.b2j.loot.LootType;
import fragrant.b2j.loot.Loot;
import fragrant.b2j.loot.LootGenerator;
import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.position.StructurePos;
import fragrant.b2j.util.random.BedrockRandom;

public class BuriedTreasure extends StructureGenerator {
    private static final int CHEST_COUNT = 1;

    public static StructurePos getBuriedTreasure(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        StructurePos treasure = getFeatureChunkInRegion(config, mt, regX, regZ);
        StructurePos pos = getFeaturePos(config, regX, regZ, treasure);

        return pos;
    }

    public static class LootSeedGenerator extends LootGenerator.LootSeedGenerator {
        public LootSeedGenerator() {
            super(CHEST_COUNT);
        }
    }

    public static class LootTable implements Loot.LootTable {
        private static final String LOOT_TABLE_PATH = "data/loot_tables/chests/buriedtreasure.json";
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

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}