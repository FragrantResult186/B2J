package fragrant.b2j.loot;

import fragrant.b2j.generator.loot.BedrockLootSeedGenerators;
import fragrant.b2j.generator.loot.BedrockLootTable;
import fragrant.b2j.generator.loot.Loot;
import fragrant.b2j.generator.loot.LootType;

import java.util.List;
import java.util.Map;

public class DesertPyramidLootExample {

    public static void main(String[] args) {
        long worldSeed = -4996715451538318882L;
        int chunkX = -115;
        int chunkZ = -115;

        Loot desertPyramid = new Loot(
                new BedrockLootTable.DesertPyramid(),
                new BedrockLootSeedGenerators.DesertPyramid()
        );

        Map<Integer, List<LootType.LootItem>> desertPyramidLoot =
                desertPyramid.getLoot(worldSeed, chunkX, chunkZ);

        desertPyramidLoot.forEach((chestIndex, items) -> {
            System.out.println("Chest " + chestIndex + " (Direction: " + getDirectionName(chestIndex) + "):");
            items.forEach(item -> System.out.println("  " + item));
        });
    }

    private static String getDirectionName(int index) {
        return switch (index) {
            case 0 -> "North";
            case 1 -> "West";
            case 2 -> "South";
            case 3 -> "East";
            default -> "Unknown";
        };
    }

}