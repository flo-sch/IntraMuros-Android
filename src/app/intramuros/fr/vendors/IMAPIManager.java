package app.intramuros.fr.vendors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import app.intramuros.fr.classes.models.User;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.ObjectMapper;

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
	private ObjectMapper objectMapper = null;
    private JsonFactory jsonFactory = null;
    private JsonParser jsonParser = null;
    private File jsonOutputFile;
    private File jsonFile;
    private User user;
    
    public IMAPIManager(Context context) {
    	this.objectMapper = new ObjectMapper();
    	this.jsonFactory = new JsonFactory();
    }
    
    /**
     * Call the IntraMuros API on a path, with a method and parameters.
     * 
     * @param path
     * @param method
     * @param local_filename
     * @param parameters
     * 
     * @return String
     */
    public String callAPIPath(String path, String method, String local_filename, ArrayList<String> parameters) {
    	Log.i(TAG, baseUrl + path);
    	URL completePath = null;
    	user = null;
    	
		try {
			completePath = new URL(baseUrl + path);
			
			if (method == "GET") {
				downloadJsonFile(completePath.toString(), "GET", local_filename);
				
				HttpURLConnection APIconnection = (HttpURLConnection) completePath.openConnection();
				APIconnection.setRequestMethod(method);
				
				jsonParser = jsonFactory.createJsonParser(jsonFile);
		        user = objectMapper.readValue(jsonParser, User.class);
		        
			} else if (method == "POST") {
				
			}
		} catch (MalformedURLException MUE) {
        	Log.e(TAG, MUE.getMessage());
        }
    	catch (IOException IOE) {
    		Log.e(TAG, IOE.getMessage());
		}
    	catch (Exception E) {
    		Log.e(TAG, E.getMessage());
    	}
    	
		return null;
    }
    
    /**
     * write a remote JSON file's content in a local file
     * 
     * @param path
     * @param method
     * @param filename
     */
    private void downloadJsonFile(String path, String method, String filename) {
        try {
            createFileAndDirectory(filename);
            URL url = new URL(path);
            
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod(method);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            
            FileOutputStream fileOutput = new FileOutputStream(jsonFile);
            InputStream inputStream = urlConnection.getInputStream();
            
            byte[] buffer = new byte[1024];
            int bufferLength = 0;
            while ((bufferLength = inputStream.read(buffer)) > 0) {
            	fileOutput.write(buffer, 0, bufferLength);
            }
            
            fileOutput.close();
        } catch (FileNotFoundException FNFE) {
        	Log.e(TAG, FNFE.getMessage());
        } catch (MalformedURLException MUE) {
        	Log.e(TAG, MUE.getMessage());
        } catch (IOException IOE) {
            Log.e(TAG, IOE.getMessage());
        }
    }
    
    private void createFileAndDirectory(String filename) throws FileNotFoundException {
        final String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
        final String jsonDirectory_path = extStorageDirectory + "/jsons";
        jsonOutputFile = new File(jsonDirectory_path, "/");
        if (jsonOutputFile.exists() == false) {
            jsonOutputFile.mkdirs();
        }
        jsonFile = new File(jsonOutputFile, filename + ".json");
    }
}
