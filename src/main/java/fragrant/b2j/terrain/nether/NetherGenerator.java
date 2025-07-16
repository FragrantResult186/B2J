package fragrant.b2j.terrain.nether;

import fragrant.b2j.util.random.BedrockRandom;

public class NetherGenerator {
    public enum NetherBiome {
        NETHER_WASTES(8, "Nether Wastes"),
        SOUL_SAND_VALLEY(178, "Soul Sand Valley"),
        CRIMSON_FOREST(179, "Crimson Forest"),
        WARPED_FOREST(180, "Warped Forest"),
        BASALT_DELTAS(181, "Basalt Deltas");

        final int id;
        final String name;

        NetherBiome(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }

    public static class Generator {
        private final NetherNoise noise;
        private final float[][] biomePoints = {
                { 0.0f, 0.0f, 0.0f, NetherBiome.NETHER_WASTES.id},
                { 0.0f,-0.5f, 0.0f, NetherBiome.SOUL_SAND_VALLEY.id},
                { 0.4f, 0.0f, 0.0f, NetherBiome.CRIMSON_FOREST.id},
                { 0.0f, 0.5f, 0.141f, NetherBiome.WARPED_FOREST.id},
                {-0.5f, 0.0f, 0.031f, NetherBiome.BASALT_DELTAS.id}
        };

        public Generator(long seed) {
            noise = new NetherNoise(seed);
        }

        public int getNetherBiome(int x, int z) {
            x >>= 2;
            z >>= 2;

            float t = (float) noise.temperature.sample(x, 0, z);
            float h = (float) noise.humidity.sample(x, 0, z);

            int bIdx = 0;
            float mind = Float.MAX_VALUE;

            for (int i = 0; i < biomePoints.length; i++) {
                float dx = biomePoints[i][0] - t;
                float dy = biomePoints[i][1] - h;
                float d = dx * dx + dy * dy + biomePoints[i][2];
                if (d < mind) {
                    mind = d;
                    bIdx = i;
                }
            }
            return (int) biomePoints[bIdx][3];
        }
    }


    static class NetherNoise {
        final DoublePerlin temperature, humidity;

        NetherNoise(long seed) {
            BedrockRandom mt = new BedrockRandom();
            mt.setSeed(seed);
            temperature = new DoublePerlin(mt, -7, 2);
            mt.setSeed(seed + 1);
            humidity = new DoublePerlin(mt, -7, 2);
        }
    }

    static class DoublePerlin {
        final double amplitude;
        final Octave octA, octB;

        DoublePerlin(BedrockRandom mt, int omin, int len) {
            amplitude = (10.0 / 6.0) * (len + 1) / (len + 2);
            octA = new Octave(mt, omin, len);
            octB = new Octave(mt, omin, len);
        }

        double sample(double x, double y, double z) {
            double f = 337.0 / 331.0;
            return (octA.sample(x, y, z) + octB.sample(x * f, y * f, z * f)) * amplitude;
        }
    }

    static class Octave {
        final Perlin[] octaves;

        Octave(BedrockRandom mt, int omin, int len) {
            octaves = new Perlin[len];
            double persi = 1.0 / ((1L << len) - 1.0);
            double lac = Math.pow(2.0, omin + len - 1);

            for (int i = 0; i < len; i++) {
                octaves[i] = new Perlin(mt, persi, lac);
                persi *= 2.0;
                lac *= 0.5;
            }
        }

        double sample(double x, double y, double z) {
            double sum = 0;
            for (Perlin p : octaves) sum += p.sample(x, y, z);
            return sum;
        }
    }

    static class Perlin {
        final double a, b, c, amplitude, lacunarity;
        final int[] d = new int[257];

        Perlin(BedrockRandom mt, double amplitude, double lacunarity) {
            this.amplitude = amplitude;
            this.lacunarity = lacunarity;
            a = mt.nextFloat() * 256;
            b = mt.nextFloat() * 256;
            c = mt.nextFloat() * 256;

            for (int i = 0; i < 256; i++) d[i] = i;
            for (int i = 0; i < 256; i++) {
                int j = mt.nextInt(256 - i) + i;
                int t = d[i];
                d[i] = d[j];
                d[j] = t;
            }
            d[256] = d[0];
        }

        double sample(double x, double y, double z) {
            x = x * lacunarity + a;
            y = y * lacunarity + b;
            z = z * lacunarity + c;

            int ix = (int) Math.floor(x);
            int iy = (int) Math.floor(y);
            int iz = (int) Math.floor(z);

            double fx = x - ix;
            double fy = y - iy;
            double fz = z - iz;

            double u = fade(fx);
            double v = fade(fy);
            double w = fade(fz);

            int a = d[ix & 255] + iy;
            int b = d[(ix + 1) & 255] + iy;
            int aa = d[a & 255] + iz;
            int ab = d[(a + 1) & 255] + iz;
            int ba = d[b & 255] + iz;
            int bb = d[(b + 1) & 255] + iz;

            int aa_ = d[aa & 255];
            int ba_ = d[ba & 255];
            int ab_ = d[ab & 255];
            int bb_ = d[bb & 255];
            int aa1 = d[(aa + 1) & 255];
            int ba1 = d[(ba + 1) & 255];
            int ab1 = d[(ab + 1) & 255];
            int bb1 = d[(bb + 1) & 255];

            double g000 = grad(aa_, fx, fy, fz);
            double g100 = grad(ba_, fx - 1, fy, fz);
            double g010 = grad(ab_, fx, fy - 1, fz);
            double g110 = grad(bb_, fx - 1, fy - 1, fz);

            double g001 = grad(aa1, fx, fy, fz - 1);
            double g101 = grad(ba1, fx - 1, fy, fz - 1);
            double g011 = grad(ab1, fx, fy - 1, fz - 1);
            double g111 = grad(bb1, fx - 1, fy - 1, fz - 1);

            double lX00 = lerp(u, g000, g100);
            double lX10 = lerp(u, g010, g110);
            double lY0 = lerp(v, lX00, lX10);

            double lX01 = lerp(u, g001, g101);
            double lX11 = lerp(u, g011, g111);
            double lY1 = lerp(v, lX01, lX11);

            double res = lerp(w, lY0, lY1);
            return amplitude * res;
        }

        private double fade(double t) {
            return t * t * t * (t * (t * 6 - 15) + 10);
        }

        private double grad(int hash, double x, double y, double z) {
            return switch (hash & 15) {
                case 0  ->  x + y;
                case 1  -> -x + y;
                case 2  ->  x - y;
                case 3  -> -x - y;
                case 4  ->  x + z;
                case 5  -> -x + z;
                case 6  ->  x - z;
                case 7  -> -x - z;
                case 8  ->  y + z;
                case 9  -> -y + z;
                case 10 ->  y - z;
                case 11 -> -y - z;
                case 12 ->  x + y;
                case 13 -> -y + z;
                case 14 -> -x + y;
                case 15 -> -y - z;
                default -> 0;
            };
        }

        private double lerp(double t, double a, double b) {
            return a + t * (b - a);
        }
    }
}