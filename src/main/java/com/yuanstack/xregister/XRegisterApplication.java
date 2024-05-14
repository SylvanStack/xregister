package com.yuanstack.xregister;

import com.yuanstack.xregister.config.XRegistryConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({XRegistryConfigProperties.class})
public class XRegisterApplication {

    public static void main(String[] args) {
        SpringApplication.run(XRegisterApplication.class, args);
    }

}
