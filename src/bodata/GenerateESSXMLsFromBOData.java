package bodata;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPOutputStream;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;


public class GenerateESSXMLsFromBOData 
{
		private static final String LOCATION = "C:\\sftp\\BO Popularity Data\\BOData";
		//private static final String CSV_REG_EX = ",(?=([^\"]*\"[^\"]*\")*[^\"]*$)";
		public static final Pattern CAR_PATTERN = Pattern.compile("^CAR:[a-zA-Z]{3}$");
		public static final Pattern PACKAGE_ORIG_PATTERN = Pattern.compile("^PKG:[a-zA-Z]{3}$");
		public static final Pattern PACKAGE_DEST_PATTERN = Pattern.compile("^PKG:[a-zA-Z]{3}:[\\d]+$");
		public static final Pattern JUST_TLA_PATTERN = Pattern.compile("^[a-zA-Z]{3}$");
		public static final Pattern JUST_DIGIT_PATTERN = Pattern.compile("^[\\d]+$");
		
		 private static final String UTF_8 = "UTF-8";
		 private static final String CRLF = "\r\n";
		 private static final String GZIP_EXTENSION  = ".gz";
		 private static final String XML_DUMP_LOCATION = "C:\\sftp\\BO Popularity Data\\XMLs\\";
		 private static final String XML_DUMP_FILE_REGION_POP = "boregionpopularity.xml";
		 private static final String XML_DUMP_FILE_REGION_TLA_POP = "boregiontlapopularity.xml";
		 private static final String XML_DUMP_FILE_TLA_TLA_POP = "botlatlapopularity.xml";
		 
		 private static final String ELEMENT_REGIONS_POPULARITY = "regionsPopularity";
		 private static final String ELEMENT_REGION_POPULARITY = "regionPopularity";
		 
		 private static final String ELEMENT_REGIONS_TLAS_POPULARITY = "regionsTlasPopularity";
		 private static final String ELEMENT_REGION_TLA_POPULARITY = "regionTlaPopularity";
		 
		 private static final String ELEMENT_TLAS_POPULARITY = "tlasPopularity";
		 private static final String ELEMENT_TLA_POPULARITY = "tlaPopularity";
		 
		 private static final String ATTRIBUTE_ID = "regionId";
		 private static final String ATTRIBUTE_SEARCH_POPULARITY = "searchPopularity";
		 private static final String ATTRIBUTE_BOOKING_POPULARITY = "bookingPopularity";
		 private static final String ATTRIBUTE_LOCALE = "locale";
		 private static final String ATTRIBUTE_LOB = "lob";
		 
		 private static final String ATTRIBUTE_ORIG_TLA = "origTla";
		 private static final String ATTRIBUTE_DEST_TLA = "destTla";
		 
		 public static Map<String, Integer> getSiteIdtoSiteNameMap()
		 {
			 Map<String, Integer> sitenametositeidmap = new HashMap<String, Integer>();
			 sitenametositeidmap.put("expedia.com", 1);
			 sitenametositeidmap.put("expedia.uk", 3);
			 sitenametositeidmap.put("expedia.ca", 4);
			 sitenametositeidmap.put("expedia.de", 6);
			 sitenametositeidmap.put("expedia.it", 8);
			 sitenametositeidmap.put("expedia.es", 9);
			 sitenametositeidmap.put("expedia.nl", 11);
			 sitenametositeidmap.put("expedia.mx", 12);
			 sitenametositeidmap.put("expedia.sg", 14);
			 sitenametositeidmap.put("expedia.my", 15);
			 sitenametositeidmap.put("expedia.kr", 16);
			 sitenametositeidmap.put("expedia.th", 17);
			 sitenametositeidmap.put("expedia.hk", 18);
			 sitenametositeidmap.put("expedia.fr", 20);
			 sitenametositeidmap.put("expedia.au", 25);
			 sitenametositeidmap.put("expedia.in", 27);
			 sitenametositeidmap.put("expedia.jp", 28);
			 sitenametositeidmap.put("expedia.nz", 29);
			 sitenametositeidmap.put("expedia.id", 61);
			 sitenametositeidmap.put("expedia.tw", 62);
			 sitenametositeidmap.put("expedia.ie", 63);
			 sitenametositeidmap.put("expedia.be", 64);
			 sitenametositeidmap.put("expedia.se", 65);
			 sitenametositeidmap.put("expedia.no", 66);
			 sitenametositeidmap.put("expedia.dk", 67);
			 sitenametositeidmap.put("expedia.ph", 68);
			 sitenametositeidmap.put("expedia.br", 69);
			 sitenametositeidmap.put("expedia.ar", 70);
			 sitenametositeidmap.put("expedia.vn", 71);
			 sitenametositeidmap.put("expedia.fi", 73);
			 sitenametositeidmap.put("expedia.at", 10122006);
			 //sitenametositeidmap.put("expedia.aarp", 10122009);
			// sitenametositeidmap.put("expedia.td", 10122010);
			 return sitenametositeidmap;

		 }
		 
