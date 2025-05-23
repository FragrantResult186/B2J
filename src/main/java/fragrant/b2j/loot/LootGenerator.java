package fragrant.b2j.loot;

import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.random.BedrockRandom;

import java.util.ArrayList;
import java.util.List;

public class LootGenerator {

    public static List<LootType.LootItem> generateLoot(LootType.LootTable lootTable, int chestSeed) {
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

            int rolls = getLootRolls(pool, mt);
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

    /**
     * 指定されたワールドシードとチャンク座標から、各チェストのシードを生成します
     */
    public static List<Integer> generateChestSeeds(long worldSeed, int chunkX, int chunkZ, int chestCount) {
        long popSeed = StructureGenerator.getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);
        BedrockRandom mt = new BedrockRandom(popSeed);
        mt.nextInt(); // Skip

        List<Integer> chestSeeds = new ArrayList<>();
        for (int i = 0; i < chestCount; i++) {
            chestSeeds.add(mt.nextInt());
        }

        return chestSeeds;
    }

    private static int getLootRolls(LootType.LootPool pool, BedrockRandom mt) {
        if (pool.getRolls() instanceof Integer) {
            mt.nextInt(); // Skip
            return (Integer) pool.getRolls();
        } else {
            LootType.RollRange rollRange = (LootType.RollRange) pool.getRolls();
            return mt.nextInt(rollRange.min(), rollRange.max() + 1);
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
                item.setCount(mt.nextInt(countRange.min(), countRange.max() + 1));
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

    /**
     * LootSeedGeneratorの実装クラス
     * 特定のチェスト数に対応したシード生成機能を提供します
     */
    public static class LootSeedGenerator implements Loot.LootSeedGenerator {
        private final int chestCount;

        public LootSeedGenerator(int chestCount) {
            this.chestCount = chestCount;
        }

        @Override
        public List<Integer> generateChestSeeds(long worldSeed, int chunkX, int chunkZ) {
            return LootGenerator.generateChestSeeds(worldSeed, chunkX, chunkZ, chestCount);
        }
    }
}