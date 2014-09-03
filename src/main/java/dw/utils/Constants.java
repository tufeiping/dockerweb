package dw.utils;

import java.io.File;
import java.net.URL;

/**
 * Constants.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:41:56
 */

public class Constants {
    private static URL URL = Constants.class.getResource("/"); // WEB-INF/classes

    public static final String CONTEXT_USER = "docker_web_user";

    public static final String DEFAULT_ENCODING = "utf-8";

    public static String RootPath() {
        String url = URL.toString();
        String path = url.substring(6, url.length());
        return "/" + path;
    }

    // APP
    public static String ClassPath() {
        String url = URL.toString();
        String path = url.substring(6, url.length());
        if (File.separator.equals("\\"))
            return path.replace('/', '\\').replaceAll("%20", " "); // bugfix for
                                                                   // windows
                                                                   // path.
        else
            return path;
    }

    // WEB
    public static String WebPath() {
        return ClassPath() + "/../..";
    }

    public static String UploadPath() {
        return ClassPath() + "/../../upload";
    }
}
