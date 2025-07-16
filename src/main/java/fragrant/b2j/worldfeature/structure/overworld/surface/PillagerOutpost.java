package fragrant.b2j.worldfeature.structure.overworld.surface;

import fragrant.b2j.loot.LootConfig;
import fragrant.b2j.loot.LootFactory;
import fragrant.b2j.loot.LootType;
import fragrant.b2j.worldfeature.BedrockFeatureConfig;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.Direction;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

import java.util.List;
import java.util.Map;

public class PillagerOutpost extends BedrockFeatureGenerator {

    private static final LootConfig LOOT_CONFIG = new LootConfig(
            "data/loot_tables/chests/pillager_outpost.json", 1, 1
    );

    public static FeaturePos getPillagerOutpost(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        int rotation = mt.nextInt(4); // Pure guess
        Direction.Offset offsetPos = Direction.getRotationPos(rotation, 7, 7);

        blockPos.setMeta("offsetX", offsetPos.x());
        blockPos.setMeta("offsetZ", offsetPos.z());

        return blockPos;
    }

    public static Map<Integer, List<LootType.LootItem>> getLoot(long worldSeed, FeaturePos pos) {
        int actualX = pos.getX() + pos.getMeta("offsetX", Integer.class);
        int actualZ = pos.getZ() + pos.getMeta("offsetZ", Integer.class);
        int actualChunkX = actualX >> 4;
        int actualChunkZ = actualZ >> 4;

        return LootFactory.getLoot(LOOT_CONFIG, worldSeed, actualChunkX, actualChunkZ);
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getX() + pos.getMeta("offsetX", Integer.class),
                pos.getZ() + pos.getMeta("offsetZ", Integer.class)
        );
    }

}