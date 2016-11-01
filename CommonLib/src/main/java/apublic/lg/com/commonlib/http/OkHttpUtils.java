package apublic.lg.com.commonlib.http;

import android.os.Environment;

import java.io.File;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;


/**
 * Created by ex-caowanjiang001 on 16/2/23.
 */
public class OkHttpUtils {

    private static OkHttpClient singleton;

    public synchronized static OkHttpClient getInstance() {
        if (singleton == null) {
            init();
        }
        return singleton;
    }

    public static void init() {
        synchronized (OkHttpUtils.class) {
            if (singleton == null) {
                OkHttpClient.Builder okHttpClient = new OkHttpClient().newBuilder();
                String dir = Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/okhttp/";
                File cacheDir = new File(dir, OkHttpConfig.RESPONSE_CACHE);
                okHttpClient.cache(new Cache(cacheDir, OkHttpConfig.RESPONSE_CACHE_SIZE));
                okHttpClient
                        .connectTimeout(OkHttpConfig.HTTP_CONNECT_TIMEOUT, TimeUnit.MILLISECONDS);
                okHttpClient.readTimeout(OkHttpConfig.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS);
                okHttpClient.retryOnConnectionFailure(true);
                okHttpClient.networkInterceptors().add((new OkHttpInterceptor()));
                singleton = okHttpClient.build();
            }
        }
    }
}
