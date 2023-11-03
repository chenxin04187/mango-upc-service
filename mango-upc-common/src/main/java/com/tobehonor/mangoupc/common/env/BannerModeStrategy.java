package com.tobehonor.mangoupc.common.env;

import org.springframework.boot.SpringApplication;

/**
 * banner 模式的策略接口
 *
 * @author William Chan
 * @since 2023/7/24
 */
public interface BannerModeStrategy {
    
    /**
     * 设定banner mode
     *
     * @param application Spring Boot 启动类
     */
    void setBannerMode(SpringApplication application);
}
