package regionhoteldata;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class RidHid 
{
	private static final String CRLF = "\r\n";
	
	public static void readAndWrite() 
	{	 
	    BufferedReader br = null;	
	    FileOutputStream hotelInfoStream = null;
	    BufferedOutputStream bufferedStream = null;
	    GZIPOutputStream zInfoStream = null;
	    
		try
		{	 
		    hotelInfoStream = new FileOutputStream("C:\\sftp\\HotelsToRegionMap\\hotelregion.xml");
		    bufferedStream = new BufferedOutputStream(hotelInfoStream);
		    //zInfoStream = new GZIPOutputStream(bufferedStream);
		    XMLStreamWriter xMLStreamWriter = createXMLStreamWriter(bufferedStream);
		    
		    String sCurrentLine;
		    Integer rid =null;
		    Integer hid = null;
			br = new BufferedReader(new FileReader("C:\\sftp\\HotelsToRegionMap\\regionhotbot.csv"));
			int line=0;
			while ((sCurrentLine = br.readLine()) != null) 
			{
				System.out.println(sCurrentLine);
				String [] ridHid = sCurrentLine.split(",");
				try 
				{
					line++;
					rid = Integer.parseInt(ridHid[0].trim());
					hid = Integer.parseInt(ridHid[1].trim());
					writeRegionHotelItem(xMLStreamWriter, hid, rid);
				}
				catch(NumberFormatException e)
				{
					System.out.println("NumberFormatException rid: "+ridHid[0].trim()+" hid: "+ridHid[1].trim());
					System.out.println("Line no : " + line);
					break;
				}
			}
			finishXMLStreamWriter(xMLStreamWriter);
		}
	    catch (Exception e) 
	    {
			e.printStackTrace();
		} 
		finally 
		{
			try 
			{
				if (br != null)
					br.close();
				hotelInfoStream.close();
				bufferedStream.close();
	//			zInfoStream.close();
			} 
			catch (IOException ex) 
			{
				ex.printStackTrace();
			}
		}
	}

	private static void writeRegionHotelItem(XMLStreamWriter hotelWriter, 
			Integer hotelId, Integer regionId) throws XMLStreamException
	{
		hotelWriter.writeStartElement("hotelRegionItem");
		hotelWriter.writeAttribute("hotelId", hotelId.toString());
		hotelWriter.writeAttribute("regionId", regionId.toString());
	
		hotelWriter.writeEndElement();
		hotelWriter.writeCharacters(CRLF);
	}
	
	private static XMLStreamWriter createXMLStreamWriter(OutputStream outStream)
	        throws FileNotFoundException, XMLStreamException
	{
		XMLOutputFactory factory = XMLOutputFactory.newInstance();
		XMLStreamWriter xmlw = factory.createXMLStreamWriter(outStream, "UTF-8");
		xmlw.writeStartDocument("utf-8", "1.0");
		xmlw.writeCharacters(CRLF);
		xmlw.writeStartElement("hotelRegionItems"); 
		xmlw.writeCharacters(CRLF);
		return xmlw;
	}

	private static void finishXMLStreamWriter(XMLStreamWriter xmlw) throws XMLStreamException
	{
		xmlw.writeEndElement();
		xmlw.flush();
		xmlw.close();
	}
	
	public static void main(String args[])
	{
		System.out.println("Starting read and write");
		readAndWrite();
		System.out.println("Completed read and write");
	}
}
