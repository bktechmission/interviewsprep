package kdtree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KDTreeTesting 
{

	/**
	 * @param args
	 */
	KDTree<String> m_geoTree = new KDTree<String>(2);
	Map<String, Integer> m_id2CounterMap = new HashMap<String, Integer>();
    List<RegionItem> m_geoItems = new ArrayList<RegionItem>();
	
    public static void main(String[] args) 
    {
		
        
        /*GeoPoint geoPoint1 = new GeoPoint(11.123, -2.45);
        RegionItem regionItem1 = new AirportRegionItem(geoPoint1, "SEA", "Seattle", "Seattle, WA", 1);
        
        String key = String.valueOf(regionItem1.getRid());*/
       /* String latlong = "47.611310|-122.334090";
        String[] split = latlong.split("\\|");
        System.out.println(split.length);
        */
        Double score = null;
        Double test = score + 0.2D;
        System.out.println(test);
        

	}
	
    
	public RegionItem getRegionItemByKey(String id)
	{
	    if (this.m_id2CounterMap.containsKey(id))
	    {
	        return getRegionItemByIndex(this.m_id2CounterMap.get(id).intValue());
	    }
	    return null;
	}
	
	public RegionItem getRegionItemByIndex(int index)
	{
		if (index < m_geoItems.size())
		{
			return m_geoItems.get(index);
		}
		return null;
	}
	 
	public int addToGeoRegionItem(String key, RegionItem item)
	{
		m_geoItems.add(item);
		int geoKeyCounter = m_geoItems.size() - 1;
		m_id2CounterMap.put(key, geoKeyCounter);
		return geoKeyCounter;
	}

}
