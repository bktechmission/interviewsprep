package tests;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CSVTesters {
	public static final Pattern CAR_PATTERN = Pattern.compile("^CAR:[a-zA-Z]{2,3}$");
	public static final Pattern PACKAGE_ORIG_PATTERN = Pattern.compile("^PKG:[a-zA-Z]{2,3}$");
	public static final Pattern PACKAGE_DEST_PATTERN = Pattern.compile("^PKG:[a-zA-Z]{2,3}:[\\d]+$");
	public static final Pattern JUST_TLA_PATTERN = Pattern.compile("^[a-zA-Z]{3}$");
	public static final Pattern JUST_DIGIT_PATTERN = Pattern.compile("^[\\d]+$");
	
	private static final String EXPERIMENTS_REGEX = "((?<=(ab=){1})[^&]+(?=(&|$)))";
	
	private static final String HOSTNAME_REGEX = "((?<=(//){0,1})[^/]+(?=(/hint)))";
	
	private static final Pattern EXPERIMENTS_PATTERN = Pattern.compile(EXPERIMENTS_REGEX);
	
	
	public static void main(String[] args)
	{ final TimeZone utc = TimeZone.getTimeZone("UTC");
		Date d = new Date();
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			d = sdf.parse("2016-01-14");
			System.out.println("date is " + d);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		d = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		sdf.setTimeZone(utc);
		
		System.out.println("date is " + sdf.format(d));
		/*
		Double x = new Double(1.9);
		int y = x.intValue();
		System.out.println(y);
		
		System.out.println((int)(Math.random()*(52)));

		replaceABPattern(null, "4994.1");
		

		String url = "http://suggest.expedia.com:8080/hint/es/v1/sri/en_US/Dongara?type=95&debug=true&lob=packages&c=make&ab=";
		Pattern pattern = Pattern.compile("((?<=(//){0,1})[^/]+(?=(/hint)))");
		Matcher matcher = pattern.matcher(url);
		while(matcher.find())
		{
			System.out.println(matcher.group());
		}
		
		System.out.println(url.replaceAll("((?<=(//){0,1})[^/]+(?=(/hint)))", "replacement"));
		System.out.println(url);
		
		String abs = "1908.1|4994.0|1234.1";
		pattern = Pattern.compile("((?<=(ab=){1})[^&]*(?=(&|$)))");
		matcher = pattern.matcher(url);
        if(matcher.find())
        {
            url = url.replaceAll("((?<=(ab=){1})[^&]*(?=(&|$)))", abs);
        }
        else
        {
            url = url + "&ab=" + abs;
        }
        System.out.println(url);
        
        Integer[] b = new Integer[10];
        Object[] a = b;
        a[0] = new Long(10);
        Integer i = b[0];
        System.out.println("i is " + i);

        File file = new File("\\\\chelobiprf3-01/Project/ESS/AccessLogs/");
        
        System.out.println("File is Directory : " + file.isDirectory());
        if(file.isDirectory())
        {
        	System.out.println("yes");
        }
        
        File[] files = file.listFiles();  
        System.out.println("acssed done");  
        if(files != null)
        {
        	 for (int i = 0; i < files.length; i++)  
             {  
                 String name = files[i].getName();  
                 System.out.println(name);  
             }  
        }
       
        
        
        String url = "smb://chelobiprf3-01/Project/ESS/AccessLogs/";
        NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("karmalab", "buildprop", "BlewDress.1");
        SmbFile dir ;
		try {
			dir = new SmbFile(url, auth);
			for (SmbFile f : dir.listFiles())
			{
			    System.out.println(f.getName());
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (SmbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String csvStr = "GEO#cheljvaewe2e007:52408 GAIA#cheljvaestr006:52408";
		String[] appconfigSet = csvStr.split(",");
        
		System.out.println(appconfigSet);
        Map<String, String> theMap = new HashMap<String, String>();
        
        for(String entry: appconfigSet)
        {
            String[] tokens = entry.split("#");
            if(tokens.length==2)
            {
                theMap.put(tokens[0].trim().toUpperCase(), tokens[1].trim());
            }
        }
        
        
       
		/*
		String log = "------------------------------------------------------------=n	EndTime=2013-09-24T12:30:20 PDT	Time=16 ms	Counters=numMultiRegionWinners-0,numRegionWinnerTlaWinners-0,numTlaWinners-0,numWinners-123,resultSize-0,termSize-3	Timers=DeDupNeighborhoodProcessor-0/2	ClientIP=172.30.153.125	ExperimentsPerformed=2443.1|1953.0|2970.0|2832.0|1908.0|2257.1	InDestFlag=true	InpClient=UNKNOWN	InpDebug=true	InpGUID=	InpIP=172.30.153.125	InpLOB=packages	InpLocale=en_US	InpMaxResults=10	InpRegionType=14	InpRequestType=SEARCH_RESOLUTION	InpTerm=BVT	InvalidatedInput=NO	Operation=RegionRestService.search	RegionIds=	RegionIndices=	RegionLocs=	RegionPackageTla=	RegionPackageTlaWinners=	RegionTypes=	RegionWinners=	RequestId=e12cdba2-a5f4-419c-9e73-3156c7724c77	ResponseStatus=ZERO_RESULTS	User-Agent=Mozilla/5.0 (Windows NT 6.1; WOW64; rv:24.0) Gecko/20100101 Firefox/24.0	Version=1	EOO=n";
		Pattern pattern = Pattern.compile("numWinners-[\\d]+");
		Matcher matcher = pattern.matcher(log);
		while(matcher.find())
		{
			System.out.println(matcher.group());
		}
		
		String[] split = new String("DEFAULT,3467118,Tarragona Train Station,,41.112113,,1,2,11818,Salou,Catalonia,Spain,,").split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
		System.out.println("Split size is : " + split.length);
		for(String x : split)
		{
			System.out.println(x + " T");
		}
		*/
		
		/*
		BufferedReader reader = null;
		try 
		{
			System.out.println("Hi I am new one");
			reader = new BufferedReader(new FileReader("C:\\Users\\a-bpanwar\\Desktop\\Popularity Data\\searchcounts.tsv"));
			String readLine = null;
			String[] split = null;
			//Skip the First Line as Headers
			System.out.println(reader.readLine());
			while(null != (readLine = reader.readLine()))
			{
				split = readLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
				for(String each : split)
				{
					System.out.print(each + "**********");
					//System.out.print(carsValidator(each) + "||");
					//System.out.print(packageOrigValidator(each) + " #");
					//System.out.print(packageDestValidator(each) + "#");
					//System.out.print(justDigitValidator(each));
				}
				System.out.println();
			}
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		finally
		{
			if(null!=reader)
			{
				try {
					reader.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		long time = 1369165140000L;
		 Calendar now =  Calendar.getInstance();
	        
	     // Booked in last 26 hrs: need diff
	     long diff = now.getTimeInMillis() - time;
	     System.out.println(diff);
	     */
		
		
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
	
	private static String replaceABPattern(String urlAfterQuestionMark, String abs)
	{
	    Matcher matcher = EXPERIMENTS_PATTERN.matcher(urlAfterQuestionMark);
        if(matcher.find())
        {
            urlAfterQuestionMark = urlAfterQuestionMark.replaceAll(EXPERIMENTS_REGEX, abs);
        }
        else
        {
            urlAfterQuestionMark = urlAfterQuestionMark + "&ab=" + abs;
        }
        urlAfterQuestionMark = urlAfterQuestionMark + "&routed=" + "true";
        return urlAfterQuestionMark;
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