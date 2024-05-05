package com.yuanstack.xregister.service;

import com.yuanstack.xregister.model.InstanceMeta;

import java.util.List;

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
}
