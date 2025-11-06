package fragrant.feature.loot.table;

import fragrant.feature.loot.ILoot;
import fragrant.feature.loot.table.impl.BuriedTreasureLoot;
import fragrant.feature.loot.table.impl.DesertPyramidLoot;

import java.util.HashMap;
import java.util.Map;

public class LootRegistry {

    private static final Map<String, ILoot> LOOT_TABLES = new HashMap<>();

    static {
        register("buried_treasure", new BuriedTreasureLoot());
        register("desert_pyramid", new DesertPyramidLoot());
//        register("end_city", new EndCityLoot());
//        register("jungle_temple", new JungleTempleLoot());
//        register("igloo", new IglooLoot());
//        register("pillager_outpost", new PillagerOutpostLoot());
//        register("shipwreck_map", new ShipwreckMapLoot());
//        register("shipwreck_supply", new ShipwreckSupplyLoot());
//        register("shipwreck_treasure", new ShipwreckTreasureLoot());
//        register("dungeon", new DungeonLoot());
//        register("bastion_bridge", new BastionBridgeLoot());
//        register("bastion_other", new BastionOtherLoot());
//        register("bastion_hoglin_stable", new BastionHoglinStableLoot());
//        register("bastion_treasure", new BastionTreasureLoot());
//        register("nether_bridge", new NetherBridgeLoot());
    }

    private static void register(String name, ILoot loot) {
        LOOT_TABLES.put(name, loot);
    }

    public static ILoot get(String name) {
        return LOOT_TABLES.get(name);
    }

    public static Pool[] getPools(String name) {
        ILoot loot = get(name);
        return loot != null ? loot.getPools() : null;
    }

    public static boolean exists(String name) {
        return LOOT_TABLES.containsKey(name);
    }
}