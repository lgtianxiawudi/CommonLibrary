package apublic.lg.com.commonlib.bridge;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.webkit.JsPromptResult;
import android.webkit.ValueCallback;
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
    public final static String YY_TETCH_QUEUE = YY_OVERRIDE_SCHEMA + "__WVJB_QUEUE_MESSAGE__";//格式为   yy://return/{function}/returncontent
    public final static String YY_TETCH_QUEUE_SYN = YY_OVERRIDE_SCHEMA+"__SYN_WVJB_QUEUE_MESSAGE__/";
    public final static String EMPTY_STR = "";
    public final static String JS_HANDLE_MESSAGE_FROM_JAVA = "javascript:WebViewJavascriptBridge._handleMessageFromNative('%s');";
    public final static String JS_SEND_MESSAGE_FROM_JAVA = "javascript:WebViewJavascriptBridge._sendMessageFromNative('%s');";
    public final static String JS_FETCH_QUEUE_FROM_JAVA = "javascript:WebViewJavascriptBridge._fetchQueue();";
    public final static String JAVASCRIPT_STR = "javascript:";
    public static final String toLoadJs = "WebViewJavascriptBridge.js";

    public final static int DISJSGETDATAFROMJS = 0;
    public final static int DISPATHCMESSAGE = 1;
    public final static int DISSENDDATATOJS = 2;

    public Map<String, BridgeHandler> messageHandlers = new HashMap<String, BridgeHandler>();
    public Map<String,BridgeCallBackFunction> sendMessageCallBack = new HashMap<>();
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
        String base64 = FileUtil.inputStreamToString(FileUtil.getAssetsToInputSteam(webView.getContext(), "WebViewJavascriptBridge.js"));
        this.webView.evaluateJavascript(JAVASCRIPT_STR + base64,null);
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
            dispatchMessage(m);
    }

    private void dispatchMessage(BrgideMessage m) {
        String messageJson = m.toJson();
        //escape special characters for json string
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
        String javascriptCommand = String.format(BridgeInstance.JS_HANDLE_MESSAGE_FROM_JAVA, messageJson);
        flushMessageQueue(DISPATHCMESSAGE,javascriptCommand);
    }

    /**
     * 发送通知去请求数据
     */
    void flushMessageQueue(int type,String msg) {
        Message message = new Message();
        message.arg1 = type;
        message.obj = msg;
        handle.sendMessage(message);
    }



    Handler handle = new Handler(Looper.getMainLooper()) {
        @Override
        public void dispatchMessage(Message msg) {

            switch (msg.arg1) {
                case DISJSGETDATAFROMJS: {
                    loadJs(msg.obj+"",new BridgeCallBack());
                }
                break;
                case DISSENDDATATOJS:{
                    loadJs(msg.obj+"",new BridgeSendCallback());
                }
                break;
                case DISPATHCMESSAGE: {
                    loadUrl(msg.obj + "");
                }
                break;
            }

        }
    };

    private void loadJs(String js, final BridgeCallBackFunction bridgeCallBack) {
        if (webView == null) {
            return;
        }
        webView.evaluateJavascript(js, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String value) {
                if (bridgeCallBack!=null){
                    bridgeCallBack.onCallBack(value);
                }
            }
        });
    }
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

    public void sendMsg(String handlerName,String data,BridgeCallBackFunction backFunction){
        BrgideMessage brgideMessage = new BrgideMessage();
        String callBackId = System.currentTimeMillis()+"_"+Math.random()*10000;
        sendMessageCallBack.put(callBackId,backFunction);
        brgideMessage.setCallbackId(callBackId);
        brgideMessage.setData(data);
        brgideMessage.setHandlerName(handlerName);
        String messageJson = brgideMessage.toJson();
        //escape special characters for json string
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
        String javascriptCommand = String.format(BridgeInstance.JS_SEND_MESSAGE_FROM_JAVA, messageJson);
        flushMessageQueue(DISSENDDATATOJS,javascriptCommand);
    }

}
