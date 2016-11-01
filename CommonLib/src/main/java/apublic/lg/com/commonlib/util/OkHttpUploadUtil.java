package apublic.lg.com.commonlib.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import apublic.lg.com.commonlib.http.OkHttpUtils;
import apublic.lg.com.commonlib.interfaces.H5LocalDownloadAsyListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.Okio;
import okio.Source;

/**
 * Created by ligang967 on 16/11/1.
 */

public class OkHttpUploadUtil {
    private static final MediaType MEDIA_OBJECT_STREAM = MediaType.parse("application/octet-stream");
    /**
     *上传文件
     * @param actionUrl 接口地址
     * @param paramsMap 参数
     * @param callBack 回调
     */
    public  void upLoadFile(String actionUrl, HashMap<String, Object> paramsMap, final H5LocalDownloadAsyListener callBack) {
        try {
            //补全请求地址
            MultipartBody.Builder builder = new MultipartBody.Builder();
            //设置类型
            builder.setType(MultipartBody.FORM);
            //追加参数
            for (String key : paramsMap.keySet()) {
                Object object = paramsMap.get(key);
                if (!(object instanceof File)) {
                    builder.addFormDataPart(key, object.toString());
                } else {
                    File file = (File) object;
                    builder.addFormDataPart(key, file.getName(), createProgressRequestBody(MEDIA_OBJECT_STREAM, file, callBack));
                }
            }
            //创建RequestBody
            RequestBody body = builder.build();
            //创建Request
            final Request request = new Request.Builder().url(actionUrl).post(body).build();
            final Call call = OkHttpUtils.getInstance().newBuilder().build().newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    LogUtil.e(e.toString());
                    if (callBack!=null){
                        callBack.onFail(e);
                    }
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        String string = response.body().string();
                        LogUtil.e("response ----->" + string);
                        if (callBack!=null){
                            callBack.onSuccess(string);
                        }
                    } else {
                        if (callBack!=null){
                            callBack.onFail(new Exception(response.message()));
                        }
                    }
                }
            });
        } catch (Exception e) {
            LogUtil.e(e.toString());
        }
    }
    /**
     * 创建带进度的RequestBody
     * @param contentType MediaType
     * @param file  准备上传的文件
     * @param callBack 回调
     * @return
     */
    public  RequestBody createProgressRequestBody(final MediaType contentType, final File file, final H5LocalDownloadAsyListener callBack) {
        return new RequestBody() {
            @Override
            public MediaType contentType() {
                return contentType;
            }

            @Override
            public long contentLength() {
                return file.length();
            }

            @Override
            public void writeTo(BufferedSink sink) throws IOException {
                Source source;
                try {
                    source = Okio.source(file);
                    Buffer buf = new Buffer();
                    long remaining = contentLength();
                    long current = 0;
                    for (long readCount; (readCount = source.read(buf, 1024*1024)) != -1; ) {
                        sink.write(buf, readCount);
                        current += readCount;
                        LogUtil.e("current------>" + current);
                        if (callBack!=null){
                            callBack.onProcess((int) (current/remaining));
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
