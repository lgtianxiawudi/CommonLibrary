package apublic.lg.com.commonlib.http;

import android.text.TextUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import apublic.lg.com.commonlib.http.OkHttpUtils;
import apublic.lg.com.commonlib.interfaces.H5LocalDownloadAsyListener;
import apublic.lg.com.commonlib.interfaces.H5LocalDownloadSynListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;

/**
 * Created by ligang967 on 16/11/1.
 */

public class OkHttpDownUtil {
    private final static long BUFFER_SIZE = 1024 * 1024;

    /**
     * 异步下载文件
     *
     * @param url
     * @param folderPath
     * @param headers
     * @param okHttpDownLoadListener
     * @param isNeedProcess
     */
    public static void downLoadFileFromServerAsy(String url, final String folderPath, Map<String, String> headers, final H5LocalDownloadAsyListener okHttpDownLoadListener, final boolean isNeedProcess) {
        if (TextUtils.isEmpty(url)) {
            if (okHttpDownLoadListener != null) {
                okHttpDownLoadListener.onFail(new IOException("url must not be null"));
            }
            return;
        }
        if (TextUtils.isEmpty(folderPath)) {
            if (okHttpDownLoadListener != null) {
                okHttpDownLoadListener.onFail(new IOException("folderPath must not be null"));
            }
            return;
        }
        final OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        final Request.Builder request = new Request.Builder();
        request.url(url);
        if (headers != null) {
            for (Map.Entry<String, String> item : headers.entrySet()
                    ) {
                String key = item.getKey();
                String keyValue = item.getValue();
                request.addHeader(key, keyValue);
            }
        }

        if (okHttpDownLoadListener != null) {
            okHttpDownLoadListener.onStart();
        }

        okHttpClient.newCall(request.build()).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (okHttpDownLoadListener != null) {
                    okHttpDownLoadListener.onFail(e);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    boolean isSuccess = writeToFile(responseBody, isNeedProcess, folderPath, okHttpDownLoadListener);
                    if (isSuccess) {
                        if (okHttpDownLoadListener != null) {
                            okHttpDownLoadListener.onSuccess(folderPath);
                        }
                    } else {
                        if (okHttpDownLoadListener != null) {
                            okHttpDownLoadListener.onFail(new IOException("Unexpected save file fail "));
                        }
                    }
                } else {
                    if (okHttpDownLoadListener != null) {
                        okHttpDownLoadListener.onFail(new IOException("Unexpected code " + response));
                    }
                }
            }
        });
    }

    /**
     * 同步下载文件
     *
     * @param url
     * @param folderPath
     * @param headers
     * @param okHttpDownLoadListener
     * @param isNeedProcess
     */
    public static String downLoadFileFromServerSyn(String url, final String folderPath, Map<String, String> headers, final H5LocalDownloadSynListener okHttpDownLoadListener, final boolean isNeedProcess) {
        if (TextUtils.isEmpty(url)) {
            return null;
        }
        if (TextUtils.isEmpty(folderPath)) {
            return null;
        }
        final OkHttpClient okHttpClient = OkHttpUtils.getInstance();
        final Request.Builder request = new Request.Builder();
        request.url(url);
        if (headers != null) {
            for (Map.Entry<String, String> item : headers.entrySet()
                    ) {
                String key = item.getKey();
                String keyValue = item.getValue();
                request.addHeader(key, keyValue);
            }
        }


        Response response = null;
        try {
            response = okHttpClient.newCall(request.build()).execute();
        } catch (IOException e) {
            return null;
        }
        if (response != null && response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            boolean isSuccess = writeToFile(responseBody, isNeedProcess, folderPath, okHttpDownLoadListener);
            return folderPath;
        } else {
            return null;
        }
    }

    private static boolean writeToFile(ResponseBody responseBody, boolean isNeedProcess, String folderPath, H5LocalDownloadSynListener okHttpDownLoadListener) {

        long contentLength = responseBody.contentLength();
        InputStream inputSteam = responseBody.byteStream();
        if (inputSteam == null) {
            return false;
        }
        BufferedSource source = null;
        BufferedSink sink = null;
        boolean isSuccess = false;
        try {
            source = Okio.buffer(Okio.source(inputSteam));
            File file = new File(folderPath);
            if (file.getParentFile() != null && !file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            sink = Okio.buffer(Okio.sink(new File(folderPath)));
            if (isNeedProcess) {
                Buffer buffer = new Buffer();
                long data = 0;
                double count = 0;
                while ((data = source.read(buffer, BUFFER_SIZE)) != -1) {
                    sink.write(buffer, data);
                    count += data;
                    if (okHttpDownLoadListener != null) {
                        okHttpDownLoadListener.onProcess((int) (100 * count / contentLength));
                    }
                }
            } else {
                sink.writeAll(source);
            }
            sink.flush();
            isSuccess = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sink != null) {
                try {
                    sink.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (source == null) {
                try {
                    source.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputSteam != null) {
                try {
                    inputSteam.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return isSuccess;
    }
}
