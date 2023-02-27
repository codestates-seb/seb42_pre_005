package com.group5.stackoverflow.question.mapper;

import com.group5.stackoverflow.question.dto.QuestionDto;
import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.entity.QuestionTag;
import com.group5.stackoverflow.tag.dto.TagDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface QuestionMapper {
    @Mapping(source = "memberId", target = "member.memberId")
    Question questionPostToQuestion(QuestionDto.Post requestBody);
    Question questionPatchToQuestion(QuestionDto.Patch requestBody);
//    QuestionDto.Response questionToQuestionResponse(Question question);
    default List<TagDto.ResponseDto> questionTagsToTagResponse(List<QuestionTag> questionTags) {
        return questionTags.stream()
                .map(questionTag -> {
                    TagDto.ResponseDto tagResponseDto = new TagDto.ResponseDto();
                    tagResponseDto.setTagId(questionTag.getTag().getTagId());
                    tagResponseDto.setTagName(questionTag.getTag().getTagName());
                    tagResponseDto.setQuestionCount(questionTag.getTag().getQuestionCount());

                    return tagResponseDto;
                })
                .collect(Collectors.toList());
    }

    default QuestionDto.Response questionToQuestionResponse(Question question) {
        if (question == null) {
            return null;
        }

        return QuestionDto.Response
                .builder()
                .questionId(question.getQuestionId())
                .title(question.getTitle())
                .content(question.getContent())
                .memberId(question.getMember().getMemberId())
                .name(question.getMember().getName())
                .voteCount(question.getVoteCount())
                .views(question.getViews())
                .tagResponseDtos(questionTagsToTagResponse(question.getQuestionTags()))
                .build();
    }
    List<QuestionDto.Response> questionsToQuestionResponses(List<Question> questions);
}
