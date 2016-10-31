package apublic.lg.com.commonlib.bridge;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by ligang967 on 16/10/31.
 */

public class BridgeWebClient extends WebViewClient {
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        BridgeInstance.getInstance().webViewLoadLocalJs();
    }
}
