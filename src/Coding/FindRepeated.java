package Coding;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



public class FindRepeated {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws URISyntaxException ,
    IOException  {
		/*Scanner sc = new Scanner (new File("Data"));
		sc.useDelimiter("\\A");
		String file  = sc.next();
		//System.out.println(file);
		Pattern pattern = Pattern.compile("value=\"[a-zA-Z_]+\"");
		Matcher matcher = pattern.matcher(file);
		Set<String> set = new HashSet<String>();
		while(matcher.find()){
			String temp = matcher.group();
			String[] split= temp.split("=");
			String find = split[1].substring(1,split[1].length()-1);
			if(!set.contains(find)){
				set.add(find);
				System.out.println(find);
			}
			else{
				System.out.println("Find : "+find);
			}
		}*/
		
		
		Double m_score = new Double(0);
		Pattern pattern = Pattern.compile("[-\\(,]+");
		String str = "San. Jose Silicon Valley, Kalifornien, USA";
		String[] ar = pattern.split(str);
		System.out.println(ar[0]);
		System.out.println("length : "+ar.length);
		
		pattern = Pattern.compile("[\\w\\.\\s]+");
		Matcher matcher = pattern.matcher(str);
		while(matcher.find())
		{
			System.out.println(matcher.group());
			break;
		}
		
		Boolean b = true;
		if(b)
		{
			System.out.println(b);
		}
		int [] a = new int[]{1,2,3,4,5,6,7,8,9,10,11,12};
		Collection <Integer> al = new ArrayList<Integer>(5);
		System.out.println("al size : "+al.size());
		for(int x:a)
		{
			al.add(x);
		}
		for(int x:al)
		{
			System.out.println(x);
		}
		
		System.out.println(al.size());
		
		String myQuery = "My Name is";

        URI uri = new URI( String.format( 
                           "http://finance.yahoo.com?q=%s", 
                           URLEncoder.encode( myQuery , "UTF8" ) ) );

        System.out.println(uri);
        System.out.println(URLEncoder.encode("http://finance.yahoo.com/en_US/help Me?q=My Name is Khan", "UTF-8"));
        URI uri1 = new URI(
                "http",
                null, // this is for userInfo
                "www.google.com",
                8080, // port number as int
                "/ig/api",
                "weather=So Paulo",
                null);
        System.out.println(uri1.toString());
        String request = uri1.toASCIIString();
        System.out.println(request);
        
        String abc = "18,\"Toorak Road, South Yarra, Victora, Australia Shadow (inactive - duplicate of region id 6159479)\",6061044,\"Toorak Road, South Yarra, Victora, Australia (inactive - duplicate of region id 6159479)\",6061045";
        String[] split= abc.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
        for(String p: split)
        {
        	System.out.println(p);
        }
        System.out.println("**************************************");
        /*Scanner sc = new Scanner(abc.trim());
        sc.useDelimiter(",");
        while(sc.hasNext())
        {
        	System.out.println(sc.next());
        }
        System.out.println("***********************************");
        pattern = Pattern.compile("[^,]*");
        matcher = pattern.matcher(abc);
        while(matcher.find())
        {
        	System.out.println(matcher.group());
        }
        
        /*
        CSVReader reader = new CSVReader(new FileReader("C:\\sftp\\AFS POI Popularity Data\\POIShadowToPOIMappings.csv"));
        String [] nextLine;
        int count =0;
        while ((nextLine = reader.readNext()) != null) {
            // nextLine[] is an array of values from the line
            System.out.println(nextLine[0] + " " + nextLine[1] + " etc...");
            count++;
            if(count==10)
            	break;
        }
        */
        
        LineOfBusinessType abcd = LineOfBusinessType.FLIGHTS;
        if(LineOfBusinessType.FLIGHTS.equals(abcd))
        {
        	System.out.println("Hi");
        }
        
        Map<Integer, String> map = new HashMap<Integer, String>();
        map.put(1, "One");
        map.put(2, "Two");
        map.put(null, "three");
        map.put(null, "four");
        map.put(4, null);
        if(map.containsKey(4))
        {
        	System.out.println("NULL KEY is in Map");
        	System.out.println("Value is : "+ map.get(4));
        }
        
        
        Pattern pattern1 = Pattern.compile("InpDest=(true|false)");
        Matcher matcher1 = pattern1.matcher("InpDest=false");
        if(matcher1.find()){
        	System.out.println(" find : " + matcher1.group());
        }
	}
	
	
}

enum LineOfBusinessType
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
	
	public static LineOfBusinessType getLineOfBusinessType(String lob)
	{
		if (lob != null)
		{
			for (LineOfBusinessType lobType : LineOfBusinessType.values())
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
