package fragrant.b2j.loot;

import fragrant.b2j.loot.generator.LootGenerator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Loot {
    private final LootTable lootTable;
    private final LootSeedGenerator seedGenerator;

    public Loot(LootTable lootTable, LootSeedGenerator seedGenerator) {
        this.lootTable = lootTable;
        this.seedGenerator = seedGenerator;
    }

    public Map<Integer, List<LootType.LootItem>> getLoot(long worldSeed, int chunkX, int chunkZ) {
        List<Integer> chestSeeds = seedGenerator.generateChestSeed(worldSeed, chunkX, chunkZ);
        Map<Integer, List<LootType.LootItem>> result = new HashMap<>();

        for (int i = 0; i < chestSeeds.size(); i++) {
            int chestSeed = chestSeeds.get(i);
            List<LootType.LootItem> items = LootGenerator.generateLoot(
                    lootTable.getLootTable(),
                    chestSeed
            );
            result.put(i, items);
        }
        return result;
    }

    public interface LootSeedGenerator {
        List<Integer> generateChestSeed(long worldSeed, int chunkX, int chunkZ);
    }

    public interface LootTable {
        LootType.LootTable getLootTable();
    }

}