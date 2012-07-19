package app.intramuros.fr.classes.controllers;

import java.util.HashMap;

import app.intramuros.fr.R;
import app.intramuros.fr.classes.models.User;
import app.intramuros.fr.vendors.IMChatChromeClient;
import app.intramuros.fr.vendors.IMChatWebClient;
import app.intramuros.fr.vendors.IMInternalStorageManager;
import android.annotation.SuppressLint;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

@SuppressLint("SetJavaScriptEnabled")
public class ChatFragment extends Fragment {
	private static final String TAG = "IM-Chat";
	private static final String imChatBaseUrl = "http://intramurosapp.com/chat/%s/show";
	public static final int PAGE_FINISHED = 1;
	private WebView webView;
	public ProgressDialog progressDialog;
	private int _layout = R.layout.im_chat;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		getActivity().setContentView(_layout);
	}
	
	@SuppressLint("HandlerLeak")
	private final Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case PAGE_FINISHED:
					Log.i(TAG, "start loading chat webview : " + msg.obj);
					User registredUser = (User) IMInternalStorageManager.getObjectFromInternalStorage("user", getActivity());
					if (registredUser != null) {
						HashMap<String, String> parameters = new HashMap<String, String>();
						ChatFragment.this.webView.loadUrl(String.format(imChatBaseUrl, registredUser.getId()), parameters);
					}
					break;
				default:
					break;
			}
		}
	};
	
	
	@Override
	public void onResume() {
		super.onResume();
		
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(getActivity());
		}
		progressDialog.setMessage(getActivity().getString(R.string.loader_message));
		progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		progressDialog.show();
		
		webView = (WebView) getActivity().findViewById(R.id.im_chat_webview);
		try {
			webView.getSettings().setJavaScriptEnabled(true);
			
			webView.setWebChromeClient(new WebChromeClient() {
				public void onProgressChanged(WebView view, int progress) {
				     progressDialog.setProgress(progress * 1000);
		   		}
			});
			webView.setWebViewClient(new WebViewClient() {
				public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				     Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onPageFinished(WebView view, String Url) {
					Log.i(TAG, "pageFinished");
				}
			});
		}
		catch (NullPointerException NPE) {
			NPE.printStackTrace();
		}
	}
}
