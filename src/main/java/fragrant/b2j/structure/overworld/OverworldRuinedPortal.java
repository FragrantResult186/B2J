package fragrant.b2j.structure.overworld;

import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.position.StructurePos;
import fragrant.b2j.util.random.BedrockRandom;

public class OverworldRuinedPortal extends StructureGenerator {

    public static StructurePos getOverworldRuinedPortal(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        StructurePos portal = getFeatureChunkInRegion(config, mt, regX, regZ);
        StructurePos pos = getFeaturePos(config, regX, regZ, portal);

        return pos;
    }

    public static String format(StructurePos pos) {
        String giant = "giant".equals(pos.getType()) ? "(giant)" : "";
        return String.format("[X=%d, Z=%d] %s",
                pos.getX() + 8,
                pos.getZ() + 8,
                giant
        );
    }

}