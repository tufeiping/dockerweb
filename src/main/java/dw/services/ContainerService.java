package dw.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.StartContainerCmd;
import com.github.dockerjava.api.model.Container;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.Ports;
import com.github.dockerjava.api.model.Ports.Binding;

import dw.utils.Constants;

/**
 * ContainerService.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:36:40
 */

@Service
public class ContainerService {

    @Autowired
    DockerService docker;

    public List<Container> list() {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return new ArrayList<Container>();
        return cli.listContainersCmd().withShowAll(true).exec();
    }

    public InspectContainerResponse info(String id) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return null;
        return cli.inspectContainerCmd(id).exec();
    }

    public void stop(String id) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        cli.stopContainerCmd(id).exec();
    }

    public void delete(String id) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        cli.removeContainerCmd(id).exec();
    }

    public void pause(String id) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        cli.pauseContainerCmd(id).exec();
    }

    public void unPause(String id) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        cli.unpauseContainerCmd(id).exec();
    }

    public void commit(String id) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        cli.commitCmd(id).exec();
    }

    public void kill(String id) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        cli.killContainerCmd(id).exec();
    }

    public void containerStart(String id) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        cli.startContainerCmd(id);
    }

    public void restart(String id) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        cli.restartContainerCmd(id).exec();
    }

    public String log(String id) throws IOException {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(cli
                .logContainerCmd(id).withStdErr().withStdOut().withTimestamps()
                .exec(), Constants.DEFAULT_ENCODING)); // bugfix: add stream to
                                                       // cmd
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        return sb.toString();
    }

    /**
     * create a new container from a image, and run it after build completed
     * 
     * @param image
     * @param name
     * @param cmd
     * @param ports
     */
    public void create(String image, String name, String cmd, String ports) {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return;
        CreateContainerCmd ccc = cli.createContainerCmd(image);
        ccc.withName(name);
        if (cmd != null && !"".equals(cmd))
            ccc.withCmd(cmd);
        CreateContainerResponse ccr = ccc.exec();
        String id = ccr.getId();
        StartContainerCmd scc = cli.startContainerCmd(id);
        Ports portBindings = new Ports();
        String[] portPairs = ports.split(" ");
        for (String portPair : portPairs) {
            String[] localHost = portPair.split(":");
            if (localHost.length != 2)
                continue;
            ExposedPort ep = new ExposedPort("tcp",
                    Integer.valueOf(localHost[0]));
            Binding binding = new Binding(Integer.valueOf(localHost[1]));
            portBindings.bind(ep, binding);
        }
        scc.withPortBindings(portBindings);
        scc.exec();
    }
}
