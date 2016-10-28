package apublic.lg.com.commonlib.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import java.util.Map;
import java.util.Set;

/**
 * Created by ligang967 on 16/9/23.
 */

public class ActivityUtil {
    /**
     * 不带参数启动Activity
     * @param context
     * @param clss
     */
    public static void startActivity(@NonNull Activity context, @NonNull Class clss) {
        Intent intent = new Intent(context, clss);
        context.startActivity(intent);
    }
    /**
     * 带参数启动Activity
     * @param context
     * @param clss
     * @param bundle
     */
    public static void startActivity(@NonNull Activity context, @NonNull Class clss, Bundle bundle) {
        Intent intent = new Intent(context, clss);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    /**
     * 带参数启动Activity
     * @param context
     * @param clss
     * @param dataKey
     * @param data
     */
    public static void startActivity(@NonNull Activity context, @NonNull Class clss, String dataKey, String data) {
        Intent intent = new Intent(context, clss);
        if (!StrUtil.isEmpty(dataKey)) {
            intent.putExtra(dataKey, data);
        }
        context.startActivity(intent);
    }

    /***
     * 带参数启动Activity
     * @param context
     * @param clss
     * @param data
     */
    public static void startActivity(@NonNull Activity context, @NonNull Class clss, Map<String, String> data) {
        Intent intent = new Intent(context, clss);
        if (data != null) {
            for (Map.Entry<String, String> bean : data.entrySet()) {
                String key = bean.getKey();
                String dataStr = bean.getValue();
                if (!StrUtil.isEmpty(key)) {
                    intent.putExtra(key, dataStr);
                }
            }
        }
        context.startActivity(intent);
    }

    /**
     * 不带参数启动Activity
     * @param context
     * @param clss
     */
    public static void startActivityForResult(@NonNull Activity context, @NonNull Class clss, int requestCode) {
        Intent intent = new Intent(context, clss);
        context.startActivityForResult(intent,requestCode);
    }
    /**
     * 带参数启动Activity
     * @param context
     * @param clss
     * @param bundle
     */
    public static void startActivityForResult(@NonNull Activity context, @NonNull Class clss, Bundle bundle,int requestCode) {
        Intent intent = new Intent(context, clss);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivityForResult(intent,requestCode);
    }

    /**
     * 带参数启动Activity
     * @param context
     * @param clss
     * @param dataKey
     * @param data
     */
    public static void startActivityForResult(@NonNull Activity context, @NonNull Class clss, String dataKey, String data,int requestCode) {
        Intent intent = new Intent(context, clss);
        if (!StrUtil.isEmpty(dataKey)) {
            intent.putExtra(dataKey, data);
        }
        context.startActivityForResult(intent,requestCode);
    }

    /***
     * 带参数启动Activity
     * @param context
     * @param clss
     * @param data
     */
    public static void startActivityForResult(@NonNull Activity context, @NonNull Class clss, Map<String, String> data,int requestCode) {
        Intent intent = new Intent(context, clss);
        if (data != null) {
            for (Map.Entry<String, String> bean : data.entrySet()) {
                String key = bean.getKey();
                String dataStr = bean.getValue();
                if (!StrUtil.isEmpty(key)) {
                    intent.putExtra(key, dataStr);
                }
            }
        }
        context.startActivityForResult(intent,requestCode);
    }
}
