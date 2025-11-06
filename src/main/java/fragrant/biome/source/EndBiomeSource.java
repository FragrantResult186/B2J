package fragrant.biome.source;

import fragrant.biome.biome.Biome;
import fragrant.biome.biome.Biomes;
import fragrant.biome.layer.BiomeLayer;
import fragrant.biome.layer.composite.VoronoiLayer;
import fragrant.biome.layer.end.EndBiomeLayer;
import fragrant.biome.layer.end.EndHeightLayer;
import fragrant.biome.layer.end.EndSimplexLayer;
import fragrant.core.util.pos.BPos;
import fragrant.core.state.Dimension;
import fragrant.core.version.MCVersion;
import fragrant.core.rand.BedrockRandom;
import fragrant.noise.perlin.PerlinNoiseSampler;

import static fragrant.noise.utils.MathHelper.*;

public class EndBiomeSource extends LayeredBiomeSource<BiomeLayer> {

    public EndSimplexLayer simplex;
    public EndHeightLayer height;
    public EndBiomeLayer full;
    public VoronoiLayer voronoi;

    private final PerlinNoiseSampler[] minOctaves = new PerlinNoiseSampler[16];
    private final PerlinNoiseSampler[] maxOctaves = new PerlinNoiseSampler[16];
    private final PerlinNoiseSampler[] mainOctaves = new PerlinNoiseSampler[8];

    public EndBiomeSource(MCVersion version, long worldSeed) {
        super(version, worldSeed);
        BedrockRandom rand = new BedrockRandom(worldSeed);
        initOctaves(minOctaves, rand, 0);
        initOctaves(maxOctaves, rand, 0);
        initOctaves(mainOctaves, rand, 0);
        this.build();
    }

    private void initOctaves(PerlinNoiseSampler[] octaves, BedrockRandom rand, int skip) {
        rand.skip(skip);
        for (int i = 0; i < octaves.length; i++) {
            octaves[i] = new PerlinNoiseSampler(rand);
        }
    }

    @Override
    public Dimension getDimension() {
        return Dimension.END;
    }

    protected void build() {
        this.layers.add(this.simplex = new EndSimplexLayer(this.getVersion(), this.getWorldSeed()));
        this.layers.add(this.height = new EndHeightLayer(this.getVersion(), this.simplex));
        this.layers.add(this.full = new EndBiomeLayer(this.getVersion(), this.height));
        this.layers.add(this.voronoi = new VoronoiLayer(this.getVersion(), this.getWorldSeed(), false, this.full) {
            @Override
            public int sample(int x, int y, int z) {
                return this.getVersion().isOlderThan(MCVersion.v1_13_0) ? Biomes.THE_END.getId() : super.sample(x, y, z);
            }
        });
        this.layers.setScales();
    }

    @Override
    public Biome getBiome(BPos bpos) {
        return Biomes.REGISTRY.get(this.voronoi.get(bpos.getX(), 0, bpos.getZ()));
    }

    @Override
    public Biome getBiome(int x, int y, int z) {
        return Biomes.REGISTRY.get(this.voronoi.get(x, 0, z));
    }

    public Biome getBiome3D(int x, int y, int z) {
        return Biomes.REGISTRY.get(this.voronoi.get(x, y, z));
    }

