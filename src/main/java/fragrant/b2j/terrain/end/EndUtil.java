package fragrant.b2j.terrain.end;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class EndUtil {

    public static int getSurfaceHeight(long seed, int x, int z) {
        EndGenerator gen = new EndGenerator(seed);
        for (int y = 80; y >= 0; y--) {
            double density = gen.getDensity(x, y, z);
            if (Double.isNaN(density)) return 0;
            if (density > 0) return y;
        }
        return 0;
    }

    public static double getDensity(long seed, int x, int y, int z) {
        return new EndGenerator(seed).getDensity(x, y, z);
    }

    public static boolean isAir(long seed, int x, int y, int z) {
        return getDensity(seed, x, y, z) <= 0;
    }

    public static boolean isEndStone(long seed, int x, int y, int z) {
        return getDensity(seed, x, y, z) > 0;
    }

    public static int[][] getHeightMap(long seed, int minX, int minZ, int maxX, int maxZ) {
        int width = maxX - minX + 1, height = maxZ - minZ + 1;
        int[][] heightMap = new int[height][width];

        IntStream.range(0, height).parallel().forEach(zIndex -> {
            int z = minZ + zIndex;
            for (int x = minX; x <= maxX; x++) {
                heightMap[zIndex][x - minX] = getSurfaceHeight(seed, x, z);
            }
        });
        return heightMap;
    }

    public static int[][] getChunkHeightMap(long seed, int chunkX, int chunkZ) {
        int[][] heightMap = new int[16][16];
        int baseX = chunkX << 4, baseZ = chunkZ << 4;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                heightMap[x][z] = getSurfaceHeight(seed, baseX + x, baseZ + z);
            }
        }
        return heightMap;
    }

    public static boolean[][][] getChunkEndstoneMap(long seed, int chunkX, int chunkZ) {
        return new EndGenerator(seed).getChunkEndstoneMap(chunkX, chunkZ);
    }

    public static Map<String, boolean[][][]> getChunkEndstoneMap(long seed, int minChunkX, int minChunkZ, int maxChunkX, int maxChunkZ) {
        Map<String, boolean[][][]> results = new ConcurrentHashMap<>();

        IntStream.rangeClosed(minChunkZ, maxChunkZ).parallel().forEach(cZ -> {
            for (int cX = minChunkX; cX <= maxChunkX; cX++) {
                String key = cX + "," + cZ;
                results.put(key, getChunkEndstoneMap(seed, cX, cZ));
            }
        });
        return results;
    }
}