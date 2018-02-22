package kdtree;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.Format;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * Represents a geographical point by latitude and longitude.
 * 
 * @author <a href="mailto:vnamuduri@expedia.com">Veeru Namuduri</a>
 */
@XmlRootElement(namespace = XMLNamespace.URL)
@XmlAccessorType(XmlAccessType.NONE)
@XmlType(namespace = XMLNamespace.URL)
public final class GeoPoint implements Serializable
{

    /* Geocode format */
    private static final Format GEOCODE_FORMAT = new DecimalFormat("00.000000");
    
    /** Canonical invalid point. */
    public static final GeoPoint INVALID_POINT = new GeoPoint(1000.0, 1000.0);

    /** Earth's mean radius in miles. */
    // TODO use a Distance type
    private static final int EARTH_MEAN_RADIUS_IN_MI = 3963;
    private static final int EARTH_MEAN_RADIUS_IN_METERS = 6371000;

    /** Mininum length of a GeoPoint string representation in DMS. */
    private static final int MIN_DMS_STRING_LENGTH = 4;

    /** Number of minutes in a degree. */
    private static final double MINUTES_IN_DEGREE = 60.0;
    /** Number of seconds in a minute. */
    private static final double SECONDS_IN_MINUTE = 60.0;

    /** The number 32. */
    private static final int S_THIRTY_TWO = 32;
    /** The number 7. */
    private static final int S_SEVEN = 7;

    /** Serialized version unique identifier. */
    private static final long serialVersionUID = -8237264645443627384L;

    /** Latitude. */
    private double m_latitude;

    /** Longitude. */
    private double m_longitude;

    /**
     * Create a new instance defaulted to <code>INVALID_POINT</code>.
     */
    public GeoPoint()
    {
        this(INVALID_POINT.getLatitude(), INVALID_POINT.getLongitude());
    }

    /**
     * Create a new point for the specified coordinates.
     * 
     * @param latitude
     *            The latitude of the point.
     * @param longitude
     *            The longitude of the point.
     */
    public GeoPoint(double latitude, double longitude)
    {
        m_latitude = latitude;
        m_longitude = longitude;
    }

    /**
     * Get the latitude for the point.
     * 
     * @return The latitude for the point.
     */
    @XmlAttribute(name = "latitude", namespace = XMLNamespace.URL, required = true)
    public double getLatitude()
    {
        return m_latitude;
    }

    /**
     * Set the latitude for the point.
     * 
     * @param latitude
     *            The latitude for the point.
     */
    @SuppressWarnings("unused")
    private void setLatitude(double latitude)
    {
        m_latitude = latitude;
    }

    /**
     * Get the longitude for the point.
     * 
     * @return The longitude for the point.
     */
    @XmlAttribute(name = "longitude", namespace = XMLNamespace.URL, required = true)
    public double getLongitude()
    {
        return m_longitude;
    }

    /**
     * Set the longitude for the point.
     * 
     * @param longitude
     *            The longitude for the point.
     */
    @SuppressWarnings("unused")
    private void setLongitude(double longitude)
    {
        m_longitude = longitude;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append(GEOCODE_FORMAT.format(m_latitude));
        sb.append(',');
        sb.append(GEOCODE_FORMAT.format(m_longitude));
        sb.append('}');
        return sb.toString();
    }

    public String toLatLngString()
    {
        return GEOCODE_FORMAT.format(m_latitude) + "," + GEOCODE_FORMAT.format(m_longitude);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || !getClass().equals(obj.getClass()))
        {
            return false;
        }
        return Double.compare(m_latitude, ((GeoPoint) obj).m_latitude) == 0
                && Double.compare(m_longitude, ((GeoPoint) obj).m_longitude) == 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode()
    {
        int hash = S_SEVEN;
        hash = (hash * (S_THIRTY_TWO - 1)) + Double.valueOf(m_latitude).hashCode();
        hash = (hash * (S_THIRTY_TWO - 1)) + Double.valueOf(m_longitude).hashCode();
        return hash;
    }

