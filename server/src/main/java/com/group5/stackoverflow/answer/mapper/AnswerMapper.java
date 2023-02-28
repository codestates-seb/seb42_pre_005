package com.group5.stackoverflow.answer.mapper;

import com.group5.stackoverflow.answer.dto.AnswerDto;
import com.group5.stackoverflow.answer.entity.Answer;
import com.group5.stackoverflow.member.entity.Member;
import com.group5.stackoverflow.question.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
//    @Mapping(source = "questionId", target = "question.questionId")
//    @Mapping(source = "memberId", target = "member.memberId")
//    Answer answerPostDtoToAnswer(AnswerDto.Post requestBody);
//
//    @Mapping(source = "questionId", target = "question.questionId")
//    @Mapping(source = "memberId", target = "member.memberId")
//    Answer answerPatchDtoToAnswer(AnswerDto.Patch requestBody);
//
//    AnswerDto.Response answerToAnswerResponse(Answer answer);

    default Answer answerPostDtoToAnswer(AnswerDto.Post requestBody) {
        if (requestBody == null) {
            return null;
        } else {
            Answer answer = new Answer();
            Question question = new Question();
            question.setQuestionId(requestBody.getQuestionId());
            Member member = new Member();
            member.setMemberId(requestBody.getMemberId());

            answer.setContent(requestBody.getContent());
            answer.setQuestion(question);
            answer.setMember(member);
            return answer;
        }
    }

    default Answer answerPatchDtoToAnswer(AnswerDto.Patch requestBody) {
        if (requestBody == null) {
            return null;
        } else {
            Answer answer = new Answer();
            Question question = new Question();
            question.setQuestionId(requestBody.getQuestionId());
            Member member = new Member();
            member.setMemberId(requestBody.getMemberId());

            answer.setContent(requestBody.getContent());
            answer.setQuestion(question);
            answer.setMember(member);
            return answer;
        }
    }

    default AnswerDto.Response answerToAnswerResponse(Answer answer) {
        if (answer == null) {
            return null;
        }
        return AnswerDto.Response.builder()
                .answerId(answer.getAnswerId())
                .questionId(answer.getQuestion().getQuestionId())
                .content(answer.getContent())
                .voteCount(answer.getVoteCount())
                .memberId(answer.getMember().getMemberId())
                .name(answer.getMember().getName())
                .build();
    }

    List<AnswerDto.Response> answersToAnswerResponses(List<Answer> answers);

//    default AnswerDto.Response answerToAnswerResponse(Answer answer) {
//        if (answer == null) {
//            return null;
//        } else {
//            AnswerDto.Response response = new AnswerDto.Response(
//                    answer.getAnswerId(),
//                    answer.getMember().getMemberId(),
//                    answer.getQuestion().getQuestionId(),
//                    answer.getMember().getName(),
//                    answer.getContent(),
//                    answer.getVoteCount()
//            );
//            return response;
//        }
//
//    }

}
