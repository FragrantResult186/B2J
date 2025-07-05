package fragrant.b2j.finder;

import de.rasmusantons.cubiomes.BiomeID;
import de.rasmusantons.cubiomes.Dimension;
import fragrant.b2j.biome.Biome;
import fragrant.b2j.feature.BedrockFeature;
import fragrant.b2j.feature.BedrockFeatureType;
import fragrant.b2j.util.position.BlockPos;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.BedrockVersion;
import kaptainwutax.featureutils.structure.generator.StrongholdGenerator;
import kaptainwutax.featureutils.structure.generator.piece.StructurePiece;
import kaptainwutax.featureutils.structure.generator.piece.stronghold.PortalRoom;
import kaptainwutax.seedutils.mc.MCVersion;
import kaptainwutax.seedutils.util.BlockBox;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MonumentPortalFinder {
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
        List<FeaturePos> monuments = BedrockFeature.getBedrockFeaturesRadius(
                List.of(BedrockFeatureType.OCEAN_MONUMENT),
                version, seed, 0, 0, radius, true
        );
        for (FeaturePos stronghold : strongholds) {
            int chunkX = stronghold.getX() >> 4;
            int chunkZ = stronghold.getZ() >> 4;
            List<BlockPos> portals = getPortalPos(seed, chunkX, chunkZ);
            for (BlockPos portal : portals) {
                for (FeaturePos monument : monuments) {
                    if (isOverlap(portal, monument)) {
                        biomeCheck(seed, monument, portal, version);
                        return;
                    }
                }
            }
        }
    }

    static void biomeCheck(long baseSeed, FeaturePos monument, BlockPos portal, int version) {
        long low32 = baseSeed & 0xFFFFFFFFL;
        long upp32 = (baseSeed >>> 32) & 0xFFFFFFFFL;
        for (int i = 0; i < 10000; i++) {
            long seed = low32 | ((upp32 + i) << 32);
            if (biomeCondition(seed, monument, version)) {
                System.out.printf("%d /tp %d 41 %d%n", seed, portal.getX(), portal.getZ());
                return;
            }
        }
    }

    static boolean biomeCondition(long seed, FeaturePos monument, int version) {
        Biome biome = Biome.getBiomeCache(seed, version);
        int centerX = monument.getX() + 8;
        int centerZ = monument.getZ() + 8;
        Set<BiomeID> deepOceanBiomes = Set.of(
                BiomeID.deep_ocean, BiomeID.deep_cold_ocean,
                BiomeID.deep_frozen_ocean, BiomeID.deep_lukewarm_ocean
        );
        Set<BiomeID> aquaticBiomes = Set.of(
                BiomeID.ocean, BiomeID.deep_ocean, BiomeID.cold_ocean, BiomeID.deep_cold_ocean,
                BiomeID.frozen_ocean, BiomeID.deep_frozen_ocean, BiomeID.lukewarm_ocean,
                BiomeID.deep_lukewarm_ocean, BiomeID.warm_ocean, BiomeID.river, BiomeID.frozen_river
        );
        for (int dx = -8; dx <= 8; dx++) {
            for (int dz = -8; dz <= 8; dz++) {
                BiomeID biomeAt = biome.getBiomeAt(centerX + dx, 60, centerZ + dz, Dimension.DIM_OVERWORLD);
                if (!deepOceanBiomes.contains(biomeAt)) {
                    return false;
                }
            }
        }
        for (int dx = -14; dx <= 14; dx++) {
            for (int dz = -14; dz <= 14; dz++) {
                BiomeID biomeAt = biome.getBiomeAt(centerX + dx, 60, centerZ + dz, Dimension.DIM_OVERWORLD);
                if (!aquaticBiomes.contains(biomeAt)) {
                    return false;
                }
            }
        }
        return true;
    }

    static List<BlockPos> getPortalPos(long seed, int chunkX, int chunkZ) {
        List<BlockPos> portals = new ArrayList<>();
        StrongholdGenerator generator = new StrongholdGenerator(MCVersion.v1_16);
        generator.generate((int) seed, chunkX, chunkZ);
        for (StructurePiece<?> piece : generator.pieceList) {
            if (piece instanceof PortalRoom) {
                BlockBox frameBB = ((PortalRoom) piece).getEndFrameBB();
                int centerX = (frameBB.minX + frameBB.maxX) / 2;
                int centerZ = (frameBB.minZ + frameBB.maxZ) / 2;
                portals.add(new BlockPos(centerX, frameBB.minY, centerZ));
            }
        }
        return portals;
    }

    static boolean isOverlap(BlockPos portal, FeaturePos monument) {
        int monumentCenterX = monument.getX() + 8;
        int monumentCenterZ = monument.getZ() + 8;
        for (int dx = -2; dx <= 2; dx++) {
            for (int dz = -2; dz <= 2; dz++) {
                int frameX = portal.getX() + dx - monumentCenterX;
                int frameZ = portal.getZ() + dz - monumentCenterZ;
                if ((Math.abs(frameX) == 29 && Math.abs(frameZ) <= 29) || (Math.abs(frameZ) == 29 && Math.abs(frameX) <= 29)) {
                    return true;
                }
            }
        }
        return false;
    }
}