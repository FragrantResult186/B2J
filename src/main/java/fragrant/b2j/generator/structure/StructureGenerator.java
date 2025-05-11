package fragrant.b2j.generator.structure;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.LongUtil;
import fragrant.b2j.util.Position;

public abstract class StructureGenerator {

    public record Feature(Position.Pos position, BedrockRandom mt) { }

    public static Feature getFeatureChunkInRegion(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Position.Pos pos = new Position.Pos(0, 0);
        BedrockRandom mt = null;

        int x, z;
        int separation = config.getSeparation();
        int spacing = config.getSpacing();

        if
        (config.getExtraInfo() == BedrockStructureConfig.linear) {
            mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
            x = mt.nextInt(separation);
            z = mt.nextInt(separation);
            pos = new Position.Pos(x, z);
        } else if
        (config.getExtraInfo() == BedrockStructureConfig.triangular) {
            mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
            x = (mt.nextInt(separation) + mt.nextInt(separation)) / 2;
            z = (mt.nextInt(separation) + mt.nextInt(separation)) / 2;
            pos = new Position.Pos(x, z);
        } else if
        (config.getExtraInfo() == BedrockStructureConfig.shiftedLinear) {
            mt = StructureGenerator.setRegionSeed(config, worldSeed,  separation * regX + 100, separation * regZ + 100);
            x = mt.nextInt(separation * regX + separation - spacing, separation * regX + spacing);
            z = mt.nextInt(separation * regZ + separation - spacing, separation * regZ + spacing);
            pos = new Position.Pos(x, z);
        }

        return new Feature(pos, mt);
    }

    public static Position.Pos getFeaturePos(BedrockStructureConfig config, int regX, int regZ, Position.Pos pos) {
        int x = (regX * config.getSpacing() + pos.getX()) << 4;
        int z = (regZ * config.getSpacing() + pos.getZ()) << 4;
        return new Position.Pos(x, z);
    }

    public static void createRngStructureFeatureBedrockSeed(long worldSeed, int chunkX, int chunkZ, BedrockRandom mt) {
        mt.setSeed(worldSeed);
        long xMul = BedrockRandom.toUnsignedLong(chunkX) * mt.nextInt();
        long zMul = BedrockRandom.toUnsignedLong(chunkZ) * mt.nextInt();
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
        long regXLong = BedrockRandom.toUnsignedLong(regX);
        long regZLong = BedrockRandom.toUnsignedLong(regZ);

        /* Change x and z mul for Stronghold */
        if (config.getStructureType() == BedrockStructureType.STRONGHOLD) {
            x = -1683231919L * regXLong;
            z = -1100435783L * regZLong;
        } else { // Other Structures
            x = 341873128712L * regXLong;
            z = 132897987541L * regZLong;
        }
        long seed = worldSeed + x + z + config.getSalt();
        return new BedrockRandom(seed);
    }

    public static int hashFeatureName(String featureName) {
        LongUtil b = LongUtil.fromNumber(1099511628211L);
        LongUtil c = LongUtil.fromNumber(-3750763034362895579L);

        for (int i = 0; i < featureName.length(); i++) {
            int charCode = featureName.charAt(i);
            LongUtil mult = b.multiply(c);
            c = LongUtil.fromNumber(charCode).xor(mult);
        }

        return c.getLowBits();
    }

    public static long mixHash(int hash, int featureHash) {
        long result = (hash >>> 2) + ((long) hash << 6) + featureHash - 1640531527;
        return result ^ hash;
    }

    public static CPos getInRegion(long worldSeed, int regionX, int regionZ, int salt, int spacing, int separation, ChunkRand rand, MCVersion version) {
        rand.setRegionSeed(worldSeed, regionX, regionZ, salt, version);

        return new CPos(
                regionX * spacing + rand.nextInt(spacing - separation),
                regionZ * spacing + rand.nextInt(spacing - separation)
        );
    }

}