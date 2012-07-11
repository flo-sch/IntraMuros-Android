package app.intramuros.fr.classes.controllers;

import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import app.intramuros.fr.R;
import app.intramuros.fr.classes.models.User;
import app.intramuros.fr.vendors.IMAPIManager;
import app.intramuros.fr.vendors.IMInternalStorageManager;

public class MainActivity extends Activity {
	private static final String TAG = "IM - MainActivity";
	
	public IMAPIManager APIManager = null;
	
	protected User registredUser = null;
	
	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				default:
					break;
			}
		}
	};
	
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.im_snail);
        
        this.APIManager = new IMAPIManager(this, handler);
        this.registredUser = (User) IMInternalStorageManager.getObjectFromInternalStorage("user", this);
        
    }
}
