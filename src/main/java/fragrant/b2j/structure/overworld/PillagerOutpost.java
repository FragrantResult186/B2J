package fragrant.b2j.structure.overworld;

import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.position.StructurePos;
import fragrant.b2j.util.random.BedrockRandom;

public class PillagerOutpost extends StructureGenerator {

    public static StructurePos getPillagerOutpost(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        StructurePos outpost = getFeatureChunkInRegion(config, mt, regX, regZ);
        StructurePos pos = getFeaturePos(config, regX, regZ, outpost);

        return pos;
    }

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}