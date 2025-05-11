package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.Position;

public class AmethystGeode extends StructureGenerator {

    public static Position.Pos getGeode(long worldSeed, int chunkX, int chunkZ, int version) {
        int featureHash = hashFeatureName("minecraft:overworld_amethyst_geode_feature");
        long popSeed = getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);
        long featureSeed = mixHash((int) popSeed, featureHash);
        BedrockRandom mt = new BedrockRandom(featureSeed);

        int spawnChance;
        int[] yRange;
        if (version >= BedrockVersion.MC_1_18) {
            spawnChance = 24;
            yRange = new int[]{-58, 30};
        } else {
            spawnChance = 53;
            yRange = new int[]{6, 47};
        }

        /* 1/24 in 1.18+, 1/53 in 1.17 */
        if (mt.nextInt(spawnChance) == 0) {
            int y = mt.nextInt(yRange[0], yRange[1]);
            return new Position.Pos(chunkX << 4, y, chunkZ << 4);
        }

        return null;
    }

    public static String format(Position.Pos pos) {
        return String.format("[X=%d, Y=%d, Z=%d]",
                pos.getX() + 4,
                pos.getY() + 4,
                pos.getZ() + 4
        );
    }

}