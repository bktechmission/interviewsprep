
package bodata;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TestSorting {

	public static void main(String[] args)
	{
		Map<Integer, BOPopularity> regionPopularityMap = new HashMap<Integer, BOPopularity>();
		BOPopularity boPop = new BOPopularity();
		boPop.setSearchCount(12);
		boPop.setBookingCount(5);
		
		regionPopularityMap.put(5, boPop);
		
		boPop = new BOPopularity();
		boPop.setSearchCount(120);
		boPop.setBookingCount(10);
		
		regionPopularityMap.put(1, boPop);
		
		boPop = new BOPopularity();
		boPop.setSearchCount(100);
		boPop.setBookingCount(7);
		
		regionPopularityMap.put(3, boPop);
		
		boPop = new BOPopularity();
		boPop.setSearchCount(120);
		boPop.setBookingCount(10);
		regionPopularityMap.put(2, boPop);
		
		boPop = new BOPopularity();
		boPop.setSearchCount(100);
		boPop.setBookingCount(7);
		
		regionPopularityMap.put(4, boPop);
		
		//sortRegionPopularityMap(regionPopularityMap);
		
		boPop = new BOPopularity();
		boPop.setSearchCount(6);
		boPop.setBookingCount(7);
		
		boolean first = true;
		for(Map.Entry<Integer, BOPopularity> entry: regionPopularityMap.entrySet())
		{
			System.out.println("Key : " + entry.getKey() + "\tBO Popularity : " + entry.getValue());
			if(first)
			{
				//regionPopularityMap.put(6, boPop);
				regionPopularityMap.put(3, boPop);
				first = false;
			}
			
		}
		
		System.out.println(EssLocale.US.getLanguage() + "_" + EssLocale.US.getCountry());
		
		LOB lob = LOB.HOTELS;
		if(LOB.HOTELS.equals(lob))
		{
			System.out.println("Equals Hotels");
		}
		
		EssLocale locale = EssLocale.US;
		
		if(EssLocale.US.equals(locale))
		{
			System.out.println("Equals US");
		}
		
	}
	
	public static void sortRegionPopularityMap(Map<Integer, BOPopularity> regionPopularityMap)
	{
		Map<BOPopularity, Set<Integer>> sortedMap = new TreeMap<BOPopularity, Set<Integer>>();
		if(null == regionPopularityMap || regionPopularityMap.size() == 0)
		{
			return;
		}
		
		Integer key;
		BOPopularity boPop;
		Set<Integer> ids;
		for(Map.Entry<Integer, BOPopularity> entry : regionPopularityMap.entrySet())
		{
			key = entry.getKey();
			boPop = entry.getValue();
			if(!sortedMap.containsKey(boPop))
			{
				ids = new HashSet<Integer>();
				sortedMap.put(boPop, ids);
			}
			ids = sortedMap.get(boPop);
			ids.add(key);
		}
		
		regionPopularityMap.clear();
		for(Map.Entry<BOPopularity, Set<Integer>> entry : sortedMap.entrySet())
		{
			boPop = entry.getKey();
			ids = entry.getValue();
			for(Integer k : ids)
			{
				regionPopularityMap.put(k, boPop);
			}
		}
	}
}
