package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.Position;

public class Igloo extends StructureGenerator {

    public static Position.Pos getIgloo(BedrockStructureConfig config, long worldSeed, int regX, int regZ) {
        Feature igloo = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        Position.Pos pos = getFeaturePos(config, regX, regZ, igloo.position());
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;

        long popSeed = StructureGenerator.getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);
        BedrockRandom mt = new BedrockRandom(popSeed);

        int rotation = mt.nextInt(4);
        /* 50% chance */
        boolean hasBasement = mt.nextDouble() >= 0.5;
        /* Ladder length */
        int size = (mt.nextInt(8) + 4) * 3; // each ladder piece is 3 blocks tall

        int offsetX = 0;
        int offsetZ = 0;
        int sx = 3; int sz = 4;
        switch (rotation) {
            case 0: offsetX =  sx; offsetZ =  sz; break;
            case 1: offsetX = -sz; offsetZ =  sx; break;
            case 2: offsetX = -sx; offsetZ = -sz; break;
            case 3: offsetX =  sz; offsetZ = -sx; break;
        }

        pos.setMeta("x", pos.getX() + offsetX);
        pos.setMeta("z", pos.getZ() + offsetZ);
        pos.setMeta("basement", hasBasement);
        pos.setMeta("ladder", size);

        return pos;
    }

    public static String format(Position.Pos pos) {
        Boolean hasBasement = pos.getMeta("basement", Boolean.class);
        Integer length = pos.getMeta("ladder", Integer.class);

        if (hasBasement) {
            return String.format(
                    "[X=%d, Z=%d] (with basement, length=%d)",
                    pos.getMeta("x", Integer.class),
                    pos.getMeta("z", Integer.class),
                    length
            );
        } else {
            return String.format(
                    "[X=%d, Z=%d]",
                    pos.getMeta("x", Integer.class),
                    pos.getMeta("z", Integer.class)
            );
        }
    }

}
