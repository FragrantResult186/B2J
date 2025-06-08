package fragrant.b2j.feature.structure;

import fragrant.b2j.feature.loot.LootConfig;
import fragrant.b2j.feature.loot.LootFactory;
import fragrant.b2j.feature.loot.LootType;
import fragrant.b2j.feature.BedrockFeatureConfig;
import fragrant.b2j.feature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.Direction;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.position.FeaturePos;

import java.util.List;
import java.util.Map;

public class Igloo extends BedrockFeatureGenerator {

    private static final LootConfig LOOT_CONFIG = new LootConfig(
            "data/loot_tables/chests/igloo_chest.json", 1, 4
    );

    public static FeaturePos getIgloo(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        int chunkX = blockPos.getX() >> 4;
        int chunkZ = blockPos.getZ() >> 4;

        long seed = BedrockFeatureGenerator.getDecorationSeed(worldSeed, chunkX, chunkZ);
        mt = new BedrockRandom(seed);

        int rotation = mt.nextInt(4);
        /* 50% chance */
        boolean hasBasement = mt.nextDouble() >= 0.5;
        /* Ladder length */
        int size = mt.nextInt(8) + 4;
        Direction.Offset offsetPos = Direction.getRotationPos(rotation, 3, 4);

        blockPos.setMeta("startX", blockPos.getX());
        blockPos.setMeta("startZ", blockPos.getZ());
        blockPos.setMeta("x", blockPos.getX() + offsetPos.x());
        blockPos.setMeta("z", blockPos.getZ() + offsetPos.z());
        blockPos.setMeta("basement", hasBasement);
        blockPos.setMeta("ladder", size);

        return blockPos;
    }

    public static Map<Integer, List<LootType.LootItem>> getLoot(long worldSeed, FeaturePos pos) {
        return LootFactory.getLoot(LOOT_CONFIG, worldSeed, pos);
    }

    public static String format(FeaturePos pos) {
        if (pos.getMeta("basement", Boolean.class)) {
            return String.format("chunkPos{X=%d, Z=%d} (with basement, length=%d) /tp %d ~ %d",
                    pos.getMeta("startX", Integer.class) >> 4,
                    pos.getMeta("startZ", Integer.class) >> 4,
                    pos.getMeta("ladder", Integer.class) * 3,
                    pos.getMeta("x", Integer.class),
                    pos.getMeta("z", Integer.class)
            );
        } else {
            return String.format("chunkPos{X=%d, Z=%d} /tp %d ~ %d",
                    pos.getMeta("startX", Integer.class) >> 4,
                    pos.getMeta("startZ", Integer.class) >> 4,
                    pos.getMeta("x", Integer.class),
                    pos.getMeta("z", Integer.class)
            );
        }
    }

}