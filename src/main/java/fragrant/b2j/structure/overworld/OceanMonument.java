package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.position.StructurePos;

public class OceanMonument extends StructureGenerator {

    public static StructurePos getOceanMonument(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature monument = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        return getFeaturePos(config, regX, regZ, monument.position());
    }

    public static String format(StructurePos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}