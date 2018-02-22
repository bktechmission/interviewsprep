package tests;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class RegionTlaMappings {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		BufferedReader br = null;
		Map<Integer, String> map = new HashMap<Integer, String>();
		String line;
		try {
			System.out.println("Reading file and Making Mappings: RegionId, MostPopularTLA");
			br = new BufferedReader(new FileReader(new File("C:\\expediasuggest\\lkgdump\\regionTLABookingCount.csv")));
			// skip first header line
			br.readLine();
			String temp;
			String[] entries = null;
			String[] entryScoreCheck = null;
			while((line = br.readLine()) != null)
			{
				entries = line.split(",");
				Integer rid = Integer.parseInt(entries[0].trim());
				String tla = entries[1].trim();
				Long score = Long.parseLong(entries[2].trim());
				if(!map.containsKey(rid))
				{
					temp = tla + ":" + score;
					map.put(rid, temp);
				}
				else
				{
					entryScoreCheck = map.get(rid).split(":");
					Long tmpScore = Long.parseLong(entryScoreCheck[1].trim());
					
					temp = tla + ":" + score;
					
					// if current tla is more popular
					if(tmpScore < score)
					{
						map.put(rid, temp);
					}
				}		
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(null != br)
			{
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		System.out.println("Done Reading. Now start Writing File regionTLAMapping.csv");
		// Now Writing to file
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new BufferedWriter(new FileWriter(new File("C:\\expediasuggest\\lkgdump\\regionTLAMapping.csv"))));
			pw.println("RegionId, TLA");
			Integer rid = null;
			String tlaScore = null;
			String[] tempSplit = null;
			String tla = null;
			
			for(Map.Entry<Integer, String> entry : map.entrySet())
			{
				rid = entry.getKey();
				tlaScore = entry.getValue();
				tempSplit = tlaScore.split(":");
				tla = tempSplit[0].trim();
				pw.println(rid + ", " + tla);		
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(null != pw)
			{
				pw.close();
			}
		}
		System.out.println("Done");
	}

}
