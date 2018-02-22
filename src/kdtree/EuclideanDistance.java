package kdtree;

/**
 * A Euclidean distance metric implementation as given by <B>Pythagorean theorem</B>.
 */
class EuclideanDistance implements DistanceMetric
{
    @Override
    public double distance(double[] a, double[] b)
    {

        return Math.sqrt(sqrdist(a, b));

    }

    protected static double sqrdist(double[] a, double[] b)
    {

        double dist = 0;

        for (int i = 0; i < a.length; ++i)
        {
            double diff = (a[i] - b[i]);
            dist += diff * diff;
        }

        return dist;
    }
}
