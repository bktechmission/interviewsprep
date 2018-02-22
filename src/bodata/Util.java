package bodata;

import java.util.Locale;

/**
 * Collection of utility methods.
 * 
 * @author <a href="mailto:vnamuduri@expedia.com">Veeru Namuduri</a>
 */
public abstract class Util
{

	@SuppressWarnings("unused")
	private static final Locale DEFAULT_LOCALE = Locale.US;
	
	private static final int DEPTH_OF_STACK = 10;
	/**
	 * Convert a string based locale into a Locale Object.
	 * Assumes the string has form "{language}_{country}_{variant}".
	 * Examples: "en", "de_DE", "_GB", "en_US_WIN", "de__POSIX", "fr_MAC"
	 * 
	 * @param localeString The String
	 * @return the Locale
	 */
	public static EssLocale toLocale(String localeString)
	{
		if (localeString == null)
		{
			return null;
			// return DEFAULT_LOCALE;
		}
		localeString = localeString.trim();
		if (localeString.equalsIgnoreCase("default"))
		{
			return new EssLocale(Locale.getDefault());
		}

		// Extract language
		int languageIndex = localeString.indexOf('_');
		String language = null;
		if (languageIndex == -1)
		{
			// No further "_" so is "{language}" only
			return new EssLocale(new Locale(localeString, ""));
		}
		else
		{
			language = localeString.substring(0, languageIndex);
		}

		// Extract country
		int countryIndex = localeString.indexOf('_', languageIndex + 1);
		String country = null;
		if (countryIndex == -1)
		{
			// No further "_" so is "{language}_{country}"
			country = localeString.substring(languageIndex + 1);
			return new EssLocale(new Locale(language, country));
		}
		else
		{
			// Assume all remaining is the variant so is "{language}_{country}_{variant}"
			country = localeString.substring(languageIndex + 1, countryIndex);
			String variant = localeString.substring(countryIndex + 1);
			return new EssLocale(new Locale(language, country, variant));
		}
	}

	/**
	 * Helps to avoid using {@code @SuppressWarnings( "unchecked"})} when casting to a generic type.
	 */
	@SuppressWarnings({ "unchecked" })
	public static <T> T uncheckedCast(Object obj)
	{
		return (T) obj;
	}
	
	/*
	 * Given a region id and array of region ids,
	 * returns an array of combined unique array of region ids.
	 */
	public static int[] appendRegionIds(int[] regionIds, int regionId)
	{
		if (regionIds == null)
		{
			regionIds = new int[1];
			regionIds[0] = regionId;
		}
		else
		{
			for (int i = 0; i < regionIds.length; i++)
			{
				if (regionIds[i] == regionId)
				{
					return regionIds;
				}
			}

			int[] newRegionIds = new int[regionIds.length + 1];
			System.arraycopy(regionIds, 0, newRegionIds, 0, regionIds.length);
			newRegionIds[newRegionIds.length - 1] = regionId;
			regionIds = newRegionIds;
		}
		return regionIds;
	}
	
	/*
	 *  Given any Throwable it will convert the stack trace to 
	 *  String. The default boolean flag true means print whole
	 *  stack trace. false means Print First 10 elements
	 *  from stack trace.
	 */
	public static String getCustomStackTrace(Throwable aThrowable, boolean printWholeStack) 
	{
		if( aThrowable == null )
			return "";
	    
		//Add the class name and any message passed to constructor
	    final StringBuilder result = new StringBuilder( "This is Customized for Exception Named : " );
	    result.append(aThrowable.toString());
	    final String NEW_LINE = System.getProperty("line.separator");
	    result.append(NEW_LINE);
	    int count = 1;
	    
	    // Get the Whole Stack Trace Here
	    StackTraceElement[] elementsArray = aThrowable.getStackTrace();
	    if(elementsArray != null && elementsArray.length > 0 )
	    {
		    int length = elementsArray.length;
		    //Add each element of the stack trace
		    for ( int i = 0; i < length; i++)
		    {
		      result.append( "\tAt : " + elementsArray[i] );
		      result.append( NEW_LINE );
		      count++;
		      if( count > DEPTH_OF_STACK &&  !printWholeStack)
		      {
		    	  break;
		      }
		    }
	    }
	    return result.toString();
	  }
	
	public static boolean equalsNotNull(String one, String two)
	{
		if (one != null && two != null)
		{
			if (one.equalsIgnoreCase(two))
			{
				return true;
			}
		}
		return false;
	}
}
