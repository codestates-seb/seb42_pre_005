package com.group5.stackoverflow.auth.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group5.stackoverflow.dto.SingleResponseDto;
import com.group5.stackoverflow.exception.BusinessLogicException;
import com.group5.stackoverflow.exception.ExceptionCode;
import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.mapper.MemberMapper;
import com.group5.stackoverflow.member.repository.MemberRepository;
import com.group5.stackoverflow.utils.Checker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Slf4j
public class MemberAuthenticationSuccessHandler implements AuthenticationSuccessHandler {  // (1)

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    public MemberAuthenticationSuccessHandler(MemberRepository memberRepository, MemberMapper memberMapper) {
        this.memberRepository = memberRepository;
        this.memberMapper = memberMapper;
    }


    // (2)
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        // 인증 성공 후, 로그를 기록하거나 사용자 정보를 response로 전송하는 등의 추가 작업을 할 수 있다.
        log.info("# Authenticated successfully!");



        Optional<Member> optionalMember = memberRepository.findByEmail(authentication.getName());
        Member member =  optionalMember.orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));

        String memberId =String.valueOf(member.getMemberId());
        response.addHeader("memeber-id", memberId);

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.toString());

        MemberDto.Response memberResponseDto = memberMapper.memberToMemberResponse(member);
        response.getWriter().write(new ObjectMapper().writeValueAsString(new SingleResponseDto<>(memberResponseDto)));
        log.info("LOGIN ID: {}", memberId);


    }
}