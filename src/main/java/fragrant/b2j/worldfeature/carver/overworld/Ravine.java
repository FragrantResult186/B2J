package fragrant.b2j.worldfeature.carver.overworld;

import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

public class Ravine extends BedrockFeatureGenerator {

    public static FeaturePos getRavine(long worldSeed, int chunkX, int chunkZ, int version) {
        long seed = BedrockFeatureGenerator.chunkGenerateRnd(worldSeed, chunkX, chunkZ);
        BedrockRandom mt = new BedrockRandom(seed);

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
            float yaw = mt.nextFloat() * 2 * (float) Math.PI; // 0 ~ 2π
            float pitch = (mt.nextFloat() - 0.5f) * 0.25f; // -0.125 ~ 0.125

            /* Size */
            float length = (mt.nextFloat() + mt.nextFloat()) * 3.0f;
            float width = 4.0f;
            boolean isGiant = false;
            /* 5% chance */
            if (mt.nextFloat() < 0.05f) {
                width = 40.0f; // 10x larger width
                length = length * 2.0f; // Double the length
                isGiant = true;
            }
            return new FeaturePos(x, y, z, width, isGiant);
        }
        return null;
    }

    public static String format(FeaturePos pos) {
        String typeLabel = pos.isGiant() ? "size=%.2f giant" : "size=%.2f";
        return String.format("chunkPos{X=%d, Z=%d} (%s) /tp %d %d %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                String.format(typeLabel, pos.getSize()),
                pos.getX(),
                pos.getY(),
                pos.getZ()
        );
    }

}