package fragrant.core.util.math;

@FunctionalInterface
public interface DistanceMetric {

	DistanceMetric EUCLIDEAN_SQ = (x, y, z) -> x * x + y * y + z * z;
	DistanceMetric EUCLIDEAN = (x, y, z) -> Math.sqrt(EUCLIDEAN_SQ.getDistance(x, y, z));
	DistanceMetric MANHATTAN = (x, y, z) -> Math.abs(x) + Math.abs(y) + Math.abs(z);

	double getDistance(int x, int y, int z);

}
