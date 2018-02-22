package mongoexamples;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;

public class MongoQueryExample {

    
	public static void main(String[] args)
	{
		MongoDBProvider mongoProvider = new MongoDBProvider("localhost", 27017);
		List<String> clientIds =  new ArrayList<>();
		clientIds.add("fsr");
		clientIds.add("hsr1");
		clientIds.add("pfsr1");
		mongoProvider.callDB(clientIds);
		
	}
	
	private static class MongoDBProvider
	{
		private static final String CLIENTIDSDB = "urgency-clientids-db-justtest";
	    private static final String CLIENTIDSCOLLECTION = "urgencyClientIds_justtest";
	    private String mongoHost;
	    private int mongoPort;
	    
	    MongoDBProvider(String mongoHost, int mongoPort)
	    {
	    	this.mongoHost = mongoHost;
	        this.mongoPort = mongoPort;
	    	
	    }
	    private void callDB(List<String> clientIds){
	    	MongoClientURI mongoClientURI = new MongoClientURI("mongodb://" + mongoHost + ":" + mongoPort + "/" + CLIENTIDSDB);
	    	MongoClient mongoClient;
	    	DB mongoDB;
	    	try {
	    		mongoClient = new MongoClient(mongoClientURI);
	            mongoDB = mongoClient.getDB(mongoClientURI.getDatabase());
	            DBCollection dbCollection = mongoDB.getCollection(CLIENTIDSCOLLECTION);
	            BasicDBObject query = new BasicDBObject("pid", "urgency-clients");
	            DBCursor cursor = dbCollection.find(query);
	            Set<String> oldIds = new HashSet<String>();
	            while(cursor.hasNext()) {
	                BasicDBObject clientIdsDoc = (BasicDBObject)  cursor.next();
	                System.out.println(clientIdsDoc);
	                BasicDBList clients = (BasicDBList) clientIdsDoc.get("clients");
	                System.out.println(clients);
	                clients.forEach(x -> oldIds.add(x.toString()));
	            }
	            oldIds.addAll(clientIds);
	            BasicDBObject fieldsToUpsert = new BasicDBObject("pid", "urgency-clients");
	            fieldsToUpsert.put("clients", oldIds.toArray());
	            
	            dbCollection.update(query,new BasicDBObject("$set", fieldsToUpsert),true,false);
	            
	            
	        }catch (UnknownHostException e) {
	        	e.printStackTrace();
	        } catch (MongoException e) {
	        	e.printStackTrace();
	        }
	    	
	    }
	}

}
