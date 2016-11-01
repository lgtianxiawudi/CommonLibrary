package apublic.lg.com.commonlib.bridge;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.webkit.JsPromptResult;
import android.webkit.WebView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import apublic.lg.com.commonlib.util.FileUtil;
import apublic.lg.com.commonlib.util.LogUtil;
import apublic.lg.com.commonlib.util.StrUtil;

/**
 * Created by ligang967 on 16/10/31.
 */

public class BridgeInstance {
    public final static String YY_OVERRIDE_SCHEMA = "wvjbscheme://";
    public final static String YY_RETURN_DATA = YY_OVERRIDE_SCHEMA + "return/";//格式为   yy://return/{function}/returncontent
    public final static String YY_FETCH_QUEUE = YY_OVERRIDE_SCHEMA + "__WVJB_QUEUE_MESSAGE__";//wvjbscheme://__WVJB_QUEUE_MESSAGE__
    public final static String YY_TETCH_QUEUE_SYN = YY_OVERRIDE_SCHEMA+"__SYN_WVJB_QUEUE_MESSAGE__/";
    public final static String EMPTY_STR = "";
    public final static String UNDERLINE_STR = "_";
    public final static String SPLIT_MARK = "/";
    public final static String CALLBACK_ID_FORMAT = "JAVA_CB_%s";
    public final static String JS_HANDLE_MESSAGE_FROM_JAVA = "javascript:WebViewJavascriptBridge._handleMessageFromNative('%s');";
    public final static String JS_FETCH_QUEUE_FROM_JAVA = "javascript:WebViewJavascriptBridge._fetchQueue();";
    public final static String JAVASCRIPT_STR = "javascript:";
    public static final String toLoadJs = "WebViewJavascriptBridge.js";
    public static final String base64Js = "base64.min.js";

    private final static int FLUSHMESSAGEQUEUE = 0;
    private final static int DISPATHCMESSAGE = 1;

    public Map<String, BridgeCallBackFunction> responseCallbacks = new HashMap<String, BridgeCallBackFunction>();
    public Map<String, BridgeHandler> messageHandlers = new HashMap<String, BridgeHandler>();
//    private List<BrgideMessage> startupMessage = new ArrayList<BrgideMessage>();
    private long uniqueId = 0;

    private static BridgeInstance singleton;

    private WebView webView;

    public static BridgeInstance getInstance() {
        return singleton;
    }

    /**
     * 获取实例
     *
     * @param webView
     * @return
     */
    public static BridgeInstance init(WebView webView) {
        if (singleton == null) {
            synchronized (BridgeInstance.class) {
                singleton = new BridgeInstance(webView);
            }
        }
        return singleton;
    }

