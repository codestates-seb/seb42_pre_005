package com.group5.stackoverflow.answer.mapper;

import com.group5.stackoverflow.answer.dto.AnswerDto;
import com.group5.stackoverflow.answer.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {
    @Mapping(source = "questionId", target = "question.questionId")
    Answer answerPostDtoToAnswer(AnswerDto.Post requestBody);

    @Mapping(source = "questionId", target = "question.questionId")
    Answer answerPatchDtoToAnswer(AnswerDto.Patch requestBody);

    AnswerDto.Response answerToAnswerResponse(Answer answer);

}
