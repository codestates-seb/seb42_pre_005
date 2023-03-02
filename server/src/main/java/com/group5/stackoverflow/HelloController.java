package com.group5.stackoverflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/")
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping
    public ResponseEntity<String> hello(HttpServletResponse response) {
        // 응답 헤더 설정

        // 응답 본문 설정
        String responseBody = "Hello, world!";
        logger.info("logloglog");



        // ResponseEntity 객체를 이용하여 응답 반환
        return ResponseEntity.ok(responseBody);
    }


}
