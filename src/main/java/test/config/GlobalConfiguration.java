package test.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 主配置类
 */
@Configuration
@EnableCaching // 开启缓存
@EnableAsync // 开启异步
@EnableScheduling // 开启计划任务
public class GlobalConfiguration {
}
