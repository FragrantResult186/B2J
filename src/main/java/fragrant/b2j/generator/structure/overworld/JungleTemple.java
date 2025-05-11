package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.Position;

public class JungleTemple extends StructureGenerator {

    public static Position.Pos getJungleTemple(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature temple = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        return getFeaturePos(config, regX, regZ, temple.position());
    }

    public static String format(Position.Pos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}
