package com.group5.stackoverflow.tag.controller;

import com.group5.stackoverflow.tag.mapper.TagMapper;
import com.group5.stackoverflow.tag.repository.TagRepository;
import com.group5.stackoverflow.tag.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;
    private final TagMapper mapper;

    public TagController(TagService tagService, TagMapper mapper) {
        this.tagService = tagService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity getTags() {
        return null;
    }

    @GetMapping("/{tag-id}")
    public ResponseEntity getOneTag() {
        return null;
    }

    @GetMapping("/sort")
    public ResponseEntity sortTags() {
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity searchTags() {
        return null;
    }


}
