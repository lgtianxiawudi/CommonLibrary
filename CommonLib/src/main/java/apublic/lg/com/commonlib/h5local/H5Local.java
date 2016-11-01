package apublic.lg.com.commonlib.h5local;

import android.os.Environment;
import android.text.TextUtils;
import android.webkit.WebResourceResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import apublic.lg.com.commonlib.util.MD5Util;

/**
 * Created by ligang967 on 16/11/1.
 */

public class H5Local {
    public static WebResourceResponse getResourceResponse(String url){

        if (TextUtils.isEmpty(url)){
            return null;
        }
        if (url.contains("?")){
            return null;
        }

        WebResourceResponse result = null;

        String localPath = getLocalPath(url);

        if (!new File(localPath).exists()){
//            localPath = HCH5LocalHttpDownloadUtil.downLoadFileFromServerAny(url, localPath, null, null,false);
        }
        if (TextUtils.isEmpty(localPath)){
            return null;
        }

        try {
            result = new WebResourceResponse(null,"UTF-8",new FileInputStream(localPath));
            return result;
        } catch (FileNotFoundException e) {
            return null;
        }
    }

    public static String getLocalPath(String url){
        int lastIndexOf = url.lastIndexOf("/");
        String fileName = "";
        if (lastIndexOf!=-1){
            fileName = url.substring(lastIndexOf+1);
        }
        if (TextUtils.isEmpty(fileName)){
            return null;
        }
        String result = Environment.getExternalStorageDirectory()+File.separator+"temp"+File.separator+ MD5Util.md5Str(url)+"_"+fileName;


        return result;
    }
}
