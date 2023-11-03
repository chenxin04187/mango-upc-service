package com.tobehonor.mangoupc.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * druid配置
 *
 * @author William Chen
 * @since 2022/8/6
 */
@Configuration
public class DruidConfig {
    
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource druid() {
        return new DruidDataSource();
    }
    
    /**
     * 配置Druid监控
     *
     * @return servlet注册bean
     */
    @Bean
    public ServletRegistrationBean statViewServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(),
                "/druid/*");
        
        Map<String, String> initParams = new HashMap<>(16);
        initParams.put("loginUsername", "admin");
        initParams.put("loginPassword", "admin");
        //默认允许所有访问
        initParams.put("allow", "0.0.0.0");
        
        servletRegistrationBean.setInitParameters(initParams);
        return servletRegistrationBean;
    }
    
    /**
     * 配置一个web监控的filter
     *
     * @return 过滤器注册bean
     */
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        
        Map<String, String> initParams = new HashMap<>(16);
        initParams.put("exclusions", "*.js,*.css,/druid/*");
        
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.setUrlPatterns(Arrays.asList("/*"));
        return filterRegistrationBean;
    }
}
