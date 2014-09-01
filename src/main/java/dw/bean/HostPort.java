package dw.bean;

public class HostPort {
    private String HostPort;
    private String HostIp = "0.0.0.0";

    public HostPort(String hostPort) {
        this.HostPort = hostPort;
    }

    public HostPort(String hostIp, String hostPort) {
        this.HostPort = hostPort;
        this.HostIp = hostIp;
    }

    public String getHostPort() {
        return HostPort;
    }

    public String getHostIp() {
        return HostIp;
    }
}
