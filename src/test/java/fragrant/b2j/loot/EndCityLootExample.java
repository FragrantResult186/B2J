package fragrant.b2j.loot;

import fragrant.b2j.worldfeature.BedrockFeature;
import fragrant.b2j.worldfeature.BedrockFeatureType;
import fragrant.b2j.worldfeature.structure.end.EndCity;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

import java.util.List;
import java.util.Map;

public class EndCityLootExample {
    public static void main(String[] args) {
        long worldSeed = 310857020810272344L;
        int blockX = -8216;
        int blockZ = -1240;
        int chunkX = blockX >> 4;
        int chunkZ = blockZ >> 4;
        int version = BedrockVersion.MC_1_21_8;
        int structureType = BedrockFeatureType.END_CITY;

        FeaturePos pos = BedrockFeature.isFeatureChunk(structureType, version, worldSeed, chunkX, chunkZ, true);
        if (pos == null) return;

        Map<Integer, List<LootType.LootItem>> loot = EndCity.getShipLoot(worldSeed, pos);
        if (loot.isEmpty()) return;

        loot.forEach((chestIndex, items) -> {
            System.out.printf("Chest%d\n", chestIndex + 1);
            items.forEach(System.out::println);
        });
    }
}