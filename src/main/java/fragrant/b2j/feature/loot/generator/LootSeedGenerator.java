package fragrant.b2j.feature.loot.generator;

import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.feature.loot.Loot;
import fragrant.b2j.util.random.BedrockRandom;

import java.util.ArrayList;
import java.util.List;

public class LootSeedGenerator implements Loot.LootSeedGenerator {
    private final int chestCount;
    private final int skipCount;

    public LootSeedGenerator(int chestCount, int skipCount) {
        this.chestCount = chestCount;
        this.skipCount = skipCount;
    }

    @Override
    public List<Integer> generateChestSeed(long worldSeed, int chunkX, int chunkZ) {
        long seed = BedrockFeatureGenerator.chunkGenerateRnd(worldSeed, chunkX, chunkZ);
        BedrockRandom mt = new BedrockRandom(seed);

        mt.skipNextInt(skipCount);

        List<Integer> chestSeeds = new ArrayList<>();
        for (int i = 0; i < chestCount; i++) {
            chestSeeds.add(mt.nextInt());
        }

        return chestSeeds;
    }

}