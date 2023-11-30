package com.tobehonor.mangoupc.common.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Spring Doc配置
 *
 * @author William Chan
 * @since 2023-11-25
 */
@Configuration
public class SpringDocConfig {
    
    /**
     * 单个Docket配置，如有需要配置多个Docket以及分组功能，可以参考<url>https://springdoc.org/migrating-from-springfox.html</url>
     *
     * @return IOC容器管理的对象
     */
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI().info(
                        new Info().title("Mango用户中心项目(Mango Upc)").description("Mango用户中心项目(Mango Upc)")
                                .version("v0.0.1").license(new License().name("Apache 2.0").url("http://www.tobehonor.com")))
                .externalDocs(new ExternalDocumentation().description("Mango Upc Server 接口文档")
                        .url("http://www.tobehonor.com"));
    }
}
