package com.group5.stackoverflow.auth.filter;

import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.utils.Checker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
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
import java.util.HashMap;
import java.util.Map;

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
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        URI uri = URI.create(request.getRequestURI());
        String method = request.getMethod();

        request.setAttribute("verified", false);
        // 특정 멤버를 타겟으로 하는 경우
        if (antPathMatcher.match("/members/\\d+", uri.getPath())
                || antPathMatcher.match("/members/\\d+/**", uri.getPath())) {
            Long memberId = Long.parseLong(uri.getPath().split("/")[2]);
            if (Checker.isVerified(jwtTokenizer, request, memberId)) {
                request.setAttribute("verified", true);
            }
        } else {
            if (Checker.isAdmin()) {
                request.setAttribute("verified", true);
            }


            filterChain.doFilter(request, response);
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURI().startsWith("/members");
    }

}