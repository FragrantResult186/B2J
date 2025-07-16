package fragrant.b2j.worldfeature.structure.overworld.underground;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import fragrant.b2j.worldfeature.BedrockFeatureConfig;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.FeaturePos;

public class TrialChambers extends BedrockFeatureGenerator {

    public static FeaturePos getTrialChambers(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BPos blockPos = generate(config, worldSeed, regX, regZ);
        FeaturePos pos = new FeaturePos(blockPos.getX(), blockPos.getY(), blockPos.getZ());

        return pos;
    }

    public static BPos generate(BedrockFeatureConfig config, long worldSeed, int regionX, int regionZ) {
        ChunkRand rand = new ChunkRand();
        CPos chunkPos = getInRegion(worldSeed, regionX, regionZ, config.getSalt(), config.getSpacing(), config.getSeparation(), rand, MCVersion.v1_20);

        rand.setCarverSeed(worldSeed, chunkPos.getX(), chunkPos.getZ(), MCVersion.v1_20);
        int x;
        int y = rand.nextInt(21) - 40; // -40 ~ -20
        int z;

        int[][] offsets = {
                { 9, 9, 9},
                {-9, 9, 9},
                {-9, 9,-9},
                { 9, 9,-9}
        };
        int r = rand.nextInt(offsets.length);

        x = (chunkPos.getX() << 4) + offsets[r][0];
        y = y + offsets[r][1];
        z = (chunkPos.getZ() << 4) + offsets[r][2];
        return new BPos(x, y, z);
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} /tp %d %d %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getX(),
                pos.getY(),
                pos.getZ()
        );
    }

}