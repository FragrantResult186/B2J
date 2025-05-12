package fragrant.b2j.generator.structure;

import static fragrant.b2j.util.BedrockVersion.*;

public class BedrockStructureConfig {
    /* Random Spread */
    private final int salt;
    private final int spacing;
    private final int separation;
    private final int structureType;
    /* Spread Type */
    public static final int linear = 1;
    public static final int triangular = 2;
    public static final int shiftedLinear = 3;
    /* Dimension */
    private final int dimension;
    public static final int DIM_OVERWORLD = 0;
    public static final int DIM_NETHER = -1;
    public static final int DIM_END = 1;

    private final int extraInfo;

    public BedrockStructureConfig(int salt, int Spacing, int Separation, int structureType, int dimension, int extraInfo) {
        this.salt = salt;
        this.spacing = Spacing;
        this.separation = Separation;
        this.structureType = structureType;
        this.dimension = dimension;
        this.extraInfo = extraInfo;
    }

    public static BedrockStructureConfig getForType(int structureType, int version) {
        /* Overworld */
        final
        BedrockStructureConfig DESERT_PYRAMID = new BedrockStructureConfig(  14357617,  32,  24, BedrockStructureType.DESERT_PYRAMID,   DIM_OVERWORLD, linear);
        BedrockStructureConfig IGLOO          = new BedrockStructureConfig(  14357617,  32,  24, BedrockStructureType.IGLOO,            DIM_OVERWORLD, linear);
        BedrockStructureConfig JUNGLE_TEMPLE  = new BedrockStructureConfig(  14357617,  32,  24, BedrockStructureType.JUNGLE_TEMPLE,    DIM_OVERWORLD, linear);
        BedrockStructureConfig SWAMP_HUT      = new BedrockStructureConfig(  14357617,  32,  24, BedrockStructureType.SWAMP_HUT,        DIM_OVERWORLD, linear);
        BedrockStructureConfig OUTPOST        = new BedrockStructureConfig( 165745296,  80,  56, BedrockStructureType.PILLAGER_OUTPOST, DIM_OVERWORLD, triangular);
        BedrockStructureConfig VILLAGE_117    = new BedrockStructureConfig(  10387312,  27,  17, BedrockStructureType.VILLAGE,          DIM_OVERWORLD, triangular);
        BedrockStructureConfig VILLAGE        = new BedrockStructureConfig(  10387312,  34,  26, BedrockStructureType.VILLAGE,          DIM_OVERWORLD, triangular);
        BedrockStructureConfig OCEAN_RUIN_117 = new BedrockStructureConfig(  14357621,  12,   5, BedrockStructureType.OCEAN_RUINS,      DIM_OVERWORLD, triangular);
        BedrockStructureConfig OCEAN_RUIN     = new BedrockStructureConfig(  14357621,  20,  12, BedrockStructureType.OCEAN_RUINS,      DIM_OVERWORLD, linear);
        BedrockStructureConfig SHIPWRECK_117  = new BedrockStructureConfig( 165745295,  10,   5, BedrockStructureType.SHIPWRECK,        DIM_OVERWORLD, triangular);
        BedrockStructureConfig SHIPWRECK      = new BedrockStructureConfig( 165745295,  24,  20, BedrockStructureType.SHIPWRECK,        DIM_OVERWORLD, linear);
        BedrockStructureConfig STRONGHOLD     = new BedrockStructureConfig(  97858791, 150, 200, BedrockStructureType.STRONGHOLD,       DIM_OVERWORLD, shiftedLinear);
        BedrockStructureConfig MONUMENT       = new BedrockStructureConfig(  10387313,  32,  27, BedrockStructureType.OCEAN_MONUMENT,   DIM_OVERWORLD, triangular);
        BedrockStructureConfig MANSION        = new BedrockStructureConfig(  10387319,  80,  60, BedrockStructureType.WOODLAND_MANSION, DIM_OVERWORLD, triangular);
        BedrockStructureConfig RUINED_PORTAL  = new BedrockStructureConfig(  40552231,  40,  25, BedrockStructureType.RUINED_PORTAL_O,  DIM_OVERWORLD, linear);
        BedrockStructureConfig ANCIENT_CITY   = new BedrockStructureConfig(  20083232,  24,  16, BedrockStructureType.ANCIENT_CITY,     DIM_OVERWORLD, triangular);
        BedrockStructureConfig TRAIL_RUINS    = new BedrockStructureConfig(  83469867,  34,   8, BedrockStructureType.TRAIL_RUINS,      DIM_OVERWORLD, linear);
        BedrockStructureConfig TRIAL_CHAMBERS = new BedrockStructureConfig(  94251327,  34,  12, BedrockStructureType.TRIAL_CHAMBERS,   DIM_OVERWORLD, linear);
        BedrockStructureConfig TREASURE       = new BedrockStructureConfig(  16842397,   4,   2, BedrockStructureType.BURIED_TREASURE,  DIM_OVERWORLD, triangular);
        BedrockStructureConfig DESERT_WELL    = new BedrockStructureConfig(         0,   1,   1, BedrockStructureType.AMETHYST_GEODE,   DIM_OVERWORLD, 0);
        BedrockStructureConfig FOSSIL         = new BedrockStructureConfig(         0,   1,   1, BedrockStructureType.FOSSIL_O,         DIM_OVERWORLD, 0);
        BedrockStructureConfig GEODE_117      = new BedrockStructureConfig(         0,   1,   1, BedrockStructureType.AMETHYST_GEODE,   DIM_OVERWORLD, 0);
        BedrockStructureConfig GEODE          = new BedrockStructureConfig(         0,   1,   1, BedrockStructureType.AMETHYST_GEODE,   DIM_OVERWORLD, 0);
        BedrockStructureConfig MINESHAFT      = new BedrockStructureConfig(         0,   1,   1, BedrockStructureType.MINESHAFT,        DIM_OVERWORLD, 0);
        BedrockStructureConfig RAVINE         = new BedrockStructureConfig(         0,   1,   1, BedrockStructureType.RAVINE,           DIM_OVERWORLD, 0);

        /* Nether */
        final
        BedrockStructureConfig FORTRESS_1_14   = new BedrockStructureConfig(        0,  1,  1, BedrockStructureType.NETHER_FORTRESS, DIM_NETHER, 0);
        BedrockStructureConfig FORTRESS        = new BedrockStructureConfig( 30084232, 30, 26, BedrockStructureType.NETHER_FORTRESS, DIM_NETHER, linear);
        BedrockStructureConfig BASTION         = new BedrockStructureConfig( 30084232, 30, 26, BedrockStructureType.BASTION_REMNANT, DIM_NETHER, linear);
        BedrockStructureConfig RUINED_PORTAL_N = new BedrockStructureConfig( 40552231, 25, 15, BedrockStructureType.RUINED_PORTAL_N, DIM_NETHER, linear);

        /* End */
        final
        BedrockStructureConfig END_CITY         = new BedrockStructureConfig( 10387313, 20, 9, BedrockStructureType.END_CITY, DIM_END, triangular);

        return switch (structureType) {
            case BedrockStructureType.DESERT_PYRAMID   -> isAtLeast(version, MC_1_14)   ? DESERT_PYRAMID : null;
            case BedrockStructureType.IGLOO            -> isAtLeast(version, MC_1_14)   ? IGLOO : null;
            case BedrockStructureType.JUNGLE_TEMPLE    -> isAtLeast(version, MC_1_14)   ? JUNGLE_TEMPLE : null;
            case BedrockStructureType.SWAMP_HUT        -> isAtLeast(version, MC_1_14)   ? SWAMP_HUT : null;
            case BedrockStructureType.PILLAGER_OUTPOST -> isAtLeast(version, MC_1_14)   ? OUTPOST : null;
            case BedrockStructureType.VILLAGE          -> isAtLeast(version, MC_1_14)   ? (isAtMost(version, MC_1_17) ? VILLAGE_117 : VILLAGE) : null;
            case BedrockStructureType.OCEAN_RUINS      -> isAtLeast(version, MC_1_16)   ? (isAtMost(version, MC_1_17) ? OCEAN_RUIN_117 : OCEAN_RUIN) : null;
            case BedrockStructureType.SHIPWRECK        -> isAtLeast(version, MC_1_14)   ? (isAtMost(version, MC_1_17) ? SHIPWRECK_117 : SHIPWRECK) : null;
            case BedrockStructureType.STRONGHOLD       -> isAtLeast(version, MC_1_14)   ? STRONGHOLD : null;
            case BedrockStructureType.OCEAN_MONUMENT   -> isAtLeast(version, MC_1_14)   ? MONUMENT : null;
            case BedrockStructureType.WOODLAND_MANSION -> isAtLeast(version, MC_1_14)   ? MANSION : null;
            case BedrockStructureType.RUINED_PORTAL_O  -> isAtLeast(version, MC_1_14)   ? RUINED_PORTAL : null;
            case BedrockStructureType.ANCIENT_CITY     -> isAtLeast(version, MC_1_19_2) ? ANCIENT_CITY : null;
            case BedrockStructureType.TRAIL_RUINS      -> isAtLeast(version, MC_1_20)   ? TRAIL_RUINS : null;
            case BedrockStructureType.TRIAL_CHAMBERS   -> isAtLeast(version, MC_1_21)   ? TRIAL_CHAMBERS : null;
            case BedrockStructureType.BURIED_TREASURE  -> isAtLeast(version, MC_1_14)   ? TREASURE : null;
            case BedrockStructureType.DESERT_WELL      -> isAtLeast(version, MC_1_18)   ? DESERT_WELL : null;
            case BedrockStructureType.FOSSIL_O         -> isAtLeast(version, MC_1_16)   ? FOSSIL : null;
            case BedrockStructureType.AMETHYST_GEODE   -> isAtLeast(version, MC_1_17)   ? (isAtMost(version, MC_1_17) ? GEODE_117 : GEODE) : null;
            case BedrockStructureType.MINESHAFT        -> isAtLeast(version, MC_1_14)   ? MINESHAFT : null;
            case BedrockStructureType.RAVINE           -> isAtLeast(version, MC_1_14)   ? RAVINE : null;

            case BedrockStructureType.NETHER_FORTRESS  -> isAtLeast(version, MC_1_14)   ? (isAtLeast(version, MC_1_16) ? FORTRESS : FORTRESS_1_14) : null;
            case BedrockStructureType.BASTION_REMNANT  -> isAtLeast(version, MC_1_16)   ? BASTION : null;
            case BedrockStructureType.RUINED_PORTAL_N  -> isAtLeast(version, MC_1_14)   ? RUINED_PORTAL_N : null;

            case BedrockStructureType.END_CITY         -> isAtLeast(version, MC_1_14)   ? END_CITY : null;

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

    public int getStructureType() {
        return structureType;
    }

    public int getDimension() {
        return dimension;
    }

    public int getExtraInfo() {
        return extraInfo;
    }

}