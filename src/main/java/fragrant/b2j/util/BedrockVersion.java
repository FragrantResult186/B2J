package fragrant.b2j.util;

public class BedrockVersion {
    public static final int MC_1_0 = 100;
    public static final int MC_1_1 = 110;
    public static final int MC_1_2 = 120;
    public static final int MC_1_3 = 130;
    public static final int MC_1_4 = 140;
    public static final int MC_1_5 = 150;
    public static final int MC_1_6 = 160;
    public static final int MC_1_7 = 170;
    public static final int MC_1_8 = 180;
    public static final int MC_1_9 = 190;
    public static final int MC_1_10 = 1100;
    public static final int MC_1_11 = 1110;
    public static final int MC_1_12 = 1120;
    public static final int MC_1_13 = 1130;
    public static final int MC_1_14 = 1140;
    public static final int MC_1_15 = 1150;
    public static final int MC_1_16 = 1160;
    public static final int MC_1_17 = 1170;
    public static final int MC_1_18 = 1180;
    public static final int MC_1_19 = 1190;
    public static final int MC_1_19_2 = 1192;
    public static final int MC_1_20 = 1200;
    public static final int MC_1_21 = 1210;
    public static final int MC_1_21_1 = 1211;
    public static final int MC_1_21_5 = 1215;
    public static final int MC_1_21_6 = 1216;
    public static final int MC_1_21_7 = 1217;
    public static final int MC_1_21_8 = 1218;
    public static final int MC_1_21_9 = 1219;
    public static final int MC_1_22 = 1220;

    public static boolean isAtLeast(int version, int minVersion) {
        return version >= minVersion;
    }

    public static boolean isAtMost(int version, int maxVersion) {
        return version <= maxVersion;
    }

}