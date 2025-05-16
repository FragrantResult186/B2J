package fragrant.b2j.generator.loot;

import fragrant.b2j.random.BedrockRandom;

import java.util.ArrayList;
import java.util.List;

public class LootGenerator {

    public static List<LootType.LootItem> generateLootItems(LootType.LootTable lootTable, int chestSeed) {
        BedrockRandom mt = new BedrockRandom(chestSeed);
        List<LootType.LootItem> items = new ArrayList<>();

        for (LootType.LootPool pool : lootTable.pools()) {
            int totalWeight = 0;
            for (LootType.LootEntry entry : pool.getEntries()) {
                totalWeight += entry.weight();
            }
            if (totalWeight <= 0) {
                continue;
            }

            mt.nextFloat(); // Skip

            int rolls = determineLootRolls(pool, mt);
            for (int i = 0; i < rolls; i++) {
                int selectedWeight = mt.nextInt(totalWeight);
                int currentWeight = 0;

                for (LootType.LootEntry entry : pool.getEntries()) {
                    currentWeight += entry.weight();
                    if (selectedWeight < currentWeight) {
                        if ("item".equals(entry.type())) {
                            items.add(generateItemFromEntry(entry, mt));
                        }
                        break;
                    }
                }
            }
        }
        return items;
    }

    private static int determineLootRolls(LootType.LootPool pool, BedrockRandom mt) {
        if (pool.getRolls() instanceof Integer) {
            mt.nextInt(); // Skip
            return (Integer) pool.getRolls();
        } else {
            LootType.RollRange rollRange = (LootType.RollRange) pool.getRolls();
            return BedrockRandom.genRandIntRange(rollRange.min(), rollRange.max(), mt);
        }
    }

    private static LootType.LootItem generateItemFromEntry(LootType.LootEntry entry, BedrockRandom mt) {
        LootType.LootItem item = new LootType.LootItem(entry.name(), 1);

        if (entry.functions() != null) {
            for (LootType.LootFunction function : entry.functions()) {
                applyLootFunction(item, function, mt);
            }
        }
        return item;
    }

    private static void applyLootFunction(LootType.LootItem item, LootType.LootFunction function, BedrockRandom mt) {
        switch (function.function()) {
            case "set_count":
                LootType.CountRange countRange = function.count();
                item.setCount(BedrockRandom.genRandIntRange(countRange.min(), countRange.max(), mt));
                break;
            case "enchant_randomly":
                mt.nextInt(); // Skip enchant_randomly RNG
                // TODO: Bedrock用エンチャントを再現する必要があります
                // item.setEnchantment(generateRandomEnchantment(mt));
                break;
        }
    }

    // TODO: Bedrock用エンチャントを再現する必要があります
    // private static LootType.Enchantment generateRandomEnchantment(BedrockRandom mt) {
    //     // エンチャントの実装
    //     return new LootType.Enchantment(enchantName, level);
    // }

}