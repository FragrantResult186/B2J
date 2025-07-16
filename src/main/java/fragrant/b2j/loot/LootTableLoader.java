package fragrant.b2j.loot;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LootTableLoader {

    public static LootType.LootTable loadFromUrl(URL url) throws IOException {
        try (Reader reader = new InputStreamReader(url.openStream())) {
            JsonObject json = JsonParser.parseReader(reader).getAsJsonObject();
            return parseLootTable(json);
        }
    }

    private static LootType.LootTable parseLootTable(JsonObject json) {
        List<LootType.LootPool> pools = new ArrayList<>();
        JsonArray poolsArray = json.getAsJsonArray("pools");

        for (JsonElement poolElement : poolsArray) {
            pools.add(parseLootPool(poolElement.getAsJsonObject()));
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
            entries.add(parseLootEntry(entryElement.getAsJsonObject()));
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
                name = applyPotionFunction(name, functions);
            }
        }

        return new LootType.LootEntry(name, weight, type, functions);
    }

    private static String applyPotionFunction(String name, List<LootType.LootFunction> functions) {
        for (LootType.LootFunction function : functions) {
            if ("set_potion".equals(function.function()) && function.potionType() != null) {
                return "minecraft:potion_" + function.potionType();
            }
        }
        return name;
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

            String functionType = functionJson.get("function").getAsString();
            LootType.LootFunction function = createLootFunction(functionType, functionJson);
            if (function != null) {
                functions.add(function);
            }
        }

        return functions;
    }


    private static LootType.LootFunction createLootFunction(String functionType, JsonObject functionJson) {
        return switch (functionType) {

            case "set_count" -> set_countFunction(functionJson, "count");
            case "set_data" -> set_countFunction(functionJson, "data");
            case "set_potion" -> set_potionFunction(functionJson);
            case "minecraft:set_damage" -> set_damageFunction(functionJson);
            case "enchant_randomly" -> new LootType.LootFunction("enchant_randomly", null, null);
            case "enchant_with_levels" -> new LootType.LootFunction("enchant_with_levels", null, null);

            default -> {
                System.err.println("unknown function type\n: " + functionType);
                yield null;
            }
        };
    }

    private static LootType.LootFunction set_countFunction(JsonObject functionJson, String key) {
        if (functionJson.has(key)) {
            JsonElement countElement = functionJson.get(key);
            if (countElement.isJsonObject()) {
                JsonObject countJson = countElement.getAsJsonObject();
                int min = countJson.get("min").getAsInt();
                int max = countJson.get("max").getAsInt();
                return new LootType.LootFunction(
                        key.equals("count") ? "set_count" : "set_data",
                        new LootType.CountRange(min, max),
                        null
                );
            } else if (countElement.isJsonPrimitive()) {
                int value = countElement.getAsInt();
                return new LootType.LootFunction(
                        key.equals("count") ? "set_count" : "set_data",
                        new LootType.CountRange(value, value),
                        null
                );
            }
        }
        return null;
    }

    private static LootType.LootFunction set_potionFunction(JsonObject functionJson) {
        if (functionJson.has("id")) {
            String potionId = functionJson.get("id").getAsString();
            return new LootType.LootFunction("set_potion", null, potionId);
        }
        return null;
    }

    private static LootType.LootFunction set_damageFunction(JsonObject functionJson) {
//        if (functionJson.has("damage")) {
//            JsonElement damageElement = functionJson.get("damage");
//            if (damageElement.isJsonObject()) {
//                JsonObject damageJson = damageElement.getAsJsonObject();
//                float min = damageJson.get("min").getAsFloat();
//                float max = damageJson.get("max").getAsFloat();
//
//                return new LootType.LootFunction(
//                        "set_damage",
//                        new LootType.CountRange((int)(min), (int)(max)),
//                        null
//                );
//            } else if (damageElement.isJsonPrimitive()) {
//                float value = damageElement.getAsFloat();
//                int intValue = (int)(value);
//                return new LootType.LootFunction(
//                        "set_damage",
//                        new LootType.CountRange(intValue, intValue),
//                        null
//                );
//            }
//        }
        return null;
    }

    private static String convertItemName(String name) {
        if (name.startsWith("minecraft:record_")) {
            return name.replace("minecraft:record_", "minecraft:music_disc_");
        }

        if (name.startsWith("minecraft:horsearmor")) {
            String armorType = name.substring("minecraft:horsearmor".length());
            return switch (armorType) {
                case "iron" -> "minecraft:iron_horse_armor";
                case "gold" -> "minecraft:golden_horse_armor";
                case "diamond" -> "minecraft:diamond_horse_armor";
                default -> name;
            };
        }

        return switch (name) {
            case "minecraft:potion" -> name + "_placeholder";
            case "minecraft:appleEnchanted" -> "minecraft:enchanted_golden_apple";
            default -> name;
        };
    }

}