package com.yuanstack.xregister.service;

import com.yuanstack.xregister.model.InstanceMeta;

import java.util.List;
import java.util.Map;

/**
 * interface for Registry Service
 *
 * @author Sylvan
 * @date 2024/05/05  21:13
 */
public interface RegistryService {

    // provider侧
    InstanceMeta register(String service, InstanceMeta instance);

    InstanceMeta unregister(String service, InstanceMeta instance);

    // consumer侧
    List<InstanceMeta> getAllInstances(String service);

    long renew(InstanceMeta instance, String... service);

    Long version(String service);

    Map<String, Long> versions(String... services);
}
