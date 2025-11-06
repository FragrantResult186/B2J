package fragrant.feature.loot.table.impl;

import fragrant.feature.loot.ILoot;
import fragrant.feature.loot.table.Pool;

import static fragrant.feature.loot.table.builder.LootBuilder.*;

public class BuriedTreasureLoot implements ILoot {

    private static final Pool[] POOLS = {
            pool(1).add(
                    item("minecraft:heart_of_the_sea")
            ).build(),
            pool(5, 12).add(
                    item("minecraft:prismarine_crystals").weight(5).count(1, 5),
                    item("minecraft:iron_ingot").weight(20).count(3, 5),
                    item("minecraft:gold_ingot").weight(10).count(1, 5),
                    item("minecraft:tnt").weight(10).count(1, 2),
                    item("minecraft:diamond").weight(15),
                    item("minecraft:music_disc_wait").weight(5),
                    item("minecraft:music_disc_mellohi").weight(5),
                    item("minecraft:name_tag").weight(10),
                    item("minecraft:chainmail_chestplate").weight(20),
                    item("minecraft:chainmail_helmet").weight(20),
                    item("minecraft:chainmail_leggings").weight(20),
                    item("minecraft:chainmail_boots").weight(20),
                    item("minecraft:writable_book").weight(5).count(1, 2),
                    item("minecraft:lead").weight(10).count(1, 3),
                    item("minecraft:experience_bottle").weight(3),
                    item("minecraft:potion").weight(15).potion("water_breathing"),
                    item("minecraft:potion").weight(10).potion("regeneration"),
                    item("minecraft:cake").weight(1)
            ).build()
    };

    @Override
    public Pool[] getPools() {
        return POOLS;
    }
}