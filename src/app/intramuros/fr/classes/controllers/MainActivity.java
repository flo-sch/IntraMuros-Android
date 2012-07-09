package app.intramuros.fr.classes.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import app.intramuros.fr.R;
import app.intramuros.fr.vendors.IMAPIManager;

public class MainActivity extends Activity {
	private static final String TAG = "IM - MainActivity";
	
	public IMAPIManager APIManager = null;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //this.APIManager = new IMAPIManager(this);
        
        try {
        	//this.APIManager.callAPIPath("api/users/lookup", "GET", "user", null);
        }
        catch (NullPointerException NPE) {
        	Log.e(TAG, "NPE caused by " + NPE.getCause() + " --> " + NPE.getMessage());
        }
        catch (Exception E) {
        	Log.e(TAG, E.toString());
        }
    }
}
