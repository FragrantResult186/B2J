package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.position.StructurePos;

public class OverworldRuinedPortal extends StructureGenerator {

    public static StructurePos getOverworldRuinedPortal(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature portal = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        StructurePos pos = getFeaturePos(config, regX, regZ, portal.position());

//        /* generateVariant */
//        portal.mt().nextFloat();
//        portal.mt().nextFloat();
//        boolean giant = portal.mt().nextFloat() < 0.05f;
//        if (giant) {
//            pos.setType("giant");
//        }

        return pos;
    }

    public static String format(StructurePos pos) {
        String giant = "giant".equals(pos.getType()) ? "(giant)" : "";
        return String.format(
                "[X=%d, Z=%d] %s",
                pos.getX() + 8,
                pos.getZ() + 8,
                giant
        );
    }

}