package app.intramuros.fr.classes.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import app.intramuros.fr.R;
import app.intramuros.fr.classes.models.User;
import app.intramuros.fr.vendors.IMAPIManager;
import app.intramuros.fr.vendors.IMInternalStorageManager;

@SuppressLint("HandlerLeak")
public class ConnectionActivity extends Activity implements OnClickListener {
	private static final String TAG = "IM-CA";
	
	private LinearLayout rootLayout = null;
	
	private final static int _layoutForm = R.layout.im_connection_form;
	private final static int _layoutCallback = R.layout.im_connexion_callback;
	
	// Form widgets
	private TextView error_empty_username = null;
	private TextView error_empty_email = null;
	private EditText field_username = null;
	private EditText field_email = null;
	private Button submit_connection = null;
	
	// Callback widgets
	private ImageView team_logo = null;
	private TextView username = null;
	private TextView team = null;
	private TextView opponent_team = null;
	private Button button_play = null;
	
	private IMAPIManager APIManager = null;
	
	public static final int REGISTER_USER = 1;
	
	@SuppressLint("HandlerLeak")
	private final Handler handler = new Handler() {
		@SuppressWarnings("unchecked")
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case REGISTER_USER:
					User registredUser = new User((HashMap<String, Object>) msg.obj);
					
					IMInternalStorageManager.saveObjectToInternalStorage(registredUser, "user", ConnectionActivity.this);
					
					ConnectionActivity.this.initProperties(ConnectionActivity._layoutCallback);
					break;
				default:
					break;
			}
		}
	};
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        rootLayout = new LinearLayout(this);
        rootLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        setContentView(rootLayout);
        
		this.APIManager = new IMAPIManager(this, handler);
        
        this.initProperties(_layoutForm);
    }
    
    private void initProperties(int layout) {
    	rootLayout.removeAllViews();
    	// Display layout
    	rootLayout.addView(getLayoutInflater().inflate(layout, null));
    	
    	switch (layout) {
	    	case _layoutForm:
	        	this.field_username = (EditText)findViewById(R.id.field_username);
	        	this.field_email = (EditText)findViewById(R.id.field_email);
	        	this.error_empty_username = (TextView)findViewById(R.id.error_empty_username);
	        	this.error_empty_email = (TextView)findViewById(R.id.error_empty_email);
	        	this.submit_connection = (Button)findViewById(R.id.submit_connection);
	        	
	        	this.submit_connection.setOnClickListener(this);
	    		break;
	    	case _layoutCallback:
	    		User registredUser = (User) IMInternalStorageManager.getObjectFromInternalStorage("user", this);
	    		
	    		this.team_logo = (ImageView)findViewById(R.id.team_logo);
	    		this.team_logo.setImageResource(R.drawable.im_logo);
	    		
	    		this.username = (TextView)findViewById(R.id.username);
	    		this.username.setText(registredUser.getUsername());
	    		
	    		this.team = (TextView)findViewById(R.id.team_name);
	    		this.team.setText(registredUser.getTeam().getName());
	    		
	    		this.opponent_team = (TextView)findViewById(R.id.opponent_team);
	    		this.opponent_team.setText((registredUser.getTeam().getIndice() == 1 ? "Les Blue Hats" : "Les Red Jackets"));
	    		
	    		this.button_play = (Button)findViewById(R.id.button_play);
	    		
	    		this.button_play.setOnClickListener(this);
	    		break;
    	}
    }
    
	public void onClick(View sender) {
		switch (sender.getId()) {
			case R.id.submit_connection:
				try {
					Log.i(TAG, this.field_username.getText().toString());
					Log.i(TAG, this.field_email.getText().toString());
					ArrayList<String> errors = new ArrayList<String>();
					
					if (this.field_username.getText().length() == 0) {
						errors.add(getString(R.string.error_empty_username));
						Log.i(TAG, "Height : " + this.error_empty_username.getHeight());
						this.error_empty_username.setHeight(25);
					} else {
						Log.i(TAG, "Height : " + this.error_empty_username.getHeight());
						this.error_empty_username.setHeight(0);
					}
					if (this.field_email.getText().length() == 0) {
						errors.add(getString(R.string.error_empty_email));
						Log.i(TAG, "Height : " + this.error_empty_email.getHeight());
						this.error_empty_email.setHeight(25);
					} else {
						Log.i(TAG, "Height : " + this.error_empty_email.getHeight());
						this.error_empty_email.setHeight(0);
					}
					
					if (errors.size() == 0) {
						HashMap<String, String> parameters = new HashMap<String, String>();
						
						parameters.put("username", this.field_username.getText().toString());
						parameters.put("email", this.field_email.getText().toString());
						
						APIManager.registerUser(parameters);
					}
				}
				catch (ActivityNotFoundException ANFE) {
					Log.e(TAG, ANFE.getMessage());
				}
		        catch (NullPointerException NPE) {
		        	Log.e(TAG, "NPE caused by " + NPE.getCause() + " --> " + NPE.getMessage());
		        	NPE.printStackTrace();
		        }
				catch (Exception E) {
					Log.e(TAG, "E caused by " + E.getCause() + " --> " + E.getMessage());
				}
				break;
			case R.id.button_play:
				try {
					Intent mainActivity = new Intent(ConnectionActivity.this, MainActivity.class);
					startActivity(mainActivity);
				}
				catch (NullPointerException NPE) {
					NPE.printStackTrace();
				}
				catch (ActivityNotFoundException ANFE) {
					ANFE.printStackTrace();
				}
				break;
			default:
				break;
		}
	}
}