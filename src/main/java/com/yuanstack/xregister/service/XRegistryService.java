package com.yuanstack.xregister.service;

import com.yuanstack.xregister.model.InstanceMeta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * default implementation of RegistryService
 *
 * @author Sylvan
 * @date 2024/05/05  21:18
 */
@Slf4j
public class XRegistryService implements RegistryService {

    private final static MultiValueMap<String, InstanceMeta> REGISTRY = new LinkedMultiValueMap<>();

    @Override
    public InstanceMeta register(String service, InstanceMeta instance) {
        List<InstanceMeta> instanceMetas = REGISTRY.get(service);
        if (instanceMetas != null && !instanceMetas.isEmpty()) {
            if (instanceMetas.contains(instance)) {
                log.info("instance {} already registered ", instance.toUrl());
                instance.setStatus(true);
                return instance;
            }

        }

        log.info("register instance {} ", instance.toUrl());
        REGISTRY.add(service, instance);
        instance.setStatus(true);
        return instance;
    }

    @Override
    public InstanceMeta unregister(String service, InstanceMeta instance) {
        List<InstanceMeta> instanceMetas = REGISTRY.get(service);
        if (instanceMetas == null || instanceMetas.isEmpty()) {
            return null;
        }
        log.info("unregister instance {}", instance.toUrl());
        instanceMetas.removeIf(m -> m.equals(instance));
        instance.setStatus(false);
        return instance;
    }

    @Override
    public List<InstanceMeta> getAllInstances(String service) {
        return REGISTRY.get(service);
    }
}
