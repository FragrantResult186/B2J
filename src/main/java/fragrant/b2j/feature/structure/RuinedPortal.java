package fragrant.b2j.feature.structure;

import fragrant.b2j.feature.BedrockFeatureConfig;
import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

public class RuinedPortal extends BedrockFeatureGenerator {

    public static FeaturePos getOverworldRuinedPortal(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        Data s = generate(worldSeed, blockPos.getX(), blockPos.getZ());

        blockPos.setMeta("giant", s.giant);
        blockPos.setMeta("underground", s.underground);
        blockPos.setMeta("variant", s.variant);
        blockPos.setMeta("rotation", s.rotation);
        blockPos.setMeta("mirror", s.mirror);
        blockPos.setMeta("x", s.x);
        blockPos.setMeta("z", s.z);

        return blockPos;
    }

    public static FeaturePos getNetherRuinedPortal(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        Data s = generate(worldSeed, blockPos.getX(), blockPos.getZ());

        blockPos.setMeta("giant", s.giant);
        blockPos.setMeta("underground", s.underground);
        blockPos.setMeta("variant", s.variant);
        blockPos.setMeta("rotation", s.rotation);
        blockPos.setMeta("mirror", s.mirror);
        blockPos.setMeta("x", s.x);
        blockPos.setMeta("z", s.z);

        return blockPos;
    }

    public static Data generate(long worldSeed, int blockX, int blockZ) {
        BedrockRandom mt = new BedrockRandom();
        long seed = BedrockFeatureGenerator.getDecorationSeed(worldSeed, blockX >> 4, blockZ >> 4);
        mt.setSeed(seed);

        Data s = new Data();

        s.underground = mt.nextFloat() < 0.5f;
        s.airpocket = s.underground;
        int rotation = mt.nextInt(4);
        boolean mirror = 0.5f <= mt.nextFloat();
        s.giant = mt.nextFloat() < 0.05f;
        if (s.giant) {
            s.variant = mt.nextInt(3) + 1;
        } else {
            s.variant = mt.nextInt(10) + 1;
        }

        FeaturePos transformPos = transform(blockX, blockZ, mirror, rotation);
        s.rotation = rotation;
        s.mirror = mirror;
        s.x = transformPos.getX();
        s.z = transformPos.getZ();
        return s;
    }

    private static FeaturePos transform(int blockX, int blockZ, boolean mirror, int rotation) {
        int x = 3, z = 3;
        if (mirror) z = -z;
        int newX = 0, newZ = 0;
        switch (rotation) {
            case 0: newX =  z; newZ =  x; break;
            case 1: newX = -z; newZ =  x; break;
            case 2: newX = -x; newZ = -z; break;
            case 3: newX =  z; newZ = -x; break;
        }
        if (mirror && rotation == 0) {
            newX = -newX;
            newZ = -newZ;
        }
        return new FeaturePos(blockX + newX, blockZ + newZ);
    }

    public static class Data {
        public int x;
        public int z;
        public boolean underground = false;
        public boolean airpocket = false;
        public boolean giant = false;
        public int variant = 1;
        public int rotation = 0;
        public boolean mirror = false;
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} (giant=%s, underground=%s, variant=%d) /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getMeta("giant", Boolean.class),
                pos.getMeta("underground", Boolean.class),
                pos.getMeta("variant", Integer.class),
                pos.getMeta("x", Integer.class),
                pos.getMeta("z", Integer.class)
        );
    }

}
