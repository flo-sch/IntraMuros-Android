package app.intramuros.fr.classes.controllers;

import java.util.HashMap;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TabHost;
import app.intramuros.fr.R;
import app.intramuros.fr.classes.models.User;
import app.intramuros.fr.vendors.IMAPIManager;
import app.intramuros.fr.vendors.IMInterfaceLoader;
import app.intramuros.fr.vendors.IMInternalStorageManager;

public class MainActivity extends Activity {
	private static final String TAG = "IM - MainActivity";
	private static final int _snailView = R.layout.im_snail;
	
	private TabHost _tabBar = null;
	private TabHost.TabSpec _tabSpec = null;
	protected User registredUser = null;
	public IMAPIManager APIManager = null;
	
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
        this.initProperties(_snailView);
        
        this.APIManager = new IMAPIManager(this, handler);
        this.registredUser = (User) IMInternalStorageManager.getObjectFromInternalStorage("user", this);
        
        Log.i(TAG, this.registredUser.getTeam().getName());
        
        this._tabBar = (TabHost)findViewById(R.id._tabbar);
        this._tabBar.setup();
        
        this.initTabbar();
    }
    
    private void initProperties(int layout) {
    	setContentView(layout);
    	
    	switch (layout) {
    		case _snailView:
    			
    			break;
    	}
    }
    
    private void initTabbar() {
        // Initialize a TabSpec for each tab and add it to the TabHost with an intent as the content activity
    	Intent intent = null;
    	HashMap<String, Object> interfaceProperties = IMInterfaceLoader.getInterfaceProperties(this);
    	
    	try {
	    	intent = new Intent().setClass(this, CheckinActivity.class);
	        _tabSpec = _tabBar.newTabSpec("checkin").setIndicator("Checkin", getResources().getDrawable((Integer) interfaceProperties.get("icon_checkin"))).setContent(intent);
	        _tabBar.addTab(_tabSpec);
	        
	        intent = new Intent().setClass(this, ChatActivity.class);
	        _tabSpec = _tabBar.newTabSpec("chat").setIndicator("Chat",  getResources().getDrawable((Integer) interfaceProperties.get("icon_chat"))).setContent(intent);
	        _tabBar.addTab(_tabSpec);
	
	        intent = new Intent().setClass(this, MapActivity.class);
	        _tabSpec = _tabBar.newTabSpec("map").setIndicator("Map",  getResources().getDrawable((Integer) interfaceProperties.get("icon_map"))).setContent(intent);
	        _tabBar.addTab(_tabSpec);
    	}
    	catch (NullPointerException NPE) {
    		NPE.printStackTrace();
    	}
    	catch (ActivityNotFoundException ANFE) {
    		ANFE.printStackTrace();
    	}
    	catch (IllegalStateException ISE) {
    		ISE.printStackTrace();
    	}
    }
}
