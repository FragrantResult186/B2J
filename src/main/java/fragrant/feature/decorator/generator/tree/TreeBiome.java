package fragrant.feature.decorator.generator.tree;

import fragrant.core.version.MCVersion;

public enum TreeBiome {
    FOREST(1252578407, 950578814),

    TAIGA(-1287000888, 941134635),

    MEGA_TAIGA(-1472720567, 396232936), // old_growth_pine_taiga

    SAVANNA(1981121442, -67619239),

    BIRCH_FOREST(1715764318, 1402234087),

    BIRCH_FOREST_MUTATED(905040449, -435207916),

    DARK_FOREST(-1984702064, 2042066867),

    PLAINS(-1858163805, 2087843378),

    SUNFLOWER_PLAINS(-1858163805, 2087843378),

    ICE_PLAINS(772916719, 2053231556), // snowy_plains

    FLOWER_FOREST(-1070913571, -376370362),

    JUNGLE(-533794537, 1997992150),

    MESA_PLATEAU_STONE(643275015, -1510604518), // wooded_badlands

    EXTREME_HILLS_PLUS_TREES(-1755800138, -683112361), // windswept_forest

    GROVE(1252578407, 950578814);


    private final int oldSalt;
    private final int newSalt;

    TreeBiome(int oldSalt, int newSalt) {
        this.oldSalt = oldSalt;
        this.newSalt = newSalt;
    }

    public int getSalt(MCVersion version) {
        if (version.isNewerOrEqualTo(MCVersion.v1_13_0)) {
            return getNewSalt();
        } else {
            return getOldSalt();
        }
    }

    public int getOldSalt() {
        return oldSalt;
    }

    public int getNewSalt() {
        return newSalt;
    }

    public static int getFeatureSalt(MCVersion version, TreeBiome biome) {
        return biome.getSalt(version);
    }
}