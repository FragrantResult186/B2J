package fragrant.b2j.biome;

import nl.jellejurre.biomesampler.BiomeSampler;
import nl.jellejurre.biomesampler.minecraft.Biome;
import nl.kallestruik.noisesampler.minecraft.Dimension;

import java.util.HashMap;
import java.util.Map;

public class BiomeCache {
    private final Map<Long, Biome> biomeCache = new HashMap<>();
    private final BiomeSampler overWorldSampler;
    private final BiomeSampler netherSampler;
    private final BiomeSampler endSampler;

    public BiomeCache(long worldSeed) {
        this.overWorldSampler = new BiomeSampler(worldSeed, Dimension.OVERWORLD);
        this.netherSampler = new BiomeSampler(worldSeed, Dimension.NETHER);
        this.endSampler = new BiomeSampler(worldSeed, Dimension.THEEND);
    }

    public Biome getBiomeAt(int x, int y, int z, Dimension dimension) {
        long key = encodePositionKey(x, y, z, dimension);

        if (biomeCache.containsKey(key)) {
            return biomeCache.get(key);
        }

        Biome biome = switch (dimension) {
            case OVERWORLD -> overWorldSampler.getBiomeFromBlockPos(x, y, z);
            case NETHER -> netherSampler.getBiomeFromBlockPos(x, y, z);
            case THEEND -> endSampler.getBiomeFromBlockPos(x, y, z);
        };

        biomeCache.put(key, biome);
        return biome;
    }

    private long encodePositionKey(int x, int y, int z, Dimension dimension) {
        return ((long)x & 0x3FFFFF) |
               (((long)z & 0x3FFFFF) << 22) |
               (((long)y & 0xFFF) << 44) |
               (((long)dimension.ordinal() & 0xF) << 56);
    }

    public void clearCache() {
        biomeCache.clear();
    }
}