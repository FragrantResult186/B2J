package fragrant.b2j.finder;

import fragrant.b2j.feature.BedrockFeature;
import fragrant.b2j.feature.BedrockFeatureType;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

import java.util.ArrayList;
import java.util.List;

public class QuadPumpkin {
    public static void main(String[] args) {
        long startSeed = 470028754L;
        int cnt = 20;
        int version = BedrockVersion.MC_1_21;

        int[][] PumpkinRegions = {
                {0, 0},
                {0, -1},
                {-1, 0},
                {-1, -1}
        };

        List<Long> foundSeeds = new ArrayList<>();
        int i = 0;
        for (long seed = startSeed; seed < 1L << 32; seed++) {
            boolean allChunksHavePumpkin = true;

            for (int[] chunk : PumpkinRegions) {
                int chunkX = chunk[0];
                int chunkZ = chunk[1];

                FeaturePos pumpkinPos = BedrockFeature.isFeatureChunk(
                        BedrockFeatureType.PUMPKIN,
                        version,
                        seed,
                        chunkX,
                        chunkZ
                );

                if (pumpkinPos == null) {
                    allChunksHavePumpkin = false;
                    break;
                }
            }

            if (allChunksHavePumpkin) {
                foundSeeds.add(seed);
                System.out.printf("%d\n", seed);
                for (int[] chunk : PumpkinRegions) {
                    int chunkX = chunk[0];
                    int chunkZ = chunk[1];
                    FeaturePos pumpkinPos = BedrockFeature.isFeatureChunk(
                            BedrockFeatureType.PUMPKIN,
                            version,
                            seed,
                            chunkX,
                            chunkZ
                    );
                    System.out.printf("%s\n", pumpkinPos);
                }
                i++;
                if (i >= cnt) break;
            }
        }
    }

}