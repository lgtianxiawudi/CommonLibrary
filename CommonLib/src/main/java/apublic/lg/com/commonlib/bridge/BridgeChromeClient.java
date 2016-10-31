package apublic.lg.com.commonlib.bridge;

import android.webkit.JsPromptResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by ligang967 on 16/10/31.
 */

public class BridgeChromeClient extends WebChromeClient {
    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        if (message.startsWith(BridgeInstance.YY_RETURN_DATA)) { // 如果是返回数据
            BridgeInstance.getInstance().handlerReturnData(message);
        } else if (message.startsWith(BridgeInstance.YY_OVERRIDE_SCHEMA)) { //
            BridgeInstance.getInstance().flushMessageQueue();
        }

        result.confirm();

        return true;
    }
}
