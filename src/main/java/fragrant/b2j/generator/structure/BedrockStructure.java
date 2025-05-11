package fragrant.b2j.generator.structure;

import fragrant.b2j.biome.BiomeCache;
import fragrant.b2j.biome.BiomeVerifier;
import fragrant.b2j.generator.structure.end.EndCity;
import fragrant.b2j.generator.structure.nether.Bastion;
import fragrant.b2j.generator.structure.nether.Fortress;
import fragrant.b2j.generator.structure.nether.NetherRuinedPortal;
import fragrant.b2j.generator.structure.overworld.*;
import fragrant.b2j.util.Position;

import java.util.ArrayList;
import java.util.List;

public class BedrockStructure {

    public static Position.Pos getBedrockStructurePos(int structureType, int version, long worldSeed, int regX, int regZ, boolean skipBiomeCheck) {
        BedrockStructureConfig config = BedrockStructureConfig.getForType(structureType, version);
        if (config == null) { return null; }

        return switch (structureType) {
            /* Overworld */
            case BedrockStructureType.DESERT_PYRAMID
                    -> DesertPyramid.getDesertPyramid(config, worldSeed, regX, regZ);

            case BedrockStructureType.JUNGLE_TEMPLE
                    -> JungleTemple.getJungleTemple(config, worldSeed, regX, regZ);

            case BedrockStructureType.SWAMP_HUT
                    -> SwampHut.getSwampHut(config, worldSeed, regX, regZ);

            case BedrockStructureType.IGLOO
                    -> Igloo.getIgloo(config, worldSeed, regX, regZ);

            case BedrockStructureType.RUINED_PORTAL_O
                    -> OverworldRuinedPortal.getOverworldRuinedPortal(config, worldSeed, regX, regZ);

            case BedrockStructureType.RUINED_PORTAL_N
                    -> NetherRuinedPortal.getNetherRuinedPortal(config, worldSeed, regX, regZ);

            case BedrockStructureType.WOODLAND_MANSION
                    -> WoodlandMansion.getWoodlandMansion(config, worldSeed, regX, regZ);

            case BedrockStructureType.OCEAN_MONUMENT
                    -> OceanMonument.getOceanMonument(config, worldSeed, regX, regZ);

            case BedrockStructureType.PILLAGER_OUTPOST
                    -> PillagerOutpost.getPillagerOutpost(config, worldSeed, regX, regZ);

            case BedrockStructureType.BURIED_TREASURE
                    -> BuriedTreasure.getBuriedTreasure(config, worldSeed, regX, regZ);

            case BedrockStructureType.VILLAGE
                    -> Village.getVillage(config, worldSeed, regX, regZ, version);

            case BedrockStructureType.ANCIENT_CITY
                    -> AncientCity.getAncientCity(config, worldSeed, regX, regZ);

            case BedrockStructureType.TRAIL_RUINS
                    -> TrailRuins.getTrailRuins(config, worldSeed, regX, regZ);

            case BedrockStructureType.TRIAL_CHAMBERS
                    -> TrialChambers.getTrialChambers(config, worldSeed, regX, regZ);

            case BedrockStructureType.OCEAN_RUINS
                    -> OceanRuins.getOceanRuins(config, worldSeed, regX, regZ, version, skipBiomeCheck);

            case BedrockStructureType.SHIPWRECK
                    -> Shipwreck.getShipwreck(config, worldSeed, regX, regZ);

            case BedrockStructureType.DESERT_WELL
                    -> DesertWell.getDesertWell(worldSeed, regX, regZ);

            case BedrockStructureType.FOSSIL_O
                    -> OverworldFossil.getOverworldFossil(worldSeed, regX, regZ);

            case BedrockStructureType.AMETHYST_GEODE
                    -> AmethystGeode.getGeode(worldSeed, regX, regZ, version);

            case BedrockStructureType.MINESHAFT
                    -> Mineshaft.getMineshaft(worldSeed, regX, regZ);

            case BedrockStructureType.RAVINE
                    -> Ravine.getRavine(worldSeed, regX, regZ, version);

            case BedrockStructureType.STRONGHOLD
                    -> Stronghold.getStaticStronghold(config, worldSeed, regX, regZ);

            /* Nether */
            case BedrockStructureType.BASTION_REMNANT
                    -> Bastion.getBastion(config, worldSeed, regX, regZ);

            case BedrockStructureType.NETHER_FORTRESS
                    -> Fortress.getFortress(config, worldSeed, regX, regZ, version);

            /* End */
            case BedrockStructureType.END_CITY
                    -> EndCity.getEndCity(config, worldSeed, regX, regZ);

            default -> {
                System.err.println("ERROR: getStructurePos: unsupported structure type " + structureType);
                yield null;
            }
        };
    }

