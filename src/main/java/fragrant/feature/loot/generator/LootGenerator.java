package fragrant.feature.loot.generator;

import fragrant.feature.loot.table.Pool;
import fragrant.feature.loot.table.Entry;
import fragrant.feature.loot.Item;
import fragrant.core.rand.BedrockRandom;
import fragrant.core.version.MCVersion;

import java.util.*;

public class LootGenerator {

    public static List<Item> generate(Pool[] pools, int lootSeed, MCVersion version) {
        BedrockRandom random = new BedrockRandom(lootSeed);
        List<Item> items = new ArrayList<>();

        for (Pool pool : pools) {
            if (pool.isActiveFor(version)) {
                getRandomItems(pool, random, items, version);
            }
        }
        return items;
    }

    private static void getRandomItems(Pool pool, BedrockRandom random, List<Item> items, MCVersion version) {
        Entry[] activeEntries = pool.getActiveEntries(version);
        int totalWeight = getTotalWeight(activeEntries);
        if (totalWeight <= 0) return;

        random.nextFloat(); // use bonus

        int rolls = pool.getRolls(random);
        for (int i = 0; i < rolls; i++) {
            getRandomItem(activeEntries, totalWeight, random, items);
        }
    }

    private static void getRandomItem(Entry[] entries, int totalWeight, BedrockRandom random, List<Item> items) {
        int selectedWeight = random.nextInt(totalWeight);
        int currentWeight = 0;

        for (Entry entry : entries) {
            currentWeight += entry.weight();
            if (selectedWeight < currentWeight) {
                if ("item".equals(entry.type())) {
                    items.add(entry.createItem(random));
                }
                break;
            }
        }
    }

    private static int getTotalWeight(Entry[] entries) {
        int total = 0;
        for (Entry entry : entries) {
            total += entry.weight();
        }
        return total;
    }
}