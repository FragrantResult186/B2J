package fragrant.b2j.feature;

public class BedrockFeatureType {
    /* Overworld */
    public static final int ANCIENT_CITY = 1;
    public static final int DESERT_PYRAMID = 2;
    public static final int IGLOO = 3;
    public static final int JUNGLE_TEMPLE = 4;
    public static final int SWAMP_HUT = 5;
    public static final int WOODLAND_MANSION = 6;
    public static final int MINESHAFT = 7;
    public static final int OCEAN_MONUMENT = 8;
    public static final int OCEAN_RUINS = 9;
    public static final int PILLAGER_OUTPOST = 10;
    public static final int SHIPWRECK = 11;
    public static final int TRAIL_RUINS = 12;
    public static final int TRIAL_CHAMBERS = 13;
    public static final int VILLAGE = 14;
    public static final int BURIED_TREASURE = 15;
    public static final int RUINED_PORTAL_O = 16;
    public static final int VILLAGE_STRONGHOLD = 17;
    public static final int STATIC_STRONGHOLD = 18;

    /* Nether */
    public static final int BASTION_REMNANT = 100;
    public static final int NETHER_FORTRESS = 101;
    public static final int RUINED_PORTAL_N = 102;

    /* End */
    public static final int END_CITY = 200;

    /* Decorator */
    public static final int AMETHYST_GEODE = 300;
    public static final int DESERT_WELL = 301;
    public static final int FOSSIL_O = 302;
    public static final int FOSSIL_N = 303;
    public static final int PUMPKIN = 304;
    public static final int RAVINE = 305;
    public static final int SWEET_BERRY = 306;

    public static String toString(int type) {
        return switch (type) {
            case ANCIENT_CITY       -> "Ancient City";
            case DESERT_PYRAMID     -> "Desert Pyramid";
            case IGLOO              -> "Igloo";
            case JUNGLE_TEMPLE      -> "Jungle Temple";
            case SWAMP_HUT          -> "Swamp Hut";
            case WOODLAND_MANSION   -> "Woodland Mansion";
            case MINESHAFT          -> "Mineshaft";
            case OCEAN_MONUMENT     -> "Ocean Monument";
            case OCEAN_RUINS        -> "Ocean Ruins";
            case PILLAGER_OUTPOST   -> "Pillager Outpost";
            case SHIPWRECK          -> "Shipwreck";
            case TRAIL_RUINS        -> "Trail Ruins";
            case TRIAL_CHAMBERS     -> "Trial Chambers";
            case VILLAGE            -> "Village";
            case BURIED_TREASURE    -> "Buried Treasure";
            case RUINED_PORTAL_O    -> "Overworld Ruined Portal";
            case VILLAGE_STRONGHOLD -> "Village Stronghold";
            case STATIC_STRONGHOLD  -> "Static Stronghold";

            case BASTION_REMNANT    -> "Bastion Remnant";
            case NETHER_FORTRESS    -> "Nether Fortress";
            case RUINED_PORTAL_N    -> "Nether Ruined Portal";

            case END_CITY           -> "End City";

            case AMETHYST_GEODE     -> "Amethyst Geode";
            case DESERT_WELL        -> "Desert Well";
            case FOSSIL_O           -> "Overworld Fossil";
            case FOSSIL_N           -> "Nether Fossil";
            case PUMPKIN            -> "Pumpkin";
            case RAVINE             -> "Ravine";
            case SWEET_BERRY        -> "Sweet Berry";

            default -> "Unknown Structure:" + type;
        };
    }

}