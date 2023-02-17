package com.group5.stackoverflow.member.controller;

import com.group5.stackoverflow.dto.MultiResponseDto;
import com.group5.stackoverflow.dto.PageInfo;
import com.group5.stackoverflow.dto.SingleResponseDto;
import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.mapper.MemberMapper;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.utils.UriCreator;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
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
    public ResponseEntity postMember(@RequestBody @Valid MemberDto.Post memberDtoPost){
        Member member =  mapper.memberDtoPostToMember(memberDtoPost);

        // TODO 서비스 연결

        URI location = UriCreator.createUri(MEMBER_DEFAULT_URL, member.getMemberId());

        return ResponseEntity.created(location).build();
    }

    // get
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId){

        // TODO 서비스 연결
        Member findmember =  new Member();
        findmember.setMemberId(7L);


        return  new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberDtoResponse(findmember)),
                HttpStatus.OK);
    }


    @GetMapping()
    public ResponseEntity getMembers(@RequestParam @Positive int size,
                                     @RequestParam @Positive int page){

        // TODO 서비스 연결

        // mock page info
        PageInfo pageInfo = new PageInfo(4, 4, 40, 40);

        // mock List<T> file
        List<Member> members = new ArrayList<>();
        Member findmember1 =  new Member();
        Member findmember2 =  new Member();
        findmember1.setMemberId(7L);
        findmember2.setMemberId(77L);
        members.add(findmember1);
        members.add(findmember2);

        MultiResponseDto<Member> memberMultiResponseDto = new MultiResponseDto<>();
        memberMultiResponseDto.setData(members);
        memberMultiResponseDto.setPageInfo(pageInfo);

        return new ResponseEntity<>(memberMultiResponseDto, HttpStatus.OK);

    }

    // delete
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){
        // TODO 서비스 연결


        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // get voteCount
    @GetMapping("/{member-id}/vote-count")
    public ResponseEntity getMemberVoteCount(@PathVariable("member-id") @Positive long memberId){
        // TODO 서비스 연결

        Member findmember =  new Member();
        findmember.setVoteCount(100);
        findmember.setMemberId(memberId);

        return new ResponseEntity<>(new SingleResponseDto<>(mapper.memberToMemberDtoResponse(findmember)),
                HttpStatus.OK);
    }


}
