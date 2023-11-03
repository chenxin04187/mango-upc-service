package com.tobehonor.mangoupc.banner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringBootVersion;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * 设置banner
 *
 * @author William Chan
 * @since 2023/7/23
 */
public class MangoUpcSingleBanner implements Banner {
    
    private static final String[] BANNER = {"  __  __                           _    _               _____ _       _",
            " |  \\/  |                         | |  | |             / ____(_)     | |",
            " | \\  / | __ _ _ __   __ _  ___   | |  | |_ __   ___  | (___  _  __ _| | ___",
            " | |\\/| |/ _` | '_ \\ / _` |/ _ \\  | |  | | '_ \\ / __|  \\___ \\| |/ _` | |/ _ \\",
            " | |  | | (_| | | | | (_| | (_) | | |__| | |_) | (__   ____) | | (_| | |  __/",
            " |_|  |_|\\__,_|_| |_|\\__, |\\___/   \\____/| .__/ \\___| |_____/|_|\\__, |_|\\___|",
            "                      __/ |              | |                     __/ |",
            "                     |___/               |_|                    |___/"};
    
    private static final int STRAP_LINE_SIZE = 77;
    
    private static final String BASE_ON_SPRING = " Base on Spring Boot ";
    
    @Override
    public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
        for (String line : BANNER) {
            out.println(line);
        }
        
        String springBootVersion = SpringBootVersion.getVersion();
        // 格式化Spring Boot的版本号
        springBootVersion = (springBootVersion != null) ? String.format("(v%s)", springBootVersion) : StringUtils.EMPTY;
        
        String mangoUpcSingleVersion = environment.getProperty("application.version", StringUtils.EMPTY);
        // 格式化应用程序版本号
        String applicationFormattedVersion =
                (mangoUpcSingleVersion != null) ? String.format("(v%s)", mangoUpcSingleVersion) : StringUtils.EMPTY;
        
        String applicationName = environment.getProperty("spring.application.banner", StringUtils.EMPTY);
        // 格式化应用程序名称
        applicationName = (applicationName != null) ? String.format(" :: %s :: ", applicationName) : StringUtils.EMPTY;
        
        StringBuilder padding = new StringBuilder();
        while (padding.length() < STRAP_LINE_SIZE - (applicationFormattedVersion.length() + applicationName.length())) {
            padding.append(" ");
        }
        
        out.println(AnsiOutput.toString(AnsiColor.GREEN, applicationName, AnsiColor.DEFAULT, padding.toString(),
                AnsiColor.DEFAULT, applicationFormattedVersion));
        out.println(AnsiOutput.toString(AnsiColor.DEFAULT, BASE_ON_SPRING, AnsiColor.DEFAULT, springBootVersion));
        out.println();
    }
}
