package app.intramuros.fr.classes.controllers;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {
	private static final String TAG = "IM - MainActivity";
	public IMAPIManager APIManager = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        this.APIManager = new IMAPIManager(this);
        
        try {
        	String callback = this.APIManager.callAPIPath("api/users/lookup", "GET", null);
        	JSONObject jObject = new JSONObject(callback);
        	Log.i(TAG, jObject.length() + " keys");
        	//Log.i(TAG, callback.toString());
        }
        catch (NullPointerException NPE) {
        	Log.e(TAG, NPE.getMessage());
        }
        catch (Exception E) {
        	Log.e(TAG, E.toString());
        }
    }
}
