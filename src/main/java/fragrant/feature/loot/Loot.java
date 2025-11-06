package fragrant.feature.loot;

import fragrant.feature.loot.generator.LootGenerator;
import fragrant.feature.loot.table.Pool;
import fragrant.feature.loot.table.LootRegistry;
import fragrant.core.version.MCVersion;

import java.util.List;

public class Loot {

    public static List<Item> generate(Pool[] pools, int lootSeed, MCVersion version) {
        return LootGenerator.generate(pools, lootSeed, version);
    }

    public static List<Item> generate(ILoot loot, int lootSeed, MCVersion version) {
        return LootGenerator.generate(loot.getPools(), lootSeed, version);
    }

    public static List<Item> generate(String lootTableName, int lootSeed, MCVersion version) {
        Pool[] pools = LootRegistry.getPools(lootTableName);
        if (pools == null) {
            throw new IllegalArgumentException("Unknown loot table: " + lootTableName);
        }
        return LootGenerator.generate(pools, lootSeed, version);
    }

}