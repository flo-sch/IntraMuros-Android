package app.intramuros.fr.vendors;

import android.app.ProgressDialog;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class IMChatChromeClient extends WebChromeClient {
	private ProgressDialog progressDialog = null;
	
	public IMChatChromeClient(ProgressDialog dialog) {
		progressDialog = dialog;
	}
	
	@Override
	public void onProgressChanged(WebView view, int progress) {
		if (progressDialog != null) {
			progressDialog.setProgress(progress * 1000);
		}
	}
}
