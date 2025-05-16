package fragrant.b2j.generator.loot;

import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;

import java.util.ArrayList;
import java.util.List;

public class BedrockLootSeedGenerators {

    public static class BuriedTreasure implements Loot.LootSeedGenerator {
        @Override
        public List<Integer> generateChestSeeds(long worldSeed, int chunkX, int chunkZ) {
            long popSeed = StructureGenerator.getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);
            BedrockRandom mt = new BedrockRandom(popSeed);
            mt.nextInt(); // Skip

            List<Integer> chestSeeds = new ArrayList<>();
            chestSeeds.add(mt.nextInt());

            return chestSeeds;
        }
    }

    public static class DesertPyramid implements Loot.LootSeedGenerator {
        @Override
        public List<Integer> generateChestSeeds(long worldSeed, int chunkX, int chunkZ) {
            long popSeed = StructureGenerator.getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);
            BedrockRandom mt = new BedrockRandom(popSeed);
            mt.nextInt(); // Skip

            List<Integer> chestSeeds = new ArrayList<>();
            chestSeeds.add(mt.nextInt()); // Chest1 (North)
            chestSeeds.add(mt.nextInt()); // Chest2 (West)
            chestSeeds.add(mt.nextInt()); // Chest3 (South)
            chestSeeds.add(mt.nextInt()); // Chest4 (East)

            return chestSeeds;
        }
    }

}