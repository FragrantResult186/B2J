package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.Position;

public class OverworldFossil extends StructureGenerator {

    public static Position.Pos getOverworldFossil(long worldSeed, int chunkX, int chunkZ) {
        int hash_coal = hashFeatureName("minecraft:desert_or_swamp_after_surface_fossil_feature");
        int hash_diamond = hashFeatureName("minecraft:desert_or_swamp_after_surface_fossil_deepslate_feature");

        long popSeed_coal = getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);
        long popSeed_diamond = getBedrockPopulationSeed(worldSeed, chunkX, chunkZ);

        long seed_coal = mixHash((int) popSeed_coal, hash_coal);
        long seed_diamond = mixHash((int) popSeed_diamond, hash_diamond);

        BedrockRandom mt_coal = new BedrockRandom(seed_coal);
        BedrockRandom mt_diamond = new BedrockRandom(seed_diamond);

        /* Coal 1/64 chance */
        if (mt_coal.nextInt(64) == 0) {
            String type = switch (mt_coal.nextInt(4)) {
                    case 0 -> "(coal, skull_1 or spine_1)";
                    case 1 -> "(coal, skull_2 or spine_2)";
                    case 2 -> "(coal, skull_3 or spine_3)";
                    case 3 -> "(coal, skull_4 or spine_4)";
                    default -> "null";
                };
            return new Position.Pos(chunkX << 4, chunkZ << 4, type);
        }
        /* Diamond 1/64 chance */
        if (mt_diamond.nextInt(64) == 0) {
            String type = switch (mt_diamond.nextInt(4)) {
                    case 0 -> "(diamond, skull_1 or spine_1)";
                    case 1 -> "(diamond, skull_2 or spine_2)";
                    case 2 -> "(diamond, skull_3 or spine_3)";
                    case 3 -> "(diamond, skull_4 or spine_4)";
                    default -> "null";
                };
            return new Position.Pos(chunkX << 4, chunkZ << 4, type);
        }

        return null;
    }

    public static String format(Position.Pos pos) {
        return String.format("[X=%d Z=%d] %s",
                pos.getX() + 8,
                pos.getZ() + 8,
                pos.getType()
        );
    }

}