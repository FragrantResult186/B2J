package fragrant.b2j.generator.loot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BedrockLootTable {

    private static final Map<String, String> LOOT_TABLE_PATHS = new HashMap<>();

    static {
        LOOT_TABLE_PATHS.put("buried_treasure", "data/loot_tables/chests/buriedtreasure.json");
        LOOT_TABLE_PATHS.put("desert_pyramid", "data/loot_tables/chests/desert_pyramid.json");

    }

    private static class JsonLootTable implements Loot.LootTable {
        private final LootType.LootTable lootTable;

        public JsonLootTable(String tableKey) {
            String path = LOOT_TABLE_PATHS.get(tableKey);
            if (path == null) {
                throw new IllegalArgumentException("Unknown loot table: " + tableKey);
            }

            try {
                this.lootTable = LootTableLoader.loadFromJson(path);
            } catch (IOException e) {
                throw new RuntimeException("Failed to load loot table: " + path, e);
            }
        }

        @Override
        public LootType.LootTable getLootTable() {
            return lootTable;
        }
    }

    public static class BuriedTreasure extends JsonLootTable {
        public BuriedTreasure() {
            super("buried_treasure");
        }
    }

    public static class DesertPyramid extends JsonLootTable {
        public DesertPyramid() {
            super("desert_pyramid");
        }
    }

}