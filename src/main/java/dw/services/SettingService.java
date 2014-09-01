package dw.services;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.Version;

import dw.bean.HostPort;

/**
 * SettingService.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年8月30日 上午8:40:27
 */

@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SettingService {

    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    DockerService docker;

    public HostPort getHostPort() {
        List dockers = jdbc
                .queryForList("select value from sysinfo where name='docker'");
        List ports = jdbc
                .queryForList("select value from sysinfo where name='port'");
        if (dockers.isEmpty() || ports.isEmpty()) {
            return null;
        }
        String host = ((Map<String, String>) dockers.get(0)).get("value");
        String port = ((Map<String, String>) ports.get(0)).get("value");

        return new HostPort(host, port);
    }

    public void setHostPort(HostPort value) {
        jdbc.update("update sysinfo set value=? where name='docker'",
                value.getHostIp());
        jdbc.update("update sysinfo set value=? where name='port'",
                value.getHostPort());
        docker.setDockerPath(String.format("http://%s:%s", value.getHostIp(),
                value.getHostPort()));
    }

    public String getHostPortUrl() {
        HostPort hostPort = getHostPort();
        return String.format("http://%s:%s", hostPort.getHostIp(),
                hostPort.getHostPort());
    }

    public Info info() {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return null;
        return cli.infoCmd().exec();
    }

    public Version version() {
        DockerClient cli = docker.getDockerClient();
        if (cli == null)
            return null;
        return cli.versionCmd().exec();
    }
}
