package apublic.lg.com.commonlib.bridge;

import android.util.Base64;


/**
 * Created by ligang967 on 16/10/31.
 */

public class BridgeUtil {

    /**
     * 从URL中获取数据
     *
     * @param url
     * @return
     */
    public static String getDataFromReturnUrlSyn(String url) {

        String temp = url.replace(BridgeInstance.YY_TETCH_QUEUE_SYN, BridgeInstance.EMPTY_STR);
        String result = "";
        try {
            result = new String(Base64.decode(temp.getBytes("UTF-8"), Base64.DEFAULT));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
