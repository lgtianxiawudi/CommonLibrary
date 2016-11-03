package apublic.lg.com.commonlib.bridge;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import apublic.lg.com.commonlib.util.LogUtil;

/**
 * Created by ligang967 on 16/10/31.
 */

public class BridgeChromeClient extends WebChromeClient {
    private boolean mIsInjectedJS = false;

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (message.equals(BridgeInstance.YY_TETCH_QUEUE)) { // 如果是返回数据
            BridgeInstance.getInstance().flushMessageQueue(BridgeInstance.DISJSGETDATAFROMJS,BridgeInstance.JS_FETCH_QUEUE_FROM_JAVA);
            result.confirm();
        } else if (message.startsWith(BridgeInstance.YY_TETCH_QUEUE_SYN)) {
            BridgeInstance.getInstance().handlerReturnDataSyn(message, result);
        }else {
            result.confirm();
        }
        return true;
    }

    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (newProgress <= 25) {
            mIsInjectedJS = false;
        } else if (!mIsInjectedJS) {
            BridgeInstance.getInstance().webViewLoadLocalJs();
            mIsInjectedJS = true;
            LogUtil.d(" inject js interfaces completely on progress " + newProgress);

        }
    }
}
