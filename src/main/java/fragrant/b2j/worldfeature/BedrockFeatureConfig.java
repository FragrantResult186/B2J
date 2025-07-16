package fragrant.b2j.worldfeature;

import static fragrant.b2j.util.BedrockVersion.*;

public class BedrockFeatureConfig {
    /* Random Spread */
    private final int salt;
    private final int spacing;
    private final int separation;
    private final int featureType;
    /* Spread Type */
    public static final int linear = 1;
    public static final int triangular = 2;
    /* Dimension */
    private final int dimension;
    public static final int DIM_OVERWORLD = 0;
    public static final int DIM_NETHER = -1;
    public static final int DIM_END = 1;

    private final int extraInfo;

    public BedrockFeatureConfig(int salt, int Spacing, int Separation, int structureType, int dimension, int extraInfo) {
        this.salt = salt;
        this.spacing = Spacing;
        this.separation = Separation;
        this.featureType = structureType;
        this.dimension = dimension;
        this.extraInfo = extraInfo;
    }

    public static BedrockFeatureConfig getForType(int structureType, int version) {
        // Overworld structures
        BedrockFeatureConfig ANCIENT_CITY       = new BedrockFeatureConfig(  20083232,  24,  16, BedrockFeatureType.ANCIENT_CITY,       DIM_OVERWORLD, triangular);
        BedrockFeatureConfig DESERT_PYRAMID     = new BedrockFeatureConfig(  14357617,  32,  24, BedrockFeatureType.DESERT_PYRAMID,     DIM_OVERWORLD, linear);
        BedrockFeatureConfig IGLOO              = new BedrockFeatureConfig(  14357617,  32,  24, BedrockFeatureType.IGLOO,              DIM_OVERWORLD, linear);
        BedrockFeatureConfig JUNGLE_TEMPLE      = new BedrockFeatureConfig(  14357617,  32,  24, BedrockFeatureType.JUNGLE_TEMPLE,      DIM_OVERWORLD, linear);
        BedrockFeatureConfig MANSION            = new BedrockFeatureConfig(  10387319,  80,  60, BedrockFeatureType.WOODLAND_MANSION,   DIM_OVERWORLD, triangular);
        BedrockFeatureConfig MINESHAFT          = new BedrockFeatureConfig(         0,   1,   1, BedrockFeatureType.MINESHAFT,          DIM_OVERWORLD, 0);
        BedrockFeatureConfig MONUMENT           = new BedrockFeatureConfig(  10387313,  32,  27, BedrockFeatureType.OCEAN_MONUMENT,     DIM_OVERWORLD, triangular);
        BedrockFeatureConfig OCEAN_RUIN_117     = new BedrockFeatureConfig(  14357621,  12,   5, BedrockFeatureType.OCEAN_RUINS,        DIM_OVERWORLD, triangular);
        BedrockFeatureConfig OCEAN_RUIN         = new BedrockFeatureConfig(  14357621,  20,  12, BedrockFeatureType.OCEAN_RUINS,        DIM_OVERWORLD, linear);
        BedrockFeatureConfig OUTPOST            = new BedrockFeatureConfig( 165745296,  80,  56, BedrockFeatureType.PILLAGER_OUTPOST,   DIM_OVERWORLD, triangular);
        BedrockFeatureConfig RUINED_PORTAL      = new BedrockFeatureConfig(  40552231,  40,  25, BedrockFeatureType.RUINED_PORTAL_O,    DIM_OVERWORLD, linear);
        BedrockFeatureConfig SHIPWRECK_117      = new BedrockFeatureConfig( 165745295,  10,   5, BedrockFeatureType.SHIPWRECK,          DIM_OVERWORLD, triangular);
        BedrockFeatureConfig SHIPWRECK          = new BedrockFeatureConfig( 165745295,  24,  20, BedrockFeatureType.SHIPWRECK,          DIM_OVERWORLD, linear);
        BedrockFeatureConfig STATIC_STRONGHOLD  = new BedrockFeatureConfig(  97858791, 150, 200, BedrockFeatureType.STATIC_STRONGHOLD,  DIM_OVERWORLD, 0);
        BedrockFeatureConfig SWAMP_HUT          = new BedrockFeatureConfig(  14357617,  32,  24, BedrockFeatureType.SWAMP_HUT,          DIM_OVERWORLD, linear);
        BedrockFeatureConfig TRAIL_RUINS        = new BedrockFeatureConfig(  83469867,  34,   8, BedrockFeatureType.TRAIL_RUINS,        DIM_OVERWORLD, linear);
        BedrockFeatureConfig TREASURE           = new BedrockFeatureConfig(  16842397,   4,   2, BedrockFeatureType.BURIED_TREASURE,    DIM_OVERWORLD, triangular);
        BedrockFeatureConfig TRIAL_CHAMBERS     = new BedrockFeatureConfig(  94251327,  34,  12, BedrockFeatureType.TRIAL_CHAMBERS,     DIM_OVERWORLD, linear);
        BedrockFeatureConfig VILLAGE_117        = new BedrockFeatureConfig(  10387312,  27,  17, BedrockFeatureType.VILLAGE,            DIM_OVERWORLD, triangular);
        BedrockFeatureConfig VILLAGE            = new BedrockFeatureConfig(  10387312,  34,  26, BedrockFeatureType.VILLAGE,            DIM_OVERWORLD, triangular);
        BedrockFeatureConfig VILLAGE_STRONGHOLD = new BedrockFeatureConfig(  97858791, 150, 200, BedrockFeatureType.VILLAGE_STRONGHOLD, DIM_OVERWORLD, 0);

        // Nether structures
        BedrockFeatureConfig BASTION         = new BedrockFeatureConfig( 30084232, 30, 26, BedrockFeatureType.BASTION_REMNANT, DIM_NETHER, linear);
        BedrockFeatureConfig FORTRESS_1_14   = new BedrockFeatureConfig(        0,  1,  1, BedrockFeatureType.NETHER_FORTRESS, DIM_NETHER, 0);
        BedrockFeatureConfig FORTRESS        = new BedrockFeatureConfig( 30084232, 30, 26, BedrockFeatureType.NETHER_FORTRESS, DIM_NETHER, linear);
        BedrockFeatureConfig RUINED_PORTAL_N = new BedrockFeatureConfig( 40552231, 25, 15, BedrockFeatureType.RUINED_PORTAL_N, DIM_NETHER, linear);

        // End structures
        BedrockFeatureConfig END_CITY = new BedrockFeatureConfig( 10387313, 20, 9, BedrockFeatureType.END_CITY, DIM_END, triangular);

        // Features
        BedrockFeatureConfig DESERT_WELL = new BedrockFeatureConfig( 0, 1, 1, BedrockFeatureType.DESERT_WELL,    DIM_OVERWORLD, 0);
        BedrockFeatureConfig END_ISLAND  = new BedrockFeatureConfig( 0, 1, 1, BedrockFeatureType.END_ISLAND,     DIM_END, 0);
        BedrockFeatureConfig FOSSIL      = new BedrockFeatureConfig( 0, 1, 1, BedrockFeatureType.FOSSIL_O,       DIM_OVERWORLD, 0);
        BedrockFeatureConfig FOSSIL_N    = new BedrockFeatureConfig( 0, 1, 1, BedrockFeatureType.FOSSIL_N,       DIM_NETHER, 0);
        BedrockFeatureConfig GEODE_117   = new BedrockFeatureConfig( 0, 1, 1, BedrockFeatureType.AMETHYST_GEODE, DIM_OVERWORLD, 0);
        BedrockFeatureConfig GEODE       = new BedrockFeatureConfig( 0, 1, 1, BedrockFeatureType.AMETHYST_GEODE, DIM_OVERWORLD, 0);
        BedrockFeatureConfig LAVA_LAKE   = new BedrockFeatureConfig( 0, 1, 1, BedrockFeatureType.LAVA_LAKE,      DIM_OVERWORLD, 0);
        BedrockFeatureConfig PUMPKIN     = new BedrockFeatureConfig( 0, 1, 1, BedrockFeatureType.PUMPKIN,        DIM_OVERWORLD, 0);
        BedrockFeatureConfig RAVINE      = new BedrockFeatureConfig( 0, 1, 1, BedrockFeatureType.RAVINE,         DIM_OVERWORLD, 0);
        BedrockFeatureConfig SWEET_BERRY = new BedrockFeatureConfig( 0, 1, 1, BedrockFeatureType.SWEET_BERRY,    DIM_OVERWORLD, 0);

        return switch (structureType) {
            // Overworld structures
            case BedrockFeatureType.ANCIENT_CITY       -> isAtLeast(version, MC_1_19_2) ? ANCIENT_CITY : null;
            case BedrockFeatureType.BURIED_TREASURE    -> isAtLeast(version, MC_1_14)   ? TREASURE : null;
            case BedrockFeatureType.DESERT_PYRAMID     -> isAtLeast(version, MC_1_14)   ? DESERT_PYRAMID : null;
            case BedrockFeatureType.IGLOO              -> isAtLeast(version, MC_1_14)   ? IGLOO : null;
            case BedrockFeatureType.JUNGLE_TEMPLE      -> isAtLeast(version, MC_1_14)   ? JUNGLE_TEMPLE : null;
            case BedrockFeatureType.MINESHAFT          -> isAtLeast(version, MC_1_1)    ? MINESHAFT : null;
            case BedrockFeatureType.OCEAN_MONUMENT     -> isAtLeast(version, MC_1_14)   ? MONUMENT : null;
            case BedrockFeatureType.OCEAN_RUINS        -> isAtLeast(version, MC_1_16)   ? (isAtMost(version, MC_1_17) ? OCEAN_RUIN_117 : OCEAN_RUIN) : null;
            case BedrockFeatureType.PILLAGER_OUTPOST   -> isAtLeast(version, MC_1_14)   ? OUTPOST : null;
            case BedrockFeatureType.RUINED_PORTAL_O    -> isAtLeast(version, MC_1_14)   ? RUINED_PORTAL : null;
            case BedrockFeatureType.SHIPWRECK          -> isAtLeast(version, MC_1_14)   ? (isAtMost(version, MC_1_17) ? SHIPWRECK_117 : SHIPWRECK) : null;
            case BedrockFeatureType.STATIC_STRONGHOLD  -> isAtLeast(version, MC_1_14)   ? STATIC_STRONGHOLD : null;
            case BedrockFeatureType.SWAMP_HUT          -> isAtLeast(version, MC_1_14)   ? SWAMP_HUT : null;
            case BedrockFeatureType.TRAIL_RUINS        -> isAtLeast(version, MC_1_20)   ? TRAIL_RUINS : null;
            case BedrockFeatureType.TRIAL_CHAMBERS     -> isAtLeast(version, MC_1_21)   ? TRIAL_CHAMBERS : null;
            case BedrockFeatureType.VILLAGE            -> isAtLeast(version, MC_1_14)   ? (isAtMost(version, MC_1_17) ? VILLAGE_117 : VILLAGE) : null;
            case BedrockFeatureType.VILLAGE_STRONGHOLD -> isAtLeast(version, MC_1_14)   ? VILLAGE_STRONGHOLD : null;
            case BedrockFeatureType.WOODLAND_MANSION   -> isAtLeast(version, MC_1_14)   ? MANSION : null;

            // Nether structures
            case BedrockFeatureType.BASTION_REMNANT -> isAtLeast(version, MC_1_16) ? BASTION : null;
            case BedrockFeatureType.NETHER_FORTRESS -> isAtLeast(version, MC_1_14) ? (isAtLeast(version, MC_1_16) ? FORTRESS : FORTRESS_1_14) : null;
            case BedrockFeatureType.RUINED_PORTAL_N -> isAtLeast(version, MC_1_14) ? RUINED_PORTAL_N : null;

            // End structures
            case BedrockFeatureType.END_CITY -> isAtLeast(version, MC_1_14) ? END_CITY : null;

            // Features
            case BedrockFeatureType.AMETHYST_GEODE -> isAtLeast(version, MC_1_17) ? (isAtMost(version, MC_1_17) ? GEODE_117 : GEODE) : null;
            case BedrockFeatureType.DESERT_WELL    -> isAtLeast(version, MC_1_18) ? DESERT_WELL : null;
            case BedrockFeatureType.END_ISLAND     -> isAtLeast(version, MC_1_6) ? END_ISLAND : null;
            case BedrockFeatureType.FOSSIL_N       -> isAtLeast(version, MC_1_16) ? FOSSIL_N : null;
            case BedrockFeatureType.FOSSIL_O       -> isAtLeast(version, MC_1_16) ? FOSSIL : null;
            case BedrockFeatureType.LAVA_LAKE      -> isAtLeast(version, MC_1_18) ? LAVA_LAKE : null;
            case BedrockFeatureType.PUMPKIN        -> isAtLeast(version, MC_1_14) ? PUMPKIN : null;
            case BedrockFeatureType.RAVINE         -> isAtLeast(version, MC_1_14) ? RAVINE : null;
            case BedrockFeatureType.SWEET_BERRY    -> isAtLeast(version, MC_1_14) ? SWEET_BERRY : null;

            default -> null;
        };
    }

    public int getSalt() {
        return salt;
    }

    public int getSpacing() {
        return spacing;
    }

    public int getSeparation() {
        return separation;
    }

    public int getFeatureType() {
        return featureType;
    }

    public int getDimension() {
        return dimension;
    }

    public int getExtraInfo() {
        return extraInfo;
    }

}