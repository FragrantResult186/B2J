package fragrant.b2j.worldfeature;

import fragrant.b2j.biome.Biome;
import fragrant.b2j.util.position.*;
import fragrant.b2j.worldfeature.carver.overworld.*;
import fragrant.b2j.worldfeature.feature.end.*;
import fragrant.b2j.worldfeature.feature.nether.*;
import fragrant.b2j.worldfeature.feature.overworld.surface.*;
import fragrant.b2j.worldfeature.feature.overworld.underground.*;
import fragrant.b2j.worldfeature.feature.overworld.vegetation.*;
import fragrant.b2j.worldfeature.structure.*;
import fragrant.b2j.worldfeature.structure.end.*;
import fragrant.b2j.worldfeature.structure.nether.*;
import fragrant.b2j.worldfeature.structure.overworld.surface.*;
import fragrant.b2j.worldfeature.structure.overworld.underground.*;
import fragrant.b2j.worldfeature.structure.overworld.underwater.*;

import java.util.ArrayList;
import java.util.List;

public class BedrockFeature {

    public static FeaturePos getBedrockFeaturePos(int featureType, int version, long worldSeed, int regX, int regZ, boolean skipBiomeCheck) {
        BedrockFeatureConfig config = BedrockFeatureConfig.getForType(featureType, version);
        if (config == null) return null;

        return switch (featureType) {
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
            case BedrockFeatureType.BASTION_REMNANT   -> Bastion.getBastion(config, worldSeed, regX, regZ, skipBiomeCheck);
            case BedrockFeatureType.NETHER_FORTRESS   -> Fortress.getFortress(config, worldSeed, regX, regZ, version);
            case BedrockFeatureType.RUINED_PORTAL_N   -> RuinedPortal.getNetherRuinedPortal(config, worldSeed, regX, regZ);
            case BedrockFeatureType.END_CITY          -> EndCity.getEndCity(config, worldSeed, regX, regZ);
            case BedrockFeatureType.AMETHYST_GEODE    -> AmethystGeode.getGeode(worldSeed, regX, regZ, version);
            case BedrockFeatureType.DESERT_WELL       -> DesertWell.getDesertWell(worldSeed, regX, regZ);
            case BedrockFeatureType.END_ISLAND        -> EndIsland.getEndIsland(worldSeed, regX, regZ, version);
            case BedrockFeatureType.FOSSIL_N          -> NetherFossil.getNetherFossil(worldSeed, regX, regZ, skipBiomeCheck);
            case BedrockFeatureType.FOSSIL_O          -> OverworldFossil.getOverworldFossil(worldSeed, regX, regZ);
            case BedrockFeatureType.LAVA_LAKE         -> LavaLake.getLavaLake(worldSeed, regX, regZ);
            case BedrockFeatureType.PUMPKIN           -> Pumpkin.getPumpkin(worldSeed, regX, regZ);
            case BedrockFeatureType.RAVINE            -> Ravine.getRavine(worldSeed, regX, regZ, version);
            case BedrockFeatureType.SWEET_BERRY       -> SweetBerry.getSweetBerry(worldSeed, regX, regZ, skipBiomeCheck);
            default -> null;
        };
    }

    public static FeaturePos isFeatureChunk(int structureType, int version, long worldSeed, int chunkX, int chunkZ, boolean skipBiomeCheck) {
        if (structureType == BedrockFeatureType.VILLAGE_STRONGHOLD) {
            BlockPos[] strongholds = Stronghold.getVillageStrongholds(worldSeed, version, skipBiomeCheck);
            for (BlockPos pos : strongholds) {
                if ((pos.getX() >> 4) == chunkX && (pos.getZ() >> 4) == chunkZ) {
                    FeaturePos structurePos = new FeaturePos(pos.getX(), pos.getZ());
                    structurePos.setFeatureType(BedrockFeatureType.VILLAGE_STRONGHOLD);
                    return structurePos;
                }
            }
            return null;
        }

        BedrockFeatureConfig config = BedrockFeatureConfig.getForType(structureType, version);
        if (config == null) return null;

        int regX = Math.floorDiv(chunkX, config.getSpacing());
        int regZ = Math.floorDiv(chunkZ, config.getSpacing());
        FeaturePos featurePos = getBedrockFeaturePos(structureType, version, worldSeed, regX, regZ, skipBiomeCheck);

        if (featurePos != null && (featurePos.getX() >> 4) == chunkX && (featurePos.getZ() >> 4) == chunkZ) {
            featurePos.setFeatureType(structureType);
            return featurePos;
        }
        return null;
    }

