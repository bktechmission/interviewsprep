package acclickdata;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

import bodata.EssLocale;
import bodata.LOB;

public class GenerateACClickData {
	 private static final String UTF_8 = "UTF-8";
	 private static final String CRLF = "\r\n";
	 private static final String GZIP_EXTENSION  = ".gz";
	 private static final String XML_DUMP_LOCATION = "C:\\sftp\\AC_Click\\XMLs\\";
	 private static final String XML_DUMP_FILE_REGION_POP = "acclickregionpopularity.xml";
	 
	 private static Pattern patternForLOB = Pattern.compile("InpLOB=[A-Z]+");
	 private static Pattern patternForLocale = Pattern.compile("InpLocale=[a-z]{2}_[A-Z]{2}"); 
	 private static Pattern patternACClick = Pattern.compile("InpRequestType=AC_CLICK");
	 private static Pattern patternRegionId = Pattern.compile("RegionId=[\\d]+");
	 private static Pattern patternIndex = Pattern.compile("Index=[\\d]+");
	 private static Pattern patternTerm = Pattern.compile("InpTerm=[\\w]+");
	 private static Pattern patternNumRequests = Pattern.compile("NumRequests=[\\d]+");
	 private static Pattern patternIsDest = Pattern.compile("InpDestFlag=(true|false)");
	 
	 private static final String ELEMENT_REGIONS_POPULARITY = "regionsPopularity";
	 private static final String ELEMENT_REGION_POPULARITY = "regionPopularity";
	 private static final String ATTRIBUTE_ID = "regionId";
	 private static final String ATTRIBUTE_ACCLICK_POPULARITY = "acclickPopularity";
	 private static final String ATTRIBUTE_LOCALE = "locale";
	 private static final String ATTRIBUTE_LOB = "lob";
	
	public static void main(String[] args) {
		try
		{
			// Create the remote directory path and check for its existence
			String remoteServer = "chc-filidx";
			String remoteDirectory = "ess";
			String remoteFileExtension = ".log.gz";
			//String remoteShare = "\\\\"+ remoteServer  + "\\" + remoteDirectory; 
			String remoteShare = "C:\\sftp\\AC_Click";
			File share = new File(remoteShare);
			int no_of_days = 7;
			if(!share.exists() || !share.isDirectory()) 
			{
				System.out.println("Specified remote share "+ remoteShare +" doesn't exist or not a directory");
				return;
			}
			genrateXmls(share, remoteFileExtension, no_of_days);
			
		}
		catch (IOException ex)
		{
			System.out.println("Unable to establish connection to remote server.");
			ex.printStackTrace();
			return;
		}
		
	}
	
