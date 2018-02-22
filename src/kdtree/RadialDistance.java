package kdtree;
/**
 * A Radial distance metric.
 * 
 * @author <a href="mailto:vnamuduri@expedia.com">Veeru Namuduri</a>
 */
class RadialDistance implements DistanceMetric
{
    /**
     * This defines the distance between 2 sets of Geo Points.
     * <ul>
     * distance cannot be negative
     * </ul>
     * <ul>
     * distance should be zero if the 2 points are exactly the same or within the range of approximation
     * </ul>
     * <ul>
     * distance is symmetric.
     * </ul>
     * <ul>
     * distance satisfies triangle inequality property.
     * </ul>
     * 
     * @param a
     *              a from geo point
     * @param b
     *              a to geo point
     * @return
     *              distance as a double value or a Double.NaN if the input is invalid instead of KeyMissingException
     *              
     */
    public double distance(double[] a, double[] b)
    {
        if (a.length != 2 && b.length != 2)
        {
            return Double.NaN;
        }
        GeoPoint from = new GeoPoint(a[0], a[1]);
        GeoPoint to = new GeoPoint(b[0], b[1]);
        
        return from.distanceFromInMeters(to);
    }
}
