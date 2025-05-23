package fragrant.b2j.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class LootTableLoader {

    public static LootType.LootTable loadFromUrl(java.net.URL url) throws IOException {
        try (Reader reader = new InputStreamReader(url.openStream())) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            return parseLootTable(json);
        }
    }

    private static LootType.LootTable parseLootTable(JsonObject json) {
        List<LootType.LootPool> pools = new ArrayList<>();

        JsonArray poolsArray = json.getAsJsonArray("pools");
        for (JsonElement poolElement : poolsArray) {
            JsonObject poolJson = poolElement.getAsJsonObject();
            pools.add(parseLootPool(poolJson));
        }

        return new LootType.LootTable(pools);
    }

    private static LootType.LootPool parseLootPool(JsonObject poolJson) {
        Object rolls = parseRolls(poolJson.get("rolls"));
        List<LootType.LootEntry> entries = parseEntries(poolJson.getAsJsonArray("entries"));

        return new LootType.LootPool(rolls, entries);
    }

    private static Object parseRolls(JsonElement rollsElement) {
        if (rollsElement.isJsonPrimitive()) {
            return rollsElement.getAsInt();
        } else if (rollsElement.isJsonObject()) {
            JsonObject rollsJson = rollsElement.getAsJsonObject();
            int min = rollsJson.get("min").getAsInt();
            int max = rollsJson.get("max").getAsInt();
            return new LootType.RollRange(min, max);
        }
        throw new IllegalArgumentException("Invalid rolls format");
    }

    private static List<LootType.LootEntry> parseEntries(JsonArray entriesArray) {
        List<LootType.LootEntry> entries = new ArrayList<>();

        for (JsonElement entryElement : entriesArray) {
            JsonObject entryJson = entryElement.getAsJsonObject();
            entries.add(parseLootEntry(entryJson));
        }

        return entries;
    }

    private static LootType.LootEntry parseLootEntry(JsonObject entryJson) {
        String type = entryJson.get("type").getAsString();
        int weight = entryJson.has("weight") ? entryJson.get("weight").getAsInt() : 1;
        String name = "";
        List<LootType.LootFunction> functions = null;

        if ("item".equals(type)) {
            name = convertItemName(entryJson.get("name").getAsString());

            if (entryJson.has("functions")) {
                functions = parseFunctions(entryJson.getAsJsonArray("functions"));
                for (LootType.LootFunction function : functions) {
                    if ("set_potion".equals(function.function())) {
                        String potionType = function.potionType();
                        name = "minecraft:potion_" + potionType;
                    }
                }
            }
        }

        return new LootType.LootEntry(name, weight, type, functions);
    }

    private static List<LootType.LootFunction> parseFunctions(JsonArray functionsArray) {
        List<LootType.LootFunction> functions = new ArrayList<>();

        for (JsonElement functionElement : functionsArray) {
            if (!functionElement.isJsonObject()) {
                System.err.println("A valid function entry (not a JsonObject)\n: " + functionElement);
                continue;
            }

            JsonObject functionJson = functionElement.getAsJsonObject();

            if (!functionJson.has("function")) {
                System.err.println("function key does not exist\n: " + functionJson);
                continue;
            }

            String function = functionJson.get("function").getAsString();

            switch (function) {
                case "set_count":
                    if (functionJson.has("count") && functionJson.get("count").isJsonObject()) {
                        JsonObject countJson = functionJson.getAsJsonObject("count");
                        int min = countJson.get("min").getAsInt();
                        int max = countJson.get("max").getAsInt();
                        functions.add(new LootType.LootFunction("set_count", new LootType.CountRange(min, max)));
                    }
                    break;

                case "enchant_randomly":
                    functions.add(new LootType.LootFunction("enchant_randomly", (LootType.CountRange) null));
                    break;

                case "set_potion":
                    if (functionJson.has("id")) {
                        String potionId = functionJson.get("id").getAsString();
                        functions.add(new LootType.LootFunction("set_potion", potionId));
                    }
                    break;

                default:
                    System.err.println("unknown function type\n: " + function);
                    break;
            }
        }

        return functions;
    }

    private static String convertItemName(String name) {
        // record_* -> music_disc_*
        if (name.startsWith("minecraft:record_")) {
            return name.replace("minecraft:record_", "minecraft:music_disc_");
        }

        // horsearmor* -> *_horse_armor
        if (name.startsWith("minecraft:horsearmor")) {
            String armorType = name.substring("minecraft:horsearmor".length());
            switch (armorType) {
                case "iron": return "minecraft:iron_horse_armor";
                case "gold": return "minecraft:golden_horse_armor";
                case "diamond": return "minecraft:diamond_horse_armor";
            }
        }

        // Potion
        if ("minecraft:potion".equals(name)) {
            return name + "_placeholder";
        }

        // appleEnchanted -> enchanted_golden_apple
        if ("minecraft:appleEnchanted".equals(name)) {
            return "minecraft:enchanted_golden_apple";
        }

        return name;
    }
}
