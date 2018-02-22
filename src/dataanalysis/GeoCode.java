package dataanalysis;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * Geo code class that contains the latitude and longitude
 */
/* tbd - refactor ResolverProcessor to use this class */

@XmlRootElement(name = "g")
public class GeoCode implements Serializable
{
	/* spherical approximation of earth's radius in km  */
	private static final double EARTH_RADIUS_KM = 6371.01;

	private static final long serialVersionUID = 2821305705344239889L;

	private String m_latitude;

	private String m_longitude;

	private double m_radLatDbl;
	
	private double m_radLngDbl; 
	
	private double m_degLatDbl;
	
	private double m_degLngDbl; 	
	
	public GeoCode()
	{
		
	}
			
    public GeoCode(String latitude, String longitude)
    {
	    m_latitude = latitude;
	    m_longitude = longitude;
	    getRadianValuesOfLatLong(); 
	    getDegreeValuesOfLatLong();
    }
    
    public GeoCode(Double degLatDbl, Double degLngDbl)
    {
    	m_degLatDbl = degLatDbl;
    	m_degLngDbl = degLngDbl;

    }    
    
    public GeoCode(LatLng latLng)
    {
    	if (latLng != null && latLng.getLat() != null && latLng.getLng() != null)
    	{
    		m_latitude = latLng.getLat().setScale(6, BigDecimal.ROUND_HALF_EVEN).toString();
    		m_longitude = latLng.getLng().setScale(6, BigDecimal.ROUND_HALF_EVEN).toString();
    	}
    	getRadianValuesOfLatLong();
    	getDegreeValuesOfLatLong();
    }
    
    private void getRadianValuesOfLatLong()
    {
    	if(m_latitude != null)
	    {
	    	m_radLatDbl = convertStringToRadians(m_latitude); 
	    }
    	if(m_longitude != null)
	    {
	    	m_radLngDbl = convertStringToRadians(m_longitude); 
	    }
    }
    private void getDegreeValuesOfLatLong()
    {
    	if(m_latitude != null)
	    {
	    	m_degLatDbl = convertStringToDegrees(m_latitude); 
	    }
    	if(m_longitude != null)
	    {
	    	m_degLngDbl = convertStringToDegrees(m_longitude); 
	    }
    }
    
	/**
	 * Computes the great circle distance between this geo instance
	 * and the location argument.
	 * @return the distance, measured in the same unit as the radius
	 * argument.
	 */
	public double distanceRadianTo(GeoCode location) 
	{
		return Math.acos(Math.sin(m_radLatDbl) * 
				Math.sin(location.m_radLatDbl) +
				Math.cos(m_radLatDbl) * 
				Math.cos(location.m_radLatDbl) *
				Math.cos(m_radLngDbl - 
				location.m_radLngDbl)) * EARTH_RADIUS_KM;
	}
	
	private double convertStringToRadians(String latOrLong)
	{
		return Math.toRadians(Double.parseDouble(latOrLong));
	}
	
	private double convertStringToDegrees(String latOrLong)
	{
		return Double.parseDouble(latOrLong);
	}	
		
	@XmlElement(name = "lat")
	public String getLatitude()
	{
		return m_latitude;
	}

	public void setLatitude(String latitude)
	{
		m_latitude = latitude;
	}

	@XmlElement(name = "lng")
	public String getLongitude()
	{
		return m_longitude;
	}

	public void setLongitude(String longitude)
	{
		m_longitude = longitude;
	}
	
	@XmlTransient
	public Double getRadLatDbl() {
		return m_radLatDbl;
	}

	public void setRadLatDbl(Double radLatDbl) {
		m_radLatDbl = radLatDbl;
	}
	
	@XmlTransient
	public Double getRadLngDbl() {
		return m_radLngDbl;
	}

	public void setRadLngDbl(Double radLngDbl) {
		m_radLngDbl = radLngDbl;
	}
	
	@XmlTransient
	public Double getDegLatDbl() {
		return m_degLatDbl;
	}

	public void setDegLatDbl(Double degLatDbl) {
		m_degLatDbl = degLatDbl;
	}

	@XmlTransient
	public Double getDegLngDbl() {
		return m_degLngDbl;
	}

	public void setDegLngDbl(Double degLngDbl) {
		m_degLngDbl = degLngDbl;
	}

	@Override
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("lat=");
		sb.append(m_latitude);
		sb.append(":lng=");
		sb.append(m_longitude);
		return sb.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((m_latitude == null) ? 0 : m_latitude.hashCode());
		result = prime * result + ((m_longitude == null) ? 0 : m_longitude.hashCode());
		return result;
	}

	@SuppressWarnings("PMD.NPathComplexity")
	// Unable to reduce code complexity.
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
		if (getClass() != obj.getClass())
		{
			return false;
		}
		GeoCode other = (GeoCode) obj;
		
		if (m_latitude == null)
		{
			if (other.m_latitude != null)
			{
				return false;
			}
		}
		else if (!m_latitude.equals(other.m_latitude))
		{
			return false;
		}
		if (m_longitude == null)
		{
			if (other.m_longitude != null)
			{
				return false;
			}
		}
		else if (!m_longitude.equals(other.m_longitude))
		{
			return false;
		}
		return true;
	}	
}