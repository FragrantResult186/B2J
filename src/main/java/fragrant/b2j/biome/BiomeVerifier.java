package fragrant.b2j.biome;

import fragrant.b2j.generator.structure.BedrockStructureType;
import nl.jellejurre.biomesampler.minecraft.Biome;
import nl.kallestruik.noisesampler.minecraft.Dimension;

import java.util.EnumSet;
import java.util.Set;

public class BiomeVerifier {
    public static final Set<Biome> DESERT_PYRAMID_BIOME = EnumSet.of(Biome.DESERT);
    public static final Set<Biome> DEEP_DARK_BIOME = EnumSet.of(Biome.DEEP_DARK);
    public static final Set<Biome> WITCH_HUT_BIOMES = EnumSet.of(Biome.SWAMP);
    public static final Set<Biome> WOODLAND_MANSION_BIOMES = EnumSet.of(Biome.DARK_FOREST);
    public static final Set<Biome> IGLOO_BIOMES = EnumSet.of(
            Biome.SNOWY_PLAINS,
            Biome.SNOWY_TAIGA,
            Biome.SNOWY_SLOPES
    );
    public static final Set<Biome> JUNGLE_TEMPLE_BIOMES = EnumSet.of(
            Biome.JUNGLE,
            Biome.BAMBOO_JUNGLE
    );
    public static final Set<Biome> OUTPOST_BIOMES = EnumSet.of(
            Biome.DESERT,
            Biome.PLAINS,
            Biome.SAVANNA,
            Biome.SNOWY_PLAINS,
            Biome.TAIGA,
            Biome.MEADOW,
            Biome.FROZEN_PEAKS,
            Biome.JAGGED_PEAKS,
            Biome.SNOWY_SLOPES,
            Biome.GROVE,
            Biome.CHERRY_GROVE
    );
    public static final Set<Biome> VILLAGE_BIOMES = EnumSet.of(
            Biome.PLAINS,
            Biome.MEADOW,
            Biome.SUNFLOWER_PLAINS,
            Biome.DESERT,
            Biome.SAVANNA,
            Biome.TAIGA,
            Biome.SNOWY_TAIGA,
            Biome.SNOWY_PLAINS
    );
    public static final Set<Biome> OCEAN_MONUMENT_BIOMES = EnumSet.of(
            Biome.DEEP_OCEAN,
            Biome.DEEP_COLD_OCEAN,
            Biome.DEEP_FROZEN_OCEAN,
            Biome.DEEP_LUKEWARM_OCEAN
    );
    public static final Set<Biome> BURIED_TREASURE_BIOMES = EnumSet.of(
            Biome.BEACH,
            Biome.SNOWY_BEACH,
            Biome.STONY_SHORE
    );
    public static final Set<Biome> OCEANIC_BIOMES = EnumSet.of(
            Biome.OCEAN,
            Biome.DEEP_OCEAN,
            Biome.COLD_OCEAN,
            Biome.DEEP_COLD_OCEAN,
            Biome.FROZEN_OCEAN,
            Biome.DEEP_FROZEN_OCEAN,
            Biome.LUKEWARM_OCEAN,
            Biome.DEEP_LUKEWARM_OCEAN,
            Biome.WARM_OCEAN
    );
    public static final Set<Biome> TRAIL_RUINS_BIOMES = EnumSet.of(
            Biome.TAIGA,
            Biome.SNOWY_TAIGA,
            Biome.OLD_GROWTH_PINE_TAIGA,
            Biome.OLD_GROWTH_SPRUCE_TAIGA,
            Biome.OLD_GROWTH_BIRCH_FOREST,
            Biome.JUNGLE
    );
    public static final Set<Biome> FOSSIL_BIOMES = EnumSet.of(
            Biome.DESERT,
            Biome.SWAMP
    );

    public static boolean isViableStructurePos(int structureType, int blockX, int blockZ, BiomeCache biomeCache) {
        return switch (structureType) {
            case BedrockStructureType.DESERT_PYRAMID   -> checkBiome(blockX, 256, blockZ, biomeCache, DESERT_PYRAMID_BIOME);
            case BedrockStructureType.IGLOO            -> checkBiome(blockX, 256, blockZ, biomeCache, IGLOO_BIOMES);
            case BedrockStructureType.SWAMP_HUT        -> checkBiome(blockX, 256, blockZ, biomeCache, WITCH_HUT_BIOMES);
            case BedrockStructureType.JUNGLE_TEMPLE    -> checkBiome(blockX, 256, blockZ, biomeCache, JUNGLE_TEMPLE_BIOMES);
            case BedrockStructureType.OCEAN_RUINS      -> checkBiome(blockX,  60, blockZ, biomeCache, OCEANIC_BIOMES);
            case BedrockStructureType.SHIPWRECK        -> checkBiome(blockX,  64, blockZ, biomeCache, OCEANIC_BIOMES);
            case BedrockStructureType.PILLAGER_OUTPOST -> checkBiome(blockX, 256, blockZ, biomeCache, OUTPOST_BIOMES);
            case BedrockStructureType.VILLAGE          -> checkBiome(blockX, 256, blockZ, biomeCache, VILLAGE_BIOMES);
            case BedrockStructureType.OCEAN_MONUMENT   -> checkBiome(blockX,  60, blockZ, biomeCache, OCEAN_MONUMENT_BIOMES);
            case BedrockStructureType.WOODLAND_MANSION -> checkBiome(blockX, 256, blockZ, biomeCache, WOODLAND_MANSION_BIOMES);
            case BedrockStructureType.ANCIENT_CITY     -> checkBiome(blockX, -51, blockZ, biomeCache, DEEP_DARK_BIOME);
            case BedrockStructureType.TRAIL_RUINS      -> checkBiome(blockX,  64, blockZ, biomeCache, TRAIL_RUINS_BIOMES);
            case BedrockStructureType.TRIAL_CHAMBERS   ->!checkBiome(blockX,  20, blockZ, biomeCache, DEEP_DARK_BIOME);
            case BedrockStructureType.BURIED_TREASURE  -> checkBiome(blockX,  60, blockZ, biomeCache, BURIED_TREASURE_BIOMES);
            case BedrockStructureType.FOSSIL_O         -> checkBiome(blockX, 256, blockZ, biomeCache, FOSSIL_BIOMES);
            case BedrockStructureType.DESERT_WELL      -> checkBiome(blockX, 128, blockZ, biomeCache, DESERT_PYRAMID_BIOME);

            // TODO: Implement Nether and End biome checks when Bedrock-specific biomes are available

            default -> true;
        };
    }

    private static boolean checkBiome(int blockX, int blockY, int blockZ, BiomeCache biomeCache, Set<Biome> validBiomes) {
        Biome biome = biomeCache.getBiomeAt(blockX, blockY, blockZ, Dimension.OVERWORLD);
        return validBiomes.contains(biome);
    }
}