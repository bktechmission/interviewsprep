package tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class ClosingUtil {
	public static void finishXMLStreamWriter(XMLStreamWriter xmlw) throws XMLStreamException
	{
		xmlw.writeEndElement();
		xmlw.flush();
		xmlw.close();
	}
	
	public static void safelyCloseStreams(XMLStreamWriter xmlw, FileOutputStream fStream, GZIPOutputStream zStream)
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
	
	public static void safelyCloseStream(XMLStreamReader xmlr)
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
	

	public static void closeFileAndGzipStreams(FileInputStream fis, GZIPInputStream gis)
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
			//LOGGER.warn("Inputstream cannot be closed.");
		}		
	}
}
