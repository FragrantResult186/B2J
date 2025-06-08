package fragrant.b2j.feature.loot.generator;

import fragrant.b2j.feature.loot.LootType;
import fragrant.b2j.util.random.BedrockRandom;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LootGenerator {
    private static final Map<String, List<String>> ITEM_DATA_MAPPINGS = new HashMap<>();
    private static final Map<String, String> ITEM_MAPPING_JSON = Map.of(
            "minecraft:goat_horn", "goat_horn.json"

    );

    static {
        loadItemDataMappings();

    }

    private static void loadItemDataMappings() {
        ITEM_MAPPING_JSON.forEach(LootGenerator::loadItemMapping);
    }

    private static void loadItemMapping(String itemName, String fileName) {
        try {
            String filePath = "data/items/" + fileName;
            URL url = LootGenerator.class.getClassLoader().getResource(filePath);
            if (url == null) {
                System.err.println("Item data mapping file not found: " + filePath);
                return;
            }

            try (Reader reader = new InputStreamReader(url.openStream())) {
                JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();
                List<String> variants = new ArrayList<>();

                for (JsonElement element : jsonArray) {
                    variants.add(element.getAsString());
                }
                ITEM_DATA_MAPPINGS.put(itemName, variants);
            }
        } catch (IOException e) {
            System.err.println("Failed to load item data mapping for " + itemName + ": " + e.getMessage());
        }
    }

    public static List<LootType.LootItem> generateLoot(LootType.LootTable lootTable, int chestSeed) {
        BedrockRandom mt = new BedrockRandom(chestSeed);
        List<LootType.LootItem> items = new ArrayList<>();

        for (LootType.LootPool pool : lootTable.pools()) {
            int totalWeight = calculateTotalWeight(pool);
            if (totalWeight <= 0) continue;
            mt.nextFloat(); // Skip
            int rolls = getLootRoll(pool, mt);
            for (int i = 0; i < rolls; i++) {
                selectLootEntry(pool, mt, totalWeight, items);
            }
        }
        return items;
    }

    private static int calculateTotalWeight(LootType.LootPool pool) {
        return pool.entries().stream()
                .mapToInt(LootType.LootEntry::weight)
                .sum();
    }

    private static void selectLootEntry(LootType.LootPool pool, BedrockRandom mt, int totalWeight, List<LootType.LootItem> items) {
        int selectedWeight = mt.nextInt(totalWeight);
        int currentWeight = 0;

        for (LootType.LootEntry entry : pool.entries()) {
            currentWeight += entry.weight();
            if (selectedWeight < currentWeight) {
                if ("item".equals(entry.type())) {
                    items.add(generateItem(entry, mt));
                }
                break;
            }
        }
    }

    private static int getLootRoll(LootType.LootPool pool, BedrockRandom mt) {
        if (pool.rolls() instanceof Integer) {
            mt.nextInt(); // Skip
            return (Integer) pool.rolls();
        } else {
            LootType.RollRange rollRange = (LootType.RollRange) pool.rolls();
            return mt.nextInt(rollRange.min(), rollRange.max() + 1);
        }
    }

    private static LootType.LootItem generateItem(LootType.LootEntry entry, BedrockRandom mt) {
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
            case "set_count" -> {
                LootType.CountRange count = function.count();
                item.setCount(mt.nextInt(count.min(), count.max() + 1));
            }
            case "set_data" -> {
                LootType.CountRange data = function.count();
                int dataValue = mt.nextInt(data.min(), data.max() + 1);
                applyDataValue(item, dataValue);
            }
            case "enchant_with_levels" -> {
                mt.nextInt(); // Skip enchant_randomly RNG
                // TODO: Bedrock用エンチャントレベル関数を再現する必要があります
            }
            case "enchant_randomly" -> {
                mt.nextInt(); // Skip enchant_randomly RNG
                // TODO: Bedrock用エンチャント関数を再現する必要があります
            }
            case "set_damage" -> {
                mt.nextFloat(); // Skip damage RNG
                // TODO: Bedrock用ダメージ関数を再現する必要があります
            }
        }
    }

    private static void applyDataValue(LootType.LootItem item, int dataValue) {
        List<String> variants = ITEM_DATA_MAPPINGS.get(item.getName());
        if (variants != null && dataValue >= 0 && dataValue < variants.size()) {
            item.setVariant(variants.get(dataValue));
        } else {
            item.setDataValue(dataValue);
        }
    }

}