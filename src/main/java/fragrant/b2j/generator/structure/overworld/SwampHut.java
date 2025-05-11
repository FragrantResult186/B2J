package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.Position;

public class SwampHut extends StructureGenerator {

    public static Position.Pos getSwampHut(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature swampHut = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        return getFeaturePos(config, regX, regZ, swampHut.position());
    }

    public static String format(Position.Pos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}