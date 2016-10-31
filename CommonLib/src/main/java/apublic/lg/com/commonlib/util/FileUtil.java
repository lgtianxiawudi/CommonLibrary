package apublic.lg.com.commonlib.util;


import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import okio.BufferedSource;
import okio.Okio;

/**
 * Created by ligang967 on 16/10/31.
 */

public class FileUtil {
    public static String inputStreamToString(InputStream inputStream,Charset charset){
//        String result = null;
//        BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));
//        try {
//            result = bufferedSource.readString(charset);
//        } catch (IOException e) {
//            LogUtil.e(e.getMessage());
//        }
//        return result;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null && !line.matches("^\\s*\\/\\/.*")) {
                    sb.append(line);
                }
            } while (line != null);

            bufferedReader.close();
            inputStream.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }
    public static String inputStreamToString(InputStream inputStream){
        return inputStreamToString(inputStream,Charset.defaultCharset());
    }
    public static InputStream getAssetsToInputSteam(Context context, String name){
        if (StrUtil.isEmpty(name)){
            return null;
        }
        try {
            return context.getAssets().open(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
