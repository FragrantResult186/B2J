package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.position.StructurePos;

public class PillagerOutpost extends StructureGenerator {

    public static StructurePos getPillagerOutpost(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature outpost = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        return getFeaturePos(config, regX, regZ, outpost.position());
    }

    public static String format(StructurePos pos) {
        return String.format(
                "[X=%d, Z=%d]",
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}