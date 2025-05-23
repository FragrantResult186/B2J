package fragrant.b2j.loot;

import fragrant.b2j.structure.overworld.BuriedTreasure;
import java.util.List;
import java.util.Map;

public class BuriedTreasureLootExample {

    public static void main(String[] args) {
        long worldSeed = -4996715451538318882L;
        int chunkX = 9;
        int chunkZ = 0;

        Loot buriedTreasure = new Loot(
                new BuriedTreasure.LootTable(),
                new BuriedTreasure.LootSeedGenerator()
        );

        Map<Integer, List<LootType.LootItem>> buriedTreasureLoot =
                buriedTreasure.getLoot(worldSeed, chunkX, chunkZ);

        buriedTreasureLoot.forEach((chestIndex, items) -> {
            items.forEach(System.out::println);
        });
    }

}