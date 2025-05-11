package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.Position;

public class WoodlandMansion extends StructureGenerator {

    public static Position.Pos getWoodlandMansion(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature mansion = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        return getFeaturePos(config, regX, regZ, mansion.position());
    }

    public static String format(Position.Pos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}
