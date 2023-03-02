package com.group5.stackoverflow.tag.mapper;

import com.group5.stackoverflow.tag.dto.TagDto;
import com.group5.stackoverflow.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

    default TagDto.ResponseDto tagToResponseDto(Tag tag) {
        TagDto.ResponseDto responseDto = new TagDto.ResponseDto();
        responseDto.setTagId(tag.getTagId());
        responseDto.setTagName(tag.getTagName());
        responseDto.setQuestionCount(tag.getQuestionCount());

        return responseDto;
    }

    default List<TagDto.ResponseDto> tagsToResponseDtos(List<Tag> tags) {
        return tags.stream()
                .map(tag -> tagToResponseDto(tag))
                .collect(Collectors.toList());
    }

}
