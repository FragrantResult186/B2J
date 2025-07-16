package fragrant.b2j.terrain.end;

import fragrant.b2j.noise.NoiseUtil;
import fragrant.b2j.noise.PerlinNoise;
import fragrant.b2j.noise.SimplexNoise;
import fragrant.b2j.util.random.BedrockRandom;

public class EndGenerator {
    private final PerlinNoise[] min = new PerlinNoise[16];
    private final PerlinNoise[] max = new PerlinNoise[16];
    private final PerlinNoise[] main = new PerlinNoise[8];
    private final int[] heightData;
    private final double[] up = {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,63./64,62./64,61./64,60./64,59./64,58./64,57./64,56./64,55./64,54./64,53./64,52./64,51./64,50./64,49./64,48./64,47./64,46./64};
    private final double[] lo = {0,0,1./7,2./7,3./7,4./7,5./7,6./7,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1};

    public EndGenerator(long seed) {
        BedrockRandom mt = new BedrockRandom(seed);
        mt.skipNextInt(17292);
        heightData = new PerlinNoise(mt).d;
        mt = new BedrockRandom(seed);

        for (int i = 0; i < 40; i++) {
            PerlinNoise p = new PerlinNoise(mt);
            if (i < 16) min[i] = p;
            else if (i < 32) max[i - 16] = p;
            else main[i - 32] = p;
        }
    }

    public double getDensity(int x, int y, int z) {
        int cx = x >> 3, cz = z >> 3, cy = y >> 2;
        double dx = (x & 7) * 0.125, dz = (z & 7) * 0.125, dy = (y & 3) * 0.25;

        double[] c = new double[8];
        sampleColumn(c, 0, cx, cz, cy);
        sampleColumn(c, 2, cx, cz + 1, cy);
        sampleColumn(c, 4, cx + 1, cz, cy);
        sampleColumn(c, 6, cx + 1, cz + 1, cy);

        return NoiseUtil.lerp3D(dy, dx, dz, c[0], c[1], c[4], c[5], c[2], c[3], c[6], c[7]);
    }

    public boolean[][][] getChunkEndstoneMap(int chunkX, int chunkZ) {
        boolean[][][] eMap = new boolean[81][16][16];
        int bX = chunkX << 4, bZ = chunkZ << 4;
        int nCX = bX >> 3, xCX = (bX + 15) >> 3, nCZ = bZ >> 3, xCZ = (bZ + 15) >> 3;
        int nCY = 0, xCY = 20;
        int cxRange = xCX - nCX + 2, czRange = xCZ - nCZ + 2, cyRange = xCY - nCY + 1;
        double[][][] sampVal = new double[cxRange][czRange][cyRange * 2];

        for (int cx = nCX; cx <= xCX + 1; cx++) {
            for (int cz = nCZ; cz <= xCZ + 1; cz++) {
                for (int cy = nCY; cy <= xCY; cy++) {
                    double[] temp = new double[2];
                    sampleColumn(temp, 0, cx, cz, cy);
                    sampVal[cx - nCX][cz - nCZ][cy * 2] = temp[0];
                    sampVal[cx - nCX][cz - nCZ][cy * 2 + 1] = temp[1];
                }
            }
        }

        for (int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                int hx = bX + x, hz = bZ + z;
                for (int y = 0; y <= 80; y++) {
                    int cx = hx >> 3, cz = hz >> 3, cy = y >> 2;
                    double dx = (hx & 7) * 0.125, dz = (hz & 7) * 0.125, dy = (y & 3) * 0.25;
                    int cxIdx = cx - nCX, czIdx = cz - nCZ, cyIdx = cy - nCY;

                    double[] c = {
                            sampVal[cxIdx][czIdx][cyIdx*2], sampVal[cxIdx][czIdx][cyIdx*2+1],
                            sampVal[cxIdx][czIdx+1][cyIdx*2], sampVal[cxIdx][czIdx+1][cyIdx*2+1],
                            sampVal[cxIdx+1][czIdx][cyIdx*2], sampVal[cxIdx+1][czIdx][cyIdx*2+1],
                            sampVal[cxIdx+1][czIdx+1][cyIdx*2], sampVal[cxIdx+1][czIdx+1][cyIdx*2+1]
                    };

                    double de = NoiseUtil.lerp3D(dy, dx, dz, c[0], c[1], c[4], c[5], c[2], c[3], c[6], c[7]);
                    eMap[y][x][z] = !Double.isNaN(de) && de > 0;
                }
            }
        }
        return eMap;
    }

    private void sampleColumn(double[] c, int idx, int x, int z, int y) {
        double d = generateHeight(x, z) - 8;
        for (int i = 0; i < 2; i++) {
            int yy = y + i;
            if (lo[yy] == 0) {
                c[idx + i] = -30;
            } else {
                double n = sampleSurface(x, yy, z, -128, 128) + d;
                n = NoiseUtil.lerp(up[yy], -3000, n);
                c[idx + i] = NoiseUtil.lerp(lo[yy], -30, n);
            }
        }
    }

    private double generateHeight(int x, int z) {
        int hx = x / 2, hz = z / 2, ox = x % 2, oz = z % 2;
        long h = 64L * (x * (long) x + z * (long) z);

        for (int j = -12; j <= 12; j++) {
            for (int i = -12; i <= 12; i++) {
                long rx = hx + i, rz = hz + j;
                if (rx * rx + rz * rz > 4096 && SimplexNoise.sample2D(rx, rz, heightData) < -0.9) {
                    int v = (int) ((Math.abs(rx) * 3439 + Math.abs(rz) * 147) % 13 + 9);
                    rx = ox - i * 2; rz = oz - j * 2;
                    long n = (rx * rx + rz * rz) * v * v;
                    if (n < h) h = n;
                }
            }
        }
        return Math.max(-100, Math.min(80, 100 - (float) Math.sqrt(h)));
    }

    private double sampleSurface(int x, int y, int z, double nmin, double nmax) {
        double xzs = 1368.824, ys = 684.412;
        double vmin = 0, vmax = 0, p = 1.0 / 32768, a = 64;

        for (int i = 15; i >= 0; i--, a *= 0.5, p *= 2) {
            double dx = x * xzs * p, dz = z * xzs * p, dy = y * ys * p;
            vmin += min[i].sample(dx, dy, dz, ys * p, dy) * a;
            vmax += max[i].sample(dx, dy, dz, ys * p, dy) * a;
            if (vmin - a > nmax && vmax - a > nmax) return nmax;
            if (vmin + a < nmin && vmax + a < nmin) return nmin;
        }

        double xs = 17.1103, yst = 4.27758, vm = 0.5;
        p = 1.0 / 128; a = 6.4;

        for (int i = 7; i >= 0; i--, a *= 0.5, p *= 2) {
            double dx = x * xs * p, dz = z * xs * p, dy = y * yst * p;
            vm += main[i].sample(dx, dy, dz, yst * p, dy) * a;
            if (vm - a > 1) return vmax;
            if (vm + a < 0) return vmin;
        }

        return vm <= 0 ? vmin : vm >= 1 ? vmax : vmin + vm * (vmax - vmin);
    }
}