    private BridgeInstance(WebView webView) {
        if (webView == null){
            return;
        }
        this.webView = webView;
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.getSettings().setJavaScriptEnabled(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        this.webView.setWebChromeClient(new BridgeChromeClient());
        this.webView.setWebViewClient(new BridgeWebClient());
    }

    /**
     * 初始化JSBridge
     */
    public void webViewLoadLocalJs() {
        LogUtil.e("webViewLoadLocalJs", "webViewLoadLocalJs");
        this.webView.clearCache(true);
        String base64 = FileUtil.inputStreamToString(FileUtil.getAssetsToInputSteam(webView.getContext(), base64Js));
        String jsContent = FileUtil.inputStreamToString(FileUtil.getAssetsToInputSteam(webView.getContext(), toLoadJs));
        this.webView.evaluateJavascript(JAVASCRIPT_STR + base64,null);
        this.webView.evaluateJavascript(JAVASCRIPT_STR + jsContent,null);
    }

    /**
     * 接受返回的数据
     * @param url
     */
    public void handlerReturnData(String url) {
        String functionName = BridgeUtil.getFunctionFromReturnUrl(url);
        BridgeCallBackFunction f = responseCallbacks.get(functionName);
        String data = BridgeUtil.getDataFromReturnUrl(url);
        if (f != null) {
            f.onCallBack(data);
            responseCallbacks.remove(functionName);
            return;
        }
    }

    /**
     * 接受返回的数据
     * @param url
     */
    public void handlerReturnDataSyn(String url, final JsPromptResult result) {
        String dataFromReturnUrlSyn = BridgeUtil.getDataFromReturnUrlSyn(url);
        BrgideMessage brgideMessage = BrgideMessage.toObject(dataFromReturnUrlSyn);
        BridgeHandler f = messageHandlers.get(brgideMessage.getHandlerName());
        if (f != null) {
            f.handler(brgideMessage.getData(), new BridgeCallBackFunction() {
                @Override
                public void onCallBack(String data) {
                    result.confirm(data+"");
                }
            });
        }
    }

    /**
     * native数据处理完成后的异步返回
     * @param m
     */
    public void queueMessage(BrgideMessage m) {
//        if (startupMessage != null) {
//            startupMessage.add(m);
//        } else {
            dispatchMessage(m);
//        }
    }

    private void dispatchMessage(BrgideMessage m) {
        String messageJson = m.toJson();
        //escape special characters for json string
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
        String javascriptCommand = String.format(BridgeInstance.JS_HANDLE_MESSAGE_FROM_JAVA, messageJson);
        Message message = handle.obtainMessage();
        message.arg1 = DISPATHCMESSAGE;
        message.obj = javascriptCommand;
        handle.sendMessage(message);
    }

    /**
     * 发送通知去请求数据
     */
    void flushMessageQueue() {
        handle.sendEmptyMessage(FLUSHMESSAGEQUEUE);
    }


    Handler handle = new Handler(Looper.getMainLooper()) {
        @Override
        public void dispatchMessage(android.os.Message msg) {

            switch (msg.arg1) {
                case FLUSHMESSAGEQUEUE: {
                    String methodName = BridgeUtil.parseFunctionName(BridgeInstance.JS_FETCH_QUEUE_FROM_JAVA);
                    if (!responseCallbacks.containsKey(methodName)){
                        responseCallbacks.put(methodName, new BridgeCallBack());
                    }
                    loadUrl(BridgeInstance.JS_FETCH_QUEUE_FROM_JAVA);
                }
                break;
                case DISPATHCMESSAGE: {
                    loadUrl(msg.obj + "");
                }
                break;
            }

        }
    };

    private void loadUrl(String jsUrl) {
        if (webView == null) {
            return;
        }
        webView.loadUrl(jsUrl);
    }

    /**
     * register handler,so that javascript can call it
     *
     * @param handlerName
     * @param handler
     */
    public void registerHandler(String handlerName, BridgeHandler handler) {
        if (handler != null) {
            messageHandlers.put(handlerName, handler);
        }
    }

    /**
     * call javascript registered handler
     *
     * @param handlerName
     * @param data
     * @param callBack
     */
    public void callHandler(String handlerName, String data, BridgeCallBackFunction callBack) {
        doSend(handlerName, data, callBack);
    }

    /**
     * send a message to javascript
     *
     * @param handlerName
     * @param data
     * @param responseCallback
     */
    private void doSend(String handlerName, String data, BridgeCallBackFunction responseCallback) {
        BrgideMessage m = new BrgideMessage();
        if (!StrUtil.isEmpty(data)) {
            m.setData(data);
        }
        if (responseCallback != null) {
            String callbackStr = String.format(BridgeInstance.CALLBACK_ID_FORMAT, ++uniqueId + (BridgeInstance.UNDERLINE_STR + SystemClock.currentThreadTimeMillis()));
            responseCallbacks.put(callbackStr, responseCallback);
            m.setCallbackId(callbackStr);
        }
        if (!StrUtil.isEmpty(handlerName)) {
            m.setHandlerName(handlerName);
        }
        queueMessage(m);
    }
}
