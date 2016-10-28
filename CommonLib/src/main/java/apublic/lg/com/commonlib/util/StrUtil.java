package apublic.lg.com.commonlib.util;

import android.text.TextUtils;

/**
 * Created by ligang967 on 16/9/6.
 */
public class StrUtil {
    public static boolean isEmpty(String content){
        if (TextUtils.isEmpty(content)||TextUtils.isEmpty(content.trim())||"null".equals(content.toLowerCase())){
            return true;
        }
        return false;
    }
}
