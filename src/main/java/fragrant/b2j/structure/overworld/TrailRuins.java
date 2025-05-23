package fragrant.b2j.structure.overworld;

import com.seedfinding.mccore.rand.ChunkRand;
import com.seedfinding.mccore.util.pos.BPos;
import com.seedfinding.mccore.util.pos.CPos;
import com.seedfinding.mccore.version.MCVersion;
import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.BedrockStructureType;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.position.StructurePos;

public class TrailRuins extends StructureGenerator {

    public static StructurePos getTrailRuins(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        BPos pos = generate(config, worldSeed, regX, regZ);
        StructurePos position = new StructurePos(pos.getX(), pos.getY(), pos.getZ());
        position.setStructureType(BedrockStructureType.TRAIL_RUINS);

        return position;
    }

    public static BPos generate(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        ChunkRand rand = new ChunkRand();
        CPos chunkPos = getInRegion(worldSeed, regX, regZ, config.getSalt(), config.getSpacing(), config.getSeparation(), rand, MCVersion.v1_20);

        rand.setCarverSeed(worldSeed, chunkPos.getX(), chunkPos.getZ(), MCVersion.v1_20);

        int x = chunkPos.getX() << 4;
        int z = chunkPos.getZ() << 4;
        return new BPos(x, 64, z);
    }

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX(),
                pos.getZ()
        );
    }

}