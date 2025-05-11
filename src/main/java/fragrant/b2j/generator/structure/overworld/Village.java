package fragrant.b2j.generator.structure.overworld;

import fragrant.b2j.generator.structure.BedrockStructureConfig;
import fragrant.b2j.generator.structure.StructureGenerator;
import fragrant.b2j.random.BedrockRandom;
import fragrant.b2j.util.Position;
import fragrant.b2j.util.BedrockVersion;

public class Village extends StructureGenerator {

    public static Position.Pos getVillage(BedrockStructureConfig config, long worldSeed, int regX, int regZ, int version) {
        Feature village = getFeatureChunkInRegion(config, worldSeed, regX, regZ);
        Position.Pos blockPos = getFeaturePos(config, regX, regZ, village.position());

        BedrockRandom mt = village.mt();
        mt.nextInt(); // Skip
        /* isZombie (2% chance in 1.18+, 20% chance in 1.18-) */
        double chance = BedrockVersion.isAtLeast(version, BedrockVersion.MC_1_18) ? 0.02f : 0.2f;
        if (mt.nextDouble() < chance) {
            blockPos.setType("zombie");
        }

        return blockPos;
    }

    public static String format(Position.Pos pos) {
        String type = "zombie".equals(pos.getType()) ? "(zombie)" : "";
        return String.format("[X=%d, Z=%d] %s",
                pos.getX() + 8,
                pos.getZ() + 8,
                type
        );
    }

}