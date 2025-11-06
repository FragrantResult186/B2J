package fragrant.feature.loot.table.impl;

import fragrant.feature.loot.ILoot;
import fragrant.feature.loot.table.Pool;

import static fragrant.feature.loot.table.builder.LootBuilder.*;

public class DesertPyramidLoot implements ILoot {

    private static final Pool[] POOLS = {
            pool(2, 4).add(
                    item("minecraft:diamond").weight(5).count(1, 3),
                    item("minecraft:iron_ingot").weight(15).count(1, 5),
                    item("minecraft:gold_ingot").weight(15).count(2, 7),
                    item("minecraft:emerald").weight(15).count(1, 3),
                    item("minecraft:bone").weight(25).count(4, 6),
                    item("minecraft:spider_eye").weight(25).count(1, 3),
                    item("minecraft:rotten_flesh").weight(25).count(3, 7),
                    item("minecraft:leather").weight(20).count(1, 5),
                    item("minecraft:iron_horse_armor").weight(15),
                    item("minecraft:copper_horse_armor").weight(15),
                    item("minecraft:golden_horse_armor").weight(10),
                    item("minecraft:diamond_horse_armor").weight(5),
                    item("minecraft:book").weight(20).enchant(),
                    item("minecraft:golden_apple").weight(20),
                    item("minecraft:enchanted_golden_apple").weight(2),
                    empty().weight(15)
            ).build(),
            pool(4).add(
                    item("minecraft:bone").weight(10).count(1, 8),
                    item("minecraft:gunpowder").weight(10).count(1, 8),
                    item("minecraft:rotten_flesh").weight(10).count(1, 8),
                    item("minecraft:string").weight(10).count(1, 8),
                    item("minecraft:sand").weight(10).count(1, 8)
            ).build(),
            pool(1).add(
                    empty().weight(6),
                    item("minecraft:dune_armor_trim_smithing_template").count(2)
            ).build()
    };

    @Override
    public Pool[] getPools() {
        return POOLS;
    }
}