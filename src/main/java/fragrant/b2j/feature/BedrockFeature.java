package fragrant.b2j.feature;

import fragrant.b2j.biome.Biome;
import fragrant.b2j.feature.decorator.*;
import fragrant.b2j.feature.structure.*;
import fragrant.b2j.util.position.*;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class BedrockFeature {

    public static FeaturePos getBedrockFeaturePos(int featureType, int version, long worldSeed, int regX, int regZ, boolean skipBiomeCheck) {
        BedrockFeatureConfig config = BedrockFeatureConfig.getForType(featureType, version);
        if (config != null) {
            return switch (featureType) {
                /* Overworld structures */
                case BedrockFeatureType.ANCIENT_CITY      -> AncientCity.getAncientCity(config, worldSeed, regX, regZ);
                case BedrockFeatureType.BURIED_TREASURE   -> BuriedTreasure.getBuriedTreasure(config, worldSeed, regX, regZ);
                case BedrockFeatureType.DESERT_PYRAMID    -> DesertPyramid.getDesertPyramid(config, worldSeed, regX, regZ);
                case BedrockFeatureType.IGLOO             -> Igloo.getIgloo(config, worldSeed, regX, regZ);
                case BedrockFeatureType.JUNGLE_TEMPLE     -> JungleTemple.getJungleTemple(config, worldSeed, regX, regZ);
                case BedrockFeatureType.MINESHAFT         -> Mineshaft.getMineshaft(worldSeed, regX, regZ, version);
                case BedrockFeatureType.OCEAN_MONUMENT    -> OceanMonument.getOceanMonument(config, worldSeed, regX, regZ);
                case BedrockFeatureType.OCEAN_RUINS       -> OceanRuins.getOceanRuins(config, worldSeed, regX, regZ, version, skipBiomeCheck);
                case BedrockFeatureType.PILLAGER_OUTPOST  -> PillagerOutpost.getPillagerOutpost(config, worldSeed, regX, regZ);
                case BedrockFeatureType.RUINED_PORTAL_O   -> RuinedPortal.getOverworldRuinedPortal(config, worldSeed, regX, regZ);
                case BedrockFeatureType.SHIPWRECK         -> Shipwreck.getShipwreck(config, worldSeed, regX, regZ);
                case BedrockFeatureType.STATIC_STRONGHOLD -> Stronghold.getStaticStronghold(worldSeed, regX, regZ);
                case BedrockFeatureType.SWAMP_HUT         -> SwampHut.getSwampHut(config, worldSeed, regX, regZ);
                case BedrockFeatureType.TRAIL_RUINS       -> TrailRuins.getTrailRuins(config, worldSeed, regX, regZ);
                case BedrockFeatureType.TRIAL_CHAMBERS    -> TrialChambers.getTrialChambers(config, worldSeed, regX, regZ);
                case BedrockFeatureType.VILLAGE           -> Village.getVillage(config, worldSeed, regX, regZ, version, skipBiomeCheck);
                case BedrockFeatureType.WOODLAND_MANSION  -> WoodlandMansion.getWoodlandMansion(config, worldSeed, regX, regZ);

                /* Nether structures */
                case BedrockFeatureType.BASTION_REMNANT -> Bastion.getBastion(config, worldSeed, regX, regZ);
                case BedrockFeatureType.NETHER_FORTRESS -> Fortress.getFortress(config, worldSeed, regX, regZ, version);
                case BedrockFeatureType.RUINED_PORTAL_N -> RuinedPortal.getNetherRuinedPortal(config, worldSeed, regX, regZ);

                /* End structures */
                case BedrockFeatureType.END_CITY -> EndCity.getEndCity(config, worldSeed, regX, regZ);

                /* Decorators */
                case BedrockFeatureType.AMETHYST_GEODE -> AmethystGeode.getGeode(worldSeed, regX, regZ, version);
                case BedrockFeatureType.DESERT_WELL    -> DesertWell.getDesertWell(worldSeed, regX, regZ);
                case BedrockFeatureType.FOSSIL_N       -> NetherFossil.getNetherFossil(worldSeed, regX, regZ);
                case BedrockFeatureType.FOSSIL_O       -> OverworldFossil.getOverworldFossil(worldSeed, regX, regZ);
                case BedrockFeatureType.PUMPKIN        -> Pumpkin.getPumpkin(worldSeed, regX, regZ);
                case BedrockFeatureType.RAVINE         -> Ravine.getRavine(worldSeed, regX, regZ, version);
                case BedrockFeatureType.SWEET_BERRY    -> SweetBerry.getSweetBerry(worldSeed, regX, regZ, skipBiomeCheck);

                default -> {
                    System.err.println("ERROR: getStructurePos: unsupported structure type " + featureType);
                    yield null;
                }
            };
        }
        return null;
    }

    public static FeaturePos isFeatureChunk(int structureType, int version, long worldSeed, int chunkX, int chunkZ, boolean skipBiomeCheck) {
        if (structureType == BedrockFeatureType.VILLAGE_STRONGHOLD) {
            return findVillageStrongholdAt(worldSeed, version, chunkX, chunkZ, skipBiomeCheck);
        }

        BedrockFeatureConfig config = BedrockFeatureConfig.getForType(structureType, version);
        if (config != null) {
            int regionSize = config.getSpacing();
            int regX = Math.floorDiv(chunkX, regionSize);
            int regZ = Math.floorDiv(chunkZ, regionSize);

            FeaturePos featurePos = getBedrockFeaturePos(structureType, version, worldSeed, regX, regZ, true);

            if (featurePos != null && isAtChunk(featurePos, chunkX, chunkZ)) {
                featurePos.setFeatureType(structureType);
                return featurePos;
            }
        }
        return null;
    }

    public static List<FeaturePos> getBedrockFeaturesRadius(
            List<Integer> featureTypes, int version, long worldSeed,
            int centerChunkX, int centerChunkZ, int radiusChunk, boolean skipBiomeCheck) {

        List<FeaturePos> allFeatures = new ArrayList<>();
        SearchArea area = new SearchArea(centerChunkX, centerChunkZ, radiusChunk);

        Biome biome = skipBiomeCheck ? null : Biome.getBiomeCache(worldSeed, version);

        for (int featureType : featureTypes) {
            processFeatureType(featureType, version, worldSeed, area, biome, skipBiomeCheck, allFeatures);
        }

        return allFeatures;
    }

    private static void processFeatureType(int featureType, int version, long worldSeed,
                                           SearchArea area, Biome biome, boolean skipBiomeCheck,
                                           List<FeaturePos> results) {
        switch (featureType) {
            case BedrockFeatureType.VILLAGE_STRONGHOLD -> findVillageStronghold(worldSeed, version, area, results, skipBiomeCheck);
            case BedrockFeatureType.STATIC_STRONGHOLD -> findStaticStronghold(worldSeed, area, results);
            default -> findStructure(featureType, version, worldSeed, area, biome, skipBiomeCheck, results);
        }
    }

    private static void findVillageStronghold(long worldSeed, int version, SearchArea area, List<FeaturePos> results, boolean skipBiomeCheck) {
        BlockPos[] strongholds = Stronghold.getVillageStrongholds(worldSeed, version, skipBiomeCheck);
        for (BlockPos pos : strongholds) {
            if (area.isWithinRadius(pos.getX(), pos.getZ())) {
                FeaturePos structurePos = new FeaturePos(pos.getX(), pos.getZ());
                structurePos.setFeatureType(BedrockFeatureType.VILLAGE_STRONGHOLD);
                structurePos.setMeta("worldSeed", worldSeed);
                results.add(structurePos);
            }
        }
    }

    private static void findStaticStronghold(long worldSeed, SearchArea area, List<FeaturePos> results) {
        int regionSize = 200;
        featuresInRegions(regionSize, area, (regPos) -> {
            FeaturePos pos = Stronghold.getStaticStronghold(worldSeed, regPos.regX(), regPos.regZ());
            if (pos != null) {
                pos.setFeatureType(BedrockFeatureType.STATIC_STRONGHOLD);
                pos.setMeta("worldSeed", worldSeed);
            }
            return pos;
        }, results);
    }

    private static void findStructure(int featureType, int version, long worldSeed,
                                      SearchArea area, Biome biome, boolean skipBiomeCheck,
                                      List<FeaturePos> results) {
        BedrockFeatureConfig config = BedrockFeatureConfig.getForType(featureType, version);
        if (config != null) {
            int regionSize = config.getSpacing();
            featuresInRegions(regionSize, area, (regPos) -> {
                FeaturePos pos = getBedrockFeaturePos(featureType, version, worldSeed, regPos.regX(), regPos.regZ(), skipBiomeCheck);

                if (pos != null && (skipBiomeCheck || biome.isViableFeaturePos(featureType, pos.getX(), pos.getZ()))) {
                    pos.setFeatureType(featureType);
                }
                return pos;
            }, results);
        }
    }

    private static void featuresInRegions(int regionSize, SearchArea area,
                                          Function<RegionPos, FeaturePos> structureProvider,
                                          List<FeaturePos> results) {
        int maxRegRadius = (int) Math.ceil((double) area.radiusChunk / regionSize) + 1;
        int centerRegX = Math.floorDiv(area.centerChunkX, regionSize);
        int centerRegZ = Math.floorDiv(area.centerChunkZ, regionSize);

        for (int regX = centerRegX - maxRegRadius; regX <= centerRegX + maxRegRadius; regX++) {
            for (int regZ = centerRegZ - maxRegRadius; regZ <= centerRegZ + maxRegRadius; regZ++) {
                FeaturePos pos = structureProvider.apply(new RegionPos(regX, regZ));
                if (pos != null && area.isWithinRadius(pos.getX(), pos.getZ())) {
                    results.add(pos);
                }
            }
        }
    }

    private static FeaturePos findVillageStrongholdAt(long worldSeed, int version, int chunkX, int chunkZ, boolean skipBiomeCheck) {
        BlockPos[] strongholds = Stronghold.getVillageStrongholds(worldSeed, version, skipBiomeCheck);
        for (BlockPos stronghold : strongholds) {
            if (isAtChunk(stronghold, chunkX, chunkZ)) {
                FeaturePos structurePos = new FeaturePos(stronghold.getX(), stronghold.getZ());
                structurePos.setFeatureType(BedrockFeatureType.VILLAGE_STRONGHOLD);
                return structurePos;
            }
        }
        return null;
    }

    private static boolean isAtChunk(BlockPos pos, int chunkX, int chunkZ) {
        return (pos.getX() >> 4) == chunkX && (pos.getZ() >> 4) == chunkZ;
    }

    private static boolean isAtChunk(FeaturePos pos, int chunkX, int chunkZ) {
        return (pos.getX() >> 4) == chunkX && (pos.getZ() >> 4) == chunkZ;
    }

    private static class SearchArea {
        final int centerChunkX, centerChunkZ, radiusChunk;
        final int centerBlockX, centerBlockZ;
        final long radiusBlockSquared;

        SearchArea(int centerChunkX, int centerChunkZ, int radiusChunk) {
            this.centerChunkX = centerChunkX;
            this.centerChunkZ = centerChunkZ;
            this.radiusChunk = radiusChunk;
            this.centerBlockX = centerChunkX << 4;
            this.centerBlockZ = centerChunkZ << 4;
            int radiusBlock = radiusChunk << 4;
            this.radiusBlockSquared = (long) radiusBlock * radiusBlock;
        }

        boolean isWithinRadius(int x, int z) {
            long dx = (long) x - centerBlockX;
            long dz = (long) z - centerBlockZ;
            return dx * dx + dz * dz <= radiusBlockSquared;
        }
    }

    private record RegionPos(int regX, int regZ) {}

}