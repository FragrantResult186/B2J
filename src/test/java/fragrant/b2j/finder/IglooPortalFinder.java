package fragrant.b2j.finder;

import de.rasmusantons.cubiomes.BiomeID;
import de.rasmusantons.cubiomes.Dimension;
import fragrant.b2j.biome.Biome;
import fragrant.b2j.feature.BedrockFeature;
import fragrant.b2j.feature.BedrockFeatureType;
import fragrant.b2j.feature.structure.Stronghold;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.BlockPos;
import fragrant.b2j.util.position.FeaturePos;

import java.util.List;
import java.util.Set;

public class IglooPortalFinder {
    public static void main(String[] args) {
        long seed = 0;
        int radius = 80;
        int version = BedrockVersion.MC_1_22;
        while (true) {
            check(seed, radius, version);
            seed++;
        }
    }

    static void check(long seed, int radius, int version) {
        List<FeaturePos> strongholds = BedrockFeature.getBedrockFeaturesRadius(
                List.of(BedrockFeatureType.STATIC_STRONGHOLD),
                version, seed, 0, 0, radius, true
        );

        List<FeaturePos> igloos = BedrockFeature.getBedrockFeaturesRadius(
                List.of(BedrockFeatureType.IGLOO),
                version, seed, 0, 0, radius, true
        );

        for (FeaturePos stronghold : strongholds) {
            BlockPos portal = Stronghold.getEndPortalPos(seed, stronghold.getX() >> 4, stronghold.getZ() >> 4);
            if (portal == null) continue;

            for (FeaturePos igloo : igloos) {
                int startX = igloo.getMeta("startX", Integer.class);
                int startZ = igloo.getMeta("startZ", Integer.class);

                if (isWithinRange(portal.getX(), portal.getZ(), startX, startZ, 8)) {
                    biomeCheck(seed, igloo, portal, version);
                    return;
                }
            }
        }
    }

    static void biomeCheck(long baseSeed, FeaturePos igloo, BlockPos portal, int version) {
        long low32 = baseSeed & 0xFFFFFFFFL;
        long upp32 = (baseSeed >>> 32) & 0xFFFFFFFFL;
        for (int i = 0; i < 10000; i++) {
            long seed = low32 | ((upp32 + i) << 32);
            if (biomeCondition(seed, igloo, version)) {
                System.out.printf("%d /tp %d ~ %d%n", seed, portal.getX(), portal.getZ());
                return;
            }
        }
    }

    static boolean biomeCondition(long seed, FeaturePos igloo, int version) {
        Biome biome = Biome.getBiomeCache(seed, version);
        int centerX = igloo.getX() + 8;
        int centerZ = igloo.getZ() + 8;
        Set<BiomeID> validBiomes = BedrockVersion.isAtLeast(version, BedrockVersion.MC_1_18)
                ? Set.of(BiomeID.snowy_tundra, BiomeID.snowy_taiga, BiomeID.snowy_slopes)
                : Set.of(BiomeID.snowy_tundra, BiomeID.snowy_taiga);

        for (int dx = -8; dx <= 8; dx++) {
            for (int dz = -8; dz <= 8; dz++) {
                BiomeID biomeAt = biome.getBiomeAt(centerX + dx, 256, centerZ + dz, Dimension.DIM_OVERWORLD);
                if (!validBiomes.contains(biomeAt)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isWithinRange(int portalX, int portalZ, int startX, int startZ, int range) {
        return Math.abs(portalX - startX) <= range && Math.abs(portalZ - startZ) <= range;
    }
}