package fragrant.b2j.loot;

import fragrant.b2j.worldfeature.BedrockFeature;
import fragrant.b2j.worldfeature.BedrockFeatureType;
import fragrant.b2j.worldfeature.structure.overworld.surface.Igloo;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

import java.util.List;
import java.util.Map;

public class IglooLootExample {
    public static void main(String[] args) {
        long worldSeed = 1234567890987654321L;
        int blockX = 1112;
        int blockZ = -376;
        int chunkX = blockX >> 4;
        int chunkZ = blockZ >> 4;
        int version = BedrockVersion.MC_1_21_8;
        int structureType = BedrockFeatureType.IGLOO;

        FeaturePos pos = BedrockFeature.isFeatureChunk(structureType, version, worldSeed, chunkX, chunkZ, false);
        if (pos == null) return;
        Map<Integer, List<LootType.LootItem>> loot = Igloo.getLoot(worldSeed, pos);

        loot.forEach((chestIndex, items) -> items.forEach(System.out::println));
    }

}
