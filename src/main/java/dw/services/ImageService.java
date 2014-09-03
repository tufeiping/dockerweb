package dw.services;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Image;

import dw.api.DockerCommand;
import dw.api.SaveImageCmdImpl;

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

    public void delete(String name) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        cli.removeImageCmd(name).exec();
    }

    public void tag(String id, String tag) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        String[] tags = tag.split(":");
        if (tags.length == 2)
            cli.tagImageCmd(id, tags[0], tags[1]).exec();
        else
            cli.tagImageCmd(id, tag, null).exec();
    }

    public InputStream save(String id) {
        DockerCommand sici = new SaveImageCmdImpl(docker.getDockerPath());
        return (InputStream) sici.exec(id);
    }
}
