package fragrant.b2j.worldfeature.structure.overworld.surface;

import de.rasmusantons.cubiomes.BiomeID;
import de.rasmusantons.cubiomes.Dimension;
import fragrant.b2j.biome.Biome;
import fragrant.b2j.worldfeature.BedrockFeatureConfig;
import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.position.Direction;
import fragrant.b2j.util.random.BedrockRandom;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

public class Village extends BedrockFeatureGenerator {

    public static FeaturePos getVillage(BedrockFeatureConfig config, long worldSeed, int regX, int regZ, int version, boolean skipBiomeCheck) {
        BedrockRandom mt = BedrockFeatureGenerator.setRegionSeed(config, worldSeed, regX, regZ);
        FeaturePos blockPos = getInRegion(config, mt, regX, regZ);

        int rotation = mt.nextInt(4);
        double chance = BedrockVersion.isAtLeast(version, BedrockVersion.MC_1_18) ? 0.02f : 0.2f;
        String type = "";
        String abandoned = "";

        /* isAbandoned (2% chance in 1.18+, 20% chance in 1.18-) */
        if (mt.nextFloat() < chance) {
            abandoned = "abandoned/";
        }

        if (!skipBiomeCheck) {
            Biome bedrockBiome = Biome.getBiomeCache(worldSeed, version);
            BiomeID biome = bedrockBiome.getBiomeAt(blockPos.getX(), 128, blockPos.getZ(), Dimension.DIM_OVERWORLD);

            if (biome == BiomeID.plains || biome == BiomeID.sunflower_plains || biome == BiomeID.meadow) {
                /* plains/town_centers */
                int meeting_point = mt.nextInt(4);
                switch (meeting_point) {
                    case 0: type = "fountain_01"; break;
                    case 1: type = "plains/meeting_point_1"; break;
                    case 2: type = "plains/meeting_point_2"; break;
                    case 3: type = "plains/meeting_point_3"; break;
                }
            } else if (biome == BiomeID.snowy_tundra) {
                /* snowy/town_centers */
                int meeting_point = mt.nextInt(3);
                switch (meeting_point) {
                    case 0: type = "snowy/meeting_point_1"; break;
                    case 1: type = "snowy/meeting_point_2"; break;
                    case 2: type = "snowy/meeting_point_3"; break;
                }
            } else if (biome == BiomeID.taiga || biome == BiomeID.snowy_taiga) {
                /* taiga/town_centers */
                int meeting_point = mt.nextInt(2);
                switch (meeting_point) {
                    case 0: type = "taiga/meeting_point_1"; break;
                    case 1: type = "taiga/meeting_point_2"; break;
                }
            } else if (biome == BiomeID.desert) {
                /* desert/town_centers */
                int meeting_point = mt.nextInt(3);
                switch (meeting_point) {
                    case 0: type = "desert/meeting_point_1"; break;
                    case 1: type = "desert/meeting_point_2"; break;
                    case 2: type = "desert/meeting_point_3"; break;
                }
            } else if (biome == BiomeID.savanna) {
                /* savanna/town_centers */
                int meeting_point = mt.nextInt(4);
                switch (meeting_point) {
                    case 0: type = "savanna/meeting_point_1"; break;
                    case 1: type = "savanna/meeting_point_2"; break;
                    case 2: type = "savanna/meeting_point_3"; break;
                    case 3: type = "savanna/meeting_point_4"; break;
                }
            } else {
                int meeting_point = mt.nextInt(4);
                switch (meeting_point) {
                    case 0: type = "fountain_01"; break;
                    case 1: type = "meeting_point_1"; break;
                    case 2: type = "meeting_point_2"; break;
                    case 3: type = "meeting_point_3"; break;
                }
            }
        }

        Direction.Offset offsetPos = Direction.getRotationPos(rotation, 4, 4);

        blockPos.setMeta("abandoned", abandoned);
        blockPos.setMeta("meeting_point", type);
        blockPos.setMeta("x", offsetPos.x());
        blockPos.setMeta("z", offsetPos.z());
        return blockPos;
    }

    public static String format(FeaturePos pos) {
        int chunkX = pos.getX() >> 4;
        int chunkZ = pos.getZ() >> 4;
        String abandoned = pos.getMeta("abandoned", String.class);
        String meeting = pos.getMeta("meeting_point", String.class);
        String label = getString(abandoned, meeting);

        return String.format("chunkPos{X=%d, Z=%d}%s /tp %d ~ %d",
                chunkX,
                chunkZ,
                label,
                pos.getX() + pos.getMeta("x", Integer.class),
                pos.getZ() + pos.getMeta("z", Integer.class)
        );
    }

    private static String getString(String abandoned, String meeting) {
        String label = "";

        String biomeType = abandoned.isEmpty() ? "" : abandoned.substring(0, abandoned.length() - 1);
        String fullMeetingPoint;

        if (!abandoned.isEmpty() && !meeting.isEmpty()) {
            fullMeetingPoint = abandoned + meeting;
            label = String.format(" (%s)", fullMeetingPoint);
        } else if (!abandoned.isEmpty()) {
            label = String.format(" (%s)", biomeType);
        } else if (!meeting.isEmpty()) {
            label = String.format(" (%s)", meeting);
        }
        return label;
    }
}