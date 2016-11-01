package apublic.lg.com.commonlib.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 作者：ex-caowanjiang001 on 2016/8/8 14:06
 * 邮箱：ex-caowanjiang001@pingan.com.cn
 * 版本：1.0.0
 */
public class OkHttpInterceptor implements Interceptor {

    private static final String TAG = OkHttpInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        request = addHeads(request.newBuilder(), getHeadWithToken()).build();
//        long t1 = System.nanoTime();
        Response response = chain.proceed(request);

        return response;
    }
}
