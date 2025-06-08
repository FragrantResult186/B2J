package fragrant.b2j.feature;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;

public abstract class BedrockFeatureGenerator {

    public static FeaturePos getInRegion(BedrockFeatureConfig config, BedrockRandom mt, int regX, int regZ) {

        int x = 0, z = 0;
        int separation = config.getSeparation();

        if (config.getExtraInfo() == BedrockFeatureConfig.linear) {
            x = mt.nextInt(separation);
            z = mt.nextInt(separation);
        } else if (config.getExtraInfo() == BedrockFeatureConfig.triangular) {
            x = (mt.nextInt(separation) + mt.nextInt(separation)) / 2;
            z = (mt.nextInt(separation) + mt.nextInt(separation)) / 2;
        }

        return new FeaturePos(
                (regX * config.getSpacing() + x) << 4,
                (regZ * config.getSpacing() + z) << 4
        );
    }

    public static int getDecorationSeed(long worldSeed, int chunkX, int chunkZ) {
        BedrockRandom mt = new BedrockRandom(worldSeed);
        int xMul = mt.nextInt() | 1;
        int zMul = mt.nextInt() | 1;
        return (chunkX * xMul + chunkZ * zMul) ^ (int) worldSeed;
    }

    public static BedrockRandom setRegionSeed(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        return new BedrockRandom(regZ * -245998635 + regX * -1724254968 + (int) worldSeed + config.getSalt());
    }

    public static CPos getInRegion(long worldSeed, int regionX, int regionZ, int salt, int spacing, int separation, ChunkRand rand, MCVersion version) {
        rand.setRegionSeed(worldSeed, regionX, regionZ, salt, version);

        return new CPos(
                regionX * spacing + rand.nextInt(spacing - separation),
                regionZ * spacing + rand.nextInt(spacing - separation)
        );
    }

}