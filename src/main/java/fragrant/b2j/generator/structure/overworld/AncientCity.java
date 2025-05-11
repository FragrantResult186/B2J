package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.util.Position;

public class AncientCity extends StructureGenerator {

    public static Position.Pos getAncientCity(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature city = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        Position.Pos pos = getFeaturePos(config, regX, regZ, city.position());
        /* 0: -x, 1: -z, 2: +x, 3: +z */
        int rotation = city.mt().nextInt(4);
        pos.setMeta("rot", rotation);
        return pos;
    }

    public static String format(Position.Pos pos) {
        Integer rot = pos.getMeta("rot", Integer.class);

        return String.format("[X=%d, Y=-33, Z=%d] rot=%d",
                pos.getX(),
                pos.getZ(),
                rot
        );
    }

}
