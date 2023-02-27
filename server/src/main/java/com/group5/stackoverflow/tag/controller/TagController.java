package com.group5.stackoverflow.tag.controller;

import com.group5.stackoverflow.dto.MultiResponseDto;
import com.group5.stackoverflow.question.service.QuestionService;
import com.group5.stackoverflow.tag.entity.Tag;
import com.group5.stackoverflow.tag.mapper.TagMapper;
import com.group5.stackoverflow.tag.repository.TagRepository;
import com.group5.stackoverflow.tag.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;

@Validated
@RestController
@RequestMapping("/tags")
@CrossOrigin(value = {"http://bucket-stackoverflow.s3-website.ap-northeast-2.amazonaws.com",
        "http://seb42-pre5.s3-website.ap-northeast-2.amazonaws.com/"})
public class TagController {

    private final TagService tagService;
    private final TagMapper mapper;

    public TagController(TagService tagService, TagMapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity getTags(@Positive @RequestParam(required = false, defaultValue = "1") int page,
                                  @Positive @RequestParam(required = false, defaultValue = "36") int size,
                                  @RequestParam(required = false, defaultValue = "") String keyword,
                                  @RequestParam(required = false, defaultValue = "popular") String tab) {

        // tab = popular, name , new
        // popular = questionCount 높은 순 Desc
        // name = tagName 기준 Asc
        // new = tagId 기준 Desc
        Page<Tag> pageTags = tagService.findTags(page - 1, size, keyword, tab);

        return new ResponseEntity(
                new MultiResponseDto<>(mapper.tagsToResponseDtos(pageTags.getContent()), pageTags),
                HttpStatus.OK
        );
    }
}
