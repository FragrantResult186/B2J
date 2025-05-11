package fragrant.b2j.generator.loot.desertypramid;

import fragrant.b2j.generator.loot.LootType;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;

import java.util.ArrayList;
import java.util.List;

public class DesertPyramidLootGenerator {

    public static List<Integer> generateChestSeed(long worldSeed, int chunkX, int chunkZ) {
        long popSeed = StructureGenerator.getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);
        BedrockRandom mt = new BedrockRandom(popSeed);
        mt.nextInt(); // Skip

        List<Integer> chestSeed = new ArrayList<>();
        chestSeed.add(mt.nextInt()); // Chest1 (North)
        chestSeed.add(mt.nextInt()); // Chest2 (West)
        chestSeed.add(mt.nextInt()); // Chest3 (South)
        chestSeed.add(mt.nextInt()); // Chest4 (East)

        return chestSeed;
    }

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
            int rolls;
            if (pool.getRolls() instanceof Integer) {
                mt.nextInt(); // Skip
                rolls = (Integer) pool.getRolls();
            } else {
                LootType.RollRange rollRange = (LootType.RollRange) pool.getRolls();
                rolls = BedrockRandom.genRandIntRange(rollRange.min(), rollRange.max(), mt);
            }

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

    private static LootType.LootItem generateItemFromEntry(LootType.LootEntry entry, BedrockRandom mt) {
        LootType.LootItem item = new LootType.LootItem(entry.name(), 1);

        if (entry.functions() != null) {
            for (LootType.LootFunction function : entry.functions()) {
                LootFunction(item, function, mt);
            }
        }
        return item;
    }

    private static void LootFunction(LootType.LootItem item, LootType.LootFunction function, BedrockRandom mt) {
        switch (function.function()) {
            case "set_count":
                LootType.CountRange countRange = function.count();
                item.setCount(BedrockRandom.genRandIntRange(countRange.min(), countRange.max(), mt));
                break;
            case "enchant_randomly":
                mt.nextInt(); // Skip enchant_randomly RNG
//                item.setEnchantment(generateRandomEnchantment(mt));
                break;
        }
    }

// TODO: Bedrock用エンチャントを再現する必要があります。（まあ再現しなくても乱数に影響ないです）
//     private static Enchantment generateRandomEnchantment(BedrockRandom mt)
//
//        return new Enchantment(enchantName, level);
//    }

}