package fragrant.b2j.structure.nether;

import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.StructurePos;

public class Fortress extends StructureGenerator {

    public static StructurePos getFortress(BedrockStructureConfig config, long worldSeed, int regX, int regZ, int version) {
        /* 1.14 - 1.15 */
        if (BedrockVersion.isAtMost(version, BedrockVersion.MC_1_16))
        {
            int x = regX >> 4;
            int z = regZ >> 4 << 4;
            long seed = x ^ z ^ worldSeed;
            BedrockRandom mt = new BedrockRandom(seed);

            mt.nextInt(); // Skip

            /* 1/3 chance */
            if (mt.nextInt(3) == 0)
            {
                int expectX = 4 + (x << 4) + mt.nextInt(8);
                int expectZ = 4 + (z << 4) + mt.nextInt(8);

                if (regX == expectX && regZ == expectZ)
                {
                    return new StructurePos(regX << 4, regZ << 4);
                }
            }
        } else
        { // 1.16 +
            BedrockRandom mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
            StructurePos chunkPos = getFeatureChunkInRegion(config, mt, regX, regZ);
            StructurePos structurePos = getFeaturePos(config, regX, regZ, chunkPos);

            /* isFortress (33% chance) */
            boolean isFortress = mt.nextInt(6) < 2;

            return isFortress ? structurePos : null;
        }
        return null;
    }

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 11,
                pos.getZ() + 11
        );
    }

}