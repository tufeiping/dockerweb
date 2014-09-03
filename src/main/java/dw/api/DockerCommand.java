package dw.api;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.core.JsonClientFilter;

/**
 * DockerCommand.java <br>
 * comment
 * 
 * @author Sunny (tufeiping@gmail.com)
 * @version 2014年9月3日 下午2:17:41
 */

public abstract class DockerCommand {
    private WebTarget baseResource;
    private Client client;

    public DockerCommand(String url) {
        this(DockerClientConfig.createDefaultConfigBuilder().withUri(url)
                .build());
    }

    public DockerCommand(DockerClientConfig dockerClientConfig) {
        ClientConfig clientConfig = new ClientConfig();
        clientConfig.register(JsonClientFilter.class);
        clientConfig.register(JacksonJsonProvider.class);
        if (dockerClientConfig.getReadTimeout() != null) {
            int readTimeout = dockerClientConfig.getReadTimeout();
            clientConfig.property(ClientProperties.READ_TIMEOUT, readTimeout);
        }
        client = ClientBuilder.newClient(clientConfig);
        WebTarget webResource = client.target(dockerClientConfig.getUri());
        if (dockerClientConfig.getVersion() != null) {
            baseResource = webResource.path("v"
                    + dockerClientConfig.getVersion());
        } else {
            baseResource = webResource;
        }
    }

    protected Client getClient() {
        return client;
    }

    protected WebTarget getResource() {
        return baseResource;
    }

    public abstract Object exec(Object... commands);
}
