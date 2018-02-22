package ReadGZIP;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class ESSDataFormatter 
{
	 private static final String UTF_8 = "UTF-8";
	 private static final String CRLF = "\r\n";
	 private static final String GZIP_EXTENSION  = ".gz";
	 private static final String XML_DUMP_LOCATION = "C:\\sftp\\Lodging Data\\";
	 private static final String XML_DUMP_NAME = "afshotelpopularitydata.xml";
	 private static final String XMLS_LOCATION = "C:\\sftp\\Lodging Data\\Scores";
	 
	 private static final String ELEMENT_BOOKING_POPULARITY_TYPES = "bookingPopularityTypes";
	 private static final String ELEMENT_BOOKING_POPULARITY_TYPE = "bookingPopularityType";
	 private static final String ATTRIBUTE_ID = "id";
	 private static final String ATTRIBUTE_SCORE = "score";
	 
	 
	public static void main(String[] args) 
	{
		File dir = new File(XMLS_LOCATION);
		if(!dir.isDirectory())
		{
			return;
		}
		XMLStreamReader xmlr = null;
		String[] fileNames = dir.list();
		if(null == fileNames || fileNames.length == 0)
		{
			return;
		}
		
		ESSDataFormatter essDataFormat = new ESSDataFormatter(); 
		FileOutputStream fStream = null;
		GZIPOutputStream zStream = null;
		XMLStreamWriter xmlw = null;
		XMLInputFactory factory = XMLInputFactory.newInstance();
		int count = 0;
		try
		{
			fStream = new FileOutputStream(XML_DUMP_LOCATION + XML_DUMP_NAME + GZIP_EXTENSION);
			zStream = new GZIPOutputStream(new BufferedOutputStream(fStream));
			xmlw = essDataFormat.createXMLStreamWriter(zStream, ELEMENT_BOOKING_POPULARITY_TYPES);
			Integer hotelId = null;
			Double score = null;
			for(String fileName:fileNames)
			{
				try
				{
					xmlr = factory.createXMLStreamReader(new FileInputStream(XMLS_LOCATION +"\\" + fileName), 
							UTF_8);
					
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
							if (xmlr.getLocalName().equals(ATTRIBUTE_ID))
							{
								hotelId = Integer.parseInt(xmlr.getElementText());
							}
							if(xmlr.getLocalName().equals(ATTRIBUTE_SCORE))
							{
								score = Double.parseDouble(xmlr.getElementText());
							}
						}
						if (event == XMLStreamConstants.END_ELEMENT)
						{
							if (xmlr.getLocalName().equals(ELEMENT_BOOKING_POPULARITY_TYPE))
							{
								if(null != hotelId && null != score)
								{
									xmlw.writeStartElement(ELEMENT_BOOKING_POPULARITY_TYPE);
									xmlw.writeAttribute(ATTRIBUTE_ID, Integer.toString(hotelId));
									xmlw.writeAttribute(ATTRIBUTE_SCORE, Double.toString(score));
									xmlw.writeEndElement();
									xmlw.writeCharacters(CRLF);
									count ++;
									hotelId = null;
									score = null;
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
					essDataFormat.safelyCloseStream(xmlr);
				}	
			}
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
			essDataFormat.safelyCloseStreams(xmlw, fStream, zStream);			
		}
		System.out.println(count);
		
	}
	
	private void safelyCloseStream(XMLStreamReader xmlr)
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
	
	private XMLStreamWriter createXMLStreamWriter(OutputStream outStream, String collectionElement)
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

	private void finishXMLStreamWriter(XMLStreamWriter xmlw) throws XMLStreamException
	{
		xmlw.writeEndElement();
		xmlw.flush();
		xmlw.close();
	}
	
	private void safelyCloseStreams(XMLStreamWriter xmlw, FileOutputStream fStream, GZIPOutputStream zStream)
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
