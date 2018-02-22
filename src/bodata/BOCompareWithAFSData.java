package bodata;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class BOCompareWithAFSData {
	
	private static final String XML_DUMP_FILE_REGION_POP = "boRegionPopularity.xml";
	private static final String AFS_REGION_HOTEL_BOOKINGS = "regionPopularityScores_BookingHitting.csv";
    private static final String ATTRIBUTE_SEARCH_POPULARITY = "searchPopularity";
    
    private static final String ATTRIBUTE_BOOKING_POPULARITY = "bookingPopularity";
    
	private static final String ATTRIBUTE_LOB = "lob";
	
	private static final String ATTRIBUTE_REGION_ID = "regionId";
	
	private static final String UTF_8 = "UTF-8";
	
	private static final String ELEMENT_REGION_POPULARITY = "regionPopularity";
	
	private static final String ATTRIBUTE_LOCALE = "locale";

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Map<EssLocale, Map<LOB, Map<Integer, BOPopularity>>> regionPop =  getBORegionPopularity();
		Map<Integer, Double> afsRegionHotelsBookingsCount = getAFSRegionHotelsBookingsCount();
		Map<Integer, Double> regionPopAgg = aggregateBOCounts(regionPop);
		for(Map.Entry<Integer, Double> regionHotelsBookings : afsRegionHotelsBookingsCount.entrySet())
		{
			Integer rid = regionHotelsBookings.getKey();
			Double afsScore = regionHotelsBookings.getValue();
			
			if(regionPopAgg.containsKey(rid))
			{
				Double boscore = regionPopAgg.get(rid);
				if(afsScore < boscore)
				{
					System.out.println("AFSScore : " + afsScore + ", BOScore : " + boscore + ", RegionId : " + rid);
				}
				else
				{
					
				}
			}
		}
		System.out.println("Done");
		
			
	}
	public static Map<Integer, Double> aggregateBOCounts(Map<EssLocale, Map<LOB, Map<Integer, BOPopularity>>> regionPop)
	{
		Map<Integer, Double> regionPopAgg = new HashMap<Integer, Double>();
		for(Map.Entry<EssLocale, Map<LOB, Map<Integer, BOPopularity>>> localeData : regionPop.entrySet())
		{
			/*EssLocale locale = localeData.getKey();
			if(!EssLocale.ENGLISH.equals(locale))
			{
				continue;
			}*/
			for(Map.Entry<LOB, Map<Integer, BOPopularity>> lobData :  localeData.getValue().entrySet())
			{
				/*LOB lob = lobData.getKey();
				if(LOB.PACKAGES == lob)
				{
					continue;
				}*/
				for(Map.Entry<Integer, BOPopularity> regionBOData: lobData.getValue().entrySet())
				{
					Integer rid = regionBOData.getKey();
					BOPopularity boPop = regionBOData.getValue();
					int bookings = boPop.getBookingCount();
					if(!regionPopAgg.containsKey(rid))
					{
						regionPopAgg.put(rid, new Double(0));
					}
					double score = regionPopAgg.get(rid);
					score = score + bookings;
					regionPopAgg.put(rid, score);
				}
			}
		}
		return regionPopAgg;
	}
	
	public static Map<Integer, Double> getAFSRegionHotelsBookingsCount()
	{
		String dataLocation = "C:\\sftp\\BO Popularity Data\\XMLs\\";
		Map<Integer, Double> afsRegionHotelBookings = new HashMap<Integer, Double>();
		System.out.println("Starting Loading AFS Region Hotels' Booking Counts from location : " + 
									(dataLocation + AFS_REGION_HOTEL_BOOKINGS));
		BufferedReader br =  null;
		try
		{
			br = new BufferedReader(new FileReader(dataLocation + AFS_REGION_HOTEL_BOOKINGS));
			// Skip the first header line
			String line = br.readLine();
			
			Integer rid = null;
			Double score = null;
			while((line = br.readLine()) != null)
			{
				String[] ridBookings = line.split(",");
				if(null != ridBookings && ridBookings.length == 3)
				{
					try
					{
						rid = Integer.parseInt(ridBookings[0].trim());
						score = Double.parseDouble(ridBookings[1].trim());
						afsRegionHotelBookings.put(rid, score);
					}
					catch(NumberFormatException nfex)
					{
						nfex.printStackTrace();
						System.out.println("NumberFormatException  in getAFSRegionHotelsBookingsCount");
					}
				}
			}
		}
		catch(IOException iex)
		{
			iex.printStackTrace();
		}
		finally
		{
			if(null != br)
			{
				try 
				{
					br.close();
				} catch (IOException e)
				{
					e.printStackTrace();
					System.out.println("BufferedReader closing threw Exception in getAFSRegionHotelsBookingsCount");
				}
			}
		}
		System.out.println("Done Loading " + (dataLocation + AFS_REGION_HOTEL_BOOKINGS));
		return afsRegionHotelBookings;
	}
	
	public static Map<EssLocale, Map<LOB, Map<Integer, BOPopularity>>> getBORegionPopularity()
	{
		Map<EssLocale, Map<LOB, Map<Integer, BOPopularity>>> regionPopularity = 
				new HashMap<EssLocale, Map<LOB, Map<Integer, BOPopularity>>>();
	
		XMLInputFactory factory = XMLInputFactory.newInstance();
		String dataLocation = "C:\\sftp\\BO Popularity Data\\XMLs\\";
		String GZIP_EXTENSION  = ".gz";
		
		
		XMLStreamReader xmlr = null;
		FileInputStream fis = null;
		GZIPInputStream gis = null;
		String regionFileName = "";
		
		EssLocale essLocale;
		LOB essLob;
		
		String localeString;
		String lobString;
		
		Map<LOB, Map<Integer, BOPopularity>> lobMap;
		Map<Integer, BOPopularity> region;
		BOPopularity boPopularity;
		
		try
		{
			regionFileName = dataLocation + XML_DUMP_FILE_REGION_POP + GZIP_EXTENSION;
			fis = new FileInputStream(regionFileName);
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
					if (xmlr.getLocalName().equals(ELEMENT_REGION_POPULARITY))
					{
						int regionId = Integer.parseInt(xmlr.getAttributeValue(null, ATTRIBUTE_REGION_ID));
						int searchCount = Integer.parseInt(xmlr.getAttributeValue(null, 
								ATTRIBUTE_SEARCH_POPULARITY));
						int bookingCount = Integer.parseInt(xmlr.getAttributeValue(null, 
								ATTRIBUTE_BOOKING_POPULARITY));
						
						// en_US as String
						localeString = xmlr.getAttributeValue(null, ATTRIBUTE_LOCALE);
						
						// PACKAGES as String
						lobString = xmlr.getAttributeValue(null, ATTRIBUTE_LOB);
						
						essLocale = Util.toLocale(localeString);
						
						essLob = LOB.getLineOfBusinessType(lobString);
						
						if(null != essLocale && null != essLob )
						{
							if(!regionPopularity.containsKey(essLocale))
							{
								lobMap = new HashMap<LOB, Map<Integer, BOPopularity>>();
								regionPopularity.put(essLocale, lobMap);
							}
							lobMap = regionPopularity.get(essLocale);
							
							if(!lobMap.containsKey(essLob))
							{
								region = new HashMap<Integer, BOPopularity>();
								lobMap.put(essLob, region);
							}
							region = lobMap.get(essLob);
							
							if(!region.containsKey(regionId))
							{
								boPopularity = new BOPopularity();
								region.put(regionId, boPopularity);
							}
							boPopularity = region.get(regionId);
							boPopularity.setSearchCount(searchCount);
							boPopularity.setBookingCount(bookingCount);
						}
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
		return regionPopularity;
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
