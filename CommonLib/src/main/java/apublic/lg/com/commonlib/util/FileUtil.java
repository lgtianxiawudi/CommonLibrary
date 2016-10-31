package apublic.lg.com.commonlib.util;


import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ligang967 on 16/10/31.
 */

public class FileUtil {
    public static String inputStreamToString(InputStream inputStream){
        InputStream in = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null && !line.matches("^\\s*\\/\\/.*")) {
                    sb.append(line);
                }
            } while (line != null);

            bufferedReader.close();
            in.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
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
