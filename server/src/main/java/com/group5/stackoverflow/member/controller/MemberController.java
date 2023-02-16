package com.group5.stackoverflow.member.controller;

import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.mapper.MemberMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberMapper mapper;

    public MemberController(MemberMapper mapper) {
        this.mapper = mapper;
    }

    // post
    @PostMapping()
    public ResponseEntity postMember(@RequestBody @Valid MemberDto.Post memberDtoPost){
        Member member =  mapper.memberDtoPostToMember(memberDtoPost);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

    // get
    @GetMapping("/{member-id}")
    public ResponseEntity getMember(@PathVariable("member-id") @Positive long memberId){
        Member findmember =  new Member();
        findmember.setMemberId(7L);


        return new ResponseEntity<>(mapper.memberToMemberDtoResponse(findmember), HttpStatus.OK);
    }

    // gets

    @GetMapping()
    public ResponseEntity getMembers(@RequestParam @Positive int size,
                                     @RequestParam @Positive int page){
        Member findmember1 =  new Member();
        Member findmember2 =  new Member();
        findmember1.setMemberId(7L);
        findmember2.setMemberId(77L);

        List<Member> members = new ArrayList<>();
        members.add(findmember1);
        members.add(findmember2);

        return new ResponseEntity<>(mapper.membersToMemberDtoResponses(members), HttpStatus.OK);
    }

    // delete
    @DeleteMapping("/{member-id}")
    public ResponseEntity deleteMember(@PathVariable("member-id") @Positive long memberId){

        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
    }

    // get voteCount
    @GetMapping("/{member-id}/vote-count")
    public ResponseEntity getMemberVoteCount(@PathVariable("member-id") @Positive long memberId){
        Member findmember =  new Member();
        findmember.setVoteCount(100);
        findmember.setMemberId(memberId);

        return new ResponseEntity<>(mapper.memberToMemberDtoResponse(findmember), HttpStatus.OK);
    }


}
