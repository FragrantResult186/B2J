package fragrant.b2j.worldfeature.structure.overworld.underground;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import fragrant.b2j.worldfeature.BedrockFeatureConfig;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.FeaturePos;

public class TrailRuins extends BedrockFeatureGenerator {

    public static FeaturePos getTrailRuins(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BPos blockPos = generate(config, worldSeed, regX, regZ);
        FeaturePos pos = new FeaturePos(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        return pos;
    }

    public static BPos generate(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        ChunkRand rand = new ChunkRand();
        CPos chunkPos = getInRegion(worldSeed, regX, regZ, config.getSalt(), config.getSpacing(), config.getSeparation(), rand, MCVersion.v1_20);

        rand.setCarverSeed(worldSeed, chunkPos.getX(), chunkPos.getZ(), MCVersion.v1_20);

        int x = chunkPos.getX() << 4;
        int z = chunkPos.getZ() << 4;
        return new BPos(x, 64, z);
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getX(),
                pos.getZ()
        );
    }

}