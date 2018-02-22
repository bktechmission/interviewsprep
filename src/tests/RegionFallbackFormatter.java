package tests;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.json.JSONObject;

public class RegionFallbackFormatter {

	private static final Map<Integer, Integer> mapofTypes = new HashMap<Integer, Integer>(){
	{
		put(11,2);
		put(14,10);
		put(18,11);
		put(21,9);
		put(22,1);
		put(23,11);
	}};
	
	static class Region{
		String id;
		int type;
		Region(String id, int type)
		{
			this.id=id;
			this.type=type;
		}
		@Override
		public int hashCode()
		{
			int prime = 31;
			int result=0;
			result = result*prime + id!=null?id.hashCode():0;
			result = result*prime + type;
			return result;
		}
		@Override
		public boolean equals(Object o)
		{
			if(o==null)
				return false;
			if(this==o)
				return true;
			
			if(getClass() != o.getClass())
				return false;
			
			Region otherPoint = (Region)o;
			if(!id.equals(otherPoint.id))
				return false;
			if(type!=otherPoint.type)
				return false;
			
			return true;
		}
	}
	public static void main(String[] args)
	{
		Scanner sc = null;
		PrintWriter pw = null;
		Map<Region,List<Region>> regionToRegionsMap = new HashMap<>();
		try
		{
			sc= new Scanner(new File("/Users/bpanwar/Desktop/ActivitiesMining/data/RegionFallbackData20160523.tab"));
			while(sc.hasNextLine())
			{
				String line = sc.nextLine().trim();
				String[] fields = line.split("\t");
				String rid = fields[0].trim();
				int ridType = Integer.parseInt(fields[1].trim());
				int ridNewType = -1;
				if(mapofTypes.containsKey(ridType))
				{
					ridNewType = mapofTypes.get(ridType);
				}
				
				Region baseRegion = new Region(rid,ridNewType);
				
				String ancestorRid = fields[3];
				int ancestorType = Integer.parseInt(fields[4].trim());
				int ancestorNewType = -1;
				if(mapofTypes.containsKey(ancestorType))
				{
					ancestorNewType = mapofTypes.get(ancestorType);
				}
				Region ancestorRegion = new Region(ancestorRid,ancestorNewType);
				
				if(!regionToRegionsMap.containsKey(baseRegion))
				{
					List<Region> points = new ArrayList<Region>();
					regionToRegionsMap.put(baseRegion, points);
				}
				List<Region> points = regionToRegionsMap.get(baseRegion);
				points.add(ancestorRegion);
			}
			sc.close();
			sc=null;
			pw = new PrintWriter("/Users/bpanwar/Desktop/ActivitiesMining/data/RECRegionFallback.data.js");
			for(Map.Entry<Region, List<Region>> entry : regionToRegionsMap.entrySet())
			{
				JSONObject js = new JSONObject();
				Region baseRegion = entry.getKey();
				List<Region> ancestorRegions = entry.getValue();
				js.put("_id", baseRegion.id);
				js.put("type", baseRegion.type);
				
				JSONObject[] jsonArray = new JSONObject[ancestorRegions.size()];
				int index=0;
				for(Region region: ancestorRegions)
				{
					JSONObject tempObject = new JSONObject();
					tempObject.put("id", region.id);
					tempObject.put("type", region.type);
					jsonArray[index++]=tempObject;
				}
				js.put("regions", jsonArray);
				pw.println(js.toString());
			}
		}
		catch(FileNotFoundException fnf)
		{
			fnf.printStackTrace();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally{
			if(sc!=null)
			{
				sc.close();
			}
			if(pw!=null)
			{
				pw.close();
			}
		}
	}
}
