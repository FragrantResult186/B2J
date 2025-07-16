package fragrant.b2j.finder;

import fragrant.b2j.worldfeature.BedrockFeature;
import fragrant.b2j.worldfeature.BedrockFeatureType;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

public class LavaLake00 {
    public static void main(String[] args) {
        long seed = 0;
        int[][] tag = {
                {-1,-1},
//                { 0,-1},
//                {-1, 0},
                { 0, 0}
        };
        int found = 0;
        while (found < 10) {
            boolean ok = true;
            for (int[] t : tag) {
                FeaturePos p = BedrockFeature.getBedrockFeaturePos(
                        BedrockFeatureType.LAVA_LAKE, BedrockVersion.MC_1_21_8,
                        seed, t[0], t[1], true
                );
                if (p == null || p.getY() > 61) {
                    ok = false;
                    break;
                }
                int dx = Math.abs(p.getX());
                int dz = Math.abs(p.getZ());
                if (dx > 9 || dz > 9) {
                    ok = false;
                    break;
                }
            }
            if (ok) {
                System.out.println(seed);
                found++;
            }
            seed++;
        }
    }
}
