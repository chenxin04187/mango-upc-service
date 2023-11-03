package com.tobehonor.mangoupc.common.env;

import java.util.HashMap;
import java.util.Map;

/**
 * Banner Mode的选择工厂
 *
 * @author William Chan
 * @since 2023/7/23
 */
public class BannerModeSettingFactory {
    
    private static final Map<EnvironmentType, BannerModeStrategy> bannerModeStrategyMap = new HashMap<>();
    
    private BannerModeSettingFactory() {}
    
    static {
        bannerModeStrategyMap.put(EnvironmentType.DEV, new DevelopmentEnvironmentBannerModeStrategy());
        bannerModeStrategyMap.put(EnvironmentType.TEST, new TestingEnvironmentBannerModeStrategy());
        bannerModeStrategyMap.put(EnvironmentType.PROD, new ProductionEnvironmentBannerModeStrategy());
    }
    
    public static BannerModeStrategy getBannerModeType(EnvironmentType type) {
        if (type == null) {
            throw new IllegalArgumentException(
                    "环境类型为空，请检查传入的环境参数");
        }
        if (!bannerModeStrategyMap.containsKey(type)) {
            throw new IllegalArgumentException(
                    "环境类型不正确，请参考com.tobehonor.mangoupc.common.env.EnvironmentType中的值");
        }
        
        return bannerModeStrategyMap.get(type);
    }
}
