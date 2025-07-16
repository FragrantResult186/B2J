package fragrant.b2j.finder;

import fragrant.b2j.worldfeature.feature.end.EndIsland;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class towerIsland {
    public static void main(String[] args) {
        int seed = new Random().nextInt();
        int version = BedrockVersion.MC_1_21;
        int minHeight = 9;
        int required = 100;

        while (true) {
            int count = 0;
            for (int cx = -10000; cx <= 10000; cx += 2) {
                for (int cz = -10000; cz <= 10000; cz += 2) {
                    List<FeaturePos> islands = new ArrayList<>();
                    for (int dx = 0; dx <= 1; dx++) {
                        for (int dz = 0; dz <= 1; dz++) {
                            FeaturePos pos = EndIsland.getEndIsland(seed, cx + dx, cz + dz, version);
                            if (pos != null) islands.add(pos);
                        }
                    }
                    if (islands.size() < 2) continue;

                    for (int i = 0; i < islands.size(); i++) {
                        for (int j = i + 1; j < islands.size(); j++) {
                            FeaturePos a = islands.get(i);
                            FeaturePos b = islands.get(j);
                            int heightA = EndIsland.getHeight(a);
                            int heightB = EndIsland.getHeight(b);

                            if (heightA < minHeight && heightB < minHeight) continue;
                            if (a.distXZ(b) > 4) continue;

                            int minYA = getMinY(a);
                            int maxYA = getMaxY(a);
                            int minYB = getMinY(b);
                            int maxYB = getMaxY(b);
                            boolean condition1 = (maxYA + 1 == minYB);
                            boolean condition2 = (maxYB + 1 == minYA);
                            if (condition1 || condition2) {
                                System.out.printf("%d\n", seed);
                                System.out.printf("/tp %d %d %d l=%d Y %d-%d\n", a.getX(), a.getY(), a.getZ(), heightA, minYA, maxYA);
                                System.out.printf("/tp %d %d %d l=%d Y %d-%d\n", b.getX(), b.getY(), b.getZ(), heightB, minYB, maxYB);
                                count++;
                                if (count >= required) return;
                            }
                        }
                    }
                }
            }
            seed++;
        }
    }

    private static int getMinY(FeaturePos pos) {
        @SuppressWarnings("unchecked")
        List<EndIsland.BlockPos> blocks = (List<EndIsland.BlockPos>) pos.getMeta("blocks", List.class);
        if (blocks == null || blocks.isEmpty()) {
            return pos.getY();
        }

        int minY = blocks.get(0).y();
        for (EndIsland.BlockPos block : blocks) {
            minY = Math.min(minY, block.y());
        }
        return minY;
    }

    private static int getMaxY(FeaturePos pos) {
        @SuppressWarnings("unchecked")
        List<EndIsland.BlockPos> blocks = (List<EndIsland.BlockPos>) pos.getMeta("blocks", List.class);
        if (blocks == null || blocks.isEmpty()) {
            return pos.getY();
        }

        int maxY = blocks.get(0).y();
        for (EndIsland.BlockPos block : blocks) {
            maxY = Math.max(maxY, block.y());
        }
        return maxY;
    }
}