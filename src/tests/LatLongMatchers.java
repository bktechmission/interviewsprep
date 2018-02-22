package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import dataanalysis.GeoCode;

public class LatLongMatchers {
	
	private static  String xmlDumpLocation = "/Users/bpanwar/Data/";
	private static final String UTF_8 = "UTF-8";
    private static final String ELEMENT_REGION = "region";
    private static final String ATTRIBUTE_REGIONID = "regionId";
    private static final String ATTRIBUTE_FEATUREID = "featureId";

    private static final String ELEMENT_REGIONLOCATION_ITEM = "regionLocation";
    
    private static final String ATTRIBUTE_LATITUDE = "latitude"; 

    private static final String ATTRIBUTE_LONGITUDE = "longitude";

	public static void main(String[] args)
	{
		Set<Integer> multicities = getMulticitiesIds();
		Map<Integer, GeoCode> regionLocations = getRegionLocations();
		
		Map<Integer, GeoCode> regionLocationsWithGaia = getRegionLocationsOfGaia();
		
		
		PrintWriter pw = null;
		PrintWriter pw_missing = null;
		PrintWriter pw_missing_gaia = null;
		PrintWriter pw_diff = null;
		PrintWriter pw_same = null;
		PrintWriter pw_withLargeDistance = null;
		try {
			pw = new PrintWriter(new File(xmlDumpLocation + "/esslocations/GEO_MulticityId_lat_long.csv"));
			pw_missing = new PrintWriter(new File(xmlDumpLocation + "/esslocations/GEO_MulticityId_missingLatLong.txt"));
			
			pw_missing_gaia = new PrintWriter(new File(xmlDumpLocation + "/esslocations/GEO_MulticityId_missingLatLongInGaia.txt"));
			pw_diff = new PrintWriter(new File(xmlDumpLocation + "/esslocations/GEO_Multicity_DIFF.csv"));
			pw_same = new PrintWriter(new File(xmlDumpLocation + "/esslocations/GEO_Multicity_SAME.csv"));
			pw_withLargeDistance = new PrintWriter(new File(xmlDumpLocation + "/esslocations/GEO_Multicity_Largethan50KMDiff.csv"));
			
			pw.println("MulticityId, Geo_Latitude, Geo_Longitude, Gaia_Latitude, Gaia_Longitude");
			pw_diff.println("MulticityId, Geo_Latitude, Geo_Longitude, Gaia_Latitude, Gaia_Longitude");
			pw_same.println("MulticityId, Geo_Latitude, Geo_Longitude, Gaia_Latitude, Gaia_Longitude");
			pw_withLargeDistance.println("MulticityId, Geo_Latitude, Geo_Longitude, Gaia_Latitude, Gaia_Longitude");
			
			int count = 0;
			for(Integer rid : multicities)
			{
				if(regionLocations.containsKey(rid))
				{
					GeoCode geocode = regionLocations.get(rid);
					
					if(regionLocationsWithGaia.containsKey(rid))
					{
						GeoCode geocodeGaia =  regionLocationsWithGaia.get(rid);
						pw.println(rid + "," + geocode.getLatitude() + "," + geocode.getLongitude() + "," + geocodeGaia.getLatitude() + "," + geocodeGaia.getLongitude());
						
						if(!geocode.equals(geocodeGaia))
						{
							if(geocode.distanceRadianTo(geocodeGaia) > 50.0)
							{
								count++;
								pw_withLargeDistance.println(rid + "," + geocode.getLatitude() + "," + geocode.getLongitude() + "," + geocodeGaia.getLatitude() + "," + geocodeGaia.getLongitude());
							}
							pw_diff.println(rid + "," + geocode.getLatitude() + "," + geocode.getLongitude() + "," + geocodeGaia.getLatitude() + "," + geocodeGaia.getLongitude());
							
						}
						else
						{							
							pw_same.println(rid + "," + geocode.getLatitude() + "," + geocode.getLongitude() + "," + geocodeGaia.getLatitude() + "," + geocodeGaia.getLongitude());
						}
						
					}
					else
					{
						pw_missing_gaia.println(rid);
					}
				}
				else
				{
					if(!regionLocationsWithGaia.containsKey(rid))
					{
						pw_missing.println(rid);
					}
				}
				
			}
			System.out.println("count is : " + count);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(pw != null)
			{
				pw.close();
			}
			if(pw_missing != null)
			{
				pw_missing.close();
			}
			if(pw_missing_gaia != null)
			{
				pw_missing_gaia.close();
			}
			
			if(pw_same != null)
			{
				pw_same.close();
			}
			
			if(pw_diff != null)
			{
				pw_diff.close();
			}
			if(pw_withLargeDistance != null)
			{
				pw_withLargeDistance.close();
			}
		}
	}

	
	public static Set<Integer> getMulticitiesIds()
	{
		Set<Integer> resultList = new HashSet<Integer>();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		// create a xml streaming reader for alias
		XMLStreamReader xmlr = null;
		FileInputStream fis = null;
		GZIPInputStream gis = null;
		String regionFileName = "";
		
		try
		{
			regionFileName = xmlDumpLocation + "/esslocations/MULTICITY-en_US.xml.gz";
			
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
					// if the current element is a alias node, add it to the trie
					if (xmlr.getLocalName().equals(ELEMENT_REGION))
					{
						int regionId = Integer.parseInt(xmlr.getAttributeValue(null, ATTRIBUTE_REGIONID));
						resultList.add(regionId);
					}
				}
			}
			System.out.println("Total number of Multicities are : " + resultList.size());
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
			ClosingUtil.safelyCloseStream(xmlr);
			ClosingUtil.closeFileAndGzipStreams(fis, gis);
		}
		return resultList;
	}
	
	
	public static Map<Integer, GeoCode> getRegionLocations()
	{
		Map<Integer, GeoCode> regionLocationMap = new HashMap<Integer, GeoCode>(); 
		XMLInputFactory factory = XMLInputFactory.newInstance();
		// create a xml streaming reader for alias
		XMLStreamReader xmlr = null;
		FileInputStream fis = null;
		GZIPInputStream gis = null;
		
		try
		{
			String fileName = xmlDumpLocation + "/esslocations/regionLocation.xml.gz";
			fis =  new FileInputStream(fileName);
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
			
			patchRegionLocation(regionLocationMap);	
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
			ClosingUtil.safelyCloseStream(xmlr);
			ClosingUtil.closeFileAndGzipStreams(fis, gis);
		}
		return regionLocationMap;
	}
	
	private static Map<Integer, GeoCode> getRegionLocationsOfGaia()
	{	
		
		Map<String, Integer> featureToRidMap = new HashMap<String, Integer>();
		XMLInputFactory factory = XMLInputFactory.newInstance();
		// create a xml streaming reader for alias
		XMLStreamReader xmlr = null;
		FileInputStream fis = null;
		GZIPInputStream gis = null;
		String regionFileName = "";
		
		try
		{
			regionFileName = xmlDumpLocation + "/MULTICITY-en_US.xml.gz";
			
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
					// if the current element is a alias node, add it to the trie
					if (xmlr.getLocalName().equals(ELEMENT_REGION))
					{
						int regionId = Integer.parseInt(xmlr.getAttributeValue(null, ATTRIBUTE_REGIONID));
						String featureId = xmlr.getAttributeValue(null, ATTRIBUTE_FEATUREID);
						featureToRidMap.put(featureId,regionId);
					}
				}
			}
			System.out.println("Total number of Feature to Multicities are : " + featureToRidMap.size());
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
			ClosingUtil.safelyCloseStream(xmlr);
			ClosingUtil.closeFileAndGzipStreams(fis, gis);
		}
		
		
		BufferedReader br =  null;
		Map<Integer, GeoCode> regionLocationMap = new HashMap<Integer, GeoCode>();
		try
		{
			br = new BufferedReader(new FileReader(xmlDumpLocation + "/GaiaMultiCityInfo.csv"));
			String line = br.readLine();
			
			while((line = br.readLine()) != null)
			{						
				String[] data = line.split(",");
				
				//verify the data
				if( data.length==3 )
				{
					String featureId = data[0];
					String latitude = data[1];
					String longitude = data[2];
					if(featureToRidMap.containsKey(featureId))
					{
						int regionId = featureToRidMap.get(featureId);
						GeoCode geoCode = new GeoCode(latitude, longitude);
						regionLocationMap.put(regionId, geoCode);	
					}			
				}
			}
			System.out.println("Total number of Gaia Multicities locations are : " + regionLocationMap.size());
		}
		catch(Exception e)
		{
			//This is an optional data patch, just consume the exception here
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				br.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();				
			}
		}
		return regionLocationMap;
	}
	
	/**
	 * This patches the missing lat-lng to the existing regionLocationMap
	 * 
	 * @param regionLocationMap
	 */
	private static void patchRegionLocation(Map<Integer, GeoCode> regionLocationMap)
	{		
		if(regionLocationMap==null)
		{
			return;
		}
			
		BufferedReader br =  null;
		
		try
		{
			br = new BufferedReader(new FileReader(xmlDumpLocation + "/esslocations/regionLocationPatch.csv"));
			String line = br.readLine();
			
			while((line = br.readLine()) != null)
			{						
				String[] data = line.split(",");
				
				//verify the data
				if( data.length==3 )
				{
					int regionId = Integer.parseInt(data[0]);
					String latitude = data[1];
					String longitude = data[2];
					
					GeoCode geoCode = new GeoCode(latitude, longitude);
					regionLocationMap.put(regionId, geoCode);			
				}
			}			
		}
		catch(Exception e)
		{
			//This is an optional data patch, just consume the exception here
			e.printStackTrace();
		}
		finally
		{
			try 
			{
				br.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();				
			}
		}	
	}
	


}
