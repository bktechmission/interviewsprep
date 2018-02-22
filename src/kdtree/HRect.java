package kdtree;

/**
 * Hyper Rectangle (can be of more than 2 dimensions)
 */
class HRect
{
    private final HPoint m_min;
    private final HPoint m_max;

    protected HRect(int ndims)
    {
        m_min = new HPoint(ndims);
        m_max = new HPoint(ndims);
    }

    protected HRect(HPoint vmin, HPoint vmax)
    {

        m_min = HPoint.getHPoint(vmin);
        m_max = HPoint.getHPoint(vmax);
    }

    public static HRect getHRect(HRect copy)
    {
        return new HRect(copy.getMin(), copy.getMax());
    }

    // from Moore's eqn. 6.6
    protected HPoint closest(HPoint t)
    {

        HPoint p = new HPoint(t.getCoordinates().length);

        for (int i = 0; i < t.getCoordinates().length; ++i)
        {
            if (t.getCoordinates()[i] <= m_min.getCoordinates()[i])
            {
                p.getCoordinates()[i] = m_min.getCoordinates()[i];
            }
            else if (t.getCoordinates()[i] >= m_max.getCoordinates()[i])
            {
                p.getCoordinates()[i] = m_max.getCoordinates()[i];
            }
            else
            {
                p.getCoordinates()[i] = t.getCoordinates()[i];
            }
        }

        return p;
    }

    // used in initial conditions of KDTree.nearest()
    protected static HRect infiniteHRect(int d)
    {

        HPoint vmin = new HPoint(d);
        HPoint vmax = new HPoint(d);

        for (int i = 0; i < d; ++i)
        {
            vmin.getCoordinates()[i] = Double.NEGATIVE_INFINITY;
            vmax.getCoordinates()[i] = Double.POSITIVE_INFINITY;
        }

        return new HRect(vmin, vmax);
    }

    // currently unused
    protected HRect intersection(HRect r)
    {

        HPoint newmin = new HPoint(m_min.getCoordinates().length);
        HPoint newmax = new HPoint(m_min.getCoordinates().length);

        for (int i = 0; i < m_min.getCoordinates().length; ++i)
        {
            newmin.getCoordinates()[i] = Math.max(m_min.getCoordinates()[i], r.m_min.getCoordinates()[i]);
            newmax.getCoordinates()[i] = Math.min(m_max.getCoordinates()[i], r.m_max.getCoordinates()[i]);
            if (newmin.getCoordinates()[i] >= newmax.getCoordinates()[i])
            {
                return null;
            }
        }

        return new HRect(newmin, newmax);
    }

    // currently unused
    protected double area()
    {

        double area = 1;

        for (int i = 0; i < m_min.getCoordinates().length; ++i)
        {
            area *= (m_max.getCoordinates()[i] - m_min.getCoordinates()[i]);
        }

        return area;
    }

    @Override
    public String toString()
    {
        return "HRect [min=" + m_min + ", max=" + m_max + "]";
    }

    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((m_max == null) ? 0 : m_max.hashCode());
        result = prime * result + ((m_min == null) ? 0 : m_min.hashCode());
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
        if (!(obj instanceof HRect))
        {
            return false;
        }
        HRect other = (HRect) obj;
        if (m_max == null)
        {
            if (other.m_max != null)
            {
                return false;
            }
        }
        else if (!m_max.equals(other.m_max))
        {
            return false;
        }
        if (m_min == null)
        {
            if (other.m_min != null)
            {
                return false;
            }
        }
        else if (!m_min.equals(other.m_min))
        {
            return false;
        }
        return true;
    }

    public HPoint getMin()
    {
        return m_min;
    }

    public HPoint getMax()
    {
        return m_max;
    }

}
