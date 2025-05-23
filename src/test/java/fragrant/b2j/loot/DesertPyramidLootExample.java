package fragrant.b2j.loot;

import fragrant.b2j.structure.overworld.DesertPyramid;
import java.util.List;
import java.util.Map;

public class DesertPyramidLootExample {

    public static void main(String[] args) {
        long worldSeed = -4996715451538318882L;
        int chunkX = -115;
        int chunkZ = -115;

        Loot desertPyramid = new Loot(
                new DesertPyramid.LootTable(),
                new DesertPyramid.LootSeedGenerator()
        );

        Map<Integer, List<LootType.LootItem>> desertPyramidLoot =
                desertPyramid.getLoot(worldSeed, chunkX, chunkZ);

        desertPyramidLoot.forEach((chestIndex, items) -> {
            System.out.println("Chest " + chestIndex + " (Direction: " +
                    DesertPyramid.getDirectionName(chestIndex) + "):");
            items.forEach(item -> System.out.println("  " + item));
        });
    }

}