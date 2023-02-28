package com.group5.stackoverflow.question.service;

import com.group5.stackoverflow.exception.BusinessLogicException;
import com.group5.stackoverflow.exception.ExceptionCode;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.entity.QuestionTag;
import com.group5.stackoverflow.question.repository.QuestionRepository;
import com.group5.stackoverflow.tag.entity.Tag;
import com.group5.stackoverflow.tag.repository.TagRepository;
import com.group5.stackoverflow.tag.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class QuestionService {
    private final QuestionRepository repository;
    private final MemberService memberService;
    private final TagService tagService;
    private final TagRepository tagRepository;

    public QuestionService(QuestionRepository repository,
                           MemberService memberService,
                           TagService tagService,
                           TagRepository tagRepository) {
        this.repository = repository;
        this.memberService = memberService;
        this.tagService = tagService;
        this.tagRepository = tagRepository;
    }

    // 질문 생성
    public Question createQuestion(Question question, List<String> tagName) {
        memberService.findVerifiedMember(question.getMember().getMemberId()); // member 확인

        List<Tag> tags = tagService.findTagsElseCreateTags(tagName);
        tags.forEach(tag -> {
            new QuestionTag(question, tag);
        });

        return repository.save(question);
    }

    // 질문 수정
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Question updateQuestion(Question question, List<String> tagNames) {
        Question findQuestion = findVerifiedQuestion(question.getQuestionId());
        // 다른 사람 질문 수정 못하게 하기
        // 토큰 확인
        if (findQuestion.getMember().getMemberId() != memberService.getLoginMember().getMemberId())
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);

        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        Optional.ofNullable(question.getContent())
                .ifPresent(content -> findQuestion.setContent(content));

        List<Tag> tags = tagService.updateQuestionTags(findQuestion, tagNames);
        tags.forEach(tag -> new QuestionTag(question, tag));

        return repository.save(findQuestion);
    }

    // 질문 찾기(1개에 대한)
    @Transactional(readOnly = true)
    public Question findQuestion(Long questionId) {
        Question findQuestion = findVerifiedQuestion(questionId);
        // view 카운트 로직
        int findViews = findQuestion.getViews() + 1;
        findQuestion.setViews(findViews);
        repository.save(findQuestion);

        return findQuestion;
    }

    // 질문 전체 찾기
    public Page<Question> findQuestions(int page, int size, String tab) {
        Page<Question> result;

        switch (tab) {
            case "unanswered":
                result = repository.findAll(PageRequest.of(page, size, Sort.by("answers").ascending()));
                break;
            default:
                result = repository.findAll(PageRequest.of(page, size, Sort.by("questionId").descending()));
        }
        return result;
    }

    public Page<Question> findMyQuestions(Long memberId, int page, int size) {
        return repository.findAllByMemberMemberId(PageRequest.of(page, size, Sort.by("questionId").descending()), memberId);
    }

    // 검색에 맞는 질문 찾기
    public Page<Question> searchQuestion(int page, int size, String keyword) {
        Page<Question> questionPage =
                repository.findAllByTitleContainingIgnoreCase(PageRequest.of(page, size), keyword);

        return questionPage;
    }


    @Transactional(readOnly = true)
    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion =
                repository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }

//    private void verifyQuestion(Question question) {
//        // member 존재 확인
//        memberService.findVerifiedMember(question.getQuestionId());
//    }

    // 추천 로직
    public Question updateVote(long questionId, String updown) {
        Question findQuestion = findVerifiedQuestion(questionId);
        int vote = (updown.equals("up")) ? findQuestion.getVoteCount() + 1 : findQuestion.getVoteCount() - 1;

        findQuestion.setVoteCount(vote);
        return repository.save(findQuestion);
    }

    // 질문 삭제
    public void deleteQuestion(Long questionId) {
        Question findQuestion = findQuestion(questionId);

        if (findQuestion.getMember().getMemberId() != memberService.getLoginMember().getMemberId())
            throw new BusinessLogicException(ExceptionCode.ACCESS_DENIED);

        repository.delete(findQuestion);
    }
}