	private static void genrateXmls(File dir, final String ext, int no_of_days) throws IOException
	{
		long now = System.currentTimeMillis(); 
		final long dayInMillis = 24 * 60 * 60 * 1000L;
		
    	
		try 
		{	
			// Get the list of files available in the remote directory
			File[] remoteFiles = dir.listFiles(new FilenameFilter() {
			    @Override
			    public boolean accept(File dir, String name) {
			        return name.startsWith("expediasuggest_") && name.endsWith(ext);
			    }
			});
			
			BufferedReader reader = null;
			GZIPInputStream zipReader = null;
			Map<EssLocale, Map<LOB, Map<Integer, Double>>> localizedLOBRegionPopularity = 
					new HashMap<EssLocale, Map<LOB, Map<Integer, Double>>>();
			
			Map<LOB, Map<Integer, Double>> lobRegionPopularity = new HashMap<LOB, Map<Integer, Double>>();
			Map<Integer, Double> regionPopularity = new HashMap<Integer, Double>();
			
			Map<Integer, Double> cost = new HashMap<Integer, Double>();
			System.out.println("Start building Maps ...");
	        for(int i = 0; i < no_of_days; i++)
	        {
				for(File file : remoteFiles) 
				{
					if(file.isFile()) 
					{
						// Check if the file is older than the required policy 
						
						long mt = file.lastModified();
			        	if ((now - mt) >= ( (i + 1) * dayInMillis)
				        		&& (now - mt) < ( (i+2) * dayInMillis)) 
				        {
				        	//System.out.println("Checking File for Old Days Policy : " + file.getName() + new Date(mt));
				        	zipReader = new GZIPInputStream(new FileInputStream(file));
				        	reader = new BufferedReader(new InputStreamReader(zipReader, "UTF-8"));
				        	//reader = new BufferedReader(new FileReader("C:\\sftp\\expediasuggest_CHEXJVAESS002_com.expedia.e3.cx.service.expediasuggest_2013-04-08-22-0000005.log"));
				        	
				        	try
				        	{
				        		String newLine=null;
				        		while(null != (newLine=reader.readLine()))
				        		{
				        			newLine = newLine.trim();
			        				Matcher matcherACClick = patternACClick.matcher(newLine);
			        				
			        				Matcher matcherNumRequests = patternNumRequests.matcher(newLine);
			        				
			        				if(matcherACClick.find() && matcherNumRequests.find())
			        				{
			        					Matcher matcherForLOB = patternForLOB.matcher(newLine);
				        				Matcher matcherForLocale = patternForLocale.matcher(newLine);
				        				Matcher matcherRegionId = patternRegionId.matcher(newLine);
				        				Matcher matcherIndex = patternIndex.matcher(newLine);
				        				Matcher matcherTerm = patternTerm.matcher(newLine);
				        				Matcher matcherIsDest = patternIsDest.matcher(newLine);
				        				
			        					if(matcherForLOB.find() && matcherForLocale.find()
				        						&& matcherRegionId.find() && matcherIndex.find() && matcherTerm.find()
				        						&& matcherIsDest.find())
				        				{

				        					//String rawACClick = matcherACClick.group().trim();
				        					String rawRegionId = matcherRegionId.group().trim();
				        					String rawLOB = matcherForLOB.group().trim();
				        					String rawLocale = matcherForLocale.group().trim();
				        					String rawIndex = matcherIndex.group().trim();
				        					//String rawTerm = matcherTerm.group().trim();
				        					String rawNumRequest = matcherNumRequests.group().trim();
				        					String rawIsDebug = matcherIsDest.group().trim();
				        					
				        					//System.out.println("Raw AC_Click : " + rawACClick);
				        					//System.out.println("Raw RegionId : " + rawRegionId);
				        					//System.out.println("Raw LOB : " + rawLOB);
				        					//System.out.println("Raw Locale : " + rawLocale);
				        					//System.out.println("Raw Index : " + rawIndex);
				        					//System.out.println("Raw Term : "+ rawTerm);
				        					//String[] splitTerm = rawTerm.split("=");
				        					//System.out.println("Split term is : " + splitTerm[1].trim());
			
				        					//System.out.println("Raw Term Length : "+ (splitTerm[1].trim().length()-2));
				        					//System.out.println("Raw rawNumRequest : "+ rawNumRequest);
				        					//System.out.println("Is Dest : " + rawIsDebug);
				        					EssLocale locale = getEssLocale(rawLocale);
				        					LOB lob = getEssLOB(rawLOB);
				        					int rid = getRID(rawRegionId);
				        					//System.out.println("Locale: " + locale);
			        						//System.out.println("LOB : " + lob);
			        						//System.out.println("RID : " + rid);
				        					if(null != locale && null != lob && 0 != rid)
				        					{
				        						if(!localizedLOBRegionPopularity.containsKey(locale))
				        						{
				        							lobRegionPopularity = new HashMap<LOB, Map<Integer, Double>>();
				        							localizedLOBRegionPopularity.put(locale, lobRegionPopularity);
				        						}
				        						lobRegionPopularity = localizedLOBRegionPopularity.get(locale);
				        						
				        						if(!lobRegionPopularity.containsKey(lob))
				        						{
				        							regionPopularity = new HashMap<Integer, Double>();
				        							lobRegionPopularity.put(lob, regionPopularity);
				        						}
				        						regionPopularity = lobRegionPopularity.get(lob);
				        						
				        						if(!regionPopularity.containsKey(rid))
				        						{
				        							regionPopularity.put(rid, 0.0);
				        						}
				        						double pop = regionPopularity.get(rid);
				        						regionPopularity.put(rid, pop+1);
				        						
				        					}
				        					
				        					String[] splitInd = rawIndex.split("=");
				        					String[] splitReq = rawNumRequest.split("=");
				        					double costVal = Double.parseDouble(splitInd[1].trim()) 
				        											+ Double.parseDouble(splitReq[1].trim());
				        					if(!cost.containsKey(i+1))
				        					{
				        						cost.put(i+1, 0.0);
				        						System.out.println("Adding Cost for indx " + (i+1));
				        					}
				        					double netCost = cost.get(i+1) + costVal;
				        					cost.put(i+1, netCost);
				        				}
			        				}
				        		}
				        	}
				        	catch(IOException ex)
				        	{
				        		ex.printStackTrace();
				        	}
				        	
				        }      
					}
				}
				System.out.println("Done for " + (i+1) + " Day");
	        }
			System.out.println("End Building Map...");
			System.out.println("Start Building XML...");
			writeBORegionPopularityXMLs(localizedLOBRegionPopularity);
			System.out.println("Done Building XML...");
			System.out.println("Cost for " + no_of_days + " is : \n");
			for(Map.Entry<Integer, Double> entry : cost.entrySet())
			{
				System.out.println("For Day : " + entry.getKey() + " cost is : " + entry.getValue());
			}
		
		}
		catch (SecurityException e)
		{
			e.printStackTrace();
		}
	}
	
