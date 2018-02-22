package kdtree;

public class AirportRegionItem extends RegionItem
{
	public static final char PREFIX_KEY = 'A';
	
	private String m_tla;
	
	public AirportRegionItem(GeoPoint geoPoint, String tla, String sname, String fname, int rid)
	{
		super(geoPoint, sname, fname, rid);
		m_tla = tla;
	}
	
	public String getTla() 
	{
		return m_tla;
	}

	public void setTla(String tla) 
	{
		m_tla = tla;
	}
	
	public char getPrefixKey()
	{
		return PREFIX_KEY;
	}

}
