package bodata;


import java.io.Serializable;

/**
 * Enumeration of line of business types
 */
public enum LOB implements Serializable
{
	FLIGHTS,
    HOTELS,
    CARS,
    CRUISES,
    ACTIVITIES,
    INSURANCE, 
    PACKAGES,
    ALL,
    UNKNOWN;
	
	/**
	 * Returns the name of this enum constant, exactly as declared in its
     * enum declaration.
	 */
	public String value()
	{
		return name();
	}
	
	public static LOB getLineOfBusinessType(String lob)
	{
		if (lob != null)
		{
			for (LOB lobType : LOB.values())
			{
				if (lob.equalsIgnoreCase(lobType.value()))
				{
					return lobType;
				}
			}
		}
		return UNKNOWN;
	}
}
