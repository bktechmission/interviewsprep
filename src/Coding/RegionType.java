package Coding;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Enumeration of region types
 */
public enum RegionType implements Serializable
{
	AIRPORT(3, "AIRPORT"),
	CITY(2, "CITY"),
	MULTICITY(0, "CITY"),
	NEIGHBORHOOD(1, "CITY"),
	POI(4, "ATTRACTION"),
	ADDRESS(5, "ADDRESS"),
	METROCODE(6, "AIRPORT"),
	HOTEL(7, "HOTEL"), 
	MULTIREGION(8, "MULTIREGION"),
	TRAINSTATION(9, "TRAINSTATION"), 
	METROSTATION(10, "METROSTATION"),
	UNKNOWN(Integer.MAX_VALUE, "");
	 
	// This is used as a default for v1 AC
	public static final int DEFAULT_V1_NON_HOTELS = (int)Math.pow(2,AIRPORT.ordinal())
			| (int)Math.pow(2,CITY.ordinal())
			| (int)Math.pow(2,MULTICITY.ordinal())
			| (int)Math.pow(2,NEIGHBORHOOD.ordinal())
			| (int)Math.pow(2,POI.ordinal())
			| (int)Math.pow(2,ADDRESS.ordinal())
			| (int)Math.pow(2,METROCODE.ordinal())
			| (int)Math.pow(2,MULTIREGION.ordinal())
			| (int)Math.pow(2,TRAINSTATION.ordinal())
			| (int)Math.pow(2,METROSTATION.ordinal());
	
	
	// This is used as a default for v2 and v3 AC
	public static final int DEFAULT_V2_AND_V3 = (int)Math.pow(2,AIRPORT.ordinal())
			| (int)Math.pow(2,CITY.ordinal())
			| (int)Math.pow(2,MULTICITY.ordinal())
			| (int)Math.pow(2,NEIGHBORHOOD.ordinal())
			| (int)Math.pow(2,POI.ordinal())
			| (int)Math.pow(2,ADDRESS.ordinal())
			| (int)Math.pow(2,METROCODE.ordinal())
			| (int)Math.pow(2,HOTEL.ordinal())
			| (int)Math.pow(2,MULTIREGION.ordinal())
			| (int)Math.pow(2,TRAINSTATION.ordinal())
			| (int)Math.pow(2,METROSTATION.ordinal());
	

	// ALL_CITIES = 14
	public static final int ALL_CITIES = (int)Math.pow(2,CITY.ordinal())
											| (int)Math.pow(2,MULTICITY.ordinal())
											| (int)Math.pow(2,NEIGHBORHOOD.ordinal());

	// ALL_REGIONS = 95
	public static final int ALL_REGIONS = (int)Math.pow(2,CITY.ordinal())
											| (int)Math.pow(2,MULTICITY.ordinal())
											| (int)Math.pow(2,NEIGHBORHOOD.ordinal())
											| (int)Math.pow(2,AIRPORT.ordinal())
											| (int)Math.pow(2,POI.ordinal()) 
											| (int)Math.pow(2,METROCODE.ordinal());
	
	// ALL_REGIONS_INCLUDING_ADDRESSES = 127
	public static final int ALL_REGIONS_INCLUDING_ADDRESSES = (int)Math.pow(2,CITY.ordinal())
												| (int)Math.pow(2,MULTICITY.ordinal())
												| (int)Math.pow(2,NEIGHBORHOOD.ordinal())
												| (int)Math.pow(2,AIRPORT.ordinal())
												| (int)Math.pow(2,POI.ordinal())
												| (int)Math.pow(2,ADDRESS.ordinal())
												| (int)Math.pow(2,METROCODE.ordinal());
	
	public static final int ALL_SUGGESTIONS_INCLUDING_REGIONS_ADDRESSES_AND_HOTEL = (int)Math.pow(2,CITY.ordinal())
												| (int)Math.pow(2,MULTICITY.ordinal())
												| (int)Math.pow(2,NEIGHBORHOOD.ordinal())
												| (int)Math.pow(2,AIRPORT.ordinal())
												| (int)Math.pow(2,POI.ordinal())
												| (int)Math.pow(2,ADDRESS.ordinal())
												| (int)Math.pow(2,METROCODE.ordinal())
												| (int)Math.pow(2,HOTEL.ordinal());
	
	

	
	//ALL_REGIONS_INCLUDING_MULTIREGIONS = 351
	public static final int ALL_REGIONS_INCLUDING_MULTIREGIONS = ALL_REGIONS 
												| (int)Math.pow(2, MULTIREGION.ordinal());
	
