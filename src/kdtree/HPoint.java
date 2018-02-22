package kdtree;
import java.util.Arrays;

/**
 * Hyper point (can be of more than 2 dimensions)
 */
public class HPoint
{

    private final double[] m_coordinates;

    protected HPoint(int n)
    {
        m_coordinates = new double[n];
    }

    protected HPoint(double[] x)
    {

        m_coordinates = new double[x.length];
        
        for (int i = 0; i < x.length; ++i)
        {
            m_coordinates[i] = x[i];
        }
    }

    /**
     * Copy constructor
     * 
     * @param copy
     *          the HPoint to copy
     */
    public static HPoint getHPoint(HPoint copy)
    {
        return new HPoint(copy.getCoordinates());
    }

    protected static double sqrdist(HPoint x, HPoint y)
    {
        return EuclideanDistance.sqrdist(x.m_coordinates, y.m_coordinates);
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + Arrays.hashCode(m_coordinates);
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (!(obj instanceof HPoint))
        {
            return false;
        }
        HPoint other = (HPoint) obj;
        if (!Arrays.equals(m_coordinates, other.m_coordinates))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "HPoint [" + Arrays.toString(m_coordinates) + "]";
    }

    public double[] getCoordinates()
    {
        return m_coordinates;
    }

}
