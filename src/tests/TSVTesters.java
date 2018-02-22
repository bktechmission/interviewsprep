package tests;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TSVTesters {
	public static final Pattern CAR_PATTERN = Pattern.compile("^CAR:[a-zA-Z]{2,3}$");
	public static final Pattern PACKAGE_ORIG_PATTERN = Pattern.compile("^PKG:[a-zA-Z]{2,3}$");
	public static final Pattern PACKAGE_DEST_PATTERN = Pattern.compile("^PKG:[a-zA-Z]{2,3}:[\\d]+$");
	public static final Pattern JUST_TLA_PATTERN = Pattern.compile("^[a-zA-Z]{3}$");
	public static final Pattern JUST_DIGIT_PATTERN = Pattern.compile("^[\\d]+$");
	
	public static void main(String[] args)
	{
		BufferedReader reader = null;
		try 
		{
			reader = new BufferedReader(new FileReader("C:\\Users\\a-bpanwar\\Desktop\\Popularity Data\\searchcounts.tsv"));
			String readLine = null;
			String[] split = null;
			//Skip the First Line as Headers
			reader.readLine();
			System.out.println("SITE_NAME \t\t\t\t\t\t ORIGIN \t\t\t\t\t\t DESTINATION \t\t\t\t\t\t LOB \t\t\t\t\t\t SEARCH_COUNT");
			while(null != (readLine = reader.readLine()))
			{
				split = readLine.split("\\t");
				
				System.out.print(split[0].trim() + " \t\t\t\t\t\t");
				System.out.print(split[1].trim() + " \t\t\t\t\t\t");
				System.out.print(split[2].trim() + " \t\t\t\t\t\t");
				System.out.print(split[3].trim() + " \t\t\t\t\t\t");
				System.out.print(split[4].trim());
				
				/*for(String each : split)
				{
					System.out.print(each.trim() + "*");
					//System.out.print(carsValidator(each) + "||");
					//System.out.print(packageOrigValidator(each) + " #");
					//System.out.print(packageDestValidator(each) + "#");
					//System.out.print(justDigitValidator(each));
				}*/
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
