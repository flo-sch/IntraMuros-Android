package app.intramuros.fr.vendors;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import app.intramuros.fr.classes.models.*;

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
	public JsonParser callAPIPath(String path, String method, String local_filename, HashMap<String, String> parameters) {
    	Log.i(TAG, baseUrl + path);
    	URL completePath = null;
    	jsonParser = null;
    	
		try {
			completePath = new URL(baseUrl + path);
			HttpURLConnection APIconnection = (HttpURLConnection) completePath.openConnection();
			APIconnection.setDoInput(true);
			
			if (method == "GET") {
				downloadJsonFile(completePath.toString(), "GET", local_filename);
				
				jsonParser = jsonFactory.createJsonParser(jsonFile);
			} else if (method == "POST") {
				APIconnection.setDoOutput(true);
				APIconnection.setRequestMethod("POST");
				
			    String data = "";
			    
			    int i = 0;
			    for (Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator(); iterator.hasNext();) {
					Map.Entry<String, String> ent = iterator.next();
					
					if (i > 0) {
						data += URLEncoder.encode("&", "UTF-8");
					}
					data += URLEncoder.encode((String) ent.getKey(), "UTF-8") + "=" + URLEncoder.encode((String) ent.getValue(), "UTF-8");
					i++;
				}
			    
			    Log.i(TAG, data.toString());
			    
				objectMapper.writeValue(APIconnection.getOutputStream(), data);

			    APIconnection.connect();
				int statusCode = APIconnection.getResponseCode();
				
				Log.i(TAG, String.format("statusCode : %d", statusCode));
				
				createFileAndDirectory(local_filename);
	            
	            FileOutputStream fileOutput = new FileOutputStream(jsonFile);
	            InputStream inputStream = APIconnection.getInputStream();
	            
	            byte[] buffer = new byte[1024];
	            int bufferLength = 0;
	            while ((bufferLength = inputStream.read(buffer)) > 0) {
	            	fileOutput.write(buffer, 0, bufferLength);
	            }
	            
	            fileOutput.close();
				
				jsonParser = jsonFactory.createJsonParser(jsonFile);
				
			}
		}
		catch (MalformedURLException MUE) {
        	Log.e(TAG, MUE.getMessage());
        }
    	catch (IOException IOE) {
    		Log.e(TAG, IOE.getMessage());
		}
    	catch (Exception E) {
    		Log.e(TAG, E.getMessage());
    	}
    	
		return jsonParser;
    }
    
	public ArrayList<User> getAllUsers() {
    	ArrayList<User> users = new ArrayList<User>();
    	
    	try {
    		jsonParser = this.callAPIPath("api/users/lookup", "GET", "users", null);
    		users.add(objectMapper.readValue(jsonParser, User.class));
		}
    	catch (JsonParseException JPE) {
			// TODO Auto-generated catch block
			Log.e(TAG, JPE.getMessage());
			JPE.printStackTrace();
		} catch (JsonMappingException JME) {
			// TODO Auto-generated catch block
			Log.e(TAG, JME.getMessage());
			JME.printStackTrace();
		} catch (IOException IOE) {
			// TODO Auto-generated catch block
			Log.e(TAG, IOE.getMessage());
			IOE.printStackTrace();
		}
        
    	return users;
    }
	
	public User registerUser(HashMap<String, String> parameters) {
		User user = null;
		
		jsonParser = this.callAPIPath("users/register", "POST", "user", parameters);
		try {
			user = objectMapper.readValue(jsonParser, User.class);
		}
		catch (JsonParseException JPE) {
			// TODO Auto-generated catch block
			Log.e(TAG, JPE.getMessage());
			JPE.printStackTrace();
		} catch (JsonMappingException JME) {
			// TODO Auto-generated catch block
			Log.e(TAG, JME.getMessage());
			JME.printStackTrace();
		} catch (IOException IOE) {
			// TODO Auto-generated catch block
			Log.e(TAG, IOE.getMessage());
			IOE.printStackTrace();
		}
		
		return user;
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
            urlConnection.setDoInput(true);
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
