package fragrant.b2j.noise;

import fragrant.b2j.util.random.BedrockRandom;

public class PerlinNoise {
    public double a, b, c, d2, t2;
    public int[] d = new int[257];
    public int h2;

    public PerlinNoise(BedrockRandom mt) {
        a = mt.nextFloat() * 256;
        b = mt.nextFloat() * 256;
        c = mt.nextFloat() * 256;

        for (int i = 0; i < 256; i++) d[i] = i;
        for (int i = 0; i < 256; i++) {
            int j = mt.nextInt(256 - i) + i;
            int t = d[i]; d[i] = d[j]; d[j] = t;
        }
        d[256] = d[0];

        double i2 = Math.floor(b);
        d2 = b - i2;
        h2 = (int) i2;
        t2 = d2 * d2 * d2 * (d2 * (d2 * 6 - 15) + 10);
    }

    public double sample(double d1, double d2, double d3, double ya, double ym) {
        int h1, h2, h3;
        double t1, t2, t3;

        if (d2 == 0) {
            d2 = this.d2;
            h2 = this.h2;
            t2 = this.t2;
        } else {
            d2 += this.b;
            double i2 = Math.floor(d2);
            d2 -= i2;
            h2 = (int) i2;
            t2 = d2 * d2 * d2 * (d2 * (d2 * 6 - 15) + 10);
        }

        d1 += this.a;
        d3 += this.c;
        double i1 = Math.floor(d1), i3 = Math.floor(d3);
        d1 -= i1;
        d3 -= i3;
        h1 = (int) i1;
        h3 = (int) i3;
        t1 = d1 * d1 * d1 * (d1 * (d1 * 6 - 15) + 10);
        t3 = d3 * d3 * d3 * (d3 * (d3 * 6 - 15) + 10);

        if (ya != 0) {
            d2 -= Math.floor(Math.min(ym, d2) / ya) * ya;
        }

        int[] d = this.d;
        int a1 = d[h1 & 0xff] + h2, b1 = d[(h1 + 1) & 0xff] + h2;
        int a2 = d[a1 & 0xff] + h3, b2 = d[b1 & 0xff] + h3;
        int a3 = d[(a1 + 1) & 0xff] + h3, b3 = d[(b1 + 1) & 0xff] + h3;

        double l1 = NoiseUtil.grad(d[a2 & 0xff], d1, d2, d3);
        double l2 = NoiseUtil.grad(d[b2 & 0xff], d1 - 1, d2, d3);
        double l3 = NoiseUtil.grad(d[a3 & 0xff], d1, d2 - 1, d3);
        double l4 = NoiseUtil.grad(d[b3 & 0xff], d1 - 1, d2 - 1, d3);
        double l5 = NoiseUtil.grad(d[(a2 + 1) & 0xff], d1, d2, d3 - 1);
        double l6 = NoiseUtil.grad(d[(b2 + 1) & 0xff], d1 - 1, d2, d3 - 1);
        double l7 = NoiseUtil.grad(d[(a3 + 1) & 0xff], d1, d2 - 1, d3 - 1);
        double l8 = NoiseUtil.grad(d[(b3 + 1) & 0xff], d1 - 1, d2 - 1, d3 - 1);

        return NoiseUtil.lerp(t3, NoiseUtil.lerp(t2, NoiseUtil.lerp(t1, l1, l2), NoiseUtil.lerp(t1, l3, l4)), NoiseUtil.lerp(t2, NoiseUtil.lerp(t1, l5, l6), NoiseUtil.lerp(t1, l7, l8)));
    }
}

