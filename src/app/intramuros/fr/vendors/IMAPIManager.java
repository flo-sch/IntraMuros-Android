package app.intramuros.fr.vendors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

/**
 * 
 * @author Flo Schield Bobby
 * 
 * This service is the project's background API-referent
 * This is a helper for every call to the API
 * It will format the JSON objects to custom classes
 * And send callback to the sender 
 *
 */
public class IMAPIManager {
	private static final String TAG = "IM-APIManager";
	public static String baseUrl = "http://intramurosapp.com/";
	
    public IMAPIManager(Context context) {
    	
    }
    
    public String callAPIPath(String path, String method, ArrayList<String> parameters) {
    	StringBuilder builder = new StringBuilder();
    	HttpClient client = new DefaultHttpClient();
    	Log.i(TAG, baseUrl + path);
    	HttpGet completePath = new HttpGet(baseUrl + path);
    	
    	try {
    		HttpResponse callback = client.execute(completePath);
    		int statusCode = callback.getStatusLine().getStatusCode();
    		
    		if (statusCode >= 200 && statusCode < 300) {
    			HttpEntity entity = callback.getEntity();
				InputStream content = entity.getContent();
				BufferedReader reader = new BufferedReader(new InputStreamReader(content));
				
				String line = null;
				while ((line = reader.readLine()) != null) {
					builder.append(line);
				}
    		} else if (statusCode >= 400) {
    			Log.e(TAG, "Error, statusCode : " + statusCode);
    			// throw a custom exception
    		}
    	}
    	catch (ClientProtocolException CPE) {
    		Log.e(TAG, CPE.getMessage());
    	}
    	catch (IOException IOE) {
    		Log.e(TAG, IOE.getMessage());
		}
    	
		return builder.toString();
    }
}
