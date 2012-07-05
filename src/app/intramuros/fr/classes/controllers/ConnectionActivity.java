package app.intramuros.fr.classes.controllers;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import app.intramuros.fr.R;
import app.intramuros.fr.classes.models.User;
import app.intramuros.fr.vendors.IMAPIManager;

public class ConnectionActivity extends Activity implements OnClickListener {
	private static final String TAG = "IM-CA";
	private TextView error_empty_username = null;
	private TextView error_empty_email = null;
	private EditText field_username = null;
	private EditText field_email = null;
	private Button submit_connection = null;
	
	private IMAPIManager APIManager = null;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);
        
        this.initProperties();
    }
    
    private void initProperties() {
    	this.APIManager = new IMAPIManager(this);
    	
    	this.field_username = (EditText)findViewById(R.id.field_username);
    	this.field_email = (EditText)findViewById(R.id.field_email);
    	this.error_empty_username = (TextView)findViewById(R.id.error_empty_username);
    	this.error_empty_email = (TextView)findViewById(R.id.error_empty_email);
    	this.submit_connection = (Button)findViewById(R.id.submit_connection);
    	
    	this.submit_connection.setOnClickListener(this);
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
						
						User user = APIManager.registerUser(parameters);
						
						Log.i(TAG, user.getUsername());
						
						
						Intent mainActivity = new Intent(ConnectionActivity.this, MainActivity.class);
						
						startActivity(mainActivity);
					}
				}
				catch (ActivityNotFoundException ANFE) {
					Log.e(TAG, ANFE.getMessage());
				}
		        catch (NullPointerException NPE) {
		        	Log.e(TAG, "NPE caused by " + NPE.getCause() + " --> " + NPE.getMessage());
		        }
				catch (Exception E) {
					Log.e(TAG, E.getMessage());
				}
				break;
		}
	}
}