	private static EssLocale getEssLocale(String rawLocale)
	{
		String[] split = rawLocale.split("=");
		return EssLocale.valueOf(split[1].trim());
		
	}
	
	private static LOB getEssLOB(String rawLOB)
	{
		String[] split = rawLOB.split("=");
		String lob = split[1].trim();
		//System.out.println("LOB is  : " + lob);
		if(LOB.PACKAGES.name().equals(lob))
		{
			return LOB.PACKAGES;
		}
		else if(LOB.HOTELS.name().equals(lob))
		{
			return LOB.HOTELS;
		}
		else if(LOB.FLIGHTS.name().equals(lob))
		{
			return LOB.FLIGHTS;
		}
		else if(LOB.CARS.name().equals(lob))
		{
			return LOB.CARS;
		}
		return null;
	}
	
	private static int getRID(String rawRegionId)
	{
		String[] split = rawRegionId.split("=");
		String stringRid = split[1].trim();
		int rid = 0;
		if(!stringRid.isEmpty())
		{
			try
			{
				rid = Integer.parseInt(stringRid);
			}
			catch(NumberFormatException ex)
			{
				System.out.println("Number Format Exception");
				ex.printStackTrace();
			}
		}
		return rid;
	}
	

	public static void writeBORegionPopularityXMLs(Map<EssLocale, Map<LOB, Map<Integer, Double>>> regionPopularity) throws IOException
	{
		FileOutputStream fStream = null;
		GZIPOutputStream zStream = null;
		XMLStreamWriter xmlw = null;
		try
		{
			fStream = new FileOutputStream(XML_DUMP_LOCATION + XML_DUMP_FILE_REGION_POP + GZIP_EXTENSION);
			zStream = new GZIPOutputStream(new BufferedOutputStream(fStream));
			xmlw = createXMLStreamWriter(zStream, ELEMENT_REGIONS_POPULARITY);
			String locale;
			String lob;
			Double pop;
			
			for(Map.Entry<EssLocale, Map<LOB, Map<Integer, Double>>> localePopEntry : regionPopularity.entrySet())
			{
				EssLocale essLocaleKey = localePopEntry.getKey();
				Map<LOB, Map<Integer, Double>> lobData = localePopEntry.getValue();
				
				if(null != essLocaleKey && null != lobData)
				{
					for(Map.Entry<LOB, Map<Integer, Double>> lobPopEntry : lobData.entrySet())
					{
						LOB lobKey = lobPopEntry.getKey();
						Map<Integer, Double> regionData = lobPopEntry.getValue();
						if(null != lobKey && null != regionData)
						{
							for(Map.Entry<Integer, Double> regionPopEntry : regionData.entrySet())
							{
								int regionId = regionPopEntry.getKey();
								pop = regionPopEntry.getValue();
												
								locale = essLocaleKey.getLanguage() + "_" + essLocaleKey.getCountry();
								lob = lobKey.name();
								
								xmlw.writeStartElement(ELEMENT_REGION_POPULARITY);
								xmlw.writeAttribute(ATTRIBUTE_ID, Integer.toString(regionId));
								xmlw.writeAttribute(ATTRIBUTE_ACCLICK_POPULARITY, Double.toString(pop));
								xmlw.writeAttribute(ATTRIBUTE_LOCALE, locale);
								xmlw.writeAttribute(ATTRIBUTE_LOB, lob);
								xmlw.writeEndElement();
								xmlw.writeCharacters(CRLF);
							}
						}
					}
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
		finally
		{
			safelyCloseStreams(xmlw, fStream, zStream);	
		}
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

}
