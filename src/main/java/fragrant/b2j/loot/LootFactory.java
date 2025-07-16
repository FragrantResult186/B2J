package fragrant.b2j.loot;

import fragrant.b2j.loot.generator.LootSeedGenerator;
import fragrant.b2j.util.position.FeaturePos;

import java.util.List;
import java.util.Map;

public class LootFactory {

    public static Loot createLoot(LootConfig config) {
        Loot.LootTable lootTable = new CachedLootTable(config.lootTablePath());
        LootSeedGenerator seedGenerator = new LootSeedGenerator(config.chestCount(), config.skipCount());

        return new Loot(lootTable, seedGenerator);
    }

    public static Map<Integer, List<LootType.LootItem>> getLoot(LootConfig config, long worldSeed, FeaturePos pos) {
        Loot loot = createLoot(config);
        return loot.getLoot(worldSeed, pos.getX() >> 4, pos.getZ() >> 4);
    }

    public static Map<Integer, List<LootType.LootItem>> getLoot(LootConfig config, long worldSeed, int chunkX, int chunkZ) {
        Loot loot = createLoot(config);
        return loot.getLoot(worldSeed, chunkX, chunkZ);
    }

    private static class CachedLootTable implements Loot.LootTable {
        private final LootType.LootTable lootTable;

        public CachedLootTable(String lootTablePath) {
            try {
                java.net.URL url = getClass().getClassLoader().getResource(lootTablePath);
                if (url == null) {
                    throw new RuntimeException("Loot table resource not found: " + lootTablePath);
                }
                this.lootTable = LootTableLoader.loadFromUrl(url);
            } catch (java.io.IOException e) {
                throw new RuntimeException("Failed to load loot table: " + lootTablePath, e);
            }
        }

        @Override
        public LootType.LootTable getLootTable() {
            return lootTable;
        }
    }

}