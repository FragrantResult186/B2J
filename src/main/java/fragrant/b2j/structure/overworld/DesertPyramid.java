package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.position.StructurePos;

public class DesertPyramid extends StructureGenerator {

    public static StructurePos getDesertPyramid(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature pyramid = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        return getFeaturePos(config, regX, regZ, pyramid.position());
    }

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 10,
                pos.getZ() + 10
        );
    }

}