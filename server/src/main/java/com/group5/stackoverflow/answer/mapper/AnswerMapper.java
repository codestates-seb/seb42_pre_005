package com.group5.stackoverflow.answer.mapper;

import com.group5.stackoverflow.answer.dto.AnswerDto;
import com.group5.stackoverflow.answer.entity.Answer;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AnswerMapper {

    Answer answerPostDtoToAnswer(AnswerDto.Post requestBody);

    Answer answerPatchDtoToAnswer(AnswerDto.Patch requestBody);

    AnswerDto.Response AnswerToAnswerResponse(Answer answer);

}
