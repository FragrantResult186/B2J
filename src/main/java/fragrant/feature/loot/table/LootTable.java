package fragrant.feature.loot.table;

import fragrant.core.version.MCVersion;

public class LootTable {

    private static Entry entry(String name, int weight) {
        return new Entry(name, weight, "item", null);
    }

    private static Entry entry(String name, int weight, Function... functions) {
        return new Entry(name, weight, "item", functions);
    }

    private static Entry empty(int weight) {
        return new Entry(null, weight, "empty", null);
    }


    private static Function set_count(int min, int max) {
        return new Function("set_count", new Range(min, max), null);
    }

    private static Function set_damage() {
        return new Function("set_damage", null, null); // TODO
    }

    private static Function set_potion(String type) {
        return new Function("set_potion", null, type);
    }

    private static Function set_data(int min, int max) {
        return new Function("set_data", new Range(min, max), null);
    }

    private static Function random_aux_value(int min, int max) {
        return new Function("random_aux_value", new Range(min, max), null);
    }

    private static Function enchant_randomly() {
        return new Function("enchant_randomly", null, null); // TODO
    }

    // ===== BURIED TREASURE =====
    public static final Pool[] BURIED_TREASURE = {
            new Pool(1, new Entry[] {
                    entry("minecraft:heart_of_the_sea", 1)
            }),
            new Pool(new Range(5, 12), new Entry[] {
                    entry("minecraft:prismarine_crystals", 5, set_count(1, 5)),
                    entry("minecraft:iron_ingot", 20, set_count(3, 5)),
                    entry("minecraft:gold_ingot", 10, set_count(1, 5)),
                    entry("minecraft:tnt", 10, set_count(1, 2)),
                    entry("minecraft:diamond", 15),
                    entry("minecraft:music_disc_wait", 5),
                    entry("minecraft:music_disc_mellohi", 5),
                    entry("minecraft:name_tag", 10),
                    entry("minecraft:chainmail_chestplate", 20),
                    entry("minecraft:chainmail_helmet", 20),
                    entry("minecraft:chainmail_leggings", 20),
                    entry("minecraft:chainmail_boots", 20),
                    entry("minecraft:writable_book", 5, set_count(1, 2)),
                    entry("minecraft:lead", 10, set_count(1, 3)),
                    entry("minecraft:experience_bottle", 3),
                    entry("minecraft:potion", 15, set_potion("water_breathing")),
                    entry("minecraft:potion", 10, set_potion("regeneration")),
                    entry("minecraft:cake", 1)
            })
    };

    // ===== END CITY =====
    public static final Pool[] END_CITY = {
            new Pool(new Range(2, 6), new Entry[] {
                    entry("minecraft:diamond", 5, set_count(2, 7)),
                    entry("minecraft:iron_ingot", 10, set_count(4, 8)),
                    entry("minecraft:gold_ingot", 15, set_count(2, 7)),
                    entry("minecraft:emerald", 2, set_count(2, 6)),
                    entry("minecraft:beetroot_seeds", 5, set_count(1, 10)),
                    entry("minecraft:saddle", 3),
                    entry("minecraft:copper_horse_armor", 1).since(MCVersion.v1_21_111),
                    entry("minecraft:iron_horse_armor", 1),
                    entry("minecraft:golden_horse_armor", 1),
                    entry("minecraft:diamond_horse_armor", 1),
                    entry("minecraft:diamond_sword", 3, enchant_randomly()),
                    entry("minecraft:diamond_boots", 3, enchant_randomly()),
                    entry("minecraft:diamond_chestplate", 3, enchant_randomly()),
                    entry("minecraft:diamond_leggings", 3, enchant_randomly()),
                    entry("minecraft:diamond_helmet", 3, enchant_randomly()),
                    entry("minecraft:diamond_pickaxe", 3, enchant_randomly()),
                    entry("minecraft:diamond_shovel", 3, enchant_randomly()),
                    entry("minecraft:iron_sword", 3, enchant_randomly()),
                    entry("minecraft:iron_boots", 3, enchant_randomly()),
                    entry("minecraft:iron_chestplate", 3, enchant_randomly()),
                    entry("minecraft:iron_leggings", 3, enchant_randomly()),
                    entry("minecraft:iron_helmet", 3, enchant_randomly()),
                    entry("minecraft:iron_pickaxe", 3, enchant_randomly()),
                    entry("minecraft:iron_shovel", 3, enchant_randomly())
            }),
            new Pool(1, new Entry[] {
                    empty(14),
                    entry("minecraft:spire_armor_trim_smithing_template", 1)
            }).since(MCVersion.v1_20_0)
    };

