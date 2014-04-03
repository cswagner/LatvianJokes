/* -----------------------------------[LatvianJokes]-
 * File:		Potato.java
 * Description: defines interactions with database
 * 				containing Latvian jokes scraped from
 * 				www.reddit.com/r/LatvianJokes/new
 * Author:		Chris Wagner (cswagner@github)
 * Date:		3.31.2014
 * --------------------------------------------------*/

package cswagner.latvianjokes;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public final class Potato {	
	public static int getInt() {
		return 11;
	}
	
	/* --------------------------------------------------
	 * Method:		getPotato
	 * Description: grabs a random Latvian joke from the
	 * 				database
	 * Parameters:  none
	 * Return:		DBObject representing the Latvian joke
	 * 				entry in the database
	 * --------------------------------------------------*/
	public static DBObject getPotato() {		
		return collection.findOne(new BasicDBObject("loc", new BasicDBObject("$near", new Double[]{Math.random(),Math.random()})));		
	}
	
	/* --------------------------------------------------
	 * Method:		seedData
	 * Description: scrapes the subreddit /r/LatvianJokes/new
	 * 				for self posts that do not contain links
	 * 				(i.e. plain-text posts)
	 * Parameters:  none
	 * Return:		none
	 * --------------------------------------------------*/
	public static void seedData() {	
		collection.drop();
		collection.ensureIndex(new BasicDBObject("loc", "2d"));

		boolean isMoreToRead = true;
		Scanner in = new Scanner(System.in);
		while (isMoreToRead) {
			String request = "/r/LatvianJokes/new.json?limit=100";
			if (!lastID.equals("")) request = "/r/LatvianJokes/new.json?limit=100&after=t3_" + lastID;	
			
			// get all posts in slice
			JSONObject object = (JSONObject) get(request);
			JSONArray array = (JSONArray) ((JSONObject) object.get("data")).get("children");
			if (array.size() <= 0) {
				System.out.println("DONE");
				isMoreToRead = false;				
			}
			
			JSONObject data;
			for (int i = 0; i < array.size(); i++) {
				data = (JSONObject) array.get(i);
				data = ((JSONObject) data.get("data"));
				
				String domain = data.get("domain").toString();
				if (domain.equals("self.LatvianJokes")) {
					// only interested in self-posts
					try {
						String id = data.get("id").toString();
						String title = data.get("title").toString();	
						String txt = data.get("selftext").toString(); 						
						String html = data.get("selftext_html").toString();
						
						// do not include posts with links in them
						if (txt.indexOf("http://") != -1 || txt.indexOf("https://") != -1 || txt.indexOf("www.") != -1) continue;
						
						BasicDBObject obj = new BasicDBObject("id", id)
													  .append("title", title).append("selftext", txt)
													  .append("selftext_html", html)
													  .append("loc", new Double[]{Math.random(),Math.random()});
						collection.insert(obj);						
					} catch (Exception e) {
						continue;
					}
				}
				
				// grab starting point of next slice
				if (i == array.size() - 1) lastID = data.get("id").toString();
			}
			System.out.println("Hit ENTER");
			in.nextLine();
		}
		in.close();
	}

	/* --------------------------------------------------
	 * Method:		get
	 * Description: performs a GET request to reddit.com
	 * 				and returns the response
	 * Parameters:  urlPath - request url
	 * Return:		Object encapsulating GET response
	 * --------------------------------------------------*/	
	private static Object get(String urlPath) {
		Object object = null;

		try {
			URL url = new URL("http://www.reddit.com" + urlPath);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setUseCaches(false);
			connection.setRequestMethod("GET");
			connection.setRequestProperty("User-Agent", "Chris & Milton's Latvian Potato App");

			// Debugging stuff
			InputStream is;
			Scanner scanner;
			String response;
			if (connection.getResponseCode() != 200) {
				scanner = new Scanner(connection.getErrorStream());
			} else {
				is = connection.getInputStream();
				scanner = new Scanner(is);
			}
			scanner.useDelimiter("\\Z");
			response = "";
			while (scanner.hasNext()) {
				response += scanner.next();
			}
			scanner.close();
			// Debugging stuff

			JSONParser parser = new JSONParser();
			object = parser.parse(response);

		} catch (IOException e) {
			System.err.println("Error making GET request to URL path: " + urlPath);
		} catch (ParseException e) {
			System.err.println("Error parsing response from GET request for URL path: " + urlPath);
		}

		return object;
	}

	private static String dbURIText = "mongodb://potato:potato@ds035167.mongolab.com:35167/potato";
	private static MongoClientURI uri = new MongoClientURI(dbURIText);
	private static MongoClient client;
	private static DB db;
	private static DBCollection collection;
	public static String lastID = "";
	static {
		// attempt to connect to database
		try {
			client = new MongoClient(uri);
			db = client.getDB("potato");
			collection = db.getCollection("latvianjokes");
		}catch(Exception e) {
			System.err.println("Cannot connect to MongoDB");
		}
	}
}