package app.intramuros.fr.vendors;

import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import app.intramuros.fr.classes.controllers.ChatFragment;

public class IMChatWebClient extends WebViewClient {
	private static final String TAG = "IMChat-WebClient";
	private Fragment contextFragment;
	private Handler handler;
	
	public IMChatWebClient(Fragment context, Handler messageHandler) {
		this.contextFragment = context;
		handler = messageHandler;
		Log.i(TAG, "IMChatWebClient created.");
	}
	
	@Override
	public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
		Log.e(TAG, "Loading error. || code || " + errorCode + " || message || " + description + " || on || " + failingUrl);
   }
	
	@Override
	public void onPageFinished(WebView view, String Url) {
		Log.i(TAG, "IMChatWebClient finished, sending message to handler.");
		Message msg = handler.obtainMessage(ChatFragment.PAGE_FINISHED);
		msg.what = ChatFragment.PAGE_FINISHED;
		msg.obj = Url;
		handler.sendMessage(msg);
	}
}