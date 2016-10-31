package apublic.lg.com.commonlib.bridge;

import android.util.Base64;


/**
 * Created by ligang967 on 16/10/31.
 */

public class BridgeUtil {
    /**
     * 获取函数名从Url
     * @param url
     * @return
     */
    public static String getFunctionFromReturnUrl(String url) {
        String temp = url.replace(BridgeInstance.YY_RETURN_DATA, BridgeInstance.EMPTY_STR);
        String[] functionAndData = temp.split(BridgeInstance.SPLIT_MARK);
        if (functionAndData.length >= 1) {
            return functionAndData[0];
        }
        return null;
    }
    /**
     * 获取方法名
     * @param jsUrl
     * @return
     */
    public static String parseFunctionName(String jsUrl) {
        return jsUrl.replace("javascript:WebViewJavascriptBridge.", "").replaceAll("\\(.*\\);", "");
    }

    /**
     * 从URL中获取数据
     * @param url
     * @return
     */
    public static String getDataFromReturnUrl(String url) {
        if (url.startsWith(BridgeInstance.YY_FETCH_QUEUE)) {
            return url.replace(BridgeInstance.YY_FETCH_QUEUE, BridgeInstance.EMPTY_STR);
        }
        String temp = url.replace(BridgeInstance.YY_RETURN_DATA, BridgeInstance.EMPTY_STR);
        int functionAndData = temp.indexOf(BridgeInstance.SPLIT_MARK);
        String result = "";
        if (functionAndData > 0) {
            result = temp.substring(functionAndData+1, temp.length());
            try {
                result = new String(Base64.decode(result.getBytes("UTF-8"), Base64.DEFAULT));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
