package fragrant.b2j.structure;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.StructurePos;

public abstract class StructureGenerator {

    public static StructurePos getFeatureChunkInRegion(BedrockStructureConfig config, BedrockRandom mt, int regX, int regZ) {
        int x, z;
        int separation = config.getSeparation();

        if (config.getExtraInfo() == BedrockStructureConfig.linear) {
            x = mt.nextInt(separation);
            z = mt.nextInt(separation);
        } else if (config.getExtraInfo() == BedrockStructureConfig.triangular) {
            x = (mt.nextInt(separation) + mt.nextInt(separation)) / 2;
            z = (mt.nextInt(separation) + mt.nextInt(separation)) / 2;
        } else {
            x = 0;
            z = 0;
        }

        return new StructurePos(x, z);
    }

    public static StructurePos getFeaturePos(BedrockStructureConfig config, int regX, int regZ, StructurePos pos) {
        int x = (regX * config.getSpacing() + pos.getX()) << 4;
        int z = (regZ * config.getSpacing() + pos.getZ()) << 4;
        return new StructurePos(x, z);
    }

    public static void createRngStructureFeatureBedrockSeed(long worldSeed, int chunkX, int chunkZ, BedrockRandom mt) {
        mt.setSeed(worldSeed);
        long xMul = Integer.toUnsignedLong(chunkX) * mt.nextInt();
        long zMul = Integer.toUnsignedLong(chunkZ) * mt.nextInt();
        mt.setSeed(worldSeed ^ xMul ^ zMul);
    }

    public static long getBedrockPopulationSeed(long worldSeed, int chunkX, int chunkZ) {
        BedrockRandom mt = new BedrockRandom(worldSeed);
        long xMul = 1 | mt.nextInt();
        long zMul = 1 | mt.nextInt();
        return worldSeed ^ (xMul * chunkX + zMul * chunkZ);
    }

    public static BedrockRandom setRegionSeed(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        long x, z;
        long regXLong = Integer.toUnsignedLong(regX);
        long regZLong = Integer.toUnsignedLong(regZ);
        x = 341873128712L * regXLong;
        z = 132897987541L * regZLong;
        long seed = worldSeed + x + z + config.getSalt();
        return new BedrockRandom(seed);
    }

    public static int BedrockFeatureSeedGenerator(String featureName) {
        long hash = -3750763034362895579L;
        for (int i = 0; i < featureName.length(); i++)
            hash = (featureName.charAt(i) ^ (hash * 1099511628211L));

        return (int) hash;
    }

    public static long bedrockfeatureseedgenerator_get_seed_for_chunk(int hash, int featureHash) {
        return hash ^ ((long) hash << 6) + (hash >>> 2) + featureHash - 1640531527;
    }

    public static long getFeatureSeed(long worldSeed, int chunkX, int chunkZ, String featureName) {
        int featureHash = BedrockFeatureSeedGenerator(featureName);
        long popSeed = getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);
        return bedrockfeatureseedgenerator_get_seed_for_chunk((int) popSeed, featureHash);
    }

    public static CPos getInRegion(long worldSeed, int regionX, int regionZ, int salt, int spacing, int separation, ChunkRand rand, MCVersion version) {
        rand.setRegionSeed(worldSeed, regionX, regionZ, salt, version);

        return new CPos(
                regionX * spacing + rand.nextInt(spacing - separation),
                regionZ * spacing + rand.nextInt(spacing - separation)
        );
    }

}