	public static final int ALL_REGIONS_INCLUDING_MULTIREGIONS_TRAINMETROSTATIONS = ALL_REGIONS 
			| (int)Math.pow(2, MULTIREGION.ordinal())
			| (int)Math.pow(2, TRAINSTATION.ordinal())
			| (int)Math.pow(2, METROSTATION.ordinal());
	
	public static final List<RegionType> REGION_TYPES =
		Collections.unmodifiableList(Arrays.asList(new RegionType[] {
				AIRPORT, CITY, MULTICITY, NEIGHBORHOOD, POI, METROCODE, HOTEL, MULTIREGION, TRAINSTATION, METROSTATION}));
	
	public static final List<RegionType> REGION_HOTEL_ADDRESS_TYPES = 
			Collections.unmodifiableList(Arrays.asList(new RegionType[] {
					AIRPORT, CITY, MULTICITY, NEIGHBORHOOD, POI, METROCODE, HOTEL, MULTIREGION, 
					TRAINSTATION, METROSTATION, ADDRESS}));

	public static final Map<Integer, Set<RegionType>> REGION_TYPES_BY_MASK = new HashMap<Integer, Set<RegionType>>();
	
	static
	{
		
		// AIRPORT
		Set<RegionType> airportRegionTypes = new HashSet<RegionType>();
		airportRegionTypes.add(RegionType.AIRPORT);
		REGION_TYPES_BY_MASK.put((int)Math.pow(2,RegionType.AIRPORT.ordinal()), airportRegionTypes);
		
		// POI
		Set<RegionType> poiRegionTypes = new HashSet<RegionType>();
		poiRegionTypes.add(RegionType.POI);
		REGION_TYPES_BY_MASK.put((int)Math.pow(2,RegionType.POI.ordinal()), poiRegionTypes);
		
		// METROCODE
		Set<RegionType> metrocodeRegionTypes = new HashSet<RegionType>();
		metrocodeRegionTypes.add(RegionType.METROCODE);
		REGION_TYPES_BY_MASK.put((int)Math.pow(2,RegionType.METROCODE.ordinal()), metrocodeRegionTypes);
		
		// ADDRESS
		Set<RegionType> addressRegionTypes = new HashSet<RegionType>();
		addressRegionTypes.add(RegionType.ADDRESS);
		REGION_TYPES_BY_MASK.put((int)Math.pow(2,RegionType.ADDRESS.ordinal()), addressRegionTypes);
		
		// HOTEL
		Set<RegionType> hotelRegionTypes = new HashSet<RegionType>();
		hotelRegionTypes.add(RegionType.HOTEL);
		REGION_TYPES_BY_MASK.put((int)Math.pow(2,RegionType.HOTEL.ordinal()), hotelRegionTypes);
		
		// TRAINSTATION
		Set<RegionType> trainstationRegionTypes = new HashSet<RegionType>();
		trainstationRegionTypes.add(RegionType.TRAINSTATION);
		REGION_TYPES_BY_MASK.put((int)Math.pow(2,RegionType.TRAINSTATION.ordinal()), trainstationRegionTypes);

		// METROSTATION
		Set<RegionType> metrostationRegionTypes = new HashSet<RegionType>();
		metrostationRegionTypes.add(RegionType.METROSTATION);
		REGION_TYPES_BY_MASK.put((int)Math.pow(2,RegionType.METROSTATION.ordinal()), metrostationRegionTypes);
		
		// ALL_CITIES
		Set<RegionType> allCitiesRegionTypes = new HashSet<RegionType>();
		for (RegionType regionType : RegionType.REGION_TYPES)
		{
			int maskToCheck = (int)Math.pow(2,regionType.ordinal());
			if ((maskToCheck & ALL_CITIES) == maskToCheck)
			{
				allCitiesRegionTypes.add(regionType);
			}
		}
		REGION_TYPES_BY_MASK.put(ALL_CITIES, allCitiesRegionTypes);
		
		// ALL_REGIONS
		Set<RegionType> allRegionsRegionTypes = new HashSet<RegionType>();
		for (RegionType regionType : RegionType.REGION_TYPES)
		{
			int maskToCheck = (int)Math.pow(2,regionType.ordinal());
			if ((maskToCheck & ALL_REGIONS) == maskToCheck)
			{
				allRegionsRegionTypes.add(regionType);
			}
		}
		REGION_TYPES_BY_MASK.put(ALL_REGIONS, allRegionsRegionTypes);
		
		// ALL_REGIONS_INCLUDING_ADDRESSES
		Set<RegionType> allRegionsAddressesRegionTypes = new HashSet<RegionType>();
		for (RegionType regionType : RegionType.REGION_TYPES)
		{
			int maskToCheck = (int)Math.pow(2,regionType.ordinal());
			if ((maskToCheck & ALL_REGIONS_INCLUDING_ADDRESSES) == maskToCheck)
			{
				allRegionsAddressesRegionTypes.add(regionType);
			}
		}
		REGION_TYPES_BY_MASK.put(ALL_REGIONS_INCLUDING_ADDRESSES, allRegionsAddressesRegionTypes);
		
		// ALL_SUGGESTIONS_INCLUDING_REGIONS_ADDRESSES_AND_HOTEL
		Set<RegionType> allRegionTypes = new HashSet<RegionType>();
		for (RegionType regionType : RegionType.REGION_TYPES)
		{
			int maskToCheck = (int)Math.pow(2,regionType.ordinal());
			if ((maskToCheck & ALL_SUGGESTIONS_INCLUDING_REGIONS_ADDRESSES_AND_HOTEL) == maskToCheck)
			{
				allRegionTypes.add(regionType);
			}
		}
		REGION_TYPES_BY_MASK.put(ALL_SUGGESTIONS_INCLUDING_REGIONS_ADDRESSES_AND_HOTEL, allRegionTypes);
		
		// ALL_REGIONS_INCLUDING_TRAINMETROSTATIONS
		Set<RegionType> allRegionTrainMetroStationTypes = new HashSet<RegionType>();
		for (RegionType regionType : RegionType.REGION_TYPES)
		{
			int maskToCheck = (int)Math.pow(2,regionType.ordinal());
			if ((maskToCheck & ALL_REGIONS_INCLUDING_MULTIREGIONS_TRAINMETROSTATIONS) == maskToCheck)
			{
				allRegionTrainMetroStationTypes.add(regionType);
			}
		}
		REGION_TYPES_BY_MASK.put(ALL_REGIONS_INCLUDING_MULTIREGIONS_TRAINMETROSTATIONS, allRegionTrainMetroStationTypes);
		
		//ALL_REGIONS_INCLUDING_MULTIREGIONS
		Set<RegionType> allRegionMultiRegionTypes = new HashSet<RegionType>();
		for (RegionType regionType : RegionType.REGION_TYPES)
		{
			int maskToCheck = (int)Math.pow(2,regionType.ordinal());
			if ((maskToCheck & ALL_REGIONS_INCLUDING_MULTIREGIONS) == maskToCheck)
			{
				allRegionMultiRegionTypes.add(regionType);
			}
		}
		REGION_TYPES_BY_MASK.put(ALL_REGIONS_INCLUDING_MULTIREGIONS, allRegionMultiRegionTypes);	
		
	}
	/*
	 * Region type sort order
	 * - currently hardcoded
	 * - eventually this sort order needs to be pulled from some config
	 */
	private int m_sortOrder;
	
