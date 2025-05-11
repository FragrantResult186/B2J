package fragrant.b2j.generator.structure.overworld;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.BedrockStructureType;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.Position;

public class TrialChambers extends StructureGenerator {

    public static Position.Pos getTrialChambers(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        BPos pos = generate(config, worldSeed, regX, regZ);
        Position.Pos position = new Position.Pos(pos.getX(), pos.getY(), pos.getZ());
        position.setStructureType(BedrockStructureType.TRIAL_CHAMBERS);
        return position;
    }

    public static BPos generate(BedrockStructureConfig config ,long worldSeed, int regionX, int regionZ) {
        /* Region Random */
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

    public static String format(Position.Pos pos) {
        return String.format("[X=%d, Y=%d, Z=%d]",
                pos.getX(),
                pos.getY() + 9, // end_1 end_2 are the same size, just add 9
                pos.getZ()
        );
    }

}