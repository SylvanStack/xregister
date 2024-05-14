package com.yuanstack.xregister.config;

import com.yuanstack.xregister.cluster.Cluster;
import com.yuanstack.xregister.health.HealthChecker;
import com.yuanstack.xregister.health.XHealthChecker;
import com.yuanstack.xregister.service.RegistryService;
import com.yuanstack.xregister.service.XRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * configuration for all beans
 *
 * @author Sylvan
 * @date 2024/05/05  21:35
 */
@Configuration
public class XRegistryConfig {

    @Bean
    public RegistryService registryService() {
        return new XRegistryService();
    }

    @Bean(initMethod = "start", destroyMethod = "stop")
    public HealthChecker healthChecker(@Autowired RegistryService registryService) {
        return new XHealthChecker(registryService);
    }

    @Bean(initMethod = "init")
    public Cluster cluster(@Autowired XRegistryConfigProperties registryConfigProperties) {
        return new Cluster(registryConfigProperties);
    }

}
