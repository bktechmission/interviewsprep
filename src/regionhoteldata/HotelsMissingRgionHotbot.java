package regionhoteldata;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class HotelsMissingRgionHotbot {
		private static final String UTF_8 = "UTF-8";
		private static final String CRLF = "\r\n";
		private static final String GZIP_EXTENSION  = ".gz";
		private static final String HOTEL_REGION_XML_FILE_NAME = "hotelRegion.xml";
		private static final String ELEMENT_HOTEL_REGION_ITEM = "hotelRegionItem";
		private static final String ATTRIBUTE_REGION_ID = "regionId";
		private static final String ATTRIBUTE_HOTEL_ID = "hotelId";
		private static  String xmlDumpLocation = "/Users/bpanwar/Data/";
		public static void main(String [] args) 
		{	 
			Set<Integer> hids = getRegionHotelMap();
			Scanner sc = null;
			try {
				sc = new Scanner(new File(xmlDumpLocation + "regionhotbotmissings.csv"));
				Set<Integer> uiqueIds = new HashSet<Integer>();
				Set<Integer> uiqueIdsPresents = new HashSet<Integer>();
				while(sc.hasNext())
				{
					String allids = sc.nextLine();
					//System.out.println(allids);
					String[] allidsArray = allids.split(",");
					//System.out.println(allidsArray.length);
					for(String ids: allidsArray)
					{
						Integer hid = Integer.parseInt(ids.trim());
						if(hids.contains(hid))
						{
							if(!uiqueIdsPresents.contains(hid))
							{
								System.out.println("id is present in region hotbot. " + hid);
								uiqueIdsPresents.add(hid);
							}
							
						}
						else
						{
							uiqueIds.add(hid);
						}
					}
				}
				
				int count = 0;
				PrintWriter pw = null;
				try {
					pw = new PrintWriter(xmlDumpLocation + "missingHotelIdsInRegionMaps.txt");
					for(Integer id: uiqueIds)
					{
						pw.println(id);
						count = count +1;
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				finally{
					pw.close();
				}
				System.out.println("total missing is " + count);
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			finally{
				if(sc!=null)
				{
					sc.close();
				}
				
			}
		}
		
		public static Set<Integer> getRegionHotelMap()
		{
			Map<Integer, ArrayList<Integer>> regionHotelMap = new HashMap<Integer, ArrayList<Integer>>();
			XMLInputFactory factory = XMLInputFactory.newInstance();
			String dataLocation = xmlDumpLocation;
			
			XMLStreamReader xmlr = null;
			FileInputStream fis = null;
			GZIPInputStream gis = null;	
			
			try
			{
				// hotelRegion.xml.gz now dumped from GenerateESSXMLsFromRegionHotbotData, 
				// here we read only
				fis = new FileInputStream(dataLocation + HOTEL_REGION_XML_FILE_NAME + GZIP_EXTENSION);
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
						if (xmlr.getLocalName().equals(ELEMENT_HOTEL_REGION_ITEM))
						{
							int regionId = Integer.parseInt(xmlr.getAttributeValue(null, ATTRIBUTE_REGION_ID));
							int hotelId = Integer.parseInt(xmlr.getAttributeValue(null, ATTRIBUTE_HOTEL_ID));
							//int[] hotelIdSet = null;
							if (!regionHotelMap.containsKey(hotelId))
							{
								//hotelIdSet = KnownIntegerSetPool.appendInt(hotelIdSet, hotelId); 
								regionHotelMap.put(hotelId, new ArrayList<Integer>());	
							}
							List<Integer>list  = regionHotelMap.get(hotelId);
							list.add(regionId);
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
			return regionHotelMap.keySet();
		}
		
		/*
		public static void main(String [] args)
		{
			File file = new File("regionhotbot.txt");
			Map<String, Set<String>> regionToHotelMap = new HashMap<String, Set<String>>();
			Map<String, Set<String>> hotelToRegionMap = new HashMap<String, Set<String>>();
			long time1 = System.currentTimeMillis();
			if(file.exists())
			{
				System.out.println("Find the File");
				
				try 
				{
					Scanner sc = new Scanner(file);
					String line = null;
					String[] split = null;
					Set<String> ids = null;
					
					while(sc.hasNextLine())
					{
						line = sc.nextLine().trim();
						split = line.split("\t");
						if(split.length == 2)
						{
							String regionID = split[0].trim();
							String hotelID = split[1].trim();
							if(!regionToHotelMap.containsKey(regionID))
							{
								regionToHotelMap.put(regionID, new HashSet<String>());
							}
							ids = regionToHotelMap.get(regionID);
							ids.add(hotelID);
							
							if(!hotelToRegionMap.containsKey(hotelID))
							{
								hotelToRegionMap.put(hotelID, new HashSet<String>());
							}
							ids = hotelToRegionMap.get(hotelID);
							ids.add(regionID);
						}
					}
					
				} catch (FileNotFoundException e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			long time2 = System.currentTimeMillis();
			System.out.println("Time taken in sec:"+(time2-time1)/1000);
			System.out.println("Size Region Map:"+regionToHotelMap.size());
			System.out.println("Size Hotel Map:"+hotelToRegionMap.size());
			
		}*/

		private static void finishXMLStreamWriter(XMLStreamWriter xmlw) throws XMLStreamException
		{
			xmlw.writeEndElement();
			xmlw.flush();
			xmlw.close();
		}
		
		private static void safelyCloseStreams(XMLStreamWriter xmlw, FileOutputStream fStream, GZIPOutputStream zStream)
		{
			// Cascaded Stream Close
			try
			{
				if(xmlw != null)
				{
					finishXMLStreamWriter(xmlw);		// close XML Writer Stream
				}	
			}
			catch(XMLStreamException e)
			{
				// Do nothing Just to make it Safe Exit
			}
			finally
			{
				try
				{
					if(zStream != null)
					{
						zStream.flush();
						zStream.close();
					}
					if(fStream != null)
					{
						fStream.close();			//close File Stream
					}
				}
				catch(IOException e)
				{
					// Do nothing Just to make it Safe Exit
				}
			}
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
