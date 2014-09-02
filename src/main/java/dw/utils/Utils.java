package dw.utils;

import java.io.File;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * Utils.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:43:27
 */

public class Utils {
    public static String getMD5String(String value) {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        try {
            byte[] bytes = value.getBytes();
            MessageDigest md5dgt = MessageDigest.getInstance("MD5");
            md5dgt.update(bytes);
            byte[] md5bytes = md5dgt.digest();
            int len = md5bytes.length;
            char chars[] = new char[len * 2];
            int k = 0;
            for (int i = 0; i < len; i++) {
                byte byte0 = md5bytes[i];
                chars[k++] = hexDigits[byte0 >>> 4 & 0xf];
                chars[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(chars);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getUser(HttpServletRequest request) {
        return (String) request.getSession(true).getAttribute(
                Constants.CONTEXT_USER);
    }

    public static void setUser(HttpServletRequest request, String userName) {
        request.getSession(true).setAttribute(Constants.CONTEXT_USER, userName);
    }

    public static void removeUser(HttpServletRequest request) {
        request.getSession(true).removeAttribute(Constants.CONTEXT_USER);
    }

    public static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        return sdf.format(date);
    }

    public static File getDockerFolder(String projectName) {
        return new File(Constants.APPath() + "/uploads/" + projectName);
    }
}
