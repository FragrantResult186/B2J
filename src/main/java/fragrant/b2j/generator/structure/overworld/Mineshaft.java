package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.Position;

public class Mineshaft extends StructureGenerator {

    public static Position.Pos getMineshaft(long worldSeed, int chunkX, int chunkZ) {
        BedrockRandom mt = new BedrockRandom(worldSeed);
        createRngStructureFeatureBedrockSeed(worldSeed, chunkX, chunkZ, mt);
        mt.nextInt(); // Skip
        /* 0.4% chance */
        if (mt.nextFloat() < 0.004f) {
            if (mt.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ))){
                return new Position.Pos(chunkX << 4, chunkZ << 4);
            }
        }
        return null;
    }

    public static String format(Position.Pos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}