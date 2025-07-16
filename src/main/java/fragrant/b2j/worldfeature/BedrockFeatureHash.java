package fragrant.b2j.worldfeature;

/*
 * This constant is precomputed to avoid redundant repeated hashing
 */
public class BedrockFeatureHash {

    public static final int overworld_amethyst_geode_feature = 1974035328;
    public static final int desert_after_surface_desert_well_feature = -1160484816;
    public static final int soulsand_valley_fossil_surface_feature = -17526839;
    public static final int desert_or_swamp_after_surface_fossil_feature = 2116138073;
    public static final int desert_or_swamp_after_surface_fossil_deepslate_feature = 877307113;
    public static final int overworld_after_surface_pumpkin_feature_rules = 329866135;
    public static final int taiga_after_surface_sweet_berry_bush_feature_rules = 1367295741;
    public static final int cold_taiga_after_surface_sweet_berry_bush_feature_rules = -568373648;

    public static int getFeatureHash(String featureName) {
        int hash = -2078137563;
        for (int i = 0; i < featureName.length(); i++) {
            hash *= 435;
            hash ^= featureName.charAt(i);
        }
        return hash;
    }

    public static int getFeatureSeed(long worldSeed, int chunkX, int chunkZ, String featureName) {
        int seed = BedrockFeatureGenerator.chunkGenerateRnd(worldSeed, chunkX, chunkZ);
        int featureHash = getFeatureHash(featureName);
        return seed ^ (seed << 6) + (seed >>> 2) + featureHash - 1640531527;
    }

    public static int getFeatureSeed(long worldSeed, int chunkX, int chunkZ, int featureHash) {
        int seed = BedrockFeatureGenerator.chunkGenerateRnd(worldSeed, chunkX, chunkZ);
        return seed ^ (seed << 6) + (seed >>> 2) + featureHash - 1640531527;
    }

}
