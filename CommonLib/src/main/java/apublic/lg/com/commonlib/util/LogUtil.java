package apublic.lg.com.commonlib.util;

import android.util.Log;



/**
 * Created by ligang967 on 16/9/13.
 */

public class LogUtil {
    private static boolean ENV_ISDEBUG = false;

    public static void setEnvIsdebug(boolean envIsdebug) {
        ENV_ISDEBUG = envIsdebug;
    }

    /**
     * Log verbose
     *
     * @param msg
     */
    public static void v(String msg) {
        if (ENV_ISDEBUG) {
            vLog(null,msg);
        }
    }

    /**
     * Log debug
     *
     * @param msg
     */
    public static void d(String msg) {
        if (ENV_ISDEBUG) {
            dLog(null,msg);
        }
    }

    /**
     * Log error
     *
     * @param msg
     */
    public static void e(String msg) {
        if (ENV_ISDEBUG) {
            eLog(null,msg);
        }
    }

    /**
     * Log info
     *
     * @param msg
     */
    public static void i(String msg) {
        if (ENV_ISDEBUG) {
            iLog(null,msg);
        }
    }

    /**
     * Log warn
     *
     * @param msg
     */
    public static void w(String msg) {
        if (ENV_ISDEBUG) {
            wLog(null,msg);
        }
    }
    /**
     * Log verbose
     *
     * @param tag
     * @param msg
     */
    public static void v(String tag,String msg) {
        if (ENV_ISDEBUG) {
            vLog(tag,msg);
        }
    }

    /**
     * Log debug
     *
     * @param tag
     * @param msg
     */
    public static void d(String tag,String msg) {
        if (ENV_ISDEBUG) {
            dLog(tag,msg);
        }
    }

    /**
     * Log error
     *
     * @param tag
     * @param msg
     */
    public static void e(String tag,String msg) {
        if (ENV_ISDEBUG) {
            eLog(tag,msg);
        }
    }

    /**
     * Log info
     *
     * @param tag
     * @param msg
     */
    public static void i(String tag,String msg) {
        if (ENV_ISDEBUG) {
            iLog(tag,msg);
        }
    }

    /**
     * Log warn
     *
     * @param tag
     * @param msg
     */
    public static void w(String tag,String msg) {
        if (ENV_ISDEBUG) {
            wLog(tag,msg);
        }
    }

    private static void vLog(String tag, String msg) {
        String tagL = StrUtil.isEmpty(tag) ? generateTag(Thread.currentThread().getStackTrace()[4]) : tag;
        Log.v(tagL,"******************start************************");
        Log.v(tagL, msg);
        Log.v(tagL,"******************end************************");
    }

    private static void dLog(String tag, String msg) {
        String tagL = StrUtil.isEmpty(tag) ? generateTag(Thread.currentThread().getStackTrace()[4]) : tag;
        Log.d(tagL,"******************start************************");
        Log.d(tagL, msg);
        Log.d(tagL,"******************end************************");
    }

    private static void eLog(String tag, String msg) {
        String tagL = StrUtil.isEmpty(tag) ? generateTag(Thread.currentThread().getStackTrace()[4]) : tag;
        Log.e(tagL,"******************start************************");
        Log.e(tagL, msg);
        Log.e(tagL,"******************end************************");
    }

    private static void wLog(String tag, String msg) {
        String tagL = StrUtil.isEmpty(tag) ? generateTag(Thread.currentThread().getStackTrace()[4]) : tag;
        Log.w(tagL,"******************start************************");
        Log.w(tagL, msg);
        Log.w(tagL,"******************end************************");
    }

    private static void iLog(String tag, String msg) {
        String tagL = StrUtil.isEmpty(tag) ? generateTag(Thread.currentThread().getStackTrace()[4]) : tag;
        Log.i(tagL,"******************start************************");
        Log.i(tagL, msg);
        Log.i(tagL,"******************end************************");
    }

    private static String generateTag(StackTraceElement stack) {
        String tag = "%s:%d";
        String className = stack.getFileName();
        tag = "("+String.format(tag,className, stack.getLineNumber())+")";
        return tag;
    }
}
