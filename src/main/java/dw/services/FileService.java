package dw.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.springframework.stereotype.Service;

/**
 * FileService.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年9月2日 上午11:05:45
 */

@Service
public class FileService {
    private boolean folderSafeCheck(File folder) {
        if (!folder.exists()) {
            folder.mkdirs();
        }
        return folder.exists();
    }

    public void saveDockerFile(File folder, String content)
            throws UnsupportedEncodingException, IOException {
        if (!folderSafeCheck(folder))
            return;
        FileOutputStream dockerFile = new FileOutputStream(
                folder.getAbsolutePath() + "/Dockerfile");
        dockerFile.write(content.getBytes("UTF-8"));
        dockerFile.close();
    }

}
