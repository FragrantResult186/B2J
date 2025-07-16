package fragrant.b2j.worldfeature.structure.overworld.underground;

import fragrant.b2j.loot.LootConfig;
import fragrant.b2j.loot.LootFactory;
import fragrant.b2j.loot.LootType;
import fragrant.b2j.worldfeature.BedrockFeatureConfig;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

import java.util.List;
import java.util.Map;

public class BuriedTreasure extends BedrockFeatureGenerator {

    private static final LootConfig LOOT_CONFIG = new LootConfig(
            "data/loot_tables/chests/buriedtreasure.json", 1, 1
    );

    public static FeaturePos getBuriedTreasure(BedrockFeatureConfig config, long worldSeed, int regX, int regZ) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        return blockPos;
    }

    public static Map<Integer, List<LootType.LootItem>> getLoot(long worldSeed, FeaturePos pos) {
        return LootFactory.getLoot(LOOT_CONFIG, worldSeed, pos);
    }

    public static String format(FeaturePos pos) {
        return String.format("chunkPos{X=%d, Z=%d} /tp %d ~ %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                pos.getX() + 8,
                pos.getZ() + 8
        );
    }

}