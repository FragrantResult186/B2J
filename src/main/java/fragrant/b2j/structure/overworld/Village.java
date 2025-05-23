package fragrant.b2j.structure.overworld;

import fragrant.b2j.structure.BedrockStructureConfig;
import fragrant.b2j.structure.StructureGenerator;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.StructurePos;

public class Village extends StructureGenerator {

    public static StructurePos getVillage(BedrockStructureConfig config, long worldSeed, int regX, int regZ, int version) {
        BedrockRandom mt = StructureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        StructurePos village = getFeatureChunkInRegion(config, mt, regX, regZ);
        StructurePos blockPos = getFeaturePos(config, regX, regZ, village);

        mt.nextInt(4); // Skip

        /* isZombie (2% chance in 1.18+, 20% chance in 1.18-) */
        double chance = BedrockVersion.isAtLeast(version, BedrockVersion.MC_1_18) ? 0.02f : 0.2f;
        if (mt.nextDouble() < chance)
        {
            blockPos.setType("zombie");
        }

        return blockPos;
    }

    public static String format(StructurePos pos) {
        String type = "zombie".equals(pos.getType()) ? "(zombie)" : "";
        return String.format("[X=%d, Z=%d] %s",
                pos.getX() + 8,
                pos.getZ() + 8,
                type
        );
    }

}