package observerpattern;

import java.net.*;
import java.io.*;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;
import java.util.concurrent.*;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicStatusLine;

class AsynHttpCallable implements Callable<String> {

    private String url;
    private final String USER_AGENT = "Mozilla/5.0 (compatible; MSIE 5.01; Windows NT 5.0)";
    public AsynHttpCallable(String url) {
        this.url = url;
    }
   
    @Override
	public String call() throws Exception {
    	int timeout = 10;
    	RequestConfig config = RequestConfig.custom()
    	  .setConnectTimeout(timeout * 1000)
    	  .setConnectionRequestTimeout(timeout * 1000)
    	  .setSocketTimeout(timeout * 1000).build();
    	
    	HttpClientBuilder clientBuilder = HttpClientBuilder.create();
    	clientBuilder.setSSLContext(createTrustAllSslContext());
    	HttpClient client = clientBuilder.setDefaultRequestConfig(config).build();
    	HttpGet request = new HttpGet("https://" + url);
    	// add request header
    	Thread.sleep(5);
    	request.setHeader("Authorization","expedia-apikey key=oeGy5EyJJnBd5tnlfYrCbHmugtTDcLbq");
    	request.setHeader("Accept", "application/json");
    	request.setHeader("Accept-Encoding","gzip");
    	HttpResponse response = client.execute(request);
 
    	System.out.println(new BasicStatusLine(response.getProtocolVersion(),response.getStatusLine().getStatusCode() ,response.getStatusLine().getReasonPhrase()).toString());

		StringBuffer content = new StringBuffer();
		
		if(response.getStatusLine().getStatusCode() == 200)
		{
			BufferedReader rd = new BufferedReader(
	    			new InputStreamReader(response.getEntity().getContent()));     // We get ZIP stream and dont need to worry about unziping as we have to with HTTPUrlconnections

	    		String line = "";
	    		while ((line = rd.readLine()) != null) {
	    			content.append(line);
	    		}
		}
    	return content.toString();
    	
    	/*
        URL urlClass =  new URL("https://" + url);
    	HttpsURLConnection httpsUrlConnection = (HttpsURLConnection)urlClass.openConnection();
    	//httpsUrlConnection.setRequestMethod("GET");
    	//httpsUrlConnection.setRequestProperty("User-Agent", USER_AGENT);
    	//httpsUrlConnection.setRequestProperty("Referer", "http://www.expedia.com");
    	
    	httpsUrlConnection.setConnectTimeout(30000);
    	httpsUrlConnection.setReadTimeout(30000);
        SSLSocketFactory sslSocketFactory = createTrustAllSslSocketFactory();
        httpsUrlConnection.setSSLSocketFactory(sslSocketFactory);
        
        httpsUrlConnection.setRequestProperty("Authorization", "expedia-apikey key=ROjIwciUS7lC6qu9xINJtyx8T5IAdmja");
        httpsUrlConnection.setRequestProperty("Accept", "application/json");
        //httpsUrlConnection.setRequestProperty("Accept-Encoding","gzip");
        
        System.out.println("url is " + urlClass.toString());
        int code = httpsUrlConnection.getResponseCode();
        System.out.println("ResponseCode is " + code + " message is : ");
        String inputLine;
        String content = "";
        if(code==200)
        {
        	BufferedReader in = new BufferedReader(new InputStreamReader(httpsUrlConnection.getInputStream()));
            while ((inputLine = in.readLine()) != null) content+= inputLine;
            in.close();

        }
        
        return content;*/
    }
    
    
    private static SSLSocketFactory createTrustAllSslSocketFactory() throws Exception {
        return createTrustAllSslContext().getSocketFactory();
    }
    
    private static SSLContext createTrustAllSslContext() throws Exception {
        TrustManager[] byPassTrustManagers = new TrustManager[] { new X509TrustManager() {
			
			@Override
			public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
			
			@Override
			public void checkServerTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void checkClientTrusted(X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				
			}
        }};
        SSLContext sslContext = SSLContext.getInstance("SSL");
        sslContext.init(null, byPassTrustManagers, new SecureRandom());
        return sslContext;
    }
}

public class AsynHttpCallings{
    public static void main(String[] args)  throws Exception {    	
    	
        String[] sites = {"166587", "247607", "166365", "166461", "286429", "166554", "223254", "166606", "166389", "166434"};

        System.out.println(sites.length);


        ExecutorService service = Executors.newCachedThreadPool();
        List<Future<String>> futures = new ArrayList<Future<String>>();

        for (int i = 0; i < sites.length; i++) {
            try{

                Future<String> future = service.submit(new AsynHttpCallable(String.format("apis.integration.karmalab.net/m/lx/activity?activityId=%s&startDate=&endDate=&enableContent=true", sites[i])));
                futures.add(future);

            }
            catch (Exception e) {  
            	e.printStackTrace();
            }
        }   
        System.out.println("Number of futures are "+ futures.size());

        for (Future<String> future : futures) {
            try {
            	future.get();
                //System.out.println("get result from Future: " + future.get());

            }
             catch (Exception e) {
                e.printStackTrace();
            }

        }

        service.shutdown();
        while (!service.isTerminated()) {

        }

    }
}