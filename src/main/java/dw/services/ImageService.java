package dw.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;

/**
 * ImageService.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:39:50
 */

@Service
public class ImageService {

    @Autowired
    DockerService docker;

    public List<Image> list() {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return new ArrayList<Image>();
        return cli.listImagesCmd().exec();
    }

    public InspectImageResponse info(String name) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return null;
        return cli.inspectImageCmd(name).exec();
    }

    public void imageDelete(String name) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        cli.removeImageCmd(name).exec();
    }

    public String imageBuild(byte[] content) {
        // return http.postBuild(Constants.getDockerPath() + "/build", content);
        return null;
    }

    public void imageTag(String id, String tag) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        String[] tags = tag.split(":");
        if (tags.length == 2)
            cli.tagImageCmd(id, tags[0], tags[1]).exec();
        else
            cli.tagImageCmd(id, null, tag).exec();
    }
}
