package com.group5.stackoverflow.utils;

import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.exception.BusinessLogicException;
import com.group5.stackoverflow.exception.ExceptionCode;
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
        if(authentication == null) return false;
        List<String> currentUserRole = authentication.getAuthorities()
                .stream().map(Object::toString)
                .collect(Collectors.toList());
        return currentUserRole.contains("ROLE_ADMIN");
    }

    public static boolean checkVerified(HttpServletRequest request){
        if(request.getAttribute("verified").equals(false)) {
            throw(new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED));}
        return true;

    }

    public static Long getMemberId(JwtTokenizer jwtTokenizer,  HttpServletRequest request){
        if(jwtTokenizer.getMemberId(request.getHeader("Authorization")) == null) return -1L;
        return jwtTokenizer.getMemberId(request.getHeader("Authorization")) ;
    }

}