    public static List<FeaturePos> getBedrockFeaturesRadius(List<Integer> featureTypes, int version, long worldSeed, int centerChunkX, int centerChunkZ, int radiusChunk, boolean skipBiomeCheck) {
        List<FeaturePos> allFeatures = new ArrayList<>();
        Biome biome = skipBiomeCheck ? null : Biome.getBiomeCache(worldSeed, version);

        int centerBlockX = centerChunkX << 4;
        int centerBlockZ = centerChunkZ << 4;
        long radiusBlockSquared = ((long) radiusChunk << 4) * ((long) radiusChunk << 4);

        for (int featureType : featureTypes) {
            if (featureType == BedrockFeatureType.VILLAGE_STRONGHOLD) {
                BlockPos[] strongholds = Stronghold.getVillageStrongholds(worldSeed, version, skipBiomeCheck);
                for (BlockPos pos : strongholds) {
                    long dx = pos.getX() - centerBlockX;
                    long dz = pos.getZ() - centerBlockZ;
                    if (dx * dx + dz * dz <= radiusBlockSquared) {
                        FeaturePos structurePos = new FeaturePos(pos.getX(), pos.getZ());
                        structurePos.setFeatureType(BedrockFeatureType.VILLAGE_STRONGHOLD);
                        structurePos.setMeta("worldSeed", worldSeed);
                        allFeatures.add(structurePos);
                    }
                }
            } else if (featureType == BedrockFeatureType.STATIC_STRONGHOLD) {
                processRegions(200, centerChunkX, centerChunkZ, radiusChunk, (regX, regZ) -> {
                    FeaturePos pos = Stronghold.getStaticStronghold(worldSeed, regX, regZ);
                    if (pos != null) {
                        pos.setFeatureType(BedrockFeatureType.STATIC_STRONGHOLD);
                        pos.setMeta("worldSeed", worldSeed);
                    }
                    return pos;
                }, centerBlockX, centerBlockZ, radiusBlockSquared, allFeatures);
            } else {
                BedrockFeatureConfig config = BedrockFeatureConfig.getForType(featureType, version);
                if (config != null) {
                    processRegions(config.getSpacing(), centerChunkX, centerChunkZ, radiusChunk, (regX, regZ) -> {
                        FeaturePos pos = getBedrockFeaturePos(featureType, version, worldSeed, regX, regZ, skipBiomeCheck);
                        if (pos != null && (skipBiomeCheck || biome.isViableFeaturePos(worldSeed, featureType, pos.getX(), pos.getZ()))) {
                            pos.setFeatureType(featureType);
                        }
                        return pos;
                    }, centerBlockX, centerBlockZ, radiusBlockSquared, allFeatures);
                }
            }
        }
        return allFeatures;
    }

    private static void processRegions(int regionSize, int centerChunkX, int centerChunkZ, int radiusChunk, RegionProcessor processor, int centerBlockX, int centerBlockZ, long radiusBlockSquared, List<FeaturePos> results) {
        int maxRegRadius = (int) Math.ceil((double) radiusChunk / regionSize) + 1;
        int centerRegX = Math.floorDiv(centerChunkX, regionSize);
        int centerRegZ = Math.floorDiv(centerChunkZ, regionSize);

        for (int regX = centerRegX - maxRegRadius; regX <= centerRegX + maxRegRadius; regX++) {
            for (int regZ = centerRegZ - maxRegRadius; regZ <= centerRegZ + maxRegRadius; regZ++) {
                FeaturePos pos = processor.process(regX, regZ);
                if (pos != null) {
                    long dx = pos.getX() - centerBlockX;
                    long dz = pos.getZ() - centerBlockZ;
                    if (dx * dx + dz * dz <= radiusBlockSquared) {
                        results.add(pos);
                    }
                }
            }
        }
    }

    @FunctionalInterface
    private interface RegionProcessor {
        FeaturePos process(int regX, int regZ);
    }
}