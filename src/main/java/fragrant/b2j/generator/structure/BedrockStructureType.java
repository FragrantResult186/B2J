package fragrant.b2j.generator.structure;

public class BedrockStructureType {
    /* Overworld */
    public static final int ANCIENT_CITY = 1;
    public static final int DESERT_PYRAMID = 2;
    public static final int IGLOO = 3;
    public static final int JUNGLE_TEMPLE = 4;
    public static final int SWAMP_HUT = 5;
    public static final int WOODLAND_MANSION = 6;
    public static final int OCEAN_MONUMENT = 7;
    public static final int OCEAN_RUINS = 8;
    public static final int PILLAGER_OUTPOST = 9;
    public static final int SHIPWRECK = 10;
    public static final int MINESHAFT = 11;
    public static final int AMETHYST_GEODE = 12;
    public static final int DESERT_WELL = 13;
    public static final int FOSSIL_O = 14;
    public static final int TRAIL_RUINS = 15;
    public static final int TRIAL_CHAMBERS = 16;
    public static final int VILLAGE = 17;
    public static final int BURIED_TREASURE = 18;
    public static final int RUINED_PORTAL_O = 19;
    public static final int STRONGHOLD = 20;
    public static final int RAVINE = 21;
    /* Nether */
    public static final int BASTION_REMNANT = 22;
    public static final int NETHER_FORTRESS = 23;
    public static final int RUINED_PORTAL_N = 24;
    /* End */
    public static final int END_CITY = 26;

    public static String toString(int type) {
        return switch (type) {
            case ANCIENT_CITY     -> "Ancient City";
            case DESERT_PYRAMID   -> "Desert Pyramid";
            case IGLOO            -> "Igloo";
            case JUNGLE_TEMPLE    -> "Jungle Temple";
            case SWAMP_HUT        -> "Swamp Hut";
            case WOODLAND_MANSION -> "Woodland Mansion";
            case OCEAN_MONUMENT   -> "Ocean Monument";
            case OCEAN_RUINS      -> "Ocean Ruins";
            case PILLAGER_OUTPOST -> "Pillager Outpost";
            case SHIPWRECK        -> "Shipwreck";
            case MINESHAFT        -> "Mineshaft";
            case AMETHYST_GEODE   -> "Amethyst Geode";
            case DESERT_WELL      -> "Desert Well";
            case FOSSIL_O         -> "Overworld Fossil";
            case TRAIL_RUINS      -> "Trail Ruins";
            case TRIAL_CHAMBERS   -> "Trial Chambers";
            case VILLAGE          -> "Village";
            case BURIED_TREASURE  -> "Buried Treasure";
            case RUINED_PORTAL_O  -> "Overworld Ruined Portal";
            case STRONGHOLD       -> "Stronghold";
            case RAVINE           -> "Ravine";
            case BASTION_REMNANT  -> "Bastion Remnant";
            case NETHER_FORTRESS  -> "Nether Fortress";
            case RUINED_PORTAL_N  -> "Nether Ruined Portal";
            case END_CITY         -> "End City";
            default -> "Unknown Structure (" + type + ")";
        };
    }

}