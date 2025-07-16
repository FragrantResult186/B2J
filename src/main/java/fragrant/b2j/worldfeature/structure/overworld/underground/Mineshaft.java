package fragrant.b2j.worldfeature.structure.overworld.underground;

import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;

public class Mineshaft extends BedrockFeatureGenerator {

    public static FeaturePos getMineshaft(long worldSeed, int chunkX, int chunkZ, int version) {
        BedrockRandom mt = new BedrockRandom();
        CPos chunkPos = new CPos(chunkX, chunkZ);
        BPos blockPos = chunkPos.toBlockPos();
        FeaturePos pos = new FeaturePos(blockPos.getX(), blockPos.getZ());

        /* 1.11+ */
        if (version >= BedrockVersion.MC_1_11) {
            mt.setSeed(worldSeed);
            long r1 = mt.nextInt();
            long r2 = mt.nextInt();
            mt.setSeed((r2 * chunkZ) ^ (r1 * chunkX) ^ worldSeed);
        }
        /* 1.10- */
        else {
            mt.setSeed(worldSeed ^ chunkZ ^ chunkX);
            pos.setMeta("y", 16);
        }
        mt.nextInt(); // Skip
        /* 0.4% chance */
        if (mt.nextFloat() < 0.004f) {
            int distLimit = mt.nextInt(80);
            int maxAbs = Math.max(Math.abs(chunkX), Math.abs(chunkZ));
            if (distLimit < maxAbs) {
                return pos;
            }
        }
        return null;
    }

    public static String format(FeaturePos pos) {
        BPos blockPos = new BPos(pos.getX(), 0, pos.getZ());
        BPos mineshaftCenter = blockPos.add(8, 0, 8);
        CPos chunkPos = blockPos.toChunkPos();

        if (pos.getMeta("y", Integer.class) != null) {
            return String.format("chunkPos{X=%d, Z=%d} /tp %d %d %d",
                    chunkPos.getX(),
                    chunkPos.getZ(),
                    mineshaftCenter.getX(),
                    pos.getMeta("y", Integer.class),
                    mineshaftCenter.getZ()
            );
        } else {
            return String.format("chunkPos{X=%d, Z=%d} /tp %d ~ %d",
                    chunkPos.getX(),
                    chunkPos.getZ(),
                    mineshaftCenter.getX(),
                    mineshaftCenter.getZ()
            );
        }
    }
}
