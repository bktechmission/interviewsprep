package afssearchcount;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.GZIPOutputStream;

import javax.xml.stream.XMLOutputFactory;

import javax.xml.stream.XMLStreamException;

import javax.xml.stream.XMLStreamWriter;


public class DataFormatterAFSSearchCount {

		// TODO Auto-generated method stub
		 private static final String UTF_8 = "UTF-8";
		 private static final String CRLF = "\r\n";
		 //private static final String GZIP_EXTENSION  = ".gz";
		 private static final String XML_DUMP_LOCATION = "C:\\sftp\\AFS POI Popularity Data\\";
		 private static final String XML_DUMP_NAME = "afspoishadowpopularitydata.xml";
		 private static final String XMLS_LOCATION = "C:\\sftp\\AFS POI Popularity Data";
		 
		 private static final String ELEMENT_SEARCH_POPULARITY_TYPES = "poiShadowSearchScores";
		 private static final String ELEMENT_SEARCH_POPULARITY_TYPE = "poiShadowSearchScore";
		 private static final String ATTRIBUTE_ID = "id";
		 private static final String ATTRIBUTE_SCORE = "score";
		 
		 
		public static void main(String[] args) 
		{
			File dir = new File(XMLS_LOCATION);
			if(!dir.isDirectory())
			{
				return;
			}

			
			DataFormatterAFSSearchCount essDataFormat = new DataFormatterAFSSearchCount(); 
			FileOutputStream fStream = null;
			//GZIPOutputStream zStream = null;
			XMLStreamWriter xmlw = null;
			int count = 0;
			try
			{
				fStream = new FileOutputStream(XML_DUMP_LOCATION + XML_DUMP_NAME);
				//zStream = new GZIPOutputStream(new BufferedOutputStream(fStream));
				xmlw = essDataFormat.createXMLStreamWriter(fStream, ELEMENT_SEARCH_POPULARITY_TYPES);
				Scanner sc = new Scanner(new File(XML_DUMP_LOCATION + "POIShadowToPOIMappings.csv"));
				// Header
				sc.nextLine();
				String read;
				String[] split = null;
				Map<Integer, Integer> poiToPoiShadowMap = new HashMap<Integer, Integer>();
				while(sc.hasNextLine())
				{
					read = sc.nextLine();
					if(null != read && !read.trim().isEmpty())
					{
						//System.out.println(read);
						split = read.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
						if(split.length == 5 && null != split[4] && !split[4].trim().isEmpty()
								&& null != split[2] && !split[2].trim().isEmpty()
								&& !"NULL".equalsIgnoreCase(split[4].trim()) 
								&& !"NULL".equalsIgnoreCase(split[2].trim()))
						{
							poiToPoiShadowMap.put(Integer.parseInt(split[4].trim()), Integer.parseInt(split[2].trim()));
						}
					}
				}
				sc.close();
				
				Map<Integer, Double> poiToScoreMap = new HashMap<Integer, Double>();
				sc = new Scanner(new File(XML_DUMP_LOCATION + "SearchPopularity.csv"));
				sc.nextLine();
				while(sc.hasNextLine())
				{
					read = sc.nextLine();
					if(null != read && !read.trim().isEmpty())
					{
						split = read.split(",");
						if(split.length == 8 && null != split[7] && !split[7].trim().isEmpty()
								&& null != split[0] && !split[0].trim().isEmpty())
						{
							poiToScoreMap.put(Integer.parseInt(split[0].trim()), Double.parseDouble(split[7].trim()));
						}
					}
				}
				sc.close();
				
				Integer poiID = null;
				Integer poiShadowID = null;
				Double poiScore = null;
				for(Map.Entry<Integer, Integer> entry : poiToPoiShadowMap.entrySet())
				{
					poiID = entry.getKey();
					poiShadowID = entry.getValue();
					if(null != poiID && null != poiShadowID)
					{
						if(poiToScoreMap.containsKey(poiID))
						{
							poiScore = poiToScoreMap.get(poiID);
							if(null != poiScore)
							{
								xmlw.writeStartElement(ELEMENT_SEARCH_POPULARITY_TYPE);
								xmlw.writeAttribute(ATTRIBUTE_ID, Integer.toString(poiShadowID));
								xmlw.writeAttribute(ATTRIBUTE_SCORE, Double.toString(poiScore));
								xmlw.writeEndElement();
								xmlw.writeCharacters(CRLF);
							}
						}
					}
					poiScore = null;
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
			finally
			{
				//essDataFormat.safelyCloseStreams(xmlw, fStream, zStream);	
				essDataFormat.safelyCloseStreams(xmlw, fStream);
			}
			System.out.println(count);
			
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
		
		//private void safelyCloseStreams(XMLStreamWriter xmlw, FileOutputStream fStream, GZIPOutputStream zStream)
		private void safelyCloseStreams(XMLStreamWriter xmlw, FileOutputStream fStream)
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