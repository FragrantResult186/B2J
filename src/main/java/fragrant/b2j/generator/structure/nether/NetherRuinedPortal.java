package fragrant.b2j.generator.structure.nether;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.Position;

public class NetherRuinedPortal extends StructureGenerator {

    public static Position.Pos getNetherRuinedPortal(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature portal = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        return getFeaturePos(config, regX, regZ, portal.position());
    }

    public static String format(Position.Pos pos) {
        return String.format("[X=%d, Z=%d]",
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}
