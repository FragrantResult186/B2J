package fragrant.b2j.generator.structure.nether;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.Position;

public class Fortress extends StructureGenerator {

    public static Position.Pos getFortress(BedrockStructureConfig config, long worldSeed, int regX, int regZ, int version) {

        /* 1.14 - 1.15 */
        if (BedrockVersion.isAtMost(version, BedrockVersion.MC_1_16)) {
            int x = regX >> 4;
            int z = regZ & ~0xF; // regZ >> 4 << 4
            long seed = x ^ z ^ worldSeed;
            BedrockRandom mt = new BedrockRandom(seed);
            mt.nextInt(); // Skip
            if (mt.nextInt(3) == 0) {
                int expectX = 4 + (x << 4) + mt.nextInt(8);
                int expectZ = 4 + (z << 4) + mt.nextInt(8);

                if (regX == expectX && regZ == expectZ) {
                    return new Position.Pos(regX << 4, regZ << 4);
                }
            }
        } else
        { // 1.16 +
            Feature fortress = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
            Position.Pos blockPos = getFeaturePos(config, regX, regZ, fortress.position());

            /* isFortress (33% chance) */
            boolean isFortress = fortress.mt().nextInt(6) < 2;
            return isFortress ? blockPos : null;
        }
        return null;
    }

    public static String format(Position.Pos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 11,
                pos.getZ() + 11
        );
    }

}