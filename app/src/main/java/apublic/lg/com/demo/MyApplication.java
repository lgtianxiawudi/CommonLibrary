package apublic.lg.com.demo;

import android.app.Application;

import apublic.lg.com.commonlib.demo.BuildConfig;
import apublic.lg.com.commonlib.util.LogUtil;


/**
 * Created by ligang967 on 16/9/23.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.setEnvIsdebug(BuildConfig.IS_DEBUG);
    }
}
