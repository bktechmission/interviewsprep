package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParsingLxContent {
	public static final Pattern ACTID_PATTERN = Pattern.compile("(?<=id\\\\\":\\\\\")(\\d+)"); 
	public static final Pattern RID_PATTERN = Pattern.compile("(?<=regionId\\\\\":\\\\\")(\\d+)");
	public static void main(String[] args)
	{
		String loc = "/Users/bpanwar/target/0-lxcontent.lx.json";
		String newFile = "/Users/bpanwar/target/actIdRidMapping.csv";
		BufferedReader br = null;
		PrintWriter pw = null;
		
		try
		{
			br = new BufferedReader(new FileReader(new File(loc)));
			pw = new PrintWriter(newFile);
			pw.println("activityId,regionId");
			String line = null;
			while((line=br.readLine())!=null)
			{
				Matcher matcherActId = ACTID_PATTERN.matcher(line);
				Matcher matcherRid = RID_PATTERN.matcher(line);
				//System.out.println(line);
				String actId = matcherActId.find()?matcherActId.group():"";
				String rid = matcherRid.find()?matcherRid.group():"";
				pw.println(actId+","+rid);
			}
		}
		catch(IOException ex)
		{
			ex.printStackTrace();
		}
		finally{
			if(pw!=null)
			{
				pw.close();
			}
			if(br!=null)
			{
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
