package com.tobehonor.mangoupc.common.env;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;

/**
 * 测试环境Banner mode的选择
 */
public class TestingEnvironmentBannerModeStrategy implements BannerModeStrategy {
    
    @Override
    public void setBannerMode(SpringApplication application) {
        application.setBannerMode(Banner.Mode.LOG);
    }
}
