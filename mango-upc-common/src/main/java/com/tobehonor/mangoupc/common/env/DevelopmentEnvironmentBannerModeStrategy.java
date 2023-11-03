package com.tobehonor.mangoupc.common.env;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

/**
 * 开发环境Banner模式的选择
 *
 * @author William Chan
 * @since 2023/7/23
 */
public class DevelopmentEnvironmentBannerModeStrategy implements BannerModeStrategy {
    
    @Override
    public void setBannerMode(SpringApplication application) {
        application.setBannerMode(Banner.Mode.CONSOLE);
    }
}
