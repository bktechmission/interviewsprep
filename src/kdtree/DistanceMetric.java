package kdtree;

/**
 * A distance metric interface.
 */
public interface DistanceMetric
{
    /**
     * This defines the distance between 2 sets of points.
     * <ul>
     * distance cannot be negative
     * </ul>
     * <ul>
     * distance should be zero if the 2 points are exactly the same or within the range of approximation
     * </ul>
     * <ul>
     * distance should be symmetric. If this condition cannot be satisfied, this distance metric should not be used to
     * construct a kd tree which assumes euclidean space, but only during kNN search.
     * </ul>
     * <ul>
     * distance should satisfy triangle inequality property. If this condition cannot be satisfied, this distance metric
     * should not be used to construct a kd tree which assumes euclidean space, but only during kNN search.
     * </ul>
     * 
     * @param a
     *              a from point
     * @param b
     *              a to point
     * @return
     *              distance as a double value
     */
    double distance(double[] a, double[] b);
}