    public static Position.Pos isBedrockStructureChunk(int structureType, int version, long worldSeed, int chunkX, int chunkZ) {
        BedrockStructureConfig config = BedrockStructureConfig.getForType(structureType, version);
        if (config == null) {
            return null;
        }

        int regionSize = config.getSpacing();
        int regX = Math.floorDiv(chunkX, regionSize);
        int regZ = Math.floorDiv(chunkZ, regionSize);

        Position.Pos structurePos = BedrockStructure.getBedrockStructurePos(structureType, version, worldSeed, regX, regZ, true);

        if (structurePos != null) {
            int structureChunkX = structurePos.getX() >> 4;
            int structureChunkZ = structurePos.getZ() >> 4;

            if (structureChunkX == chunkX && structureChunkZ == chunkZ) {
                structurePos.setStructureType(structureType);
                return structurePos;
            }
        }

        return null;
    }

    public static List<Position.Pos> getBedrockStructuresRadius(List<Integer> structureTypes, int version, long worldSeed, int centerChunkX, int centerChunkZ, int radiusChunk, boolean skipBiomeCheck) {
        List<Position.Pos> allStructures = new ArrayList<>();
        BiomeCache biomeCache = skipBiomeCheck ? null : new BiomeCache(worldSeed);

        int centerX = centerChunkX << 4;
        int centerZ = centerChunkZ << 4;
        int radiusBlock = radiusChunk << 4;
        int radiusBlockSquared = radiusBlock * radiusBlock;

        if (structureTypes.contains(BedrockStructureType.STRONGHOLD) && !skipBiomeCheck) {
            for (Position.BlockPos blockPos : Stronghold.getVillageStronghold(worldSeed, version)) {
                int x = blockPos.getX();
                int z = blockPos.getZ();

                if (isWithinRadius(x, z, centerX, centerZ, radiusBlockSquared) && BiomeVerifier.isViableStructurePos(BedrockStructureType.STRONGHOLD, x, z, biomeCache)) {
                    Position.Pos pos = new Position.Pos(x, z, "village_stronghold");
                    pos.setStructureType(BedrockStructureType.STRONGHOLD);
                    allStructures.add(pos);
                }
            }
        }

        for (int structureType : structureTypes) {
            if (structureType == BedrockStructureType.STRONGHOLD && !skipBiomeCheck) {
                continue;
            }

            BedrockStructureConfig config = BedrockStructureConfig.getForType(structureType, version);
            if (config == null) continue;

            int regionSize = config.getSpacing();
            int maxRegRadius = (int) Math.ceil((double) radiusBlock / (regionSize * 16));
            int centerRegX = Math.floorDiv(centerX, regionSize * 16);
            int centerRegZ = Math.floorDiv(centerZ, regionSize * 16);

            for (int regX = centerRegX - maxRegRadius; regX <= centerRegX + maxRegRadius; regX++) {
                for (int regZ = centerRegZ - maxRegRadius; regZ <= centerRegZ + maxRegRadius; regZ++) {
                    Position.Pos pos = getBedrockStructurePos(structureType, version, worldSeed, regX, regZ, skipBiomeCheck);
                    if (pos == null) continue;

                    int x = pos.getX();
                    int z = pos.getZ();

                    if (isWithinRadius(x, z, centerX, centerZ, radiusBlockSquared) && (skipBiomeCheck || BiomeVerifier.isViableStructurePos(structureType, x, z, biomeCache))) {
                        pos.setStructureType(structureType);
                        allStructures.add(pos);
                    }
                }
            }
        }

        return allStructures;
    }

    private static boolean isWithinRadius(int x, int z, int centerX, int centerZ, int radiusSquared) {
        int dx = x - centerX;
        int dz = z - centerZ;
        return dx * dx + dz * dz <= radiusSquared;
    }

}