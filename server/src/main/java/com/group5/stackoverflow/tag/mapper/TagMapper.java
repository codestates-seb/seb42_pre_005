package com.group5.stackoverflow.tag.mapper;

import com.group5.stackoverflow.tag.dto.TagDto;
import com.group5.stackoverflow.tag.entity.Tag;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TagMapper {

}
