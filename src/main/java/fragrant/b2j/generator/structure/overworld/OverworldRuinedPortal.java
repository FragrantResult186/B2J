package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.Position;

public class OverworldRuinedPortal extends StructureGenerator {

    public static Position.Pos getOverworldRuinedPortal(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature portal = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        Position.Pos pos = getFeaturePos(config, regX, regZ, portal.position());

        /* generateVariant */
        portal.mt().nextFloat();
        portal.mt().nextFloat();
        boolean giant = portal.mt().nextFloat() < 0.05f;
        if (giant) {
            pos.setType("giant");
        }

        return pos;
    }

    public static String format(Position.Pos pos) {
        String giant = "giant".equals(pos.getType()) ? "(giant)" : "";
        return String.format(
                "[X=%d, Z=%d] %s",
                pos.getX() + 8,
                pos.getZ() + 8,
                giant
        );
    }

}