	/*
	 * Search type used by downstream Hotel search library
	 * that maps to the region type.
	 */
	private String m_searchType;

	private RegionType(int sortOrder, String searchType)
	{
		m_sortOrder = sortOrder;
		m_searchType = searchType;
	}

	public int getSortOrder()
	{
		return m_sortOrder;
	}
	
	public String getSearchType()
	{
		return m_searchType;
	}
	
	/**
	 * Returns the set of region types based on a region type binary mask
	 * 
	 * @param regionTypeMask		region type binary mask
	 * 	
	 * @return						set of region types, cannot be null
	 */
	public static Set<RegionType> getRegionTypesByMask(int regionTypeMask)
	{
		Set<RegionType> regionTypes = null;
		if (RegionType.REGION_TYPES_BY_MASK.containsKey(regionTypeMask))
		{
			regionTypes = RegionType.REGION_TYPES_BY_MASK.get(regionTypeMask);
		}
		else
		{
			regionTypes = new HashSet<RegionType>();

			if (regionTypeMask > 0)
			{
				for (RegionType regionType : RegionType.REGION_TYPES)
				{
					int maskToCheck = (int)Math.pow(2,regionType.ordinal());
					if ((maskToCheck & regionTypeMask) == maskToCheck)
					{
						regionTypes.add(regionType);
					}
				}
			}
			if (regionTypeMask == -1)
			{
				for (RegionType regionType : RegionType.REGION_HOTEL_ADDRESS_TYPES)
				{
					int maskToCheck = (int)Math.pow(2,regionType.ordinal());
					if ((maskToCheck & regionTypeMask) == maskToCheck)
					{
						regionTypes.add(regionType);
					}
				}
			}
			
		}
		return regionTypes;
	}
	
	/**
	 * Returns the name of this enum constant, exactly as declared in its
     * enum declaration.
	 */
	public String value()
	{
		return name();
	}
}
