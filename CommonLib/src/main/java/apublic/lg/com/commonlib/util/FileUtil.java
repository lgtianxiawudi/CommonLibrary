package apublic.lg.com.commonlib.util;


import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
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


    public static long fileToLong(File file) {
        long result = -1;
        BufferedSource bufferedSource = null;
        try {
            bufferedSource = Okio.buffer(Okio.source(file));
        } catch (FileNotFoundException e) {
            LogUtil.e(e.getMessage());
        }
        try {
            result = bufferedSource.readLong();
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
        }
        return result;
    }

    public static int fileToInt(File file) {
        int result = -1;
        BufferedSource bufferedSource = null;
        try {
            bufferedSource = Okio.buffer(Okio.source(file));
        } catch (FileNotFoundException e) {
            LogUtil.e(e.getMessage());
        }
        try {
            result = bufferedSource.readInt();
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
        }
        return result;
    }

    public static String fileToString(File file, Charset charset) {
        String result = null;
        BufferedSource bufferedSource = null;
        try {
            bufferedSource = Okio.buffer(Okio.source(file));
        } catch (FileNotFoundException e) {
            LogUtil.e(e.getMessage());
        }
        try {
            result = bufferedSource.readString(charset);
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
        }
        return result;
    }

    public static String fileToString(File file) {
        return fileToString(file, Charset.defaultCharset());
    }


    public static long inputSteamToLong(InputStream inputStream) {
        long result = -1;
        BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));
        try {
            result = bufferedSource.readLong();
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
        }
        return result;
    }

    public static int inputSteamToInt(InputStream inputStream) {
        int result = -1;
        BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));
        try {
            result = bufferedSource.readInt();
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
        }
        return result;
    }

    public static String inputStreamToString(InputStream inputStream, Charset charset) {
        String result = null;
        BufferedSource bufferedSource = Okio.buffer(Okio.source(inputStream));
        try {
            result = bufferedSource.readString(charset);
        } catch (IOException e) {
            LogUtil.e(e.getMessage());
        }
        return result;
    }

    public static String inputStreamToString(InputStream inputStream) {
        return inputStreamToString(inputStream, Charset.defaultCharset());
    }

    public static InputStream getAssetsToInputSteam(Context context, String name) {
        if (StrUtil.isEmpty(name)) {
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
