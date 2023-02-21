package com.group5.stackoverflow.vote.service;

import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.answer.repository.AnswerRepository;
import com.group5.stackoverflow.answer.service.AnswerService;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.member.service.MemberService;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.repository.QuestionRepository;
import com.group5.stackoverflow.question.service.QuestionService;
import com.group5.stackoverflow.vote.dto.VoteDto;
import com.group5.stackoverflow.vote.entity.Vote;
import com.group5.stackoverflow.vote.repository.VoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoteService {
    private final VoteRepository voteRepository;
    private final MemberService memberService;
    private final QuestionService questionService;
    private final AnswerService answerService;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public VoteService(VoteRepository voteRepository,
                       MemberService memberService,
                       QuestionService questionService,
                       AnswerService answerService,
                       QuestionRepository questionRepository,
                       AnswerRepository answerRepository) {
        this.voteRepository = voteRepository;
        this.memberService = memberService;
        this.questionService = questionService;
        this.answerService = answerService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    public VoteDto.QuestionResponse saveQuestionVote(Long questionId, Long memberId, int count) {
        Question findQuestion = questionService.findQuestion(questionId);
        Member findMember = memberService.findMember(memberId);
        List<Vote> voteList = voteRepository.findAllByMemberAndQuestion(findMember, findQuestion);

        if (voteList.isEmpty()) {
            Vote createdVote = createVote(findQuestion, findMember, count);
            voteRepository.save(createdVote);

            int voteCount = voteRepository.findAllByQuestion(findQuestion)
                    .stream()
                    .mapToInt(Vote::getCount)
                    .sum();
            findQuestion.setVoteCount(voteCount);
            questionRepository.save(findQuestion);

            return VoteDto.QuestionResponse.builder()
                    .memberId(memberId)
                    .voteCount(voteCount)
                    .upCount(count == 1)
                    .downCount(count == -1)
                    .questionId(questionId)
                    .build();
        } else {
            voteRepository.deleteAll(voteList);
            int voteCount = voteRepository.findAllByQuestion(findQuestion)
                    .stream()
                    .mapToInt(Vote::getCount)
                    .sum();
            findQuestion.setVoteCount(voteCount);
            questionRepository.save(findQuestion);

            return VoteDto.QuestionResponse.builder()
                    .memberId(memberId)
                    .voteCount(voteCount)
                    .upCount(false)
                    .downCount(false)
                    .questionId(questionId)
                    .build();
        }
    }

    public VoteDto.AnswerResponse saveAnswerVote(Long answerId, Long memberId, int count) {
        Answer findAnswer = answerService.findVerifiedAnswer(answerId);
        Member findMember = memberService.findMember(memberId);
        List<Vote> voteList = voteRepository.findAllByMemberAndAnswer(findMember, findAnswer);

        if (voteList.isEmpty()) {
            Vote createdVote = createVote(findAnswer, findMember, count);
            voteRepository.save(createdVote);

            int voteCount = voteRepository.findAllByAnswer(findAnswer)
                    .stream()
                    .mapToInt(Vote::getCount)
                    .sum();
            findAnswer.setVoteCount(voteCount);
            answerRepository.save(findAnswer);

            return VoteDto.AnswerResponse.builder()
                    .memberId(memberId)
                    .voteCount(voteCount)
                    .upCount(count == 1)
                    .downCount(count == -1)
                    .answerId(answerId)
                    .build();
        } else {
            voteRepository.deleteAll(voteList);
            int voteCount = voteRepository.findAllByAnswer(findAnswer)
                    .stream()
                    .mapToInt(Vote::getCount)
                    .sum();
            findAnswer.setVoteCount(voteCount);
            answerRepository.save(findAnswer);

            return VoteDto.AnswerResponse.builder()
                    .memberId(memberId)
                    .voteCount(voteCount)
                    .upCount(false)
                    .downCount(false)
                    .answerId(answerId)
                    .build();
        }
    }

    public Vote createVote(Question question, Member member, int count) {
        return Vote.builder()
                .member(member)
                .question(question)
                .count(count)
                .build();
    }

    public Vote createVote(Answer answer, Member member, int count) {
        return Vote.builder()
                .member(member)
                .answer(answer)
                .count(count)
                .build();
}
}
