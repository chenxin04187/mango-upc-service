package com.tobehonor.mangoupc.common.env;

/**
 * 环境配置枚举值，和Environment类的设定是一样的，
 * 在设置spring.profiles.active值时，最好是这几个，否则需要转换成大写后再进行匹配
 *
 * @author William Chan
 * @since 2023/7/23
 */
public enum EnvironmentType {
    DEV, TEST, PROD
}
