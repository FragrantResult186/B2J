package fragrant.b2j.worldfeature.feature.end;

import fragrant.b2j.worldfeature.BedrockFeatureGenerator;
import fragrant.b2j.util.BedrockVersion;
import fragrant.b2j.util.position.FeaturePos;
import fragrant.b2j.util.random.BedrockRandom;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.Set;

public class EndIsland extends BedrockFeatureGenerator {

    public static FeaturePos getEndIsland(long worldSeed, int chunkX, int chunkZ, int version) {
        int seed = BedrockFeatureGenerator.chunkGenerateRnd(worldSeed, chunkX, chunkZ);
        BedrockRandom mt = new BedrockRandom(seed);

        if (Math.hypot(chunkX, chunkZ) >= 64L) {
            if (version >= BedrockVersion.MC_1_18) {
                mt.nextInt(); // Burn one 1.18+
            }
            /* 1/14 chance */
            if (mt.nextInt(14) == 0) {
                int x = chunkX * 16 + mt.nextInt(16) + 8;
                int y = mt.nextInt(16) + 55;
                int z = chunkZ * 16 + mt.nextInt(16) + 8;

                FeaturePos pos = new FeaturePos(x, y, z);
                Set<BlockPos> blocks = new LinkedHashSet<>();
                generate(pos, mt, blocks);
                /* 1/3 chance adding */
                if (mt.nextInt(3) == 0) {
                    generate(pos, mt, blocks);
                }
                pos.setMeta("blocks", new ArrayList<>(blocks));
                return pos;
            }
        }
        return null;
    }

    private static void generate(FeaturePos pos, BedrockRandom mt, Set<BlockPos> blocks) {
        float r = mt.nextInt(3) + 4.0f; // 4 ~ 6
        int bX = pos.getX(), bY = pos.getY(), bZ = pos.getZ();
        int depth = 0;

        while (r > 0.5f) {
            int rMin = (int) -r, rMax = (int) r;
            int lMin = rMin - 1, lStart = rMin - 1;
            if ((float) rMin <= -r) {
                lMin = rMin;
                lStart = rMin;
            }
            int lMax = (float) rMax < r ? rMax + 1 : rMax;
            for (int dx = lStart; dx <= lMax; dx++) {
                for (int dz = lMin; dz <= lMax; dz++) {
                    if ((dx * dx + dz * dz) <= (r + 1.0f) * (r + 1.0f)) {
                        blocks.add(new BlockPos(bX + dz, bY + depth, bZ + dx));
                    }
                }
            }
            depth--;
            r += -0.5f - mt.nextInt(2);
        }
    }

    public record BlockPos(int x, int y, int z) {
        @Override
        public String toString() {
            return String.format("blockPos{x=%d, y=%d, z=%d}", x, y, z);
        }
    }

    public static List<BlockPos> getBlocks(FeaturePos pos) {
        @SuppressWarnings("unchecked")
        List<BlockPos> blocks = (List<BlockPos>) pos.getMeta("blocks", List.class);
        return blocks != null ? blocks : new ArrayList<>();
    }

    public static int getHeight(FeaturePos pos) {
        @SuppressWarnings("unchecked")
        List<BlockPos> blocks = (List<BlockPos>) pos.getMeta("blocks", List.class);
        if (blocks == null || blocks.isEmpty()) return 0;
        int minY = blocks.get(0).y(), maxY = blocks.get(0).y();
        for (BlockPos block : blocks) {
            minY = Math.min(minY, block.y());
            maxY = Math.max(maxY, block.y());
        }
        return maxY - minY + 1;
    }

    public static String format(FeaturePos pos) {
        @SuppressWarnings("unchecked")
        List<BlockPos> blocks = (List<BlockPos>) pos.getMeta("blocks", List.class);
        return String.format("chunkPos{X=%d, Z=%d} (blocks=%d) /tp %d %d %d",
                pos.getX() >> 4,
                pos.getZ() >> 4,
                blocks != null ? blocks.size() : 0,
                pos.getX(),
                pos.getY(),
                pos.getZ()
        );
    }

}