package regiontlamappings;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class RegionTLAConversion {

	/**
	 * @param args
	 */
	private static final String UTF_8 = "UTF-8";
	private static final String PACKAGETLA_DUMP_FILE_NAME = "packageTlaItems.xml";

    private static final String ELEMENT_PACKAGETLA_ITEM = "packageTlaItem";

    private static final String ATTRIBUTE_REGIONID = "regionId";
   
    private static final String ATTRIBUTE_PRIMARYTLA = "primaryTla";	
    
    private static final String PATH="/Users/bpanwar/P4/Data";
    
	
	public static void main(String[] args) {
		BufferedReader br = null;
		List<Integer> listofids = new ArrayList<Integer>();
		
		
		int lineno = 1;
		try {
			br = new BufferedReader(new FileReader(PATH+"/RemoveTLA.txt"));
			
			String line = null;
			while((line=br.readLine()) != null)
			{
				int rid = Integer.parseInt(line);
				listofids.add(rid);
				lineno++;
			}
			
		} catch (IOException e) {
			System.out.println("Caught Exception in line : " + lineno);
			
		}
		finally
		{
			if(br!=null)
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		System.out.println("Added the RIDS. Lines are " + (lineno-1)  + " and IDS are " + listofids.size());
		
		Map<Integer, Set<String>> packageTlaItems = getPackageTlaItems();
		PrintWriter pr=null;
		try {
			pr = new PrintWriter(new BufferedWriter(new FileWriter(PATH + "/regionsremovedfromRegionTLAMappings_July_22_2014.csv")));
			for(int rid : listofids)
			{
				if(packageTlaItems.containsKey(rid))
				{
					Set<String> tlas = packageTlaItems.get(rid);
					for(String tla : tlas)
					{
						pr.println(rid+",-"+tla+",,PACKAGES");
					}
				}
			}
		} catch (IOException e) {
			System.out.println("Got IOException in Writing to file regionsremovedfromRegionTLAMappings.csv");
		}
		finally{
			if(pr!=null)
			{
				pr.close();
			}
		}
		
		
		System.out.println("Made the removal file ready.");
	}
	
	
	public static Map<Integer, Set<String>> getPackageTlaItems()
	{		
		Map<Integer, Set<String>> packageTlaItems = new HashMap<Integer, Set<String>>(); 
		XMLInputFactory factory = XMLInputFactory.newInstance();
		// create a xml streaming reader for alias
		XMLStreamReader xmlr = null;
		FileInputStream fis = null;
		GZIPInputStream gis = null;
		
		try
		{
			String fileName = PATH + "/" + PACKAGETLA_DUMP_FILE_NAME + ".gz";
			fis = new FileInputStream(fileName);
			gis = new GZIPInputStream(fis);
			xmlr = factory.createXMLStreamReader(gis, UTF_8);

			System.out.println("Start PACKAGE TLA");
			while (xmlr.hasNext())
			{
				int event = xmlr.next();
				if (event == XMLStreamConstants.END_DOCUMENT)
				{
					break;
				}
				if (event == XMLStreamConstants.START_ELEMENT)
				{
					if (xmlr.getLocalName().equals(ELEMENT_PACKAGETLA_ITEM))
					{
						int regionId = Integer.parseInt(xmlr.getAttributeValue(null, ATTRIBUTE_REGIONID));
						String primaryTla = xmlr.getAttributeValue(null, ATTRIBUTE_PRIMARYTLA);
						
						Set<String> primaryTlaSet = packageTlaItems.get(regionId);
						if(primaryTlaSet == null)
						{
							// 
							primaryTlaSet = new HashSet<String>();
							packageTlaItems.put(regionId, primaryTlaSet); 
						}						
						primaryTlaSet.add(primaryTla);
						
					}
				}
			}
			System.out.println("End PackageTLA");
		}
		catch (IOException e)
		{
			System.out.println("IOException in PackageTLA");
		}
		catch (XMLStreamException e)
		{
			System.out.println("XMLStreamException in PackageTLA");
		}
		finally
		{
			safelyCloseStream(xmlr);
			closeFileAndGzipStreams(fis, gis);
		}
		return packageTlaItems;
	}
	
	private static void safelyCloseStream(XMLStreamReader xmlr)
	{
		// Just 1 Reader Stream Close
		try
		{
			if(xmlr != null)
			{
				xmlr.close();					//close XML Reader Stream
			}	
		}
		catch(XMLStreamException e)
		{
			// Do nothing Just to make it Safe Exit
		}
	}
	

	private static void closeFileAndGzipStreams(FileInputStream fis, GZIPInputStream gis)
	{
		try
		{
			if(fis!=null)
			{
				fis.close();
			}
		
			if(gis!=null)
			{
				gis.close();
			}
		}
		catch(IOException e)
		{
			System.out.println("Inputstream cannot be closed.");
		}		
	}

}
