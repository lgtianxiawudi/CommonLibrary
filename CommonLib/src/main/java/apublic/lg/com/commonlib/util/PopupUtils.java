package apublic.lg.com.commonlib.util;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;


/**
 * Created by ligang967 on 16/7/12.
 */
public class PopupUtils {


    public static PopupWindow createCustomVIewPopupWindow(View drapView, View customView,
                                                          PopupWindow.OnDismissListener onDismissListener) {
        //自适配长、框设置
        final PopupWindow popupWindow = new PopupWindow(customView,
                ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(customView.getContext(),300));
        popupWindow.setFocusable(true);
        popupWindow.setOnDismissListener(onDismissListener);
        ColorDrawable dw = new ColorDrawable(00000);
        popupWindow.setBackgroundDrawable(dw);
        customView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return false;
            }
        });
        popupWindow.showAtLocation(drapView, Gravity.BOTTOM,0,0);
        return popupWindow;
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    private void backgroundAlpha(Activity context, float bgAlpha) {
        if (context == null) {
            return;
        }
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        context.getWindow().setAttributes(lp);
    }
}
