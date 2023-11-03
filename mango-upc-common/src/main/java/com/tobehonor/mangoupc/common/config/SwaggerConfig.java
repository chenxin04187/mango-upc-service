package com.tobehonor.mangoupc.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * 配置信息:
 * <pre>
 *     enable()，是否启动 Swagger，如果为 false，则不能通过浏览器访问 Swagger
 *     groupName()，设置分组，参数为组名，如果要分多个组，就创建多个 Docket 对象即可
 *     RequestHandlerSelectors，配置扫描接口的方式
 *         basePackage()，指定要扫描的包
 *         any()，全部扫描
 *         none()，不进行扫描
 *         withClassAnnotation()，扫描类上面的注解，需要传递一个注解类的反射对象 XXX.class
 *         withMethodAnnotation()，扫描方法上面的注解
 *     路径过滤，也就是指定扫描的路径
 *         ant()，指定路径，可以使用通配符
 *         regex()，使用正则进行过滤
 * </pre>
 *
 * @author William Chan
 * @since 2023/7/30
 */
@Configuration
public class SwaggerConfig {
    
    /**
     * 根docket，可以自定义组
     *
     * @param environment 系统环境变量
     * @return Docket
     */
    @Bean
    public Docket rootDocket(Environment environment) {
        Profiles profiles = Profiles.of("dev");
        return new Docket(DocumentationType.OAS_30).apiInfo(
                        new ApiInfoBuilder().title("Mango Upc Single").version("0.0.1").build())
                // 判断是否为自己设定的环境中
                .enable(environment.acceptsProfiles(profiles)).groupName("root").select()
                .apis(RequestHandlerSelectors.basePackage("com.tobehonor.mangoupc.web")).paths(PathSelectors.ant("/**"))
                .build();
    }
    
}
