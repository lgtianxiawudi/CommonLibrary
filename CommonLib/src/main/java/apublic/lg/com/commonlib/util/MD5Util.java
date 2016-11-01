package apublic.lg.com.commonlib.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


/**
 * Created by ligang967 on 16/11/1.
 */
public class MD5Util {
    private static final String TAG = MD5Util.class.getSimpleName();
    private static final int STREAM_BUFFER_LENGTH = 1024;

    /**
     * Md 5 byte [ ].
     *
     * @param txt the txt
     * @return the byte [ ]
     */
    public static byte[] md5(String txt) {
        return md5(txt.getBytes());
    }

    /**
     * Gets digest.
     *
     * @param algorithm the algorithm
     * @return the digest
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    private static MessageDigest getDigest(final String algorithm) throws NoSuchAlgorithmException {
        return MessageDigest.getInstance(algorithm);
    }


    public static byte[] sha1(byte[] bytes) {
        try {
            MessageDigest digest = getDigest("SHA1");
            digest.update(bytes);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e(Arrays.toString(e.getStackTrace()) + e.getMessage() + "");
        }
        return null;
    }

    /**
     * Md 5 byte [ ].
     *
     * @param bytes the bytes
     * @return the byte [ ]
     */
    private static byte[] md5(byte[] bytes) {
        try {
            MessageDigest digest = getDigest("MD5");
            digest.update(bytes);
            return digest.digest();
        } catch (NoSuchAlgorithmException e) {
            LogUtil.e(Arrays.toString(e.getStackTrace()) + e.getMessage() + "");
        }
        return null;
    }

    /**
     * Md 5 byte [ ].
     *
     * @param is the is
     * @return the byte [ ]
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws IOException              the io exception
     */
    public static byte[] md5(InputStream is) throws NoSuchAlgorithmException, IOException {
        return updateDigest(getDigest("MD5"), is).digest();
    }

    /**
     * Update digest message digest.
     *
     * @param digest the digest
     * @param data   the data
     * @return the message digest
     * @throws IOException the io exception
     */
    private static MessageDigest updateDigest(final MessageDigest digest, final InputStream data)
            throws IOException {
        final byte[] buffer = new byte[STREAM_BUFFER_LENGTH];
        int read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);

        while (read > -1) {
            digest.update(buffer, 0, read);
            read = data.read(buffer, 0, STREAM_BUFFER_LENGTH);
        }

        return digest;
    }

    /**
     * Md 5 hex 8 string.
     *
     * @param is the is
     * @return the string
     * @throws NoSuchAlgorithmException the no such algorithm exception
     * @throws IOException              the io exception
     */
    public static String md5Hex8(InputStream is) throws NoSuchAlgorithmException, IOException {
        return byteArrayToHex(md5(is));
    }

    private static String byteArrayToHex(byte[] byteArray) {
        char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };

        char[] resultCharArray = new char[byteArray.length / 2];

        int index = 0;
        for (int i = 0; i < byteArray.length; i += 4) {
            byte b = byteArray[i];
            resultCharArray[(index++)] = hexDigits[(b >>> 4 & 0xF)];
            resultCharArray[(index++)] = hexDigits[(b & 0xF)];
        }

        return new String(resultCharArray);
    }

    /**
     * Md 5 str string.
     *
     * @param txt the txt
     * @return the string
     */
    public static String md5Str(String txt) {
        byte[] buf = md5(txt);
        if (buf == null) {
            return "";
        }
        StringBuilder hex = new StringBuilder(buf.length * 2);
        for (byte b : buf) {
            if ((b & 0xFF) < 0x10) {
                hex.append("0");
            }
            hex.append(Integer.toHexString(b & 0xFF));
        }
        return hex.toString();
    }

    public static String md5Str2(String txt) {
        byte[] buf = md5(txt);
        if (buf == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (int offset = 0; offset < buf.length; offset++) {
            i = buf[offset];
            if (i < 0) {
                i += 256;
            }
            if (i < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(i));
        }
        return sb.toString();
    }


    public static String getSignatrueSha1(Context context) {
        String sha1 = null;
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(),
                            PackageManager.GET_SIGNATURES);
            Signature[] signs = packageInfo.signatures;
            Signature sign = signs[0];
            byte[] digest = MD5Util.sha1(sign.toByteArray());
            sha1 = toHexString(digest);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtil.e(e.getMessage());
        }
        return sha1;
    }


    private static void byte2hex(byte b, StringBuffer buf) {
        char[] hexChars = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        int high = ((b & 0xf0) >> 4);
        int low = (b & 0x0f);
        buf.append(hexChars[high]);
        buf.append(hexChars[low]);
    }

    private static String toHexString(byte[] block) {
        StringBuffer buf = new StringBuffer();
        int len = block.length;
        for (int i = 0; i < len; i++) {
            byte2hex(block[i], buf);
            if (i < len - 1) {
                buf.append(":");
            }
        }
        return buf.toString();
    }
}
