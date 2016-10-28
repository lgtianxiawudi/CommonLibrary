package apublic.lg.com.commonlib.util;

import android.view.View;

/**
 * Created by ligang967 on 16/9/8.
 */
public class MeasureUtil {
    /**
     * 测量VIew的宽高
     * @param measureSPec
     * @param defaultSpec
     * @return
     */
    public static int getMeasureByModel(int measureSPec,int defaultSpec){
        int model = View.MeasureSpec.getMode(measureSPec);
        int specSize = View.MeasureSpec.getSize(measureSPec);
        if (View.MeasureSpec.EXACTLY == model){
            return specSize;
        }else if (View.MeasureSpec.AT_MOST == model){
            return Math.min(defaultSpec,specSize);
        }else {
            return defaultSpec;
        }
    }
}
