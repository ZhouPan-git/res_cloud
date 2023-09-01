package com.yc.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.util.pattern.PathPatternParser;

/**
 * @Author zp
 * @Date 2023/8/30 15:19
 * @PackageName:com.yc.configs
 * @ClassName: CorsConfig
 * @Description:
 * @Version 1.0
 */
@Configuration
public class CorsConfig {
    @Bean
    public CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod("*");
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");

        CorsConfigurationSource source = new UrlBasedCorsConfigurationSource(new PathPatternParser());
        ((UrlBasedCorsConfigurationSource) source).registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }
}