package regionhoteldata;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.zip.GZIPOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class RegionHotelMapped {
	private static final String UTF_8 = "UTF-8";
	private static final String CRLF = "\r\n";
	private static final String GZIP_EXTENSION  = ".gz";
	private static final String XML_DUMP_FILE_REGIONHOTEL_POP = "hotelRegion.xml";
	private static final String ELEMENT_HOTEL_REGION_ITEMS = "hotelRegionItems";
	private static final String ELEMENT_HOTEL_REGION_ITEM = "hotelRegionItem";
	private static final String ATTRIBUTE_REGION_ID = "regionId";
	private static final String ATTRIBUTE_HOTEL_ID = "hotelId";
	public static void main(String [] args) 
	{	 
	    BufferedReader hotbotFilereader = null;	
	    FileOutputStream hotelInfoStream = null;
	    GZIPOutputStream zInfoStream = null;
	    XMLStreamWriter xMLStreamWriter = null;
	    String xmlDumpLocation = "C:\\expediasuggest\\";
	    String regionhotbotfile = "regionhotbot.csv";
		try
		{	 
		    hotelInfoStream = new FileOutputStream(xmlDumpLocation + XML_DUMP_FILE_REGIONHOTEL_POP + GZIP_EXTENSION);
		    zInfoStream = new GZIPOutputStream(new BufferedOutputStream(hotelInfoStream));
		    xMLStreamWriter = createXMLStreamWriter(zInfoStream, ELEMENT_HOTEL_REGION_ITEMS);
		    
		    String readLine = "";
		    Integer rid = null;
		    Integer hid = null;
			hotbotFilereader = new BufferedReader(new FileReader(xmlDumpLocation + regionhotbotfile));	 
			while ((readLine = hotbotFilereader.readLine()) != null) 
			{
				String [] ridHid = readLine.split(",");
				if(null != ridHid && ridHid.length == 2)
				{
					try 
					{
						
						rid = Integer.parseInt(ridHid[1].trim());
						hid = Integer.parseInt(ridHid[1].trim());
						writeRegionHotelItem(xMLStreamWriter, hid, rid);
					}
					catch(NumberFormatException e)
					{
						System.out.println("NumberFormatException rid: " + ridHid[0] + " hid: " + ridHid[1]);
						continue;					
					}
				}
				
			}
			hotbotFilereader.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (XMLStreamException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			safelyCloseStreams(xMLStreamWriter, hotelInfoStream, zInfoStream);			
		} 
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
	
	private static void writeRegionHotelItem(XMLStreamWriter hotelWriter, 
			Integer hotelId, Integer regionId) throws XMLStreamException
	{
		hotelWriter.writeStartElement(ELEMENT_HOTEL_REGION_ITEM);
		hotelWriter.writeAttribute(ATTRIBUTE_HOTEL_ID, hotelId.toString());
		hotelWriter.writeAttribute(ATTRIBUTE_REGION_ID, regionId.toString());
	
		hotelWriter.writeEndElement();
		hotelWriter.writeCharacters(CRLF);
	}
	
	private static XMLStreamWriter createXMLStreamWriter(OutputStream outStream, String collectionElement)
	        throws FileNotFoundException, XMLStreamException
	{
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlw = factory.createXMLStreamWriter(outStream, UTF_8);
		xmlw.writeStartDocument("utf-8", "1.0");
		xmlw.writeCharacters(CRLF);
		xmlw.writeStartElement(collectionElement); 
		xmlw.writeCharacters(CRLF);
		return xmlw;
	}

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
}
