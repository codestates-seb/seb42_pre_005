package com.group5.stackoverflow.member.controller;

import com.group5.stackoverflow.auth.tokenizer.JwtTokenizer;
import com.group5.stackoverflow.dto.MultiResponseDto;
import com.group5.stackoverflow.dto.SingleResponseDto;
import com.group5.stackoverflow.exception.BusinessLogicException;
import com.group5.stackoverflow.exception.ExceptionCode;
import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.mapper.MemberMapper;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.utils.Checker;
import com.group5.stackoverflow.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/members")
@Validated
@Slf4j
public class MemberController {
    private final MemberService memberService;
    private final MemberMapper mapper;
    private final JwtTokenizer jwtTokenizer;

    public MemberController(MemberService memberService, MemberMapper mapper,
                            JwtTokenizer jwtTokenizer) {
        this.memberService = memberService;
        this.mapper = mapper;
        this.jwtTokenizer = jwtTokenizer;
    }

    private final static String MEMBER_DEFAULT_URL = "/members";


    // post
    @PostMapping()
    public ResponseEntity postMember(@Valid  @RequestBody MemberDto.Post memberDtoPost){
        Member member =  mapper.memberPostToMember(memberDtoPost);



        Member createdMember =  memberService.createMember(member);
        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, createdMember.getMemberId());

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(
            @PathVariable("member-id") @Positive long memberId,
            @Valid @RequestBody MemberDto.Patch requestBody,
                                HttpServletRequest request) throws IllegalAccessException {

        if(!Checker.isVerified(jwtTokenizer, request, memberId)) {
            throw (new BusinessLogicException(ExceptionCode.MEMBER_UNAUTHORIZED));
        }
        requestBody.setMemberId(memberId);
        Member member = memberService.updateMember(mapper.memberPatchToMember(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponse(member)),
                HttpStatus.OK);
    }

    // get
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId,
                                    HttpServletRequest request){

        Member findmember =
                memberService.findMember(memberId);

        MemberDto.Response  response = request.getAttribute("verified").equals(true) ?
                                        mapper.memberToMemberResponse(findmember):
                                        mapper.memberToMemberResponseForPublic(findmember);

        return  new ResponseEntity<>(
                new SingleResponseDto<>(response), HttpStatus.OK);

    }




    @GetMapping()
    public ResponseEntity getMembers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size,
                                     @RequestParam(required = false, defaultValue = "base") String mode){
        Page<Member> pageMembers = memberService.findMembers(page-1, size, mode);
        List<Member> members = pageMembers.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(mapper.membersToMemberResponses(members), pageMembers),
                HttpStatus.OK);

    }

    // delete
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // get voteCount
    @GetMapping("/{member-id}/vote-count")
    public ResponseEntity getMemberVoteCount(@PathVariable("member-id") @Positive long memberId){
        Member findmember =  new Member();
        findmember.setVoteCount(100);
        findmember.setMemberId(memberId);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponse(findmember)),
                HttpStatus.OK);
    }
}
