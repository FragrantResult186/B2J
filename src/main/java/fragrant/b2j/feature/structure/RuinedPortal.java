package fragrant.b2j.feature.structure;

import fragrant.b2j.feature.BedrockFeatureConfig;
import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.feature.loot.LootConfig;
import fragrant.b2j.util.position.Direction;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

public class RuinedPortal extends BedrockFeatureGenerator {

    private static final LootConfig LOOT_CONFIG = new LootConfig(
            "data/loot_tables/chests/ruined_portal.json", 1, 5
    );

    private static final int[][] GIANT_COORDS = {
            {0, 0}, // giant_portal_1
            {4, 3},
            {9, 9},
            {9, 3}  // giant_portal_4
    };
    private static final int[][] GIANT_CHEST_OFFSETS = {
            {0, 0}, // portal_1
            {2, 0},
            {8, 6},
            {3, 6},
            {3, 2},
            {4, 2},
            {1, 4},
            {0, 2},
            {4, 2},
            {4, 0},
            {2, 7}  // portal_10
    };

    public static FeaturePos getOverworldRuinedPortal(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        return getRuinedPortal(config, worldSeed, regX, regZ);
    }

    public static FeaturePos getNetherRuinedPortal(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        return getRuinedPortal(config, worldSeed, regX, regZ);
    }

    private static FeaturePos getRuinedPortal(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        Data data = generate(worldSeed, blockPos.getX() >> 4, blockPos.getZ() >> 4);

        blockPos.setMeta("giant", data.giant);
        blockPos.setMeta("underground", data.underground);
        blockPos.setMeta("variant", data.variant);
        blockPos.setMeta("rotation", data.rotation);
        blockPos.setMeta("mirror", data.mirror);
        blockPos.setMeta("chestX", data.chestX);
        blockPos.setMeta("chestZ", data.chestZ);

        return blockPos;
    }

    public static Data generate(long worldSeed, int chunkX, int chunkZ) {
        long seed = BedrockFeatureGenerator.chunkGenerateRnd(worldSeed, chunkX, chunkZ);
        BedrockRandom mt = new BedrockRandom(seed);

        Data data = new Data();
        /* 50% chance */
        data.underground = mt.nextFloat() < 0.5f;
        data.airpocket = data.underground;
        int rotation = mt.nextInt(4);
        /* 50% chance */
        boolean mirror = 0.5f <= mt.nextFloat();
        /* 5% chance */
        data.giant = mt.nextFloat() < 0.05f;
        if (data.giant) {
            data.variant = mt.nextInt(3) + 1; // giant_portal_1..3
        } else {
            data.variant = mt.nextInt(10) + 1; // portal_1..10
        }

        FeaturePos chestPos = getChestPos(data.giant, data.variant, chunkX << 4, chunkZ<< 4, mirror, rotation);
        data.rotation = rotation;
        data.mirror = mirror;
        data.chestX = chestPos.getX();
        data.chestZ = chestPos.getZ();

        return data;
    }

    private static FeaturePos getChestPos(boolean giant, int variant, int blockX, int blockZ, boolean mirror, int rotation) {
        int[][] coords = giant ? GIANT_COORDS : GIANT_CHEST_OFFSETS;
        if (variant < 1 || variant >= coords.length) return new FeaturePos(blockX, blockZ);
        int x = coords[variant][0];
        int z = coords[variant][1];
        if (mirror) z = -z;
        Direction.Offset rotated = Direction.getRotationPos(rotation, x, z);
        return new FeaturePos(blockX + rotated.x(), blockZ + rotated.z());
    }

//    public static Map<Integer, List<LootType.LootItem>> getLoot(long worldSeed, FeaturePos pos) {
//        int chestX = pos.getMeta("chestX", Integer.class);
//        int chestZ = pos.getMeta("chestZ", Integer.class);
//        int chestChunkX = chestX >> 4;
//        int chestChunkZ = chestZ >> 4;
//
//        return LootFactory.getLoot(LOOT_CONFIG, worldSeed, chestChunkX, chestChunkZ);
//    }

    public static class Data {
        public int chestX;
        public int chestZ;
        public boolean underground = false;
        public boolean airpocket = false;
        public boolean giant = false;
        public int variant = 1;
        public int rotation = 0;
        public boolean mirror = false;
    }

    public static String format(FeaturePos pos) {
        return String.format(
                "chunkPos{X=%d, Z=%d} (giant=%s, underground=%s, variant=%d) /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getMeta("giant", Boolean.class),
                pos.getMeta("underground", Boolean.class),
                pos.getMeta("variant", Integer.class),
                pos.getMeta("chestX", Integer.class),
                pos.getMeta("chestZ", Integer.class)
        );
    }

}