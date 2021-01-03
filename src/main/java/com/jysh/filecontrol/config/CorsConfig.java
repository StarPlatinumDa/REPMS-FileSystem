package com.jysh.filecontrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author flamemaster
 * @date 2021/1/2
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsFilter corsFilter() {
        // 跨域配置
        CorsConfiguration corsConfig = new CorsConfiguration();
        // 原始域放行
        corsConfig.addAllowedOrigin("*");
        // 放行哪些原始域(请求方式)
        corsConfig.addAllowedMethod("*");
        // 放行哪些原始域(头部信息)
        corsConfig.addAllowedHeader("*");
        // 暴露哪些头部信息
        UrlBasedCorsConfigurationSource configurationSource
                = new UrlBasedCorsConfigurationSource();
        configurationSource.registerCorsConfiguration("/**", corsConfig);
        return new CorsFilter(configurationSource);
    }
}
