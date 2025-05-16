package fragrant.b2j.loot;

import fragrant.b2j.generator.loot.BedrockLootSeedGenerators;
import fragrant.b2j.generator.loot.BedrockLootTable;
import fragrant.b2j.generator.loot.Loot;
import fragrant.b2j.generator.loot.LootType;

import java.util.List;
import java.util.Map;

public class BuriedTreasureLootExample {

    public static void main(String[] args) {
        long worldSeed = -4996715451538318882L;
        int chunkX = 9;
        int chunkZ = 0;

        Loot buriedTreasure = new Loot(
                new BedrockLootTable.BuriedTreasure(),
                new BedrockLootSeedGenerators.BuriedTreasure()
        );

        Map<Integer, List<LootType.LootItem>> buriedTreasureLoot =
                buriedTreasure.getLoot(worldSeed, chunkX, chunkZ);

        buriedTreasureLoot.forEach((chestIndex, items) -> {
            items.forEach(System.out::println);
        });
    }

}