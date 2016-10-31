package apublic.lg.com.commonlib.bridge;

/**
 * Created by ligang967 on 16/10/31.
 */

public interface WebViewJavascriptBridge {
    public void send(String data);
    public void send(String data, BridgeCallBackFunction responseCallback);
}
