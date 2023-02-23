package com.group5.stackoverflow.helper;

import com.group5.stackoverflow.question.dto.QuestionDto;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.util.HashMap;
import java.util.Map;

public class MockQuestion {

    private static Map<HttpMethod, Object> stubRequestBody;
    static {
        stubRequestBody = new HashMap<>();
        stubRequestBody.put(HttpMethod.POST, new QuestionDto.Post("질문1", "내용1", 1L));
    }

    public static Object getRequestBody(HttpMethod method) {
        return stubRequestBody.get(method);
    }

}


