package com.yuanstack.xregister.controller;

import com.yuanstack.xregister.cluster.Cluster;
import com.yuanstack.xregister.cluster.Server;
import com.yuanstack.xregister.cluster.Snapshot;
import com.yuanstack.xregister.model.InstanceMeta;
import com.yuanstack.xregister.service.XRegistryService;
import com.yuanstack.xregister.service.RegistryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * rest controller for xregister service
 *
 * @author Sylvan
 * @date 2024/05/05  21:35
 */
@RestController
@Slf4j
public class XRegistryController {

    @Autowired
    RegistryService registryService;
    @Autowired
    Cluster cluster;

    @RequestMapping("/register")
    public InstanceMeta register(@RequestParam String service, @RequestBody InstanceMeta instance) {
        log.info("register {} @ {}", service, instance);
        checkLeader();
        return registryService.register(service, instance);
    }

    @RequestMapping("/unregister")
    public InstanceMeta unregister(@RequestParam String service, @RequestBody InstanceMeta instance) {
        log.info("unregister {} @ {}", service, instance);
        checkLeader();
        return registryService.unregister(service, instance);
    }

    @RequestMapping("/findAll")
    public List<InstanceMeta> getAllInstances(String service) {
        log.info("findAllInstances {}", service);
        return registryService.getAllInstances(service);
    }

    @RequestMapping("/renew")
    public long renew(@RequestParam String service, @RequestBody InstanceMeta instance) {
        log.info("renew {} @ {}", service, instance);
        return registryService.renew(instance, service);
    }

    @RequestMapping("/renews")
    public long renews(@RequestParam String services, @RequestBody InstanceMeta instance) {
        log.info("renew {} @ {}", services, instance);
        checkLeader();
        return registryService.renew(instance, services.split(","));
    }

    @RequestMapping("/version")
    public long version(@RequestParam String service) {
        log.info("version is {}", service);
        return registryService.version(service);
    }

    @RequestMapping("/versions")
    public Map<String, Long> versions(@RequestParam String services) {
        log.info("versions is {}", services);
        return registryService.versions(services.split(","));
    }

    @RequestMapping("/info")
    public Server info() {
        log.info(" ===> info: {}", cluster.self());
        return cluster.self();
    }

    @RequestMapping("/cluster")
    public List<Server> cluster() {
        log.info(" ===> info: {}", cluster.getServers());
        return cluster.getServers();
    }

    @RequestMapping("/leader")
    public Server leader() {
        log.info(" ===> leader: {}", cluster.leader());
        return cluster.leader();
    }

    @RequestMapping("/sl")
    public Server sl() {
        cluster.self().setLeader(true);
        log.info(" ===> leader: {}", cluster.self());
        return cluster.self();
    }

    @RequestMapping("/snapshot")
    public Snapshot snapshot() {
        return XRegistryService.snapshot();
    }

    private void checkLeader() {
        if(!cluster.self().isLeader()) {
            throw new RuntimeException("current server is not a leader, the leader is " + cluster.leader().getUrl());
        }
    }
}
