package acclickdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

public class ACClickCounts {
	
	
	public static void main(String[] args)
	{
		Map<EssLocale , Map<LOB, Map<Boolean, Set<String>>>> taMap = 
							new HashMap<EssLocale , Map<LOB, Map<Boolean, Set<String>>>>();
		Map<LOB, Map<Boolean, Set<String>>> taMapLob = new HashMap<LOB, Map<Boolean, Set<String>>>();
		Map<Boolean, Set<String>> taMapExp = new HashMap<Boolean, Set<String>>();
		Set<String> taExpSet = new HashSet<String>();
		Set<String> taCtrlSet = new HashSet<String>();
		
		Map<EssLocale , Map<LOB, Set<String>>> acMap = 
							new HashMap<EssLocale , Map<LOB, Set<String>>>();
		Map<LOB, Set<String>> acMapLob = new HashMap<LOB, Set<String>>();
		Set<String> acSet = new HashSet<String>();
		
		
		File dir = new File("C:\\sftp\\esslogs");
		File[] files = dir.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".log.gz");
			}
		});
		BufferedReader reader = null;
		Pattern patternTA = Pattern.compile("InpRequestType=TYPEAHEAD");
		Pattern patternAC = Pattern.compile("InpRequestType=AC_CLICK");
		Pattern patternGUID= Pattern.compile("InpGUID=[a-zA-Z0-9]+");
		Pattern patternLOB = Pattern.compile("InpLOB=(FLIGHTS|PACKAGES|CARS|HOTELS)");
		Pattern patternLocale = Pattern.compile("InpLocale=[a-z]{2}_[A-Z]{2}");
		Pattern patternExp = Pattern.compile("ExperimentsPerformed=[\\d\\.\\|]*2219.1[\\d\\.\\|]*");
		Pattern patternCtrl = Pattern.compile("ExperimentsPerformed=[\\d\\.\\|]*2219.0[\\d\\.\\|]*");
		
		// Reading Data and making Map
		for(File file : files)
		{
			System.out.println("File to Read : " + file.getName());
			System.out.println("File Exitst : " + file.isFile());
			String line = null;
			try
			{
				reader = new BufferedReader(new InputStreamReader(new GZIPInputStream(new FileInputStream(file))));
				// for each Line
				while((line = reader.readLine()) != null)
				{
					line = line.trim();
					LOB lob = null;
					EssLocale locale = null;
					String guid = null;
					Matcher matcherExp = patternExp.matcher(line);
					Matcher matcherCtrl = patternCtrl.matcher(line);
					Matcher matcherAC = patternAC.matcher(line);
					// Find the Exp 2219.1
					if(matcherExp.find())
					{
						//System.out.println("find the exp : " + matcherExp.group());
						Matcher matcherTA = patternTA.matcher(line);
						if(matcherTA.find())
						{
							
							//System.out.println("TA in Exp" );
							Matcher matcherLob = patternLOB.matcher(line);
							Matcher matcherLocale = patternLocale.matcher(line);
							Matcher mactherGuid = patternGUID.matcher(line);
							if(mactherGuid.find())
							{
								String[] rawGuid = mactherGuid.group().trim().split("=");
								guid = rawGuid[1].trim();
							}
							if(guid == null || guid.isEmpty())
							{
								continue;
							}
							if(matcherLob.find())
							{
								String[] rawLob = matcherLob.group().trim().split("=");
								lob = getLOB(rawLob[1].trim());
							}
							if(lob == null)
							{
								continue;
							}
							if(matcherLocale.find())
							{
								String[] rawLocale = matcherLocale.group().trim().split("=");
								locale = getLocale(rawLocale[1].trim());
							}
							if(locale == null)
							{
								continue;
							}
							
							
							if(!taMap.containsKey(locale))
							{
								taMapLob = new HashMap<LOB, Map<Boolean, Set<String>>>();
								taMap.put(locale, taMapLob);
							}
							taMapLob = taMap.get(locale);
							if(!taMapLob.containsKey(lob))
							{
								taMapExp = new HashMap<Boolean, Set<String>>();
								taMapLob.put(lob, taMapExp);
							}
							taMapExp = taMapLob.get(lob);
							if(!taMapExp.containsKey(true))
							{
								taExpSet = new HashSet<String>();
								taMapExp.put(true, taExpSet);
							}
							taExpSet = taMapExp.get(true);
							taExpSet.add(guid);
						}
					}
					// find the exp 2219.0
					else if(matcherCtrl.find())
					{
						//System.out.println("find the ctrl : " + matcherCtrl.group());
						Matcher matcherTA = patternTA.matcher(line);
						if(matcherTA.find())
						{
							//System.out.println("TA NOT in Exp" );
							Matcher matcherLob = patternLOB.matcher(line);
							Matcher matcherLocale = patternLocale.matcher(line);
							Matcher mactherGuid = patternGUID.matcher(line);
							if(mactherGuid.find())
							{
								String[] rawGuid = mactherGuid.group().trim().split("=");
								guid = rawGuid[1].trim();
							}
							if(guid == null || guid.isEmpty())
							{
								continue;
							}
							if(matcherLob.find())
							{
								String[] rawLob = matcherLob.group().trim().split("=");
								lob = getLOB(rawLob[1].trim());
							}
							if(lob == null)
							{
								continue;
							}
							if(matcherLocale.find())
							{
								String[] rawLocale = matcherLocale.group().trim().split("=");
								locale = getLocale(rawLocale[1].trim());
							}
							if(locale == null)
							{
								continue;
							}
							
							
							if(!taMap.containsKey(locale))
							{
								taMapLob = new HashMap<LOB, Map<Boolean, Set<String>>>();
								taMap.put(locale, taMapLob);
							}
							taMapLob = taMap.get(locale);
							if(!taMapLob.containsKey(lob))
							{
								taMapExp = new HashMap<Boolean, Set<String>>();
								taMapLob.put(lob, taMapExp);
							}
							taMapExp = taMapLob.get(lob);
							if(!taMapExp.containsKey(false))
							{
								taCtrlSet = new HashSet<String>();
								taMapExp.put(false, taCtrlSet);
							}
							taCtrlSet = taMapExp.get(false);
							taCtrlSet.add(guid);
						}
					}
					// AC Click found
					else if(matcherAC.find())
					{
						//System.out.println("AC" );
						Matcher matcherLob = patternLOB.matcher(line);
						Matcher matcherLocale = patternLocale.matcher(line);
						Matcher mactherGuid = patternGUID.matcher(line);
						if(mactherGuid.find())
						{
							String[] rawGuid = mactherGuid.group().trim().split("=");
							guid = rawGuid[1].trim();
						}
						if(guid == null || guid.isEmpty())
						{
							continue;
						}
						if(matcherLob.find())
						{
							String[] rawLob = matcherLob.group().trim().split("=");
							lob = getLOB(rawLob[1].trim());
						}
						if(lob == null)
						{
							continue;
						}
						if(matcherLocale.find())
						{
							String[] rawLocale = matcherLocale.group().trim().split("=");
							locale = getLocale(rawLocale[1].trim());
						}
						if(locale == null)
						{
							continue;
						}
						
						
						if(!acMap.containsKey(locale))
						{
							acMapLob = new HashMap<LOB, Set<String>>();
							acMap.put(locale, acMapLob);
						}
						acMapLob = acMap.get(locale);
						if(!acMapLob.containsKey(lob))
						{
							acSet = new HashSet<String>();
							acMapLob.put(lob, acSet);
						}
						acSet = acMapLob.get(lob);
						acSet.add(guid);
					}
				} // End of line loop

			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			finally
			{
				if(reader != null)
				{
					try 
					{
						reader.close();
					} 
					catch (IOException e) 
					{
						e.printStackTrace();
					}
				}
			}
		} // Each File Loop Ends
		
		System.gc();
		
		System.out.println("Loaded All Data in Memory");
		System.out.println("Making analysis Metrics...");
		
		File file = new File("C:\\sftp\\esslogs\\output\\analysis.txt");
	
		PrintWriter pw = null;
		try
		{
			pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
			
			Map<LOB, Map<Boolean, Set<String>>> talocaleMap = null;
			Map<LOB, Set<String>> aclocaleMap = null;
			for(Map.Entry<EssLocale, Map<LOB, Map<Boolean, Set<String>>>> taEntry : taMap.entrySet())
			{
				EssLocale locale = taEntry.getKey();
				talocaleMap = taEntry.getValue();
				if(acMap.containsKey(locale))
				{
					aclocaleMap = acMap.get(locale);
					for(Map.Entry<LOB, Map<Boolean, Set<String>>> taLobEntry : talocaleMap.entrySet())
					{
						LOB taLob = taLobEntry.getKey();
						Map<Boolean, Set<String>> taLobExpSet = taLobEntry.getValue();
						if(aclocaleMap.containsKey(taLob))
						{
							Set<String> acGuidsClone = new HashSet<String>(aclocaleMap.get(taLob));
							
							// Get Expts Guids
							Set<String> taExptGuids = taLobExpSet.get(true);
							
							// Get the Ctrl Guids 
							Set<String> taCtrlGuids	= taLobExpSet.get(false);
						
							if(taCtrlGuids == null)
							{
								//System.out.println("Ctrl is NULL");
								taCtrlGuids = new HashSet<String>();
							}
							
							if(taExptGuids == null)
							{
								//System.out.println("Expt is NULL");
								taExptGuids = new HashSet<String>();
							}
							
							acGuidsClone.retainAll(taExptGuids);
							int expClicked = acGuidsClone.size();
							int totalInExp = taExptGuids.size();
							
							acGuidsClone = new HashSet<String>(aclocaleMap.get(taLob));
							//System.out.println("size of acGuids : " + acGuidsClone.size());
							acGuidsClone.retainAll(taCtrlGuids);
							int ctrlClicked = acGuidsClone.size();
							int totalInCtrl = taCtrlGuids.size();
							
							pw.println("Locale: " + locale.toString() + "; LOB: " + taLob.toString() + 
									"; totalInExpACCounts: " + totalInExp + "; expClickedCounts: " + expClicked + 
									"; totalInCtrlACCounts: " + totalInCtrl + "; ctrlClickedCounts: " + ctrlClicked);
							//System.out.println("Locale: " + locale.toString() + "; LOB: " + taLob.toString() 
							//+ "; totalInExpACCounts: " + totalInExp + "; expClickedCounts: " + expClicked
							//+ "; totalInCtrlACCounts: " + totalInCtrl + "; ctrlClickedCounts: " + ctrlClicked);
						}
						
					}
				}
			}	 
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			pw.flush();
			pw.close();
		}
		
		System.out.println("Done");
		
	}
	
	private static LOB getLOB(String lob)
	{
		if(lob.equalsIgnoreCase(LOB.PACKAGES.name()))
		{
			return LOB.PACKAGES;
		}
		else if(lob.equalsIgnoreCase(LOB.HOTELS.name()))
		{
			return LOB.HOTELS;
		}
		else if(lob.equalsIgnoreCase(LOB.CARS.name()))
		{
			return LOB.CARS;
		}
		else if(lob.equalsIgnoreCase(LOB.FLIGHTS.name()))
		{
			return LOB.FLIGHTS;
		}
		return null;
	}
	
	private static EssLocale getLocale(String locale)
	{
		return EssLocale.valueOf(locale);
	}
}
