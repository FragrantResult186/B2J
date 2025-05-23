package fragrant.b2j.structure.overworld;

import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.StructurePos;

public class DesertWell extends StructureGenerator {

    public static StructurePos getDesertWell(long worldSeed, int chunkX, int chunkZ) {
        long featureSeed = getFeatureSeed(worldSeed, chunkX, chunkZ, "minecraft:desert_after_surface_desert_well_feature");
        BedrockRandom mt = new BedrockRandom(featureSeed);

        /* 1/500 chance */
        if (mt.nextInt(500) == 0)
        {
            int blockX = chunkX << 4;
            int blockZ = chunkZ << 4;
            int offsetZ = mt.nextInt(0, 16);
            int offsetX = mt.nextInt(0, 16);
            return new StructurePos(blockX + offsetX, blockZ + offsetZ);
        }

        return null;
    }

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX(),
                pos.getZ()
        );
    }

}