    // ===== DESERT PYRAMID =====
    public static final Pool[] DESERT_PYRAMID = {
            new Pool(new Range(2, 4), new Entry[] {
                    entry("minecraft:diamond", 5, set_count(1, 3)),
                    entry("minecraft:iron_ingot", 15, set_count(1, 5)),
                    entry("minecraft:gold_ingot", 15, set_count(2, 7)),
                    entry("minecraft:emerald", 15, set_count(1, 3)),
                    entry("minecraft:bone", 25, set_count(4, 6)),
                    entry("minecraft:spider_eye", 25, set_count(1, 3)),
                    entry("minecraft:rotten_flesh", 25, set_count(3, 7)),
                    entry("minecraft:saddle", 20).until(MCVersion.v1_21_111),
                    entry("minecraft:leather", 20, set_count(1, 5)).since(MCVersion.v1_21_111),
                    entry("minecraft:copper_horse_armor", 15).since(MCVersion.v1_21_111),
                    entry("minecraft:iron_horse_armor", 15),
                    entry("minecraft:golden_horse_armor", 10),
                    entry("minecraft:diamond_horse_armor", 5),
                    entry("minecraft:book", 20, enchant_randomly()),
                    entry("minecraft:golden_apple", 20),
                    entry("minecraft:enchanted_golden_apple", 2),
                    empty(15)
            }),
            new Pool(4, new Entry[] {
                    entry("minecraft:bone", 10, set_count(1, 8)),
                    entry("minecraft:gunpowder", 10, set_count(1, 8)),
                    entry("minecraft:rotten_flesh", 10, set_count(1, 8)),
                    entry("minecraft:string", 10, set_count(1, 8)),
                    entry("minecraft:sand", 10, set_count(1, 8))
            }),
            new Pool(1, new Entry[] {
                    empty(6),
                    entry("minecraft:dune_armor_trim_smithing_template", 1, set_count(2, 2))
            }).since(MCVersion.v1_20_0)
    };

    // ===== PILLAGER OUTPOST =====
    public static final Pool[] PILLAGER_OUTPOST = {
            new Pool(new Range(0, 1), new Entry[] {
                    entry("minecraft:crossbow", 1)
            }),
            new Pool(new Range(2, 3), new Entry[] {
                    entry("minecraft:wheat", 7, set_count(3, 5)),
                    entry("minecraft:potato", 5, set_count(2, 5)),
                    entry("minecraft:carrot", 5, set_count(3, 5))
            }),
            new Pool(new Range(1, 3), new Entry[] {
                    entry("minecraft:dark_oak_log", 1, set_count(2, 3))
            }),
            new Pool(new Range(2, 3), new Entry[] {
                    entry("minecraft:experience_bottle", 7),
                    entry("minecraft:string", 4, set_count(1, 6)),
                    entry("minecraft:arrow", 4, set_count(2, 7)),
                    entry("minecraft:tripwire_hook", 3, set_count(1, 3)),
                    entry("minecraft:iron_ingot", 3, set_count(1, 3)),
                    entry("minecraft:book", 1, enchant_randomly())
            }),
            new Pool(new Range(0, 1), new Entry[] {
                    entry("minecraft:goat_horn", 1, set_data(0, 3))
            }).since(MCVersion.v1_19_0),
            new Pool(1, new Entry[] {
                    empty(3),
                    entry("minecraft:sentry_armor_trim_smithing_template", 1, set_count(2, 2))
            }).since(MCVersion.v1_20_0)
    };

    // ===== JUNGLE TEMPLE =====
    public static final Pool[] JUNGLE_TEMPLE = {
            new Pool(new Range(2, 6), new Entry[] {
                    entry("minecraft:diamond", 15, set_count(1, 3)),
                    entry("minecraft:iron_ingot", 50, set_count(1, 5)),
                    entry("minecraft:gold_ingot", 75, set_count(2, 7)),
                    entry("minecraft:emerald", 10, set_count(1, 3)),
                    entry("minecraft:bone", 100, set_count(4, 6)),
                    entry("minecraft:rotten_flesh", 80, set_count(3, 7)),
                    entry("minecraft:bamboo", 75, set_count(1, 3)),
                    entry("minecraft:saddle", 15).until(MCVersion.v1_21_111),
                    entry("minecraft:leather", 15, set_count(1, 5)).since(MCVersion.v1_21_111),
                    entry("minecraft:copper_horse_armor", 5).since(MCVersion.v1_21_111),
                    entry("minecraft:iron_horse_armor", 5),
                    entry("minecraft:golden_horse_armor", 5),
                    entry("minecraft:diamond_horse_armor", 5),
                    entry("minecraft:book", 6, enchant_randomly())
            }),
            new Pool(1, new Entry[] {
                    empty(2),
                    entry("minecraft:wild_armor_trim_smithing_template", 1, set_count(2, 2))
            }).since(MCVersion.v1_20_0)
    };

