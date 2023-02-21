package com.group5.stackoverflow.answer.mapper;

import com.group5.stackoverflow.answer.dto.AnswerDto;
import com.group5.stackoverflow.answer.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {

//    default Answer answerPostDtoToAnswer(AnswerDto.Post requestBody) {
//        if (requestBody == null) {
//            return null;
//        } else {
//            Answer answer = new Answer();
//            Question question = new Question();
//            Member member = new Member();
//            question.setQuestionId(requestBody.getQuestionId());
//            member.setMemberId(requestBody.getMemberId());
//            answer.setContent(requestBody.getContent());
//            answer.setQuestion(question);
//            answer.setMember(member);
//            return answer;
//        }
//    }

    @Mapping(source = "memberId", target = "member.memberId")
    @Mapping(source = "questionId", target = "question.questionId")
    Answer answerPostDtoToAnswer(AnswerDto.Post requestBody);

    @Mapping(source = "memberId", target = "member.memberId")
    @Mapping(source = "questionId", target = "question.questionId")
    Answer answerPatchDtoToAnswer(AnswerDto.Patch requestBody);

    AnswerDto.Response answerToAnswerResponse(Answer answer);

}
