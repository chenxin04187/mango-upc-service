package com.tobehonor.mangoupc.common.env;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

/**
 * 生产环境Banner mode的选择
 *
 * @author William Chan
 * @since 2023/7/23
 */
public class ProductionEnvironmentBannerModeStrategy implements BannerModeStrategy {
    
    @Override
    public void setBannerMode(SpringApplication application) {
        application.setBannerMode(Banner.Mode.LOG);
    }
}
