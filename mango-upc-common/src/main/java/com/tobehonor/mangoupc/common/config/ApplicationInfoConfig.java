package com.tobehonor.mangoupc.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties
public class ApplicationInfoConfig {

    private String applicationTitle = "test";
    private String applicationVersion = "cd";
}
