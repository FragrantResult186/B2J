package fragrant.b2j.noise;

public class NoiseUtil {
    public static double grad(int i, double x, double y, double z) {
        return switch (i & 15) {
            case 0  ->  x +y;
            case 1  -> -x +y;
            case 2  ->  x -y;
            case 3  -> -x -y;
            case 4  ->  x +z;
            case 5  -> -x +z;
            case 6  ->  x -z;
            case 7  -> -x -z;
            case 8  ->  y +z;
            case 9  -> -y +z;
            case 10 ->  y -z;
            case 11 -> -y -z;
            case 12 ->  x +y;
            case 13 -> -y +z;
            case 14 -> -x +y;
            case 15 -> -y -z;
            default -> 0;
        };
    }

    public static double lerp(double t, double a, double b) {
        return a + t * (b - a);
    }

    public static double lerp3D(double dx, double dy, double dz, double v000, double v100, double v010, double v110, double v001, double v101, double v011, double v111) {
        return lerp(dz, lerp(dy, lerp(dx, v000, v100), lerp(dx, v010, v110)), lerp(dy, lerp(dx, v001, v101), lerp(dx, v011, v111)));
    }
}
