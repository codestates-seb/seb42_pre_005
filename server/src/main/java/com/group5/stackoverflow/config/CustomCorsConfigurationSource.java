package com.group5.stackoverflow.config;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class CustomCorsConfigurationSource implements CorsConfigurationSource {

    private final CorsConfigurationSource delegate;

    public CustomCorsConfigurationSource(CorsConfigurationSource delegate) {
        this.delegate = delegate;

    }

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
        log.debug("CORS 요청 발생 - {}", request.getRequestURI());
        CorsConfiguration corsConfiguration = delegate.getCorsConfiguration(request);
        if (corsConfiguration == null) {
            corsConfiguration = new CorsConfiguration();
        }
        return corsConfiguration;
    }
}

