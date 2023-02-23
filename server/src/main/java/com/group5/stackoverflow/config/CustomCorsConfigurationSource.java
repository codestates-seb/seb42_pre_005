package com.group5.stackoverflow.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class CustomCorsConfigurationSource implements CorsConfigurationSource {

    private final CorsConfigurationSource source;

    public CustomCorsConfigurationSource(CorsConfigurationSource source) {
        this.source = source;
    }

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        log.info("CORS 요청 발생 - {}", request.getRequestURI());
        CorsConfiguration corsConfiguration = source.getCorsConfiguration(request);
        log.info("CORS 요청 발생 - {}", corsConfiguration.getAllowedOrigins());
        log.info("CORS 요청 발생 - {}", corsConfiguration.getAllowedHeaders());
        return corsConfiguration;
    }
}