		 public static Map<String, Integer> getSiteIdToSLocaleMap()
		 {
			 Map<String, Integer> localeToSiteName = new HashMap<String, Integer>();
			 localeToSiteName.put("de_DE", 6);
			 localeToSiteName.put("it_IT", 8);
			 localeToSiteName.put("fr_FR", 20);
			 localeToSiteName.put("nl_NL", 11);
			 localeToSiteName.put("es_ES", 9);
			 localeToSiteName.put("da_DK", 67);
			 localeToSiteName.put("nb_NO", 66);
			 localeToSiteName.put("sv_SE", 65);
			 localeToSiteName.put("nl_BE", 64);
			 localeToSiteName.put("fr_BE", 64);
			 localeToSiteName.put("fr_CA", 4);
			 localeToSiteName.put("es_MX", 12);
			 localeToSiteName.put("ja_JP", 28);
			 localeToSiteName.put("en_AU", 25);
			 localeToSiteName.put("en_NZ", 29);
			 localeToSiteName.put("en_IN", 27);
			 localeToSiteName.put("en_IE", 63);
			 localeToSiteName.put("en_CA", 4);
			 localeToSiteName.put("en_GB", 3);
			 localeToSiteName.put("en_US", 1);
			 localeToSiteName.put("de_AT", 10122006);
			 localeToSiteName.put("en_HK", 18);
			 localeToSiteName.put("vi_VN", 71);
			 localeToSiteName.put("zh_TW", 62);
			 localeToSiteName.put("en_SG", 14);
			 localeToSiteName.put("es_AR", 70);
			 localeToSiteName.put("pt_BR", 69);
			 localeToSiteName.put("en_PH", 68);
			 localeToSiteName.put("th_TH", 17);
			 localeToSiteName.put("ms_MY", 15);
			 localeToSiteName.put("en_MY", 15);
			 localeToSiteName.put("zh_HK", 18);
			 localeToSiteName.put("ko_KR", 16);
			 localeToSiteName.put("tl_PH", 68);
			 localeToSiteName.put("id_ID", 61);
			 localeToSiteName.put("zh_CN", 3208);
			 localeToSiteName.put("it_CH", 72);
			 localeToSiteName.put("de_CH", 72);
			 localeToSiteName.put("fr_CH", 72);
			 localeToSiteName.put("fi_FI", 73);
			// localeToSiteName.put("fk_FK", 10122009);
			// localeToSiteName.put("fk_PK", 10122010);
			 
			 return localeToSiteName;
		 }
		 
		 
		 public static Map<String, List<String>> getSiteNameToLocales(Map<String, Integer> localeToSiteIdMap,
				 Map<String, Integer> sitenameToSiteId)
		 {
			 Map<Integer, List<String>> siteIdToLocalesMap = new HashMap<Integer, List<String>>();
			 List<String> locales = null;
			 for(Map.Entry<String, Integer> entry : localeToSiteIdMap.entrySet())
			 {
				 String k = entry.getKey();
				 Integer v = entry.getValue();
				 
				 if(!siteIdToLocalesMap.containsKey(v))
				 {
					 locales = new ArrayList<String>();
					 siteIdToLocalesMap.put(v, locales);
				 }
				 
				 locales = siteIdToLocalesMap.get(v);
				 if(!locales.contains(k))
				 {
					 locales.add(k);
				 }
				 
			 }
			 Map<String, List<String>> sitenameToLocales = new HashMap<String, List<String>>();
			 for(Map.Entry<String, Integer> entry : sitenameToSiteId.entrySet())
			 {
				 String k = entry.getKey();
				 Integer v = entry.getValue();
				 
				 if(siteIdToLocalesMap.containsKey(v))
				 {
					 sitenameToLocales.put(k, siteIdToLocalesMap.get(v));
				 }
			 }
			 
			 return sitenameToLocales;
		 }
		 
		 
		 public static LOB getLineOfBusinessFromString(String lobString)
		 {

			 if("HOTELS".equals(lobString))
			 {
				 return LOB.HOTELS;
			 }
			 
			 if("CARS".equals(lobString))
			 {
				 return LOB.CARS;
			 }
			 
			 if("PACKAGE".equals(lobString))
			 {
				 return LOB.PACKAGES;
			 }
			 
			 if("FLIGHT".equals(lobString))
			 {
				 return LOB.FLIGHTS;
			 }
			 
			 return null;
		 }
				 
