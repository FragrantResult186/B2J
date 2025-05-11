package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.Position;

public class DesertWell extends StructureGenerator {

    public static Position.Pos getDesertWell(long worldSeed, int chunkX, int chunkZ) {
        int featureHash = hashFeatureName("minecraft:desert_after_surface_desert_well_feature");
        long popSeed = getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);
        long featureSeed = mixHash((int) popSeed, featureHash);
        BedrockRandom mt = new BedrockRandom(featureSeed);

        /* 1/500 chance */
        if (mt.nextInt(500) == 0) {
            int blockX = chunkX << 4;
            int blockZ = chunkZ << 4;
            int offsetZ = mt.nextInt(0, 16);
            int offsetX = mt.nextInt(0, 16);
            return new Position.Pos(blockX + offsetX, blockZ + offsetZ);
        }

        return null;
    }

    public static String format(Position.Pos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX(),
                pos.getZ()
        );
    }

}