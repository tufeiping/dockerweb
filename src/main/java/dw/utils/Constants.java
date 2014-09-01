package dw.utils;

import java.net.URL;

/**
 * Constants.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:41:56
 */

public class Constants {
    private static URL URL = Constants.class.getResource("/");

    public static final String CONTEXT_USER = "docker_web_user";

    public static String RootPath() {
        String url = URL.toString();
        String path = url.substring(6, url.length()); // file:/
        return "/" + path;
    }

    // APP
    public static String APPath() {
        String url = URL.toString();
        String path = url.substring(6, url.length());
        return path.replace('/', '\\');
    }

    // WEB
    public static String WebPath() {
        return APPath();
    }
}
