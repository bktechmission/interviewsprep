package kdtree;

public class RegionItem {

	private GeoPoint m_point;
	
	private String m_shortName;
	
	private String m_longName;
	
	private int m_rid;
	
	public RegionItem(GeoPoint point, String sname, String lname, Integer rid)
	{
		m_point = point;
		m_shortName = sname;
		m_longName = lname;
		m_rid = rid;
	}

	public GeoPoint getPoint() 
	{
		return m_point;
	}

	public void setPoint(GeoPoint point) 
	{
		this.m_point = point;
	}

	public String getShortName() 
	{
		return m_shortName;
	}

	public void setShortName(String shortName) 
	{
		this.m_shortName = shortName;
	}

	public String getLongName()
	{
		return m_longName;
	}

	public void setLongName(String longName) 
	{
		this.m_longName = longName;
	}
	
	public void setRid(int rid)
	{
		m_rid = rid;
	}
	
	public int getRid()
	{
		return m_rid;
	}
	
}
