package fragrant.b2j;

import fragrant.b2j.generator.loot.LootType;
import fragrant.b2j.generator.loot.desertypramid.DesertPyramidLoot;
import fragrant.b2j.generator.structure.BedrockStructure;
import fragrant.b2j.generator.structure.BedrockStructureType;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.Position;

import java.util.List;
import java.util.Map;

public class Example2 {
    public static void main(String[] args) {
        long worldSeed = -987654321L;
        int blockX = -216;
        int blockZ = 1752;

        int chunkX = blockX >> 4;
        int chunkZ = blockZ >> 4;

        Position.Pos pyramidPos = BedrockStructure.isBedrockStructureChunk(BedrockStructureType.DESERT_PYRAMID, BedrockVersion.MC_1_21_6, worldSeed, chunkX, chunkZ);

        if (pyramidPos != null) {
            Map<Integer, List<LootType.LootItem>> chestLoot = DesertPyramidLoot.getChestLoot(worldSeed, chunkX, chunkZ);

            for (int chestIndex : chestLoot.keySet()) {
                System.out.println("\nChest#" + (chestIndex + 1));

                List<LootType.LootItem> items = chestLoot.get(chestIndex);
                for (LootType.LootItem item : items) {
                    System.out.println(item.toString());
                }
            }
        } else {
            System.out.println("Not Found at blockPos(" + blockX + ", " + blockZ + ")");
        }
    }

}