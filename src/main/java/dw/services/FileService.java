package dw.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
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
    private boolean folderCheck(File folder) {
        return folder.exists();
    }

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

    public void saveFile(File folder, String name, InputStream input)
            throws IOException {
        if (!folderSafeCheck(folder))
            return;
        FileOutputStream fos = new FileOutputStream(folder.getAbsolutePath()
                + "/" + name);
        IOUtils.copyLarge(input, fos);
        fos.close();
    }

    public List<String> getFiles(File folder) {
        List<String> result = new ArrayList<String>();
        if (!folderCheck(folder))
            return result;
        File[] files = folder.listFiles();
        for (File file : files) {
            result.add(file.getName());
        }
        return result;
    }
}
