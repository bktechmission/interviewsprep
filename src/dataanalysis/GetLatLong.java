package dataanalysis;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.PrintWriter;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;


import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class GetLatLong {

    private static final String UTF_8 = "UTF-8";
    private static String GZIP_EXTENSION  = ".gz";
    
    private static final String ATTRIBUTE_LATITUDE = "latitude"; 
    private static final String ATTRIBUTE_LONGITUDE = "longitude";
    private static final String ELEMENT_REGIONLOCATION_ITEM = "regionLocation";
    private static final String ATTRIBUTE_REGIONID = "regionId";
    
    private static final String ELEMENT_REGION = "region";
    private static final String ATTRIBUTE_REGIONTYPE = "regionType";
    

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		Set<Integer> multicitySet = new HashSet<Integer>();
		Set<Integer> airportSet = new HashSet<Integer>();
		Set<Integer> metrocodeSet = new HashSet<Integer>();
		String REGIONLOCATION_DUMP_FILE_NAME = "regionLocation.xml";
		Map<Integer, GeoCode> regionLocationMap = new HashMap<Integer, GeoCode>(); 
		XMLInputFactory factory = XMLInputFactory.newInstance();
		
		XMLStreamReader xmlr = null;
		FileInputStream fis = null;
		GZIPInputStream gis = null;
		String dataLocation = "C:\\Logs";
		
		System.out.println("Building Region Lat Long Mapping");
		try
		{
			fis =  new FileInputStream(dataLocation + "\\" + REGIONLOCATION_DUMP_FILE_NAME + GZIP_EXTENSION);
			gis = new GZIPInputStream(fis);
			xmlr = factory.createXMLStreamReader(gis, UTF_8);
			while (xmlr.hasNext())
			{
				int event = xmlr.next();
				if (event == XMLStreamConstants.END_DOCUMENT)
				{
					break;
				}
				if (event == XMLStreamConstants.START_ELEMENT)
				{
					if (xmlr.getLocalName().equals(ELEMENT_REGIONLOCATION_ITEM))
					{
						int regionId = Integer.parseInt(xmlr.getAttributeValue(null, ATTRIBUTE_REGIONID));
						String latitude = xmlr.getAttributeValue(null, ATTRIBUTE_LATITUDE);
						String longitude = xmlr.getAttributeValue(null, ATTRIBUTE_LONGITUDE);
						
						GeoCode geoCode = new GeoCode(latitude, longitude);
						regionLocationMap.put(regionId, geoCode);
					}
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (XMLStreamException e)
		{
			e.printStackTrace();
		}
		finally
		{
			safelyCloseStream(xmlr);
			closeFileAndGzipStreams(fis, gis);
		}
		
		System.out.println("Size of Region Lat Long is : "+ regionLocationMap.size());
		
		factory = XMLInputFactory.newInstance();

		String regionFileName = "";
		File dir = new File(dataLocation);
		final String ext = ".xml.gz";
		// Get the list of files available in the remote directory
		File[] remoteFiles = dir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.startsWith("MULTICITY-") && name.endsWith(ext);
		    }
		});
		
		System.out.println("Building Multicity Mapping");
		for(File file : remoteFiles)
		{
			try
			{
				regionFileName = file.getName();
				
				fis = new FileInputStream(dataLocation +"\\"+ regionFileName);
				gis = new GZIPInputStream(fis);
				xmlr = factory.createXMLStreamReader(gis , UTF_8);
				while (xmlr.hasNext())
				{
					int event = xmlr.next();
					if (event == XMLStreamConstants.END_DOCUMENT)
					{
						break;
					}
					if (event == XMLStreamConstants.START_ELEMENT)
					{
						// if the current element is a alias node, add it to the trie
						if (xmlr.getLocalName().equals(ELEMENT_REGION))
						{
							String readRegionType = xmlr.getAttributeValue(null,
							        ATTRIBUTE_REGIONTYPE);
							if (!"MULTICITY".equalsIgnoreCase(readRegionType))
							{
								// We expect the region type to be same. 
								continue;
							}

							
							int regionId = Integer.parseInt(xmlr.getAttributeValue(null, ATTRIBUTE_REGIONID));
										
							multicitySet.add(regionId);
						}
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (XMLStreamException e)
			{
				e.printStackTrace();
			}
			finally
			{
				safelyCloseStream(xmlr);
				closeFileAndGzipStreams(fis, gis);
			}
		}
		
		System.out.println("Size of Multicity : " + multicitySet.size());
		
		remoteFiles = dir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.startsWith("AIRPORT-") && name.endsWith(ext);
		    }
		});
		
		System.out.println("Building Airport Mapping");
		for(File file : remoteFiles)
		{
			try
			{
				regionFileName = file.getName();
				
				fis = new FileInputStream(dataLocation + "\\" + regionFileName);
				gis = new GZIPInputStream(fis);
				xmlr = factory.createXMLStreamReader(gis , UTF_8);
				while (xmlr.hasNext())
				{
					int event = xmlr.next();
					if (event == XMLStreamConstants.END_DOCUMENT)
					{
						break;
					}
					if (event == XMLStreamConstants.START_ELEMENT)
					{
						// if the current element is a alias node, add it to the trie
						if (xmlr.getLocalName().equals(ELEMENT_REGION))
						{
							String readRegionType = xmlr.getAttributeValue(null,
							        ATTRIBUTE_REGIONTYPE);
							if (!"AIRPORT".equalsIgnoreCase(readRegionType))
							{
								// We expect the region type to be same. 
								continue;
							}

							
							int regionId = Integer.parseInt(xmlr.getAttributeValue(null, ATTRIBUTE_REGIONID));
										
							airportSet.add(regionId);
						}
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (XMLStreamException e)
			{
				e.printStackTrace();
			}
			finally
			{
				safelyCloseStream(xmlr);
				closeFileAndGzipStreams(fis, gis);
			}
			
		}
		
		System.out.println("Size of Airport : " + airportSet.size());
		
		remoteFiles = dir.listFiles(new FilenameFilter() {
		    @Override
		    public boolean accept(File dir, String name) {
		        return name.startsWith("METROCODE-") && name.endsWith(ext);
		    }
		});
		
		System.out.println("Building Metrocode Mapping");
		for(File file : remoteFiles)
		{
			try
			{
				regionFileName = file.getName();
				
				fis = new FileInputStream(dataLocation + "\\" + regionFileName);
				gis = new GZIPInputStream(fis);
				xmlr = factory.createXMLStreamReader(gis , UTF_8);
				while (xmlr.hasNext())
				{
					int event = xmlr.next();
					if (event == XMLStreamConstants.END_DOCUMENT)
					{
						break;
					}
					if (event == XMLStreamConstants.START_ELEMENT)
					{
						// if the current element is a alias node, add it to the trie
						if (xmlr.getLocalName().equals(ELEMENT_REGION))
						{
							String readRegionType = xmlr.getAttributeValue(null,
							        ATTRIBUTE_REGIONTYPE);
							if (!"METROCODE".equalsIgnoreCase(readRegionType))
							{
								// We expect the region type to be same. 
								continue;
							}

							
							int regionId = Integer.parseInt(xmlr.getAttributeValue(null, ATTRIBUTE_REGIONID));
										
							metrocodeSet.add(regionId);
						}
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			catch (XMLStreamException e)
			{
				e.printStackTrace();
			}
			finally
			{
				safelyCloseStream(xmlr);
				closeFileAndGzipStreams(fis, gis);
			}
			
		}
		
		System.out.println("Size of Metrocode is : " + metrocodeSet.size());
		
		System.out.println("Writing to multicity.txt");
		PrintWriter pw = null;
		try{
			pw= new PrintWriter(new BufferedWriter(new FileWriter("C:\\Logs\\LogAnalysis\\multicity.txt")));
			if(multicitySet != null && multicitySet.size() > 0)
			{
				for(Integer k : multicitySet)
				{
					if(!regionLocationMap.containsKey(k))
					{
						pw.println(k);
					}
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(null != pw)
			{
				pw.close();
			}
		}
		
		
		System.out.println("Writing to airport.txt");
		try{
			pw= new PrintWriter(new BufferedWriter(new FileWriter("C:\\Logs\\LogAnalysis\\airport.txt")));
			if(multicitySet != null && multicitySet.size() > 0)
			{
				for(Integer k : airportSet)
				{
					if(!regionLocationMap.containsKey(k))
					{
						System.out.println("Airport : " + k);
						pw.println(k);
					}
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(null != pw)
			{
				pw.close();
			}
		}
		
		System.out.println("Writing to metrocode.txt");
		try{
			pw= new PrintWriter(new BufferedWriter(new FileWriter("C:\\Logs\\LogAnalysis\\metrocode.txt")));
			if(multicitySet != null && multicitySet.size() > 0)
			{
				for(Integer k : airportSet)
				{
					if(!regionLocationMap.containsKey(k))
					{
						System.out.println("Metrocode : " + k);
						pw.println(k);
					}
				}
			}
		}
		catch(IOException e){
			e.printStackTrace();
		}
		finally{
			if(null != pw)
			{
				pw.close();
			}
		}
		
		System.out.println("Done Analysis");
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
			e.printStackTrace();
		}		
	}
}
