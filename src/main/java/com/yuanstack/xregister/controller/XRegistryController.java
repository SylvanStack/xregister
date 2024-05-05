package com.yuanstack.xregister.controller;

import com.yuanstack.xregister.model.InstanceMeta;
import com.yuanstack.xregister.service.RegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * rest controller for xregister service
 *
 * @author Sylvan
 * @date 2024/05/05  21:35
 */
@RestController
public class XRegistryController {

    @Autowired
    RegistryService registryService;

    @RequestMapping("/register")
    public InstanceMeta register(@RequestParam String service, @RequestBody InstanceMeta instance) {
        return registryService.register(service, instance);
    }

    @RequestMapping("/unregister")
    public InstanceMeta unregister(@RequestParam String service, @RequestBody InstanceMeta instance) {
        return registryService.unregister(service, instance);
    }

    @RequestMapping("/findAll")
    public List<InstanceMeta> getAllInstances(String service) {
        return registryService.getAllInstances(service);
    }
}
