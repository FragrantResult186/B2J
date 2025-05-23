package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.StructurePos;

public class Mineshaft extends StructureGenerator {

    public static StructurePos getMineshaft(long worldSeed, int chunkX, int chunkZ) {
        BedrockRandom mt = new BedrockRandom(worldSeed);
        createRngStructureFeatureBedrockSeed(worldSeed, chunkX, chunkZ, mt);
        mt.nextInt(); // Skip
        /* 0.4% chance */
        if (mt.nextFloat() < 0.004f) {
            if (mt.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ))){
                return new StructurePos(chunkX << 4, chunkZ << 4);
            }
        }
        return null;
    }

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}