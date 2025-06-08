package fragrant.b2j.finder;

import fragrant.b2j.feature.BedrockFeatureHash;

public class FeatureHash {
    public static void main(String[] args) {
        String[] featureNames = {
                "minecraft:overworld_amethyst_geode_feature",
                "minecraft:desert_after_surface_desert_well_feature",
                "minecraft:soulsand_valley_fossil_surface_feature",
                "minecraft:desert_or_swamp_after_surface_fossil_feature",
                "minecraft:desert_or_swamp_after_surface_fossil_deepslate_feature",
                "minecraft:overworld_after_surface_pumpkin_feature_rules",
                "minecraft:taiga_after_surface_sweet_berry_bush_feature_rules",
                "minecraft:cold_taiga_after_surface_sweet_berry_bush_feature_rules"
        };
        for (String featureName : featureNames) {
            int hash = BedrockFeatureHash.getFeatureHash(featureName);
            System.out.printf("public static final int %s = %s;\n", featureName, hash);
        }
    }

}