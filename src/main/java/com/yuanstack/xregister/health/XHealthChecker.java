package com.yuanstack.xregister.health;

import com.yuanstack.xregister.model.InstanceMeta;
import com.yuanstack.xregister.service.RegistryService;
import com.yuanstack.xregister.service.XRegistryService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Default implementation of HealthChecker.
 *
 * @author Sylvan
 * @date 2024/05/05  22:09
 */
@Slf4j
public class XHealthChecker implements HealthChecker {
    RegistryService registryService;
    long timeout = 20_000;
    final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);

    public XHealthChecker(RegistryService registryService) {
        this.registryService = registryService;
    }

    @Override
    public void start() {
        executor.scheduleWithFixedDelay(
                () -> {
                    log.info("Health checker running...");
                    long now = System.currentTimeMillis();
                    XRegistryService.TIMESTAMPS.keySet().forEach(serviceAndInst -> {
                        long timestamp = XRegistryService.TIMESTAMPS.get(serviceAndInst);
                        if (now - timestamp > timeout) {
                            log.info("Health checker: {} is down", serviceAndInst);
                            int index = serviceAndInst.indexOf("@");
                            String service = serviceAndInst.substring(0, index);
                            String url = serviceAndInst.substring(index + 1);
                            InstanceMeta instance = InstanceMeta.from(url);
                            registryService.unregister(service, instance);
                            XRegistryService.TIMESTAMPS.remove(serviceAndInst);
                        }
                    });

                },
                10, 10, TimeUnit.SECONDS);
    }

    @Override
    public void stop() {
        executor.shutdown();
    }
}
