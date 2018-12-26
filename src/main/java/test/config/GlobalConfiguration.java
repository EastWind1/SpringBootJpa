package test.config;

import org.springframework.cache.annotation.EnableCaching;

import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.annotation.EnableAsync;


@Configuration
@EnableCaching // 开启缓存
@EnableAsync // 开启异步
public class GlobalConfiguration {
}
