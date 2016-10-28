package apublic.lg.com.commonlib.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

/**
 * Created by ligang967 on 16/9/6.
 */
public class ToastUtil {
    /** 上下文. */
    private static Context mContext = null;

    /** 显示Toast. */
    public static final int SHOW_TOAST = 0;

    /**
     * 描述：Toast提示文本.
     * @param text  文本
     */
    public static void showToast(@NonNull Context context,@NonNull String text) {
        mContext = context;
        if(!StrUtil.isEmpty(text)){
            Toast.makeText(context,text, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 描述：Toast提示文本.
     * @param resId  文本的资源ID
     */
    public static void showToast(@NonNull Context context,@NonNull int resId) {
        mContext = context;
        Toast.makeText(context,""+context.getResources().getText(resId), Toast.LENGTH_SHORT).show();
    }

    public static void showToastCustomView(@NonNull Context context,@NonNull View view){
        Toast toast = new Toast(context);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }
}
