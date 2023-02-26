package com.group5.stackoverflow.auth.filter;

import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.utils.Checker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;

@Slf4j
public class MemberUrIVerificationFilter extends OncePerRequestFilter {  // (1)
    private final JwtTokenizer jwtTokenizer;

    // (2)
    public MemberUrIVerificationFilter(JwtTokenizer jwtTokenizer) {
        this.jwtTokenizer = jwtTokenizer;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // API URL에 따라 권한 체크
        String uriString = request.getRequestURI();
        String method = request.getMethod();
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        URI uri = URI.create(uriString);

        // get members는 유저 정보에 따라 다른 정보를 준다.
        if (method.equals("GET") &&
                antPathMatcher.match("/members/*", uriString)) {
            if (Checker.isVerified(jwtTokenizer, request
                                    , Long.parseLong(uri.getPath().split("/", 3)[2]))) {
                request.setAttribute("verified", true);
            }
            else request.setAttribute("verified", false);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().startsWith("/members");
    }

}
