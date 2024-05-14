package com.yuanstack.xregister.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * registry config properties.
 *
 * @author Sylvan
 * @date 2024/05/14  22:12
 */
@Data
@ConfigurationProperties(prefix = "xregister")
public class XRegistryConfigProperties {
    private List<String> servers;
}
