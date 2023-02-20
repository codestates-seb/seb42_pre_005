package com.group5.stackoverflow.member.controller;

import com.group5.stackoverflow.dto.MultiResponseDto;
import com.group5.stackoverflow.dto.PageInfo;
import com.group5.stackoverflow.dto.SingleResponseDto;
import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.mapper.MemberMapper;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
@Validated
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    public MemberController(MemberService memberService, MemberMapper mapper) {
        this.memberService = memberService;
        this.mapper = mapper;
    }

    private final static String MEMBER_DEFAULT_URL = "/members";


    // post
    @PostMapping()
    public ResponseEntity postMember(@Valid  @RequestBody MemberDto.Post memberDtoPost){
        Member member =  mapper.memberPostToMember(memberDtoPost);

        // TODO 서비스 연결

        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, 10000);

        return ResponseEntity.created(location).build();
    }

    @PatchMapping("/{member-id}")
    public ResponseEntity patchMember(
            @PathVariable("member-id") @Positive long memberId,
            @Valid @RequestBody MemberDto.Patch requestBody) {
        requestBody.setMemberId(memberId);

        Member member =
                memberService.updateMember(mapper.memberPatchToMember(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponse(member)),
                HttpStatus.OK);
    }

    // get
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId){

        // TODO 서비스 연결
        Member findmember =
                memberService.findMember(memberId);



        return  new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponse(findmember)),
                HttpStatus.OK);
    }




    @GetMapping()
    public ResponseEntity getMembers(@RequestParam @Positive int page,
                                     @RequestParam @Positive int size,
                                     @RequestParam String by){
        // TODO 서비스 연결
        Page<Member> pageMembers = memberService.findMembers(page-1, size, by);
        List<Member> members = pageMembers.getContent();
        return new ResponseEntity<>(new MultiResponseDto<>(mapper.membersToMemberResponses(members), pageMembers),
                HttpStatus.OK);

    }

    // delete
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){
        // TODO 서비스 연결
        memberService.deleteMember(memberId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // get voteCount
    @GetMapping("/{member-id}/vote-count")
    public ResponseEntity getMemberVoteCount(@PathVariable("member-id") @Positive long memberId){
        // TODO 서비스 연결

        Member findmember =  new Member();
        findmember.setVoteCount(100);
        findmember.setMemberId(memberId);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberResponse(findmember)),
                HttpStatus.OK);
    }


}