    /**
     * Calculates distance between two GeoPoints in miles.
     * 
     * @param targetPoint
     *            The point from which to calculate the distance.
     * 
     * @return The distance from this point to targetPoint in miles. Infinity (earth's mean radius) if targetPoint is
     *         null or either point is invalid.
     */
    public double distanceFromInMi(final GeoPoint targetPoint)
    {

        // TODO use a Distance return type

        if (targetPoint == null || equals(GeoPoint.INVALID_POINT)
                || targetPoint.equals(GeoPoint.INVALID_POINT))
        {
            return EARTH_MEAN_RADIUS_IN_MI;
        }

        double dLatRad = Math.toRadians(this.getLatitude() - targetPoint.getLatitude());
        double dLonRad = Math.toRadians(this.getLongitude() - targetPoint.getLongitude());

        double a = Math.sin(dLatRad / 2) * Math.sin(dLatRad / 2)
                + Math.cos(Math.toRadians(this.getLatitude()))
                * Math.cos(Math.toRadians(targetPoint.getLatitude())) * Math.sin(dLonRad / 2)
                * Math.sin(dLonRad / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_MEAN_RADIUS_IN_MI * c;
    }

    /**
     * Calculates distance between two GeoPoints in meters.
     * 
     * @param targetPoint
     *            The point from which to calculate the distance.
     * 
     * @return The distance from this point to targetPoint in meters. Infinity (earth's mean radius) if targetPoint is
     *         null or either point is invalid.
     */
    public double distanceFromInMeters(final GeoPoint targetPoint)
    {

        // TODO use a Distance return type

        if (targetPoint == null || equals(GeoPoint.INVALID_POINT)
                || targetPoint.equals(GeoPoint.INVALID_POINT))
        {
            return EARTH_MEAN_RADIUS_IN_METERS;
        }

        double dLatRad = Math.toRadians(this.getLatitude() - targetPoint.getLatitude());
        double dLonRad = Math.toRadians(this.getLongitude() - targetPoint.getLongitude());

        double a = Math.sin(dLatRad / 2) * Math.sin(dLatRad / 2)
                + Math.cos(Math.toRadians(this.getLatitude()))
                * Math.cos(Math.toRadians(targetPoint.getLatitude())) * Math.sin(dLonRad / 2)
                * Math.sin(dLonRad / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_MEAN_RADIUS_IN_METERS * c;
    }

    /**
     * Construct a GeoPoint instance from a string representation.
     * 
     * @param s
     *            String representation as returned from toString()
     * 
     * @return GeoPoint for the given string, or null if it cannot be parsed.
     * 
     * @exception IllegalArgumentException
     *                if the string cannot be parsed.
     */
    public static GeoPoint parse(String s) throws IllegalArgumentException
    {

        if (s == null)
        {
            return null;
        }
        String trimmed = s.trim();

        // if (trimmed.length() > 2)
        // {
        // // strip braces
        // trimmed = s.substring(1, s.length() - 1);
        // }
        String[] pair = trimmed.split(",");
        if (pair.length == 2)
        {
            try
            {
                return new GeoPoint(Double.parseDouble(pair[0].trim()), Double.parseDouble(pair[1]
                        .trim()));
            }
            catch (NumberFormatException e)
            {
                // fall through
            }
        }
        return null;
    }

    /**
     * Convert absolute deg/min/sec to decimal degrees.
     * 
     * @param deg
     *            degrees
     * @param min
     *            minutes
     * @param sec
     *            seconds
     * 
     * @return lat-long in decimal
     */
    public static double dmsToDec(int deg, int min, int sec)
    {
        return deg + (min / MINUTES_IN_DEGREE) + (sec / (MINUTES_IN_DEGREE * SECONDS_IN_MINUTE));
    }

    /**
     * Convert deg/min/sec in form [+|-][[[D]D]D]MMSS to decimal degrees.
     * 
     * @param dms
     *            lat-long in degrees, minutes, seconds.
     * 
     * @return lat-long in decimal.
     * 
     * @exception IllegalArgumentException
     *                if the string cannot be parsed.
     */
    public static double dmsToDec(String dms) throws IllegalArgumentException
    {
        if (dms != null)
        {
            int idxEnd = dms.length();

            if (idxEnd >= MIN_DMS_STRING_LENGTH)
            {
                try
                {
                    int sec = Integer.parseInt(dms.substring(idxEnd - 2));
                    int min = Integer.parseInt(dms.substring(idxEnd - MIN_DMS_STRING_LENGTH,
                            idxEnd - 2));
                    int mult = 1;
                    int idxBegin = 0;

                    if (dms.charAt(0) == '+')
                    {
                        ++idxBegin;
                    }
                    else if (dms.charAt(0) == '-')
                    {
                        ++idxBegin;
                        mult = -1;
                    }

                    String degrees = dms.substring(idxBegin, idxEnd - MIN_DMS_STRING_LENGTH);
                    int deg = 0;

                    if (degrees.length() > 0)
                    {
                        deg = Integer.parseInt(degrees);
                    }

                    return mult * dmsToDec(deg, min, sec);
                }
                catch (Exception ex)
                {
                    // throw exception below
                }
            }
        }

        throw new IllegalArgumentException("Failed to parse dms: " + dms);
    }

}
