package fragrant.b2j.biome;

import fragrant.b2j.terrain.nether.NetherUtil;
import fragrant.b2j.worldfeature.BedrockFeatureType;
import fragrant.b2j.util.BedrockVersion;
import de.rasmusantons.cubiomes.*;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Biome {
    private static final Map<String, Biome> WORLD_MANAGERS = new ConcurrentHashMap<>();

    private final Map<Long, BiomeID> biomeCache = new HashMap<>();
    private final Cubiomes cubiomes;
    private final long worldSeed;
    private final int mcVersion;

    public static final Set<BiomeID> DESERT_PYRAMID_BIOME = EnumSet.of(BiomeID.desert);
    public static final Set<BiomeID> DEEP_DARK_BIOME = EnumSet.of(BiomeID.deep_dark);
    public static final Set<BiomeID> WITCH_HUT_BIOMES = EnumSet.of(BiomeID.swamp);
    public static final Set<BiomeID> WOODLAND_MANSION_BIOMES = EnumSet.of(
            BiomeID.dark_forest, BiomeID.pale_garden);
    public static final Set<BiomeID> IGLOO_BIOMES_1_14 = EnumSet.of(
            BiomeID.snowy_tundra, BiomeID.snowy_taiga);
    public static final Set<BiomeID> IGLOO_BIOMES_1_18 = EnumSet.of(
            BiomeID.snowy_tundra, BiomeID.snowy_taiga, BiomeID.snowy_slopes);
    public static final Set<BiomeID> JUNGLE_TEMPLE_BIOMES = EnumSet.of(BiomeID.jungle);
    public static final Set<BiomeID> OUTPOST_BIOMES_1_14 = EnumSet.of(
            BiomeID.desert, BiomeID.plains, BiomeID.savanna, BiomeID.snowy_tundra, BiomeID.taiga);
    public static final Set<BiomeID> OUTPOST_BIOMES_1_18 = EnumSet.of(
            BiomeID.desert, BiomeID.plains, BiomeID.savanna, BiomeID.snowy_tundra,
            BiomeID.taiga, BiomeID.meadow, BiomeID.frozen_peaks, BiomeID.jagged_peaks,
            BiomeID.snowy_slopes, BiomeID.grove);
    public static final Set<BiomeID> OUTPOST_BIOMES_1_19 = EnumSet.of(
            BiomeID.desert, BiomeID.plains, BiomeID.savanna, BiomeID.snowy_tundra,
            BiomeID.taiga, BiomeID.meadow, BiomeID.frozen_peaks, BiomeID.jagged_peaks,
            BiomeID.snowy_slopes, BiomeID.grove, BiomeID.cherry_grove);
    public static final Set<BiomeID> VILLAGE_BIOMES_1_14 = EnumSet.of(
            BiomeID.plains, BiomeID.desert, BiomeID.savanna, BiomeID.taiga,
            BiomeID.snowy_taiga, BiomeID.snowy_tundra);
    public static final Set<BiomeID> VILLAGE_BIOMES_1_18 = EnumSet.of(
            BiomeID.plains, BiomeID.meadow, BiomeID.sunflower_plains, BiomeID.desert,
            BiomeID.savanna, BiomeID.taiga, BiomeID.snowy_taiga, BiomeID.snowy_tundra);
    public static final Set<BiomeID> OCEAN_MONUMENT_BIOMES = EnumSet.of(
            BiomeID.deep_ocean, BiomeID.deep_cold_ocean, BiomeID.deep_frozen_ocean, BiomeID.deep_lukewarm_ocean);
    public static final Set<BiomeID> BURIED_TREASURE_BIOMES = EnumSet.of(
            BiomeID.beach, BiomeID.snowy_beach, BiomeID.stone_shore);
    public static final Set<BiomeID> OCEANIC_BIOMES = EnumSet.of(
            BiomeID.ocean, BiomeID.deep_ocean, BiomeID.cold_ocean, BiomeID.deep_cold_ocean,
            BiomeID.frozen_ocean, BiomeID.deep_frozen_ocean, BiomeID.lukewarm_ocean,
            BiomeID.deep_lukewarm_ocean, BiomeID.warm_ocean);
    public static final Set<BiomeID> TRAIL_RUINS_BIOMES = EnumSet.of(
            BiomeID.taiga, BiomeID.snowy_taiga, BiomeID.giant_tree_taiga, BiomeID.giant_spruce_taiga,
            BiomeID.tall_birch_forest, BiomeID.jungle);
    public static final Set<BiomeID> FOSSIL_BIOMES = EnumSet.of(BiomeID.desert, BiomeID.swamp);
    public static final Set<BiomeID> FOSSIL_N_BIOMES = EnumSet.of(BiomeID.soul_sand_valley);
    public static final Set<BiomeID> PUMPKIN_RESTRICTED_BIOMES = EnumSet.of(
            BiomeID.ocean, BiomeID.deep_ocean, BiomeID.cold_ocean, BiomeID.deep_cold_ocean,
            BiomeID.frozen_ocean, BiomeID.deep_frozen_ocean, BiomeID.lukewarm_ocean,
            BiomeID.deep_lukewarm_ocean, BiomeID.warm_ocean, BiomeID.river, BiomeID.frozen_river,
            BiomeID.desert, BiomeID.badlands);
    public static final Set<BiomeID> LAVA_LAKE_RESTRICTED_BIOMES = EnumSet.of(
            BiomeID.ocean, BiomeID.deep_ocean, BiomeID.cold_ocean, BiomeID.deep_cold_ocean,
            BiomeID.frozen_ocean, BiomeID.deep_frozen_ocean, BiomeID.lukewarm_ocean,
            BiomeID.deep_lukewarm_ocean, BiomeID.warm_ocean, BiomeID.river, BiomeID.frozen_river);
    public static final Set<BiomeID> BASTION_BIOMES = EnumSet.of(
            BiomeID.nether_wastes, BiomeID.soul_sand_valley,
            BiomeID.crimson_forest, BiomeID.warped_forest);
    private static final Map<Integer, BiomeID> NETHER_BIOME_MAP = new HashMap<>();
    static {
        NETHER_BIOME_MAP.put(8, BiomeID.nether_wastes);
        NETHER_BIOME_MAP.put(178, BiomeID.soul_sand_valley);
        NETHER_BIOME_MAP.put(179, BiomeID.crimson_forest);
        NETHER_BIOME_MAP.put(180, BiomeID.warped_forest);
        NETHER_BIOME_MAP.put(181, BiomeID.basalt_deltas);
    }

    private Biome(long worldSeed, int mcVersion) {
        this.worldSeed = worldSeed;
        this.mcVersion = mcVersion;
        MCVersion cubiomesVersion = getCubiomesMCVersion(mcVersion);
        this.cubiomes = new Cubiomes(cubiomesVersion);
    }

    public static Biome getBiomeCache(long worldSeed, int mcVersion) {
        String key = worldSeed + "_" + mcVersion;
        return WORLD_MANAGERS.computeIfAbsent(key, k -> new Biome(worldSeed, mcVersion));
    }

    private MCVersion getCubiomesMCVersion(int bedrockVersion) {
        if (bedrockVersion >= BedrockVersion.MC_1_21) {
            return MCVersion.MC_1_21;
        } else if (bedrockVersion >= BedrockVersion.MC_1_20) {
            return MCVersion.MC_1_20;
        } else if (bedrockVersion >= BedrockVersion.MC_1_19) {
            return MCVersion.MC_1_19;
        } else if (bedrockVersion >= BedrockVersion.MC_1_18) {
            return MCVersion.MC_1_18;
        } else if (bedrockVersion >= BedrockVersion.MC_1_17) {
            return MCVersion.MC_1_17;
        } else if (bedrockVersion >= BedrockVersion.MC_1_16) {
            return MCVersion.MC_1_16;
        } else if (bedrockVersion >= BedrockVersion.MC_1_15) {
            return MCVersion.MC_1_15;
        } else {
            return MCVersion.MC_1_14;
        }
    }

    public BiomeID getBiomeAt(int x, int y, int z, Dimension dimension) {
        long key = encodePositionKey(x, y, z, dimension);

        if (biomeCache.containsKey(key)) return biomeCache.get(key);

        cubiomes.applySeed(dimension, this.worldSeed);
        BiomeID biome = cubiomes.getBiomeAt(1, x, y, z);

        if (biome == null) {
            biome = BiomeID.none;
        }

        biomeCache.put(key, biome);
        return biome;
    }

    public boolean isViableFeaturePos(long worldSeed, int structureType, int blockX, int blockZ) {
        return switch (structureType) {
            case BedrockFeatureType.ANCIENT_CITY -> checkBiome(blockX, -51, blockZ, DEEP_DARK_BIOME);
            case BedrockFeatureType.BASTION_REMNANT -> checkBiomeNether(worldSeed, blockX, blockZ, BASTION_BIOMES);
            case BedrockFeatureType.BURIED_TREASURE -> checkBiome(blockX, 60, blockZ, BURIED_TREASURE_BIOMES);
            case BedrockFeatureType.DESERT_PYRAMID -> checkBiome(blockX, 256, blockZ, DESERT_PYRAMID_BIOME);
            case BedrockFeatureType.DESERT_WELL -> checkBiome(blockX, 128, blockZ, DESERT_PYRAMID_BIOME);
            case BedrockFeatureType.FOSSIL_O -> checkBiome(blockX, 256, blockZ, FOSSIL_BIOMES);
            case BedrockFeatureType.FOSSIL_N -> checkBiomeNether(worldSeed, blockX, blockZ, FOSSIL_N_BIOMES);
            case BedrockFeatureType.IGLOO -> checkBiome(blockX, 256, blockZ, getIglooBiomes());
            case BedrockFeatureType.JUNGLE_TEMPLE -> checkBiome(blockX, 256, blockZ, JUNGLE_TEMPLE_BIOMES);
            case BedrockFeatureType.LAVA_LAKE -> !checkBiome(blockX, 60, blockZ, LAVA_LAKE_RESTRICTED_BIOMES);
            case BedrockFeatureType.OCEAN_MONUMENT -> checkBiome(blockX, 60, blockZ, OCEAN_MONUMENT_BIOMES);
            case BedrockFeatureType.OCEAN_RUINS -> checkBiome(blockX, 60, blockZ, OCEANIC_BIOMES);
            case BedrockFeatureType.PILLAGER_OUTPOST -> checkBiome(blockX, 256, blockZ, getOutpostBiomes());
            case BedrockFeatureType.PUMPKIN -> !checkBiome(blockX, 256, blockZ, PUMPKIN_RESTRICTED_BIOMES);
            case BedrockFeatureType.SHIPWRECK -> checkBiome(blockX, 64, blockZ, OCEANIC_BIOMES);
            case BedrockFeatureType.SWAMP_HUT -> checkBiome(blockX, 256, blockZ, WITCH_HUT_BIOMES);
            case BedrockFeatureType.TRAIL_RUINS -> checkBiome(blockX, 64, blockZ, TRAIL_RUINS_BIOMES);
            case BedrockFeatureType.TRIAL_CHAMBERS -> !checkBiome(blockX, 20, blockZ, DEEP_DARK_BIOME);
            case BedrockFeatureType.VILLAGE -> checkBiome(blockX, 256, blockZ, getVillageBiomes());
            case BedrockFeatureType.WOODLAND_MANSION -> checkBiome(blockX, 256, blockZ, WOODLAND_MANSION_BIOMES);
            default -> true;
        };
    }

    private Set<BiomeID> getIglooBiomes() {
        if (BedrockVersion.isAtLeast(mcVersion, BedrockVersion.MC_1_18)) {
            return IGLOO_BIOMES_1_18;
        }
        return IGLOO_BIOMES_1_14;
    }

    private Set<BiomeID> getOutpostBiomes() {
        if (BedrockVersion.isAtLeast(mcVersion, BedrockVersion.MC_1_19)) {
            return OUTPOST_BIOMES_1_19;
        } else if (BedrockVersion.isAtLeast(mcVersion, BedrockVersion.MC_1_18)) {
            return OUTPOST_BIOMES_1_18;
        }
        return OUTPOST_BIOMES_1_14;
    }

    private Set<BiomeID> getVillageBiomes() {
        if (BedrockVersion.isAtLeast(mcVersion, BedrockVersion.MC_1_18)) {
            return VILLAGE_BIOMES_1_18;
        }
        return VILLAGE_BIOMES_1_14;
    }

    private long encodePositionKey(int x, int y, int z, Dimension dimension) {
        return ((long) x & 0x3FFFFF) |
                (((long) z & 0x3FFFFF) << 22) |
                (((long) y & 0xFFF) << 44) |
                (((long) dimension.getValue() & 0xF) << 56);
    }

    private boolean checkBiome(int blockX, int blockY, int blockZ, Set<BiomeID> validBiomes) {
        BiomeID biome = getBiomeAt(blockX, blockY, blockZ, Dimension.DIM_OVERWORLD);
        return validBiomes.contains(biome);
    }

    private boolean checkBiomeNether(long worldSeed, int blockX, int blockZ, Set<BiomeID> validBiomes) {
        int biomeId = NetherUtil.getBiome(worldSeed, blockX, blockZ);
        BiomeID biome = NETHER_BIOME_MAP.getOrDefault(biomeId, BiomeID.none);
        return validBiomes.contains(biome);
    }

    public void clearCache() {
        biomeCache.clear();
    }

    public long getWorldSeed() {
        return worldSeed;
    }

    public int getMcVersion() {
        return mcVersion;
    }
}