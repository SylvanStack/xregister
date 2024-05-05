package com.yuanstack.xregister.config;

import com.yuanstack.xregister.service.RegistryService;
import com.yuanstack.xregister.service.XRegistryService;
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
}