		public static void main(String[] args)
		{
			BufferedReader reader = null;
			Map<String, Integer> localeToSiteIdMap = getSiteIdToSLocaleMap();
			Map<String, Integer> sitenameToSiteId = getSiteIdtoSiteNameMap();
			
			Map<String, List<String>> sitenameToLocales = getSiteNameToLocales(localeToSiteIdMap, sitenameToSiteId);
			
			try 
			{
				Map<EssLocale, Map<LOB, Map<Integer, BOPopularity>>> regionPopularity = 
						new HashMap<EssLocale, Map<LOB, Map<Integer, BOPopularity>>>();
				
				Map<EssLocale, Map<LOB, Map<Integer, Map<String, BOPopularity>>>> regionTLAPopularity = 
						new HashMap<EssLocale, Map<LOB, Map<Integer, Map<String, BOPopularity>>>>();
				
				Map<EssLocale, Map<LOB, Map<String, Map<String, BOPopularity>>>> origTLAPopularity =
						new HashMap<EssLocale, Map<LOB, Map<String, Map<String, BOPopularity>>>>();
				
				/*
				 *  First Starts the Search Counts
				 */
				
				//File file = new File(LOCATION + "\\" + "all_search_counts_20130320.tsv");
				//System.out.println("File with path is : " + file.getName());
				reader = new BufferedReader(new FileReader(LOCATION + "\\" + "all_search_counts_20130320.tsv"));
				
				String readLine = null;
				String[] split = null;
				
				//Skip the First Line as Headers
				reader.readLine();
				
				String sitename = "";
				String origin = "";
				String destination = "";
				String lobString = "";
				String count = "";
				
				System.out.println("Starts Search Counts Mapping");
				
				while(null != (readLine = reader.readLine()))
				{
					split = readLine.split("\\t");
					
					sitename = split[0];
					
					origin = split[1];
					
					destination = split[2];
					
					lobString = split[3];
					
					count = split[4];
					
					if(null == sitename || sitename.trim().isEmpty()
							|| null == lobString || lobString.trim().isEmpty())
					{
						continue;
					}
					
					lobString = lobString.trim();

					LOB lob = getLineOfBusinessFromString(lobString);
					
					if(null == lob)
					{
						continue;
					}
					
					
					if(LOB.PACKAGES.equals(lob))
					{
						updatePackagesRelatedMapsData("SEARCH", sitename, lob, origin, destination, count,
								regionPopularity, regionTLAPopularity, origTLAPopularity, sitenameToLocales);
					}
					else if(LOB.CARS.equals(lob))
					{
						updateCarsRelatedMapsData("SEARCH", sitename, lob, origin, destination, count,
								origTLAPopularity, sitenameToLocales);
					}
					else if(LOB.FLIGHTS.equals(lob))
					{
						updateFlightsRelatedMapsData("SEARCH", sitename, lob, origin, destination, count,
								origTLAPopularity, sitenameToLocales);
					}
					
					else if(LOB.HOTELS.equals(lob))
					{
						updateHotelsRelatedMapsData("SEARCH", sitename, lob, origin, destination, count,
								regionPopularity, sitenameToLocales);
					}
				} // Ending Search Counts
				
				reader.close();
				
				System.out.println("Ends Search Counts Maping");
				
				//-----------------------------------------------------------------------------------------------
				
				/*
				 *  Now starts Booking Counts
				 */
				reader = new BufferedReader(new FileReader(LOCATION + "\\" + "all_booking_counts_20130320.tsv"));
				
				//Skip the First Line as Headers
				reader.readLine();
				
				System.out.println("Starts Booking Counts Maping");
				
				while(null != (readLine = reader.readLine()))
				{
					split = readLine.split("\\t");
					
					sitename = split[0];
					
					origin = split[1];
					
					destination = split[2];
					
					lobString = split[3];
					
					count = split[4];
					
					if(null == sitename || sitename.trim().isEmpty()
							|| null == lobString || lobString.trim().isEmpty())
					{
						continue;
					}
					
					lobString = lobString.trim();
					LOB lob = getLineOfBusinessFromString(lobString);
					
					if(null == lob)
					{
						continue;
					}
					
					
					if(LOB.PACKAGES.equals(lob))
					{						
						updatePackagesRelatedMapsData("BOOKING", sitename, lob, origin, destination, count,
								regionPopularity, regionTLAPopularity, origTLAPopularity, sitenameToLocales);
					}
					else if(LOB.CARS.equals(lob))
					{
						updateCarsRelatedMapsData("BOOKING", sitename, lob, origin, destination, count,
								origTLAPopularity, sitenameToLocales);
					}
					else if(LOB.FLIGHTS.equals(lob))
					{
						updateFlightsRelatedMapsData("BOOKING", sitename, lob, origin, destination, count,
								origTLAPopularity, sitenameToLocales);
					}
					
					else if(LOB.HOTELS.equals(lob))
					{
						updateHotelsRelatedMapsData("BOOKING", sitename, lob, origin, destination, count,
								regionPopularity, sitenameToLocales);
					}
				} // Ending Booking Counts
				
				reader.close();
				
				System.out.println("Ends Booking Counts Maping");
				
				/***************** MAPPINGS DONE, Starting Building XML Files  **************/
				System.out.println("Building XMLs ...");
				writeBORegionPopularityXMLs(regionPopularity);
				writeBORegionTlaPopularityXMLs(regionTLAPopularity);
				writeBOTlaTlaPopularityXMLs(origTLAPopularity);
				System.out.println("XMLs Done !");
				
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			finally
			{
				close(reader);
			}
			 
		}
		
		public static void updatePackagesRelatedMapsData(String sorb, String sitename, LOB lob, String origin, String destination,
				String count, Map<EssLocale, Map<LOB, Map<Integer, BOPopularity>>> regionPopularity,
				Map<EssLocale, Map<LOB, Map<Integer, Map<String, BOPopularity>>>> regionTLAPopularity,
				Map<EssLocale, Map<LOB, Map<String, Map<String, BOPopularity>>>> origTLAPopularity,
				Map<String, List<String>> sitenameToLocales)
				
		{
			sitename = sitename.trim().toLowerCase(EssLocale.US.getLocale());
			
			List<String> localesList = new ArrayList<String>();
			
			if(sitenameToLocales.containsKey(sitename))
			{
				localesList = sitenameToLocales.get(sitename);
			}
			
			Map<LOB, Map<Integer, BOPopularity>> lobMap;
			
			Map<Integer, BOPopularity> region;
	
			for(String localeString : localesList)
			{
				EssLocale locale = EssLocale.valueOf(localeString);
				
				/*********** Region Popularity Mapping with Locales and LOB ************/
				
				if(!regionPopularity.containsKey(locale))
				{
					lobMap = new HashMap<LOB, Map<Integer, BOPopularity>>();
					regionPopularity.put(locale, lobMap);
				}
				lobMap = regionPopularity.get(locale);
				
				if(!lobMap.containsKey(lob))
				{
					region = new HashMap<Integer, BOPopularity>();
					lobMap.put(lob, region);
				}
				region = lobMap.get(lob);
				
				
				
				/*********** Region TLA Popularity Mapping with Locales and LOB ************/
				Map<LOB, Map<Integer, Map<String, BOPopularity>>> lobMapRetionTla;
				if(!regionTLAPopularity.containsKey(locale))
				{
					lobMapRetionTla = new HashMap<LOB, Map<Integer, Map<String, BOPopularity>>>();
					regionTLAPopularity.put(locale, lobMapRetionTla);
				}
				lobMapRetionTla = regionTLAPopularity.get(locale);
						
				Map<Integer, Map<String, BOPopularity>> regionTla;
				if(!lobMapRetionTla.containsKey(lob))
				{
					regionTla = new HashMap<Integer, Map<String, BOPopularity>>();
					lobMapRetionTla.put(lob, regionTla);
				}
				regionTla = lobMapRetionTla.get(lob);
				
				
				
				/*********** TLA-TLA Popularity Mapping with Locales and LOB ************/
				Map<LOB, Map<String, Map<String, BOPopularity>>> lobMapTlaTla;
				if(!origTLAPopularity.containsKey(locale))
				{
					lobMapTlaTla = new HashMap<LOB, Map<String, Map<String, BOPopularity>>>();
					origTLAPopularity.put(locale, lobMapTlaTla);
				}
				lobMapTlaTla = origTLAPopularity.get(locale);
						
				Map<String, Map<String, BOPopularity>> tlaTla;
				if(!lobMapTlaTla.containsKey(lob))
				{
					tlaTla = new HashMap<String, Map<String, BOPopularity>>();
					lobMapTlaTla.put(lob, tlaTla);
				}
				tlaTla = lobMapTlaTla.get(lob);
				
				try
				{
					setPackagesMapsData(sorb, region, regionTla, tlaTla, origin, destination, "PACKAGE", count);
				}
				catch (NumberFormatException e) 
				{	
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		public static void updateHotelsRelatedMapsData(String sorb, String sitename, LOB lob, String origin, String destination,
				String count, Map<EssLocale, Map<LOB, Map<Integer, BOPopularity>>> regionPopularity,
				Map<String, List<String>> sitenameToLocales)
				
		{
			sitename = sitename.trim().toLowerCase(EssLocale.US.getLocale());
			
			List<String> localesList = new ArrayList<String>();
			
			if(sitenameToLocales.containsKey(sitename))
			{
				localesList = sitenameToLocales.get(sitename);
			}
			
			Map<LOB, Map<Integer, BOPopularity>> lobMap;
			
			Map<Integer, BOPopularity> region;
			
			for(String localeString : localesList)
			{
				EssLocale locale = EssLocale.valueOf(localeString);
				
				if(!regionPopularity.containsKey(locale))
				{
					lobMap = new HashMap<LOB, Map<Integer, BOPopularity>>();
					regionPopularity.put(locale, lobMap);
				}
				lobMap = regionPopularity.get(locale);
				
				if(!lobMap.containsKey(lob))
				{
					region = new HashMap<Integer, BOPopularity>();
					lobMap.put(lob, region);
				}
				
				region = lobMap.get(lob);
				
				
				try
				{
					setHotelsMapsData(sorb, region, origin, destination, "HOTELS", count);
				}
				catch (NumberFormatException e) 
				{	
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		public static void updateFlightsRelatedMapsData(String sorb, String sitename, LOB lob, String origin, String destination,
				String count, Map<EssLocale, Map<LOB, Map<String, Map<String, BOPopularity>>>> origTLAPopularity,
				Map<String, List<String>> sitenameToLocales)
				
		{
			
			sitename = sitename.trim().toLowerCase(EssLocale.US.getLocale());
			
			List<String> localesList = new ArrayList<String>();
			
			if(sitenameToLocales.containsKey(sitename))
			{
				localesList = sitenameToLocales.get(sitename);
			}
		
			for(String localeString : localesList)
			{
				EssLocale locale = EssLocale.valueOf(localeString);
				
				Map<LOB, Map<String, Map<String, BOPopularity>>> lobMapTlaTla;
				if(!origTLAPopularity.containsKey(locale))
				{
					lobMapTlaTla = new HashMap<LOB, Map<String, Map<String, BOPopularity>>>();
					origTLAPopularity.put(locale, lobMapTlaTla);
				}
				lobMapTlaTla = origTLAPopularity.get(locale);
						
				Map<String, Map<String, BOPopularity>> tlaTla;
				if(!lobMapTlaTla.containsKey(lob))
				{
					tlaTla = new HashMap<String, Map<String, BOPopularity>>();
					lobMapTlaTla.put(lob, tlaTla);
				}
				tlaTla = lobMapTlaTla.get(lob);
				
				
				/***************** For en_US FLIGHTS *********************/
				if(!lobMapTlaTla.containsKey(lob))
				{
					tlaTla = new HashMap<String, Map<String, BOPopularity>>();
					lobMapTlaTla.put(lob, tlaTla);
				}
				tlaTla = lobMapTlaTla.get(lob);
				
				try
				{
					setFlightsMapsData(sorb, tlaTla, origin, destination, "FLIGHT", count);
				}
				catch (NumberFormatException e) 
				{	
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		
		
		public static void updateCarsRelatedMapsData(String sorb, String sitename, LOB lob, String origin, String destination,
				String count, Map<EssLocale, Map<LOB, Map<String, Map<String, BOPopularity>>>> origTLAPopularity,
				Map<String, List<String>> sitenameToLocales)
				
		{
			
			sitename = sitename.trim().toLowerCase(EssLocale.US.getLocale());
			
			List<String> localesList = new ArrayList<String>();
			
			if(sitenameToLocales.containsKey(sitename))
			{
				localesList = sitenameToLocales.get(sitename);
			}

			for(String localeString : localesList)
			{
				EssLocale locale = EssLocale.valueOf(localeString);
				
				Map<LOB, Map<String, Map<String, BOPopularity>>> lobMapTlaTla;
				if(!origTLAPopularity.containsKey(locale))
				{
					lobMapTlaTla = new HashMap<LOB, Map<String, Map<String, BOPopularity>>>();
					origTLAPopularity.put(locale, lobMapTlaTla);
				}
				lobMapTlaTla = origTLAPopularity.get(locale);
						
				Map<String, Map<String, BOPopularity>> tlaTla;
				if(!lobMapTlaTla.containsKey(lob))
				{
					tlaTla = new HashMap<String, Map<String, BOPopularity>>();
					lobMapTlaTla.put(lob, tlaTla);
				}
				tlaTla = lobMapTlaTla.get(lob);

				if(!lobMapTlaTla.containsKey(lob))
				{
					tlaTla = new HashMap<String, Map<String, BOPopularity>>();
					lobMapTlaTla.put(lob, tlaTla);
				}
				tlaTla = lobMapTlaTla.get(lob);
				
				try
				{
					setCarsMapsData(sorb, tlaTla, origin, destination, "CARS", count);
				}
				catch (NumberFormatException e) 
				{	
					e.printStackTrace();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
		}
		

		public static void writeBORegionPopularityXMLs(Map<EssLocale, Map<LOB, Map<Integer, BOPopularity>>> regionPopularity) throws IOException
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
				BOPopularity boPop;
				
				for(Map.Entry<EssLocale, Map<LOB, Map<Integer, BOPopularity>>> localePopEntry : regionPopularity.entrySet())
				{
					EssLocale essLocaleKey = localePopEntry.getKey();
					Map<LOB, Map<Integer, BOPopularity>> lobData = localePopEntry.getValue();
					
					if(null != essLocaleKey && null != lobData)
					{
						for(Map.Entry<LOB, Map<Integer, BOPopularity>> lobPopEntry : lobData.entrySet())
						{
							LOB lobKey = lobPopEntry.getKey();
							Map<Integer, BOPopularity> regionData = lobPopEntry.getValue();
							if(null != lobKey && null != regionData)
							{
								for(Map.Entry<Integer, BOPopularity> regionPopEntry : regionData.entrySet())
								{
									int regionId = regionPopEntry.getKey();
									boPop = regionPopEntry.getValue();
									int searchCount = boPop.getSearchCount();
									int bookingCount = boPop.getBookingCount();
									
									locale = essLocaleKey.getLanguage() + "_" + essLocaleKey.getCountry();
									lob = lobKey.name();
									
									xmlw.writeStartElement(ELEMENT_REGION_POPULARITY);
									xmlw.writeAttribute(ATTRIBUTE_ID, Integer.toString(regionId));
									xmlw.writeAttribute(ATTRIBUTE_SEARCH_POPULARITY, Integer.toString(searchCount));
									xmlw.writeAttribute(ATTRIBUTE_BOOKING_POPULARITY, Integer.toString(bookingCount));
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
		
		public static void writeBORegionTlaPopularityXMLs(Map<EssLocale, Map<LOB, 
				Map<Integer, Map<String, BOPopularity>>>> regionTLAPopularity) throws IOException
		{
			FileOutputStream fStream = null;
			GZIPOutputStream zStream = null;
			XMLStreamWriter xmlw = null;
			try
			{
				fStream = new FileOutputStream(XML_DUMP_LOCATION + XML_DUMP_FILE_REGION_TLA_POP + GZIP_EXTENSION);
				zStream = new GZIPOutputStream(new BufferedOutputStream(fStream));
				xmlw = createXMLStreamWriter(zStream, ELEMENT_REGIONS_TLAS_POPULARITY);
				String locale;
				String lob;
				String tla;
				BOPopularity boPop;
				for(Map.Entry<EssLocale, Map<LOB, 
						Map<Integer, Map<String, BOPopularity>>>> localePopEntry : regionTLAPopularity.entrySet())
				{
					EssLocale essLocaleKey = localePopEntry.getKey();
					Map<LOB, Map<Integer, Map<String, BOPopularity>>> lobData = localePopEntry.getValue();
					
					if(null != essLocaleKey && null != lobData)
					{
						for(Map.Entry<LOB, Map<Integer, Map<String, BOPopularity>>> lobPopEntry : lobData.entrySet())
						{
							LOB lobKey = lobPopEntry.getKey();
							Map<Integer, Map<String, BOPopularity>> regionData = lobPopEntry.getValue();
							if(null != lobKey && null != regionData)
							{
								for(Map.Entry<Integer, Map<String, BOPopularity>> regionPopEntry : regionData.entrySet())
								{
									int regionId = regionPopEntry.getKey();
									Map<String, BOPopularity> tlaData = regionPopEntry.getValue();
									if(null != tlaData)
									{
										for(Map.Entry<String, BOPopularity> tlaPopEntry : tlaData.entrySet())
										{
											tla = tlaPopEntry.getKey();
											boPop = tlaPopEntry.getValue();
											int searchCount = boPop.getSearchCount();
											int bookingCount = boPop.getBookingCount();
											
											locale = essLocaleKey.getLanguage() + "_" + essLocaleKey.getCountry();
											lob = lobKey.name();
											
											xmlw.writeStartElement(ELEMENT_REGION_TLA_POPULARITY);
											xmlw.writeAttribute(ATTRIBUTE_ID, Integer.toString(regionId));
											xmlw.writeAttribute(ATTRIBUTE_DEST_TLA, tla);
											xmlw.writeAttribute(ATTRIBUTE_SEARCH_POPULARITY, Integer.toString(searchCount));
											xmlw.writeAttribute(ATTRIBUTE_BOOKING_POPULARITY, Integer.toString(bookingCount));
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
		
		public static void writeBOTlaTlaPopularityXMLs(Map<EssLocale, Map<LOB, 
				Map<String, Map<String, BOPopularity>>>> tlaTLAPopularity) throws IOException
		{
			FileOutputStream fStream = null;
			GZIPOutputStream zStream = null;
			XMLStreamWriter xmlw = null;
			try
			{
				fStream = new FileOutputStream(XML_DUMP_LOCATION + XML_DUMP_FILE_TLA_TLA_POP + GZIP_EXTENSION);
				zStream = new GZIPOutputStream(new BufferedOutputStream(fStream));
				xmlw = createXMLStreamWriter(zStream, ELEMENT_TLAS_POPULARITY);
				String locale;
				String lob;
				String origTla;
				String destTla;
				BOPopularity boPop;
				for(Map.Entry<EssLocale, Map<LOB, 
						Map<String, Map<String, BOPopularity>>>> localePopEntry : tlaTLAPopularity.entrySet())
				{
					EssLocale essLocaleKey = localePopEntry.getKey();
					Map<LOB, Map<String, Map<String, BOPopularity>>> lobData = localePopEntry.getValue();
					
					if(null != essLocaleKey && null != lobData)
					{
						for(Map.Entry<LOB, Map<String, Map<String, BOPopularity>>> lobPopEntry : lobData.entrySet())
						{
							LOB lobKey = lobPopEntry.getKey();
							Map<String, Map<String, BOPopularity>> origTlaData = lobPopEntry.getValue();
							if(null != lobKey && null != origTlaData)
							{
								for(Map.Entry<String, Map<String, BOPopularity>> regionPopEntry : origTlaData.entrySet())
								{
									origTla = regionPopEntry.getKey();
									Map<String, BOPopularity> tlaData = regionPopEntry.getValue();
									if(null != tlaData)
									{
										for(Map.Entry<String, BOPopularity> tlaPopEntry : tlaData.entrySet())
										{
											destTla = tlaPopEntry.getKey();
											boPop = tlaPopEntry.getValue();
											int searchCount = boPop.getSearchCount();
											int bookingCount = boPop.getBookingCount();
											
											locale = essLocaleKey.getLanguage() + "_" + essLocaleKey.getCountry();
											lob = lobKey.name();
											
											xmlw.writeStartElement(ELEMENT_TLA_POPULARITY);
											xmlw.writeAttribute(ATTRIBUTE_ORIG_TLA, origTla);
											xmlw.writeAttribute(ATTRIBUTE_DEST_TLA, destTla);
											xmlw.writeAttribute(ATTRIBUTE_SEARCH_POPULARITY, Integer.toString(searchCount));
											xmlw.writeAttribute(ATTRIBUTE_BOOKING_POPULARITY, Integer.toString(bookingCount));
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
		
		
		//private void safelyCloseStreams(XMLStreamWriter xmlw, FileOutputStream fStream, GZIPOutputStream zStream)
		private static void safelyCloseStreams(XMLStreamWriter xmlw, FileOutputStream fStream)
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
		
		public static void setPackagesMapsData(String sb, Map<Integer, BOPopularity> region,
				Map<Integer, Map<String, BOPopularity>> regionTla, Map<String, Map<String, BOPopularity>> tlaTla,
				String pkgOrig, String pkgDest, String pkg, String count)
						throws NumberFormatException, IOException
		{
			Map<String, BOPopularity> boPopTla;
			BOPopularity boPopularity;
					
			if(isNotNullAndNotEmpty(pkgOrig) && packageOrigValidator(pkgOrig)
					&& isNotNullAndNotEmpty(pkgDest) && packageDestValidator(pkgDest)
					&& isNotNullAndNotEmpty(pkg) &&  "PACKAGE".equalsIgnoreCase(pkg)
					&& isNotNullAndNotEmpty(count) && justDigitValidator(count))
			{
				String[] pkgOrigTlaSplit = pkgOrig.split(":");
				String[] pkgDestTlaRegionIdSplit = pkgDest.split(":");
						
				if(null != pkgOrigTlaSplit && pkgOrigTlaSplit.length==2 
					&& null != pkgDestTlaRegionIdSplit && pkgDestTlaRegionIdSplit.length==3)
				{
					String origTla = pkgOrigTlaSplit[1];
							
					String destTla = pkgDestTlaRegionIdSplit[1];
					String regionId = pkgDestTlaRegionIdSplit[2];
							
					if(isNotNullAndNotEmpty(origTla) && justTLAValidator(origTla)
						&& isNotNullAndNotEmpty(destTla) && justTLAValidator(destTla) 
						&& isNotNullAndNotEmpty(regionId) && justDigitValidator(regionId)
							)
					{
						int  serchOrbookingcount = Integer.parseInt(count);
						int regId = Integer.parseInt(regionId);
								
								
						/************* Region Id Popularity **************/
						if(!region.containsKey(regId))
						{
							boPopularity = new BOPopularity();
							region.put(regId, boPopularity);
						}
						boPopularity = region.get(regId);
								
						if("SEARCH".equalsIgnoreCase(sb))
						{
							int scount = boPopularity.getSearchCount() + serchOrbookingcount;
							boPopularity.setSearchCount(scount);
						}
						else if("BOOKING".equalsIgnoreCase(sb))
						{
							int bcount = boPopularity.getBookingCount() + serchOrbookingcount;
							boPopularity.setBookingCount(bcount);
						}
								
						/****** Region - TLA Mappings ***************/
						if(!regionTla.containsKey(regId))
						{
							boPopTla = new HashMap<String, BOPopularity>();
							regionTla.put(regId, boPopTla);
						}
								
						boPopTla = regionTla.get(regId);
								
						if(!boPopTla.containsKey(destTla))
						{
							boPopularity = new BOPopularity();
							boPopTla.put(destTla, boPopularity);
						}
								
						boPopularity = boPopTla.get(destTla);
								
						if("SEARCH".equalsIgnoreCase(sb))
						{
							int scount = boPopularity.getSearchCount() + serchOrbookingcount;
							boPopularity.setSearchCount(scount);
						}
						else if("BOOKING".equalsIgnoreCase(sb))
						{
							int bcount = boPopularity.getBookingCount() + serchOrbookingcount;
							boPopularity.setBookingCount(bcount);
						}
								
								
						/*************** TLA - TLA Mappings ***************/
						if(!tlaTla.containsKey(origTla))
						{
							boPopTla = new HashMap<String, BOPopularity>();
							tlaTla.put(origTla, boPopTla);
						}
								
						boPopTla = tlaTla.get(origTla);
								
						if(!boPopTla.containsKey(destTla))
						{
							boPopularity = new BOPopularity();
							boPopTla.put(destTla, boPopularity);
						}
								
						boPopularity = boPopTla.get(destTla);
								
						if("SEARCH".equalsIgnoreCase(sb))
						{
							int scount = boPopularity.getSearchCount() + serchOrbookingcount;
							boPopularity.setSearchCount(scount);
						}
						else if("BOOKING".equalsIgnoreCase(sb))
						{
							int bcount = boPopularity.getBookingCount() + serchOrbookingcount;
							boPopularity.setBookingCount(bcount);
						}
					}
				}
			} // End of PACKAGES Search Booking Count
		}
		
		public static void sortRegionPopularityMap(Map<Integer, BOPopularity> regionPopularityMap)
		{
			Map<BOPopularity, Set<Integer>> sortedMap = new TreeMap<BOPopularity, Set<Integer>>();
			if(null == regionPopularityMap || regionPopularityMap.size() == 0)
			{
				return;
			}
			
			Integer key;
			BOPopularity boPop;
			Set<Integer> ids;
			for(Map.Entry<Integer, BOPopularity> entry : regionPopularityMap.entrySet())
			{
				key = entry.getKey();
				boPop = entry.getValue();
				if(!sortedMap.containsKey(boPop))
				{
					ids = new HashSet<Integer>();
					sortedMap.put(boPop, ids);
				}
				ids = sortedMap.get(boPop);
				ids.add(key);
			}
			
			regionPopularityMap.clear();
			for(Map.Entry<BOPopularity, Set<Integer>> entry : sortedMap.entrySet())
			{
				boPop = entry.getKey();
				ids = entry.getValue();
				for(Integer k : ids)
				{
					regionPopularityMap.put(k, boPop);
				}
			}
		}
		
		public static void sortRegionTlaPopularityMap(Map<Integer, Map<String, BOPopularity>> regionTlaPopularityMap)
		{
			

			if(null == regionTlaPopularityMap || regionTlaPopularityMap.size() == 0)
			{
				return;
			}
			
			for(Map.Entry<Integer, Map<String, BOPopularity>> regionEntry : regionTlaPopularityMap.entrySet())
			{
				Map<String, BOPopularity> tlaPopularityMap = regionEntry.getValue();
				Integer rid = regionEntry.getKey();
				
				Map<BOPopularity, Set<String>> sortedMap = new TreeMap<BOPopularity, Set<String>>();
				
				String tla;
				BOPopularity boPop;
				Set<String> tlas;
				for(Map.Entry<String, BOPopularity> entry : tlaPopularityMap.entrySet())
				{
					tla = entry.getKey();
					boPop = entry.getValue();
					if(!sortedMap.containsKey(boPop))
					{
						tlas = new HashSet<String>();
						sortedMap.put(boPop, tlas);
					}
					tlas = sortedMap.get(boPop);
					tlas.add(tla);
				}
				
				tlaPopularityMap.clear();
				for(Map.Entry<BOPopularity, Set<String>> entry : sortedMap.entrySet())
				{
					boPop = entry.getKey();
					tlas = entry.getValue();
					for(String key : tlas)
					{
						tlaPopularityMap.put(key, boPop);
					}
				}
				regionTlaPopularityMap.put(rid, tlaPopularityMap);
			}
		}
		
		public static void sortTlaTlaPopularityMap(Map<String, Map<String, BOPopularity>> tlaTlaPopularityMap)
		{
			

			if(null == tlaTlaPopularityMap || tlaTlaPopularityMap.size() == 0)
			{
				return;
			}
			
			for(Map.Entry<String, Map<String, BOPopularity>> regionEntry : tlaTlaPopularityMap.entrySet())
			{
				Map<String, BOPopularity> tlaPopularityMap = regionEntry.getValue();
				String tlaKey= regionEntry.getKey();
				
				Map<BOPopularity, Set<String>> sortedMap = new TreeMap<BOPopularity, Set<String>>();
				
				String tla;
				BOPopularity boPop;
				Set<String> tlas;
				for(Map.Entry<String, BOPopularity> entry : tlaPopularityMap.entrySet())
				{
					tla = entry.getKey();
					boPop = entry.getValue();
					if(!sortedMap.containsKey(boPop))
					{
						tlas = new HashSet<String>();
						sortedMap.put(boPop, tlas);
					}
					tlas = sortedMap.get(boPop);
					tlas.add(tla);
				}
				
				tlaPopularityMap.clear();
				for(Map.Entry<BOPopularity, Set<String>> entry : sortedMap.entrySet())
				{
					boPop = entry.getKey();
					tlas = entry.getValue();
					for(String key : tlas)
					{
						tlaPopularityMap.put(key, boPop);
					}
				}
				tlaTlaPopularityMap.put(tlaKey, tlaPopularityMap);
			}
		}
		
		
		public static void setFlightsMapsData(String sorb, Map<String, Map<String, BOPopularity>> tlaTla,
				String origTla, String destTla, String flight, String count) 
				throws NumberFormatException, IOException
		{
			Map<String, BOPopularity> boPopTla;
			BOPopularity boPopularity;
			if(isNotNullAndNotEmpty(origTla) && justTLAValidator(origTla)
				&& isNotNullAndNotEmpty(destTla) && justTLAValidator(destTla)
				&& isNotNullAndNotEmpty(flight) &&  "FLIGHT".equalsIgnoreCase(flight)
				&& isNotNullAndNotEmpty(count) && justDigitValidator(count))
			{
				int  serchOrbookingcount = Integer.parseInt(count);
						
				/*************** TLA - TLA Mappings ***************/
				if(!tlaTla.containsKey(origTla))
				{
					boPopTla = new HashMap<String, BOPopularity>();
					tlaTla.put(origTla, boPopTla);
				}
						
				boPopTla = tlaTla.get(origTla);
						
				if(!boPopTla.containsKey(destTla))
				{
					boPopularity = new BOPopularity();
					boPopTla.put(destTla, boPopularity);
				}
						
				boPopularity = boPopTla.get(destTla);
						
				if("SEARCH".equalsIgnoreCase(sorb))
				{
					int scount = boPopularity.getSearchCount() + serchOrbookingcount;
					boPopularity.setSearchCount(scount);
				}
				else if("BOOKING".equalsIgnoreCase(sorb))
				{
					int bcount = boPopularity.getBookingCount() + serchOrbookingcount;
					boPopularity.setBookingCount(bcount);
				}
			}		
		}
		
		
		public static void setCarsMapsData(String sorb, Map<String, Map<String, BOPopularity>> tlaTla,
				String carsOrig, String carsDest, String cars, String count) throws NumberFormatException, IOException
		{
			Map<String, BOPopularity> boPopTla;
			BOPopularity boPopularity;
			if(isNotNullAndNotEmpty(carsOrig) && carsValidator(carsOrig)
				&& isNotNullAndNotEmpty(carsDest) && carsValidator(carsDest)
				&& isNotNullAndNotEmpty(cars) &&  "CARS".equalsIgnoreCase(cars)
				&& isNotNullAndNotEmpty(count) && justDigitValidator(count))
			{
				String[] carsOrigTlaSplit = carsOrig.split(":");
				String[] carsDestTlaSplit = carsDest.split(":");
						
				if(null != carsOrigTlaSplit && carsOrigTlaSplit.length==2 
					&& null != carsDestTlaSplit && carsDestTlaSplit.length==2)
				{
					String origTla = carsOrigTlaSplit[1];
							
					String destTla = carsDestTlaSplit[1];
							
					if(isNotNullAndNotEmpty(origTla) && justTLAValidator(origTla)
						&& isNotNullAndNotEmpty(destTla) && justTLAValidator(destTla))
					{
						int  serchOrbookingcount = Integer.parseInt(count);

								
						/*************** TLA - TLA Mappings ***************/
						if(!tlaTla.containsKey(origTla))
						{
							boPopTla = new HashMap<String, BOPopularity>();
							tlaTla.put(origTla, boPopTla);
						}
								
						boPopTla = tlaTla.get(origTla);
								
						if(!boPopTla.containsKey(destTla))
						{
							boPopularity = new BOPopularity();
							boPopTla.put(destTla, boPopularity);
						}
								
						boPopularity = boPopTla.get(destTla);
								
						if("SEARCH".equalsIgnoreCase(sorb))
						{
							int scount = boPopularity.getSearchCount() + serchOrbookingcount;
							boPopularity.setSearchCount(scount);
						}
						else if("BOOKING".equalsIgnoreCase(sorb))
						{
							int bcount = boPopularity.getBookingCount() + serchOrbookingcount;
							boPopularity.setBookingCount(bcount);
						}
					}
				}
			}
		}
		
		public static void setHotelsMapsData(String sb, Map<Integer, BOPopularity> region,
				String origin, String regionId, String hotels, String count) throws NumberFormatException, IOException
		{

			BOPopularity boPopularity;

			if(isNotNullAndNotEmpty(regionId) && justDigitValidator(regionId)
				&& isNotNullAndNotEmpty(hotels) &&  "HOTELS".equalsIgnoreCase(hotels)
				&& isNotNullAndNotEmpty(count) && justDigitValidator(count))
			{
				int  serchOrbookingcount = Integer.parseInt(count);
				int regId = Integer.parseInt(regionId);
						
						
				/************* Region Id Popularity **************/
				if(!region.containsKey(regId))
				{
					boPopularity = new BOPopularity();
					region.put(regId, boPopularity);
				}
				boPopularity = region.get(regId);
						
				if("SEARCH".equalsIgnoreCase(sb))
				{
					int scount = boPopularity.getSearchCount() + serchOrbookingcount;
					boPopularity.setSearchCount(scount);
				}
				else if("BOOKING".equalsIgnoreCase(sb))
				{
					int bcount = boPopularity.getBookingCount() + serchOrbookingcount;
					boPopularity.setBookingCount(bcount);
				}
			}
		}
		
		public static void close(BufferedReader reader)
		{
			if(null != reader)
			{
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		public static boolean isNotNullAndNotEmpty(String string)
		{
			if(null == string)
				return false;
			string = string.trim();
			if(string.isEmpty())
			{
				return false;
			}
			return true;
		}
		
		public static boolean carsValidator(String carString)
		{
			Matcher matcher = CAR_PATTERN.matcher(carString);
			return matcher.find();
		}
		
		public static boolean packageOrigValidator(String packageOrigString)
		{
			Matcher matcher = PACKAGE_ORIG_PATTERN.matcher(packageOrigString);
			return matcher.find();
		}
		
		public static boolean packageDestValidator(String packageDestString)
		{
			Matcher matcher = PACKAGE_DEST_PATTERN.matcher(packageDestString);
			return matcher.find();
		}
		
		public static boolean justTLAValidator(String justTLAString)
		{
			Matcher matcher = JUST_TLA_PATTERN.matcher(justTLAString);
			return matcher.find();
		}
		
		public static boolean justDigitValidator(String justDigittString)
		{
			Matcher matcher = JUST_DIGIT_PATTERN.matcher(justDigittString);
			return matcher.find();
		}
}