    @Override
    public Biome getBiomeForNoiseGen(int x, int y, int z) {
        return Biomes.REGISTRY.get(this.full.get(x, 0, z));
    }

//    public int getSurfaceHeight(int blockX, int centerZ) {
//        int chunkX = blockX >> 4;
//        int chunkZ = centerZ >> 4;
//        int localX = blockX & 15;
//        int localZ = centerZ & 15;
//
//        int[][] chunkHeights = generateChunkHeights(chunkX, chunkZ);
//        return chunkHeights[localX][localZ];
//    }
//
//    private int[][] generateChunkHeights(int chunkX, int chunkZ) {
//        double[][][] corners = new double[3][3][];
//
//        for (int i = 0; i < 3; i++) {
//            for (int j = 0; j < 3; j++) {
//                corners[i][j] = sampleColumn(chunkX * 2 + i, chunkZ * 2 + j);
//            }
//        }
//
//        int[][] heights = new int[16][16];
//
//        for (int blockX = 0; blockX < 16; blockX++) {
//            for (int centerZ = 0; centerZ < 16; centerZ++) {
//                int worldX = chunkX * 16 + blockX;
//                int worldZ = chunkZ * 16 + centerZ;
//
//                int sampleChunkX = worldX >> 3;
//                int sampleChunkZ = worldZ >> 3;
//
//                double dx = (worldX & 7) / 8.0;
//                double dz = (worldZ & 7) / 8.0;
//
//                int relativeX = sampleChunkX - (chunkX * 2);
//                int relativeZ = sampleChunkZ - (chunkZ * 2);
//
//                double[] c00 = corners[relativeX][relativeZ];
//                double[] c01 = corners[relativeX][relativeZ + 1];
//                double[] c10 = corners[relativeX + 1][relativeZ];
//                double[] c11 = corners[relativeX + 1][relativeZ + 1];
//
//                heights[blockX][centerZ] = getHeight(c00, c01, c10, c11, dx, dz);
//            }
//        }
//
//        return heights;
//    }
//
//    private double[] sampleColumn(int chunkX, int chunkZ) {
//        double[] column = new double[33];
//        double noiseValue = this.height.getNoiseValueAt(chunkX, chunkZ) - 8.0f;
//
//        double[] upperDensity = {
//                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
//                63.0/64, 62.0/64, 61.0/64, 60.0/64, 59.0/64, 58.0/64, 57.0/64, 56.0/64,
//                55.0/64, 54.0/64, 53.0/64, 52.0/64, 51.0/64, 50.0/64, 49.0/64, 48.0/64,
//                47.0/64, 46.0/64
//        };
//        double[] lowerDensity = {
//                0, 0, 1.0/7, 2.0/7, 3.0/7, 4.0/7, 5.0/7, 6.0/7,
//                1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
//        };
//
//        for (int y = 0; y <= 32; y++) {
//            if (lowerDensity[y] == 0) {
//                column[y] = -30;
//                continue;
//            }
//
//            double noise = sampleNoise(chunkX, y, chunkZ, -128, 128) + noiseValue;
//
//            if (upperDensity[y] != 1) {
//                noise = upperDensity[y] * noise + (1 - upperDensity[y]) * (-3000);
//            }
//
//            if (lowerDensity[y] != 1) {
//                noise = lowerDensity[y] * noise + (1 - lowerDensity[y]) * (-30);
//            }
//
//            column[y] = noise;
//        }
//
//        return column;
//    }
//
//    private double sampleNoise(int blockX, int y, int centerZ, double noiseMin, double noiseMax) {
//        double xzScale = 684.412 * 2;
//        double yScale = 684.412;
//        double minValue = 0;
//        double maxValue = 0;
//        double persistence = 1.0 / 32768;
//        double amplitude = 64;
//
//        for (int i = 15; i >= 0; i--, amplitude *= 0.5, persistence *= 2) {
//            double dx = blockX * xzScale * persistence;
//            double dz = centerZ * xzScale * persistence;
//            double sy = yScale * persistence;
//            double dy = y * sy;
//
//            minValue += minOctaves[i].sample(dx, dy, dz, sy, dy) * amplitude;
//            maxValue += maxOctaves[i].sample(dx, dy, dz, sy, dy) * amplitude;
//
//            if (minValue - amplitude > noiseMax && maxValue - amplitude > noiseMax) {
//                return noiseMax;
//            }
//            if (minValue + amplitude < noiseMin && maxValue + amplitude < noiseMin) {
//                return noiseMin;
//            }
//        }
//
//        double xScale = xzScale / 80;
//        double yScaleMain = yScale / 160;
//        double mainValue = 0.5;
//        persistence = 1.0 / 128;
//        amplitude = 0.05 * 128;
//
//        for (int i = 7; i >= 0; i--, amplitude *= 0.5, persistence *= 2) {
//            double dx = blockX * xScale * persistence;
//            double dz = centerZ * xScale * persistence;
//            double sy = yScaleMain * persistence;
//            double dy = y * sy;
//
//            mainValue += mainOctaves[i].sample(dx, dy, dz, sy, dy) * amplitude;
//
//            if (mainValue - amplitude > 1) return maxValue;
//            if (mainValue + amplitude < 0) return minValue;
//        }
//
//        if (mainValue <= 0) return minValue;
//        if (mainValue >= 1) return maxValue;
//        return minValue + mainValue * (maxValue - minValue);
//    }
//
//    private int getHeight(double[] c00, double[] c01, double[] c10, double[] c11, double dx, double dz) {
//        for (int chunkY = 31; chunkY >= 0; chunkY--) {
//            for (int y = 3; y >= 0; y--) {
//                double dy = y / 4.0;
//
//                double noise = lerp3(dy, dx, dz,
//                        c00[chunkY], c00[chunkY + 1], c10[chunkY], c10[chunkY + 1],
//                        c01[chunkY], c01[chunkY + 1], c11[chunkY], c11[chunkY + 1]);
//
//                if (noise > 0.0) {
//                    return chunkY * 4 + y;
//                }
//            }
//        }
//        return 0;
//    }

}