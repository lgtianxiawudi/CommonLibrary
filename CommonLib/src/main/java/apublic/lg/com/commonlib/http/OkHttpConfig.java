package apublic.lg.com.commonlib.http;

import java.io.File;

/**
 * Created by ex-caowanjiang001 on 16/2/23.
 */
public class OkHttpConfig {

    public static int RESPONSE_CACHE_SIZE = 10 * 1024 * 1024;

    public static String RESPONSE_CACHE = "cache_name";

    public static int HTTP_CONNECT_TIMEOUT = 20 * 1000;

    public static int HTTP_READ_TIMEOUT = 20 * 1000;

    public static File CACHAE_DIR = null;

    public static final String LOGON_SUCCESS_BROADCAST = "LOGON_SUCCESS_BROADCAST";
}
