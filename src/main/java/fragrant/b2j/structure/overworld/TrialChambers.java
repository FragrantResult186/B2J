package fragrant.b2j.structure.overworld;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.BedrockStructureType;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.position.StructurePos;

public class TrialChambers extends StructureGenerator {

    public static StructurePos getTrialChambers(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        BPos pos = generate(config, worldSeed, regX, regZ);
        StructurePos position = new StructurePos(pos.getX(), pos.getY(), pos.getZ());
        position.setStructureType(BedrockStructureType.TRIAL_CHAMBERS);

        return position;
    }

    public static BPos generate(BedrockStructureConfig config ,long worldSeed, int regionX, int regionZ) {
        ChunkRand rand = new ChunkRand();
        CPos chunkPos = getInRegion(worldSeed, regionX, regionZ, config.getSalt(), config.getSpacing(), config.getSeparation(), rand, MCVersion.v1_20);

        rand.setCarverSeed(worldSeed, chunkPos.getX(), chunkPos.getZ(), MCVersion.v1_20);
        int y = rand.nextInt(21) - 40; // -40 ~ -20

        int[][] offsets = {
                {+9, +9},
                {-9, +9},
                {-9, -9},
                {+9, -9}
        };
        int r = rand.nextInt(offsets.length);

        int x = (chunkPos.getX() << 4) + offsets[r][0];
        int z = (chunkPos.getZ() << 4) + offsets[r][1];
        return new BPos(x, y, z);
    }

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Y=%d, Z=%d]",
                pos.getX(),
                pos.getY() + 9, // end_1 end_2 are the same size, just add 9
                pos.getZ()
        );
    }

}