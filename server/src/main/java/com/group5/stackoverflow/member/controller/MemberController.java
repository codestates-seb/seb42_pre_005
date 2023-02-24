package com.group5.stackoverflow.member.controller;

import com.group5.stackoverflow.dto.MultiResponseDto;
import com.group5.stackoverflow.dto.PageInfo;
import com.group5.stackoverflow.dto.SingleResponseDto;
import com.group5.stackoverflow.member.dto.MemberDto;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.mapper.MemberMapper;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.question.dto.QuestionDto;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.mapper.QuestionMapper;
import com.group5.stackoverflow.question.service.QuestionService;
import com.group5.stackoverflow.utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static com.group5.stackoverflow.question.controller.QuestionController.QUESTION_DEFAULT_URL;

@RestController
@RequestMapping("/members")
@Validated
@Slf4j
@CrossOrigin(value = {"http://bucket-stackoverflow.s3-website.ap-northeast-2.amazonaws.com/",
        "http://seb42-pre5.s3-website.ap-northeast-2.amazonaws.com"})
public class MemberController {

    private final MemberService memberService;
    private final MemberMapper mapper;

    private final QuestionService questionService;

    private final QuestionMapper questionMapper;

    public MemberController(MemberService memberService, MemberMapper mapper,
                            QuestionService questionService, QuestionMapper questionMapper) {
        this.memberService = memberService;
        this.mapper = mapper;
        this.questionService = questionService;
        this.questionMapper = questionMapper;
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
            @Valid @RequestBody MemberDto.Patch requestBody) throws IllegalAccessException {
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

        Member findmember =
                memberService.findMember(memberId);

        return  new ResponseEntity<>(
                new SingleResponseDto<>(mapper.memberToMemberResponse(findmember)),
                HttpStatus.OK);
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

    @PostMapping("/{member-id}/questions")
    public ResponseEntity postQuestionOfMember(@PathVariable("member-id") @Positive long memberId,
                                               @Valid @RequestBody QuestionDto.Post requestBody) {
        requestBody.addMemberId(memberId);
        Question createdQuestion = questionService.createQuestion(questionMapper.questionPostToQuestion(requestBody));
        URI location = UriCreator.createUri(QUESTION_DEFAULT_URL, createdQuestion.getQuestionId());

        return ResponseEntity.created(location).build();
    }
}
