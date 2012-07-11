package app.intramuros.fr.vendors;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import app.intramuros.fr.R;
import app.intramuros.fr.classes.controllers.ConnectionActivity;
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
    private final Handler messageHandler;
    protected Context contextActivity = null;
    
    public IMAPIManager(Context context, Handler handler) {
    	this.contextActivity = context;
    	this.objectMapper = new ObjectMapper();
    	this.jsonFactory = new JsonFactory();
    	this.messageHandler = handler;
    }
    
	@SuppressWarnings("unchecked")
	public void getAllUsers() {
    	try {
	    	IMAPICaller caller = new IMAPICaller(this);
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("path", "api/users/lookup");
			params.put("method", "GET");
			params.put("parameters", null);
			caller.execute(params);
		}
		catch (NullPointerException NPE) {
			Log.e(TAG, "NPE --> " + NPE.getMessage());
			NPE.printStackTrace();
		}
    }
	
	@SuppressWarnings("unchecked")
	public void registerUser(HashMap<String, String> parameters) {
		try {
			IMAPICaller caller = new IMAPICaller(this);
			HashMap<String, Object> params = new HashMap<String, Object>();
			params.put("path", "api/users/register");
			params.put("method", "POST");
			params.put("parameters", parameters);
			params.put("class", User.class);
			params.put("code", ConnectionActivity.REGISTER_USER);
			caller.execute(params);
		}
		catch (NullPointerException NPE) {
			Log.e(TAG, "NPE --> " + NPE.getMessage());
			NPE.printStackTrace();
		}
	}
    
    private class IMAPICaller extends AsyncTask<HashMap<String, Object>, Integer, HashMap<String, Object>> {
        private HttpURLConnection APIconnection = null;
        int activityCode = 0;
        HashMap<String, Object> response = null;
        public ProgressDialog loader = null;
        
        public IMAPICaller(IMAPIManager APIManager) {
        	
        }
        
        @Override
        protected void onPreExecute() {
            Log.i(IMAPIManager.TAG, "onPreExecute");
            if (this.loader == null) {
            	this.loader = new ProgressDialog(contextActivity);
            }
            this.loader.setMessage(contextActivity.getString(R.string.loader_message));
        	this.loader.show();
        }
    	
		@SuppressWarnings("unchecked")
		@Override
		protected  HashMap<String, Object> doInBackground(HashMap<String, Object>... args) {
			String URLPath = (String) args[0].get("path");
			String method = (String) args[0].get("method");
			HashMap<String, String> parameters = (HashMap<String, String>) args[0].get("parameters");
			Object responseClass = (Object) args[0].get("class");
			activityCode = Integer.parseInt(args[0].get("code").toString());
			
			JsonParser callbackParser = null;
			URL completePath = null;
			try {
				completePath = new URL(IMAPIManager.baseUrl + URLPath);
				APIconnection = (HttpURLConnection) completePath.openConnection();
				APIconnection.setDoInput(true);
				
				if (method == "GET") {
					APIconnection.setRequestMethod("GET");
					APIconnection.connect();
					
					callbackParser = jsonFactory.createJsonParser(APIconnection.getInputStream());
				} else if (method == "POST") {
					APIconnection.setDoOutput(true);
					APIconnection.setRequestMethod("POST");
					APIconnection.setRequestProperty("Accept", "application/json");
				    String encodedParams = "";
				    
				    int i = 0;
				    for (Iterator<Map.Entry<String, String>> iterator = parameters.entrySet().iterator(); iterator.hasNext();) {
						Map.Entry<String, String> ent = iterator.next();
						
						if (i > 0) {
							encodedParams += "&";
						}
						encodedParams += URLEncoder.encode((String) ent.getKey(), "UTF-8") + "=" + URLEncoder.encode((String) ent.getValue(), "UTF-8");
						i++;
					}
				    
				    Log.i(TAG, encodedParams.toString());
				    
					try {
						OutputStream os = APIconnection.getOutputStream();
						os.write(encodedParams.getBytes("UTF-8"));
					}
					catch (ProtocolException PE) {
						Log.e(TAG, PE.getMessage());
						PE.printStackTrace();
					}
					catch (JsonGenerationException e) {
						e.printStackTrace();
					}
					catch (JsonMappingException e) {
						e.printStackTrace();
					}
					catch (IOException e) {
						e.printStackTrace();
					}
					
				    APIconnection.connect();
				    
					int statusCode = APIconnection.getResponseCode();
					if ((statusCode >= HttpURLConnection.HTTP_OK) && (statusCode < HttpURLConnection.HTTP_MULT_CHOICE)) {
						Log.i(TAG, "statusCode correct !!");
			            callbackParser = jsonFactory.createJsonParser(APIconnection.getInputStream());
					} else {
						Log.w(TAG, String.format("statusCode : %d", statusCode));
						Log.w(TAG, APIconnection.getResponseMessage());
						for (Entry<String, List<String>> header : APIconnection.getHeaderFields().entrySet()) {
						    Log.w(IMAPIManager.TAG, header.getKey() + " --> " + header.getValue());
						}
					}
				}
				if (callbackParser != null) {
					Log.i(TAG, "response : " + responseClass.toString());
					Log.i(TAG, "callback : " + callbackParser.getClass().toString());
					Log.i(TAG, callbackParser.toString());
					response = objectMapper.readValue(callbackParser, HashMap.class);
					Log.i(TAG, "response has been read");
				}
			}
			catch (NullPointerException NPE) {
				Log.e(IMAPIManager.TAG, "NPE : " + NPE.getMessage());
				NPE.printStackTrace();
			}
			catch (JsonParseException JPE) {
				Log.e(IMAPIManager.TAG, "JPE : " + JPE.getMessage());
				JPE.printStackTrace();
			}
			catch (JsonMappingException JME) {
				Log.e(IMAPIManager.TAG, "JME : " + JME.getMessage() + " caused by : " + JME.getCause());
				JME.printStackTrace();
			}
			catch (MalformedURLException MUE) {
				Log.e(IMAPIManager.TAG, "MUE : " + MUE.getMessage());
				MUE.printStackTrace();
			}
			catch (IOException IOE) {
				Log.e(IMAPIManager.TAG, "IOE : " + IOE.getMessage());
				IOE.printStackTrace();
			}
			finally {
				Log.i(IMAPIManager.TAG, "disconnection");
				APIconnection.disconnect();
			}
			return response;
		}
		
		@Override
		protected void onPostExecute(HashMap<String, Object> responseObject) {
            Log.i(IMAPIManager.TAG, "onPostExecute");
            this.loader.cancel();
            
			// Send responseObject to UIThread
			if (responseObject != null) {
				Log.i(TAG, "responseObject is not null [ instance of Class : " + responseObject.getClass().toString() + "]");
				Message msg = messageHandler.obtainMessage(activityCode);
				Log.i(IMAPIManager.TAG, "code : " + activityCode);
				msg.what = activityCode;
				msg.obj = responseObject;
				messageHandler.sendMessage(msg);
			}
	    }
    }
}