    // ===== IGLOO =====
    public static final Pool[] IGLOO = {
            new Pool(new Range(2, 8), new Entry[] {
                    entry("minecraft:apple", 15, set_count(1, 3)),
                    entry("minecraft:coal", 15, set_count(1, 4)),
                    entry("minecraft:gold_nugget", 10, set_count(1, 3)),
                    entry("minecraft:stone_axe", 2),
                    entry("minecraft:rotten_flesh", 10),
                    entry("minecraft:emerald", 1),
                    entry("minecraft:wheat", 10, set_count(2, 3)),
            }),
            new Pool(1, new Entry[] {
                    entry("minecraft:golden_apple", 1),
            })
    };

    // ===== SHIPWRECK =====
    public static final Pool[] SHIPWRECK_MAP = {
            new Pool(1, new Entry[]{
                    entry("minecraft:map", 8),
            }),
            new Pool(3, new Entry[]{
                    entry("minecraft:compass", 1),
                    entry("minecraft:map", 1),
                    entry("minecraft:clock", 1),
                    entry("minecraft:paper", 20, set_count(1, 10)),
                    entry("minecraft:feather", 10, set_count(1, 5)),
                    entry("minecraft:book", 5, set_count(1, 5)),
            }),
            new Pool(1, new Entry[] {
                    empty(5),
                    entry("minecraft:coast_armor_trim_smithing_template", 1, set_count(2, 2))
            }).since(MCVersion.v1_20_0)
    };

    public static final Pool[] SHIPWRECK_SUPPLY = {
            new Pool(new Range(3, 10), new Entry[]{
                    entry("minecraft:paper", 8, set_count(1, 12)),
                    entry("minecraft:potato", 7, set_count(2, 6)),
                    entry("minecraft:moss_block", 7, set_count(1, 4)),
                    entry("minecraft:poisonous_potato", 7, set_count(2, 6)),
                    entry("minecraft:carrot", 7, set_count(4, 8)),
                    entry("minecraft:wheat", 7, set_count(8, 21)),
                    entry("minecraft:coal", 6, set_count(2, 8)),
                    entry("minecraft:rotten_flesh", 5, set_count(5, 24)),
                    entry("minecraft:bamboo", 2, set_count(1, 3)),
                    entry("minecraft:pumpkin", 2, set_count(1, 3)),
                    entry("minecraft:gunpowder", 3, set_count(1, 5)),
                    entry("minecraft:tnt", 1, set_count(1, 2)),
                    entry("minecraft:leather_helmet", 3, enchant_randomly()),
                    entry("minecraft:leather_chestplate", 3, enchant_randomly()),
                    entry("minecraft:leather_leggings", 3, enchant_randomly()),
                    entry("minecraft:leather_boots", 3, enchant_randomly()),
                    entry("minecraft:suspicious_stew", 10, random_aux_value(0, 6)),
            }),
            new Pool(1, new Entry[] {
                    empty(5),
                    entry("minecraft:coast_armor_trim_smithing_template", 1, set_count(2, 2))
            }).since(MCVersion.v1_20_0)
    };

    public static final Pool[] SHIPWRECK_TREASURE = {
            new Pool(new Range(3, 6), new Entry[]{
                    entry("minecraft:iron_ingot", 90, set_count(1, 5)),
                    entry("minecraft:gold_ingot", 10, set_count(1, 5)),
                    entry("minecraft:emerald", 40, set_count(1, 5)),
                    entry("minecraft:diamond", 5),
                    entry("minecraft:experience_bottle", 5),
            }),
            new Pool(new Range(2, 5), new Entry[]{
                    entry("minecraft:iron_nugget", 50, set_count(1, 10)),
                    entry("minecraft:gold_nugget", 10, set_count(1, 10)),
                    entry("minecraft:dye", 20, set_count(1, 10)),
            }),
            new Pool(1, new Entry[] {
                    empty(5),
                    entry("minecraft:coast_armor_trim_smithing_template", 1, set_count(2, 2))
            }).since(MCVersion.v1_20_0)
    };

