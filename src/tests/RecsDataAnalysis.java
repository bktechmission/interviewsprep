package tests;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONObject;

public class RecsDataAnalysis {
	private static Pattern pattern = Pattern
			.compile("(?<=\"totalResultsCount\":)\\d+");
	private static final RequestConfig requestConfig = RequestConfig.custom()
			.setConnectTimeout(4000).setSocketTimeout(10000).build();
	private static final HttpClient client = HttpClientBuilder.create()
			.setDefaultRequestConfig(requestConfig).build();
	private static final String USER_AGENT = "Mozilla/5.0";
	private static final String URL = "http://www.expedia.com/Hotel-Recommender-Ajax?scenario=homepage&rule=persona-region-item-xsell&itemType=11&maxCount=11&reqRetItemType=0&itemId=%s&travellersInfo=1&startDate=&endDate=";

	public static void main(String[] args) {
		Scanner sc = null;
		PrintWriter pw1 = null;
		PrintWriter pw2 = null;
		PrintWriter pw3 = null;
		try {
//			sc = new Scanner(new File("/Users/bpanwar/Desktop/DataForHIS.txt"));
//			HashSet<String> setOfAll = new HashSet<String>();
//			while (sc.hasNextLine()) {
//				String rid = sc.nextLine().trim();
//				boolean isThere = setOfAll.add(rid);
//				if(!isThere)
//				{
//					System.out.println("InAll set duplicate is : "+rid);
//				}
//					
//			}
//			sc.close();
//			HashSet<String> setOfNotFound = new HashSet<String>();
//			sc = new Scanner(new File("/Users/bpanwar/Desktop/RecommendationNOTThereForIds.txt"));
//			while (sc.hasNextLine()) {
//				
//				String rid = sc.nextLine().trim();
//				boolean isThere = setOfNotFound.add(rid);
//				if(!isThere)
//				{
//					System.out.println("InNotFound set duplicate is : "+rid);
//				}
//			}
//			pw = new PrintWriter(new File("/Users/bpanwar/Desktop/RecommendationAlreadyThereForIds.txt"));
//			for(String data : setOfAll)
//			{
//				if(!setOfNotFound.contains(data))
//				{
//					pw.println(data);
//				}
//			}
			
			
			
			
			pw1 = new PrintWriter(
					"/Users/bpanwar/Desktop/ActivitiesMining/data/RecommendationAlreadyThereForIds.txt");
			pw2 = new PrintWriter(
					"/Users/bpanwar/Desktop/ActivitiesMining/data/RecommendationNOTThereForIds.txt");
			pw3 = new PrintWriter(
					"/Users/bpanwar/Desktop/ActivitiesMining/data/DataForExceptionOccurred.txt");
			
			sc = new Scanner(new File("/Users/bpanwar/Desktop/DataForHIS.txt"));

			while (sc.hasNextLine()) {
				String rid = sc.nextLine().trim();
				//System.out.println(rid);
				BufferedReader rd = null;
				try
				{
					/*URL url = new URL(String.format(URL, 271));

					// create a urlconnection object
					URLConnection urlConnection = url.openConnection();

					// wrap the urlconnection in a bufferedreader
					BufferedReader bufferedReader = new BufferedReader(
							new InputStreamReader(urlConnection.getInputStream()));

					String line;

					// read from the urlconnection via the bufferedreader
					while ((line = bufferedReader.readLine()) != null) {
						System.out.println(line);
						JSONObject jresponse = new JSONObject(line);
						int totalCount = Integer.parseInt(jresponse.get(
								"totalResultsCount").toString());
						if (totalCount < 1) {
							pw.println(rid);
						}
					}
					bufferedReader.close();*/

					HttpGet request = new HttpGet(String.format(URL,rid));
					request.setHeader("User-Agent", USER_AGENT);
					request.setHeader("Accept","application/json,;q=0.9,*/*;q=0.8");
					//request.setHeader("Content-type","application/json; charset=utf-8");
					request.setHeader("Accept-Language", "en-US,en;q=0.5");
					HttpResponse response = client.execute(request);

					// Get the response
					rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

					String line = "";
					while ((line = rd.readLine()) != null) {
						//System.out.println(line);
						JSONObject jresponse=new JSONObject(line);
						// Matcher matcher = pattern.matcher(line);
						// if(matcher.find())
						// {
						// System.out.println(matcher.group());
						// }
						int totalCount = Integer.parseInt(jresponse.get("totalResultsCount").toString());
						if(totalCount>0)
						{
							pw1.println(rid);
						}
						else
						{
							System.out.println("Not found for: "+ rid);
							pw2.println(rid);
						}
					}
					rd.close();
					rd=null;
					Thread.sleep(100);
				}
				catch (Exception e) {
					// TODO Auto-generated catch block
					System.out.println("Error occurred for RID : "+rid +" : "+ e.getMessage());
					pw3.println(rid);
				}
				finally{
					if(rd!=null)
					{
						rd.close();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  finally {
			if (pw1 != null) {
				pw1.close();
			}
			if (pw2 != null) {
				pw2.close();
			}
			if (pw3 != null) {
				pw3.close();
			}
			if (sc != null) {
				sc.close();
			}
		}

	}

}