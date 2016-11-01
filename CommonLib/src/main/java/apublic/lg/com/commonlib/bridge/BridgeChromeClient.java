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
        if (message.startsWith(BridgeInstance.YY_RETURN_DATA)) { // 如果是返回数据
            BridgeInstance.getInstance().handlerReturnData(message);
            result.confirm();
        } else if (message.startsWith(BridgeInstance.YY_TETCH_QUEUE_SYN)) {
            BridgeInstance.getInstance().handlerReturnDataSyn(message, result);
        } else if (message.startsWith(BridgeInstance.YY_FETCH_QUEUE)) { //
            BridgeInstance.getInstance().flushMessageQueue();
            result.confirm();
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
            LogUtil.d(" inject js interface completely on progress " + newProgress);

        }
    }
}
