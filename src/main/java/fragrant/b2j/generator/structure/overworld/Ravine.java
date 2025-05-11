package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.Position;

public class Ravine extends StructureGenerator {

    public static Position.Pos getRavine(long worldSeed, int chunkX, int chunkZ, int version) {
        long popSeed = StructureGenerator.getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);
        BedrockRandom mt = new BedrockRandom(popSeed);

        /* 1.21.60+ 1/100 chance, older versions 1/150 chance */
        if (mt.nextInt(version >= BedrockVersion.MC_1_21_6 ? 100 : 150) == 0) {
            int x = 16 * chunkX + mt.nextInt(16);
            int y;
            if (version >= BedrockVersion.MC_1_21_6) {
                y = mt.nextInt(58) + 10;
                mt.nextInt(); // Skip
            } else {
                int r = mt.nextInt(40);
                y = mt.nextInt(r + 8) + 20;
            }
            mt.nextInt(); // Skip
            int z = 16 * chunkZ + mt.nextInt(16);

            /* Angle */
            float TWO_PI = (float) (Math.PI * 2);
            float yaw = mt.nextFloat() * TWO_PI;
            float pitch = (mt.nextFloat() - 0.5f) * 0.25f;

            /* Size */
            float thick = (mt.nextFloat() + mt.nextFloat()) * 3;
            float width = thick;
            boolean isGiant = false;
            if (mt.nextFloat() < 0.05f) {
                width = thick * 2;
                isGiant = true;
            }
            return new Position.Pos(x, y, z, width, isGiant);
        }
        return null;
    }

    public static String format(Position.Pos pos) {
        String typeLabel = pos.isGiant() ? "size=%.2f giant" : "size=%.2f";
        return String.format("[X=%d, Y=%d, Z=%d] (%s)",
                pos.getX(),
                pos.getY(),
                pos.getZ(),
                String.format(typeLabel, pos.getSize())
        );
    }

}