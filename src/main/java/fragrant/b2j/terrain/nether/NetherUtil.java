package fragrant.b2j.terrain.nether;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

public class NetherUtil {

    private static final Map<Integer, String> BIOME_NAMES = Map.of(
            8, "nether_wastes",
            178, "soul_sand_valley",
            179, "crimson_forest",
            180, "warped_forest",
            181, "basalt_deltas"
    );

    public static int getBiome(long seed, int x, int z) {
        return new NetherGenerator.Generator(seed).getNetherBiome(x, z);
    }

    public static String getBiomeName(long seed, int x, int z) {
        return BIOME_NAMES.getOrDefault(getBiome(seed, x, z), "Unknown");
    }

    public static String getBiomeName(int biomeId) {
        return BIOME_NAMES.getOrDefault(biomeId, "Unknown");
    }

    public static int[][] getBiomeMap(long seed, int minX, int minZ, int maxX, int maxZ) {
        int width = maxX - minX + 1;
        int height = maxZ - minZ + 1;
        int[][] biomeMap = new int[height][width];

        IntStream.range(0, height).parallel().forEach(zIndex -> {
            int z = minZ + zIndex;
            for (int x = minX; x <= maxX; x++) {
                biomeMap[zIndex][x - minX] = getBiome(seed, x, z);
            }
        });
        return biomeMap;
    }

    public static int[][] getChunkBiomeMap(long seed, int chunkX, int chunkZ) {
        int[][] biomeMap = new int[16][16];
        int bX = chunkX << 4;
        int bZ = chunkZ << 4;

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                biomeMap[x][z] = getBiome(seed, bX + x, bZ + z);
            }
        }
        return biomeMap;
    }

    public static Map<String, int[][]> getChunkBiomeMap(long seed, int minChunkX, int minChunkZ, int maxChunkX, int maxChunkZ) {
        Map<String, int[][]> results = new ConcurrentHashMap<>();

        IntStream.rangeClosed(minChunkZ, maxChunkZ).parallel().forEach(cZ -> {
            for (int cX = minChunkX; cX <= maxChunkX; cX++) {
                String key = cX + "," + cZ;
                int[][] biomeMap = getChunkBiomeMap(seed, cX, cZ);
                results.put(key, biomeMap);
            }
        });
        return results;
    }
}