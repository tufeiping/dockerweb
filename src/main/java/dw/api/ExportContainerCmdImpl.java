package dw.api;

import java.io.InputStream;

import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import com.github.dockerjava.api.command.LogContainerCmd;
import com.github.dockerjava.core.DockerClientConfig;

/**
 * ExportContainerCmdImpl.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年9月3日 下午2:35:13
 */

public class ExportContainerCmdImpl extends DockerCommand {

    public ExportContainerCmdImpl(DockerClientConfig dockerClientConfig) {
        super(dockerClientConfig);
    }

    public ExportContainerCmdImpl(String url) {
        super(url);
    }

    @Override
    public Object exec(Object... commands) {
        if (commands.length < 1)
            return null;
        String id = (String) commands[0];
        WebTarget webResource = getResource().path("/containers/{id}/export")
                .resolveTemplate("id", id);

        return webResource.request().get(Response.class)
                .readEntity(InputStream.class);
    }
}
