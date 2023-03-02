package com.group5.stackoverflow.auth.filter;

import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.utils.Checker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
public class LogFilter extends OncePerRequestFilter {

//    private final JwtTokenizer jwtTokenizer;
//
//    public LogFilter(JwtTokenizer jwtTokenizer) {
//        this.jwtTokenizer = jwtTokenizer;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("# New Request!");
        log.info("Request adr: {}", request.getRemoteAddr());
        log.info("Request Origin header: {}", request.getHeader("Origin"));
        log.info("Request Method: {}", request.getMethod());
        log.info("Request URI: {}", request.getRequestURI());

        filterChain.doFilter(request, response);

    }
}
