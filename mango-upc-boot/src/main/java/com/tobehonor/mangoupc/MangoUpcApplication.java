package com.tobehonor.mangoupc;

import com.tobehonor.mangoupc.banner.MangoUpcSingleBanner;
import com.tobehonor.mangoupc.common.env.BannerModeSettingFactory;
import com.tobehonor.mangoupc.common.env.BannerModeStrategy;
import com.tobehonor.mangoupc.common.env.EnvironmentType;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import springfox.documentation.oas.annotations.EnableOpenApi;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * mango-upc 启动类
 *
 * @author William Chen
 * @since 2022/8/6
 */
@EnableOpenApi
@SpringBootApplication
@MapperScan("com.tobehonor.mangoupc.persistence.dao")
@EnableDiscoveryClient
public class MangoUpcApplication {
    
    /**
     * 启动时要加上私钥: -Djasypt.encryptor.password=94586466474
     */
    public static void main(String[] args) {
        SpringApplication springApplication = new SpringApplication(MangoUpcApplication.class);
        springApplication.setBanner(new MangoUpcSingleBanner());
        BannerModeStrategy bannerModeStrategy = BannerModeSettingFactory.getBannerModeType(getProfiles(args));
        bannerModeStrategy.setBannerMode(springApplication);
        springApplication.run(args);
    }
    
    /**
     * 从启动参数中获取指定的环境值
     * <p>需要使用这个参数: --spring.profiles.active=dev</p>
     *
     * @return 适用的环境值
     */
    private static EnvironmentType getProfiles(String[] args) {
        if (args.length == 0) {
            return EnvironmentType.DEV;
        }
        Map<String, String> cr = new HashMap<>();
        String envKey = "spring.profiles.active";
        for (String arg : args) {
            String[] split = arg.split("=");
            if (envKey.equals(split[0])) {
                cr.put(split[0], split[1].toUpperCase(Locale.ENGLISH));
            }
        }
        if (cr.isEmpty()) {
            return EnvironmentType.DEV;
        }
        return EnvironmentType.valueOf(cr.get(envKey));
    }
    
}
