package fragrant.b2j.generator.loot.desertypramid;

import fragrant.b2j.generator.loot.LootType;

import java.util.Collections;
import java.util.List;

public class DesertPyramidLootTable {

    public static LootType.LootTable createLootTable() {
        return new LootType.LootTable(List.of(

                /* Pool1 */
                new LootType.LootPool(new LootType.RollRange(2, 4), List.of(
                        entry("minecraft:diamond", 5, 1, 3),
                        entry("minecraft:iron_ingot", 15, 1, 5),
                        entry("minecraft:gold_ingot", 15, 2, 7),
                        entry("minecraft:emerald", 15, 1, 3),
                        entry("minecraft:bone", 25, 4, 6),
                        entry("minecraft:spider_eye", 25, 1, 3),
                        entry("minecraft:rotten_flesh", 25, 3, 7),
                        entry("minecraft:saddle", 20),
                        entry("minecraft:iron_horse_armor", 15),
                        entry("minecraft:golden_horse_armor", 10),
                        entry("minecraft:diamond_horse_armor", 5),
                        entry("minecraft:enchanted_book", 20, new LootType.LootFunction("enchant_randomly", null)),
                        entry("minecraft:golden_apple", 20),
                        entry("minecraft:enchanted_golden_apple", 2),
                        entry(15)
                )),

                /* Pool2 */
                new LootType.LootPool(4, List.of(
                        entry("minecraft:bone", 10, 1, 8),
                        entry("minecraft:gunpowder", 10, 1, 8),
                        entry("minecraft:rotten_flesh", 10, 1, 8),
                        entry("minecraft:string", 10, 1, 8),
                        entry("minecraft:sand", 10, 1, 8)
                ))

//                /* Pool3 */
//                new LootType.LootPool(1, List.of(
//                        entry(6)
//                        entry("minecraft:dune_armor_trim_smithing_template", 1),
//                ))

        ));
    }

    private static LootType.LootEntry entry(String name, int weight, int min, int max) {
        return new LootType.LootEntry(name, weight, "item", Collections.singletonList(new LootType.LootFunction("set_count", new LootType.CountRange(min, max))));
    }

    private static LootType.LootEntry entry(String name, int weight) {
        return new LootType.LootEntry(name, weight, "item", null);
    }

    private static LootType.LootEntry entry(String name, int weight, LootType.LootFunction function) {
        return new LootType.LootEntry(name, weight, "item", Collections.singletonList(function));
    }

    private static LootType.LootEntry entry(int weight) {
        return new LootType.LootEntry("", weight, "empty", null);
    }

}