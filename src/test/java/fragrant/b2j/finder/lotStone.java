package fragrant.b2j.finder;

import fragrant.b2j.worldfeature.feature.end.EndIsland;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

import java.util.List;

public class lotStone {

    public static void main(String[] args) {
        long seed = 1234567890L;
        int version = BedrockVersion.MC_1_16;

        int r = 10000;
        int minBlock = 771;
        int required = 100;

        int fnd = 0;
        for (int cx = -r; cx <= r; cx++) {
            for (int cz = -r; cz <= r; cz++) {
                FeaturePos pos = EndIsland.getEndIsland(seed, cx, cz, version);
                if (pos == null) continue;
                List<EndIsland.BlockPos> blocks = EndIsland.getBlocks(pos);
                if (blocks.size() >= minBlock) {
                    System.out.printf("%d %s\n", blocks.size(), EndIsland.format(pos));
                    fnd++;
                    if (fnd >= required) return;
                }
            }
        }
    }
}
