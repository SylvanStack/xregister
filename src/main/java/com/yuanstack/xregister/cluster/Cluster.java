package com.yuanstack.xregister.cluster;

import com.yuanstack.xregister.config.XRegistryConfigProperties;
import com.yuanstack.xregister.service.XRegistryService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.cloud.commons.util.InetUtilsProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry cluster.
 * <p>
 * 节点变更后的选主
 *
 * @author Sylvan
 * @date 2024/05/14  22:10
 */
@Slf4j
public class Cluster {

    @Value("${server.port}")
    String port;
    String host;
    Server MYSELF;
    XRegistryConfigProperties registryConfigProperties;
    @Getter
    private List<Server> servers;

    public Cluster(XRegistryConfigProperties registryConfigProperties) {
        this.registryConfigProperties = registryConfigProperties;
    }

    public void init() {
        initMyself();
        initServers();
        new ServerHealth(this).checkServerHealth();
    }

    private void initServers() {
        List<Server> servers = new ArrayList<>();
        for (String url : registryConfigProperties.getServers()) {
            Server server = new Server();
            if (url.contains("localhost")) {
                url = url.replace("localhost", host);
            } else if (url.contains("127.0.0.1")) {
                url = url.replace("127.0.0.1", host);
            }
            if (url.equals(MYSELF.getUrl())) {
                servers.add(MYSELF);
            } else {
                server.setUrl(url);
                server.setStatus(false);
                server.setLeader(false);
                server.setVersion(-1L);
                servers.add(server);
            }
        } // todo ...
        this.servers = servers;
    }

    private void initMyself() {
        try (InetUtils inetUtils = new InetUtils(new InetUtilsProperties())) {
            host = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
            log.info(" ===> find First Non Loop back Host Info = " + host);
        } catch (Exception e) {
            host = "127.0.0.1";
        }

        MYSELF = new Server("http://" + host + ":" + port, true, false, -1L);
        log.info(" ===> myself = " + MYSELF);
    }

    public Server self() {
        MYSELF.setVersion(XRegistryService.VERSION.get());
        return MYSELF;
    }

    public Server leader() {
        return this.servers.stream().filter(Server::isStatus)
                .filter(Server::isLeader).findFirst().orElse(null);
    }
}