    // ===== SIMPLE DUNGEON =====
    public static final Pool[] DUNGEON = {
            new Pool(new Range(1, 3), new Entry[] {
                    entry("minecraft:leather", 20, set_count(1, 5)),
                    entry("minecraft:golden_apple", 15),
                    entry("minecraft:enchanted_golden_apple", 2),
                    entry("minecraft:music_disc_13", 15),
                    entry("minecraft:music_disc_cat", 15),
                    entry("minecraft:music_disc_otherside", 2),
                    entry("minecraft:name_tag", 20),
                    entry("minecraft:gold_horse_armor", 10),
                    entry("minecraft:iron_horse_armor", 15),
                    entry("minecraft:copper_horse_armor", 15),
                    entry("minecraft:diamond_horse_armor", 5),
                    entry("minecraft:enchanted_book", 10, enchant_randomly()),
            }),
            new Pool(new Range(1, 4), new Entry[] {
                    entry("minecraft:iron_ingot", 10, set_count(1, 4)),
                    entry("minecraft:gold_ingot", 5, set_count(1, 4)),
                    entry("minecraft:bread", 20),
                    entry("minecraft:wheat", 20, set_count(1, 4)),
                    entry("minecraft:bucket", 10),
                    entry("minecraft:redstone", 15, set_count(1, 4)),
                    entry("minecraft:coal", 15, set_count(1, 4)),
                    entry("minecraft:melon_seeds", 10, set_count(2, 4)),
                    entry("minecraft:pumpkin_seeds", 10, set_count(2, 4)),
                    entry("minecraft:beetroot_seeds", 10, set_count(2, 4))
            }),
            new Pool(3, new Entry[] {
                    entry("minecraft:bone", 10, set_count(1, 8)),
                    entry("minecraft:gunpowder", 10, set_count(1, 8)),
                    entry("minecraft:rotten_flesh", 10, set_count(1, 8)),
                    entry("minecraft:string", 10, set_count(1, 8))
            })
    };

    // ===== BASTION =====
    public static final Pool[] BASTION_BRIDGE = {
            new Pool(1, new Entry[] {
                    entry("minecraft:lodestone", 1, set_count(1, 1)),
            }),
            new Pool(new Range(1, 2), new Entry[] {
                    entry("minecraft:crossbow", 1, set_damage(), enchant_randomly()),
                    entry("minecraft:arrow", 1, set_count(10, 28)),
                    entry("minecraft:gilded_blackstone", 1, set_count(8, 12)),
                    entry("minecraft:crying_obsidian", 1, set_count(3, 8)),
                    entry("minecraft:gold_block", 1, set_count(1, 1)),
                    entry("minecraft:gold_ingot", 1, set_count(4, 9)),
                    entry("minecraft:iron_ingot", 1, set_count(4, 9)),
                    entry("minecraft:golden_sword", 1, set_count(1, 1)),
                    entry("minecraft:golden_chestplate", 1, set_count(1, 1), enchant_randomly()),
                    entry("minecraft:golden_helmet", 1, set_count(1, 1), enchant_randomly()),
                    entry("minecraft:golden_leggings", 1, set_count(1, 1), enchant_randomly()),
                    entry("minecraft:golden_boots", 1, set_count(1, 1), enchant_randomly()),
                    entry("minecraft:golden_axe", 1, set_count(1, 1), enchant_randomly()),
            }),
            new Pool(new Range(2, 4), new Entry[] {
                    entry("minecraft:string", 1, set_count(1, 6)),
                    entry("minecraft:leather", 1, set_count(1, 3)),
                    entry("minecraft:arrow", 1, set_count(5, 17)),
                    entry("minecraft:iron_nugget", 1, set_count(2, 6)),
                    entry("minecraft:gold_nugget", 1, set_count(2, 6)),
            }),
            new Pool(1, new Entry[] {
                    empty(11),
                    entry("minecraft:snout_armor_trim_smithing_template", 1)
            }).since(MCVersion.v1_20_0),
            new Pool(1, new Entry[] {
                    empty(9),
                    entry("minecraft:netherite_upgrade_smithing_template", 1)
            }).since(MCVersion.v1_20_0)
    };

