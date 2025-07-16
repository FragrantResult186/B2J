package fragrant.b2j.noise;

public class SimplexNoise {
    private static final double S = 0.366025403784;
    private static final double U = 0.211324865405;

    public static double sample2D(double x, double y, int[] d) {
        double h = (x + y) * S;
        int hx = (int) Math.floor(x + h);
        int hz = (int) Math.floor(y + h);

        double m = (hx + hz) * U;
        double x0 = x - (hx - m), y0 = y - (hz - m);

        int ox = x0 > y0 ? 1 : 0, oz = 1 - ox;
        double x1 = x0 - ox + U, y1 = y0 - oz + U;
        double x2 = x0 - 1 + 2 * U, y2 = y0 - 1 + 2 * U;

        int g0 = d[0xff & (d[0xff & hz] + hx)];
        int g1 = d[0xff & (d[0xff & (hz + oz)] + hx + ox)];
        int g2 = d[0xff & (d[0xff & (hz + 1)] + hx + 1)];

        return 70 * (gradient(g0 % 12, x0, y0) + gradient(g1 % 12, x1, y1) + gradient(g2 % 12, x2, y2));
    }

    private static double gradient(int i, double x, double y) {
        double c = 0.5 - x * x - y * y;
        return c < 0 ? 0 : c * c * c * c * NoiseUtil.grad(i, x, y, 0);
    }
}
