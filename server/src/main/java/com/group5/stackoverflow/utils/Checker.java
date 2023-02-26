package com.group5.stackoverflow.utils;

import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

public class Checker {
    public static boolean isVerified(JwtTokenizer jwtTokenizer,  HttpServletRequest request, Long memberId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> currentUserRole = authentication.getAuthorities()
                                        .stream().map(Object::toString)
                                        .collect(Collectors.toList());
        if(currentUserRole.contains("ROLE_ADMIN")) return true;
        return jwtTokenizer.getMemberId(request.getHeader("Authorization")) == memberId;
    }

    public static boolean isAdmin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        List<String> currentUserRole = authentication.getAuthorities()
                .stream().map(Object::toString)
                .collect(Collectors.toList());
        return currentUserRole.contains("ROLE_ADMIN");
    }

}