    public static final Pool[] BASTION_OTHER = {
            new Pool(1, new Entry[] {
                    entry("minecraft:crossbow", 6, set_damage(), enchant_randomly()),
                    entry("minecraft:diamond_pickaxe", 6, enchant_randomly()),
                    entry("minecraft:diamond_shovel", 6),
                    entry("minecraft:ancient_debris", 12, set_count(1, 1)),
                    entry("minecraft:netherite_scrap", 4, set_count(1, 1)),
                    entry("minecraft:arrow", 10, set_count(10, 22)),
                    entry("minecraft:golden_carrot", 12, set_count(6, 17)),
                    entry("minecraft:banner_pattern", 9, set_count(1, 1)),
                    entry("minecraft:golden_apple", 9, set_count(1, 1)),
                    entry("minecraft:record_pigstep", 5, set_count(1, 1)),
                    entry("minecraft:book", 10, enchant_randomly()),
            }),
            new Pool(2, new Entry[] {
                    entry("minecraft:golden_boots", 1, set_count(1, 1), enchant_randomly()),
                    entry("minecraft:golden_axe", 1, enchant_randomly()),
                    entry("minecraft:gold_block", 1, set_count(1, 1)),
                    entry("minecraft:iron_block", 1, set_count(1, 1)),
                    entry("minecraft:crossbow", 1, set_count(1, 1)),
                    entry("minecraft:iron_sword", 6, set_damage(), enchant_randomly()),
                    entry("minecraft:gold_ingot", 1, set_count(1, 6)),
                    entry("minecraft:iron_ingot", 1, set_count(1, 6)),
                    entry("minecraft:golden_sword", 1, set_count(1, 1)),
                    entry("minecraft:golden_chestplate", 1, set_count(1, 1)),
                    entry("minecraft:golden_helmet", 1, set_count(1, 1)),
                    entry("minecraft:golden_leggings", 1, set_count(1, 1)),
                    entry("minecraft:golden_boots", 1, set_count(1, 1)),
                    entry("minecraft:crying_obsidian", 1, set_count(1, 5)),
            }),
            new Pool(new Range(3, 5), new Entry[] {
                    entry("minecraft:gilded_blackstone", 2, set_count(1, 5)),
                    entry("minecraft:chain", 1, set_count(2, 10)),
                    entry("minecraft:magma_cream", 2, set_count(2, 6)),
                    entry("minecraft:bone_block", 1, set_count(3, 6)),
                    entry("minecraft:iron_nugget", 1, set_count(2, 8)),
                    entry("minecraft:obsidian", 1, set_count(4, 6)),
                    entry("minecraft:gold_nugget", 1, set_count(2, 8)),
                    entry("minecraft:string", 1, set_count(4, 6)),
                    entry("minecraft:arrow", 2, set_count(5, 17)),
                    entry("minecraft:cooked_porkchop", 1, set_count(1, 1)),
            }),
            new Pool(1, new Entry[] {
                    empty(11),
                    entry("minecraft:snout_armor_trim_smithing_template", 1)
            }).since(MCVersion.v1_20_0),
            new Pool(1, new Entry[] {
                    empty(9),
                    entry("minecraft:netherite_upgrade_smithing_template", 1)
            }).since(MCVersion.v1_20_0)
    };

    public static final Pool[] BASTION_HOGLIN_STABLE = {
            new Pool(1, new Entry[] {
                    entry("minecraft:diamond_shovel", 15, set_damage(), enchant_randomly()),
                    entry("minecraft:netherite_scrap", 8, set_count(1, 1)),
                    entry("minecraft:ancient_debris", 5, set_count(1, 1)),
                    entry("minecraft:saddle", 12, set_count(1, 1)),
                    entry("minecraft:gold_block", 16, set_count(2, 4)),
                    entry("minecraft:diamond_pickaxe", 12, set_damage(), enchant_randomly()),
                    entry("minecraft:golden_carrot", 10, set_count(8, 17)),
                    entry("minecraft:golden_apple", 10, set_count(1, 1)),
                    entry("minecraft:aaaaaaa", 0, set_count(1, 1)),
            }),
            new Pool(new Range(3, 4), new Entry[] {
                    entry("minecraft:glowstone", 1, set_count(3, 6)),
                    entry("minecraft:gilded_blackstone", 1, set_count(2, 5)),
                    entry("minecraft:soul_sand", 1, set_count(2, 7)),
                    entry("minecraft:crimson_nylium", 1, set_count(2, 7)),
                    entry("minecraft:gold_nugget", 1, set_count(2, 8)),
                    entry("minecraft:leather", 1, set_count(1, 3)),
                    entry("minecraft:arrow", 1, set_count(5, 17)),
                    entry("minecraft:string", 1, set_count(3, 8)),
                    entry("minecraft:porkchop", 1, set_count(2, 5)),
                    entry("minecraft:cooked_porkchop", 1, set_count(2, 5)),
                    entry("minecraft:crimson_fungus", 1, set_count(2, 7)),
                    entry("minecraft:crimson_roots", 1, set_count(2, 7)),
                    entry("minecraft:crying_obsidian", 1, set_count(1, 5)),
                    entry("minecraft:golden_axe", 1, enchant_randomly()),
            }),
            new Pool(1, new Entry[] {
                    empty(11),
                    entry("minecraft:snout_armor_trim_smithing_template", 1)
            }).since(MCVersion.v1_20_0),
            new Pool(1, new Entry[] {
                    empty(9),
                    entry("minecraft:netherite_upgrade_smithing_template", 1)
            }).since(MCVersion.v1_20_0)
    };

    public static final Pool[] BASTION_TREASURE = {
            new Pool(3, new Entry[] {
                    entry("minecraft:netherite_ingot", 15, set_count(1, 1)),
                    entry("minecraft:enchanted_golden_apple", 2, set_count(1, 1)),
                    entry("minecraft:netherite_scrap", 8, set_count(1, 1)),
                    entry("minecraft:ancient_debris", 4, set_count(2, 2)),
                    entry("minecraft:diamond_sword", 6, set_damage(), enchant_randomly()),
                    entry("minecraft:diamond_chestplate", 6, set_damage(), enchant_randomly()),
                    entry("minecraft:diamond_helmet", 6, set_damage(), enchant_randomly()),
                    entry("minecraft:diamond_leggings", 6, set_damage(), enchant_randomly()),
                    entry("minecraft:diamond_boots", 6, set_damage(), enchant_randomly()),
                    entry("minecraft:diamond_sword", 6),
                    entry("minecraft:diamond_chestplate", 5),
                    entry("minecraft:diamond_helmet", 5),
                    entry("minecraft:diamond_boots", 5),
                    entry("minecraft:diamond_leggings", 5),
                    entry("minecraft:diamond", 5, set_count(2, 6)),
            }),
            new Pool(new Range(3, 4), new Entry[] {
                    entry("minecraft:arrow", 1, set_count(12, 25)),
                    entry("minecraft:gold_block", 1, set_count(2, 5)),
                    entry("minecraft:gold_ingot", 1, set_count(3, 9)),
                    entry("minecraft:iron_ingot", 1, set_count(3, 9)),
                    entry("minecraft:crying_obsidian", 1, set_count(3, 5)),
                    entry("minecraft:quartz", 1, set_count(8, 23)),
                    entry("minecraft:gilded_blackstone", 1, set_count(5, 15)),
                    entry("minecraft:magma_cream", 1, set_count(3, 8)),
                    entry("minecraft:iron_block", 1, set_count(2, 5)),
            }),
            new Pool(1, new Entry[] {
                    empty(11),
                    entry("minecraft:snout_armor_trim_smithing_template", 1)
            }).since(MCVersion.v1_20_0),
            new Pool(1, new Entry[] {
                    entry("minecraft:netherite_upgrade_smithing_template", 1)
            }).since(MCVersion.v1_20_0)
    };

    // ===== NETHER FORTRESS =====
    public static final Pool[] NETHER_BRIDGE = {
            new Pool(new Range(2, 4), new Entry[] {
                    entry("minecraft:diamond", 5, set_count(1, 3)),
                    entry("minecraft:iron_ingot", 5, set_count(1, 5)),
                    entry("minecraft:gold_ingot", 15, set_count(1, 3)),
                    entry("minecraft:golden_sword", 5),
                    entry("minecraft:golden_chestplate", 5),
                    entry("minecraft:flint_and_steel", 5),
                    entry("minecraft:nether_wart", 5, set_count(3, 7)),
                    entry("minecraft:saddle", 10),
                    entry("minecraft:gold_horse_armor", 8),
                    entry("minecraft:iron_horse_armor", 5),
                    entry("minecraft:diamond_horse_armor", 3),
                    entry("minecraft:obsidian", 2, set_count(2, 4)),
            }),
            new Pool(1, new Entry[] {
                    empty(14),
                    entry("minecraft:rib_armor_trim_smithing_template", 1)
            }).since(MCVersion.v1_20_0)
    };

}