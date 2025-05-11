package fragrant.b2j.generator.loot.desertypramid;

import fragrant.b2j.generator.loot.LootType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DesertPyramidLoot {

    public static Map<Integer, List<LootType.LootItem>> getChestLoot(long worldSeed, int chunkX, int chunkZ) {
        List<Integer> chestSeeds = DesertPyramidLootGenerator.generateChestSeed(worldSeed, chunkX, chunkZ);
        Map<Integer, List<LootType.LootItem>> result = new HashMap<>();

        for (int i = 0; i < chestSeeds.size(); i++) {
            int chestSeed = chestSeeds.get(i);
            List<LootType.LootItem> items = DesertPyramidLootGenerator.generateLootItems(
                    DesertPyramidLootTable.createLootTable(),
                    chestSeed
            );
            result.put(i, items);
        }
        return result;
    }

    public static List<LootType.LootItem> getAllChestLoot(long worldSeed, int chunkX, int chunkZ) {
        Map<Integer, List<LootType.LootItem>> chestMap = getChestLoot(worldSeed, chunkX, chunkZ);
        List<LootType.LootItem> result = new ArrayList<>();

        for (List<LootType.LootItem> items : chestMap.values()) {
            result.addAll(items);
        }
        return result;
    }

}