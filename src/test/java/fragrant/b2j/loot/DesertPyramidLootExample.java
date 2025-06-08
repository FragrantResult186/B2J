package fragrant.b2j.loot;

import fragrant.b2j.feature.BedrockFeature;
import fragrant.b2j.feature.BedrockFeatureType;
import fragrant.b2j.feature.loot.LootType;
import fragrant.b2j.feature.structure.DesertPyramid;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

import java.util.List;
import java.util.Map;

public class DesertPyramidLootExample {
    public static void main(String[] args) {
        long worldSeed = 1234567890987654321L;
        int blockX = 3400;
        int blockZ = 2056;
        int chunkX = blockX >> 4;
        int chunkZ = blockZ >> 4;
        int version = BedrockVersion.MC_1_21_8;
        int structureType = BedrockFeatureType.DESERT_PYRAMID;

        FeaturePos pos = BedrockFeature.isFeatureChunk(structureType, version, worldSeed, chunkX, chunkZ);
        if (pos == null) return;
        Map<Integer, List<LootType.LootItem>> loot = DesertPyramid.getLoot(worldSeed, pos);

        loot.forEach((chestIndex, items) -> {
            System.out.printf("Chest%d\n", chestIndex + 1);
            items.forEach(System.out::println);
        });
    }

}
