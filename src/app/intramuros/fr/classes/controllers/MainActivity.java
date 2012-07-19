package app.intramuros.fr.classes.controllers;

import java.util.HashMap;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
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
import app.intramuros.fr.vendors.IMTabListener;

public class MainActivity extends Activity {
	private static final String TAG = "IM - MainActivity";
	private static final int _snailView = R.layout.im_snail;
	
	private TabHost _tabBar = null;
	private TabHost.TabSpec _tabSpec = null;
	protected User registredUser = null;
	public IMAPIManager APIManager = null;
	public ActionBar actionBar = null;
	
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
        //this.initProperties(_snailView);
        
        this.APIManager = new IMAPIManager(this, handler);
        this.registredUser = (User) IMInternalStorageManager.getObjectFromInternalStorage("user", this);
        
        Log.i(TAG, this.registredUser.getTeam().getName());
        
        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);
        
        /* Init each tab */
        Tab tab = null;
        
        // Snail
        tab = actionBar.newTab();
        tab.setText(R.string.activity_snail);
        tab.setTabListener(new IMTabListener<SnailFragment>(this, "snail", SnailFragment.class));
        actionBar.addTab(tab);
        actionBar.setSelectedNavigationItem(0);
        actionBar.selectTab(tab);
        

        // Chat
        tab = actionBar.newTab();
        tab.setText(R.string.activity_chat);
        tab.setTabListener(new IMTabListener<ChatFragment>(this, "chat", ChatFragment.class));
        actionBar.addTab(tab);
        

        // Map
        tab = actionBar.newTab();
        tab.setText(R.string.activity_map);
        tab.setTabListener(new IMTabListener<MapFragment>(this, "map", MapFragment.class));
        actionBar.addTab(tab);
    }
    
    private void initProperties(int layout) {
    	setContentView(layout);
    	
    	switch (layout) {
    		case _snailView:
    			
    			break;
    	}
    }
}
