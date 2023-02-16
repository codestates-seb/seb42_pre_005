package com.group5.stackoverflow.tag.controller;

import com.group5.stackoverflow.tag.mapper.TagMapper;
import com.group5.stackoverflow.tag.repository.TagRepository;
import com.group5.stackoverflow.tag.service.TagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagController {

    private final TagService tagService;
    private final TagRepository tagRepository;
    private final TagMapper mapper;

    public TagController(TagService tagService, TagRepository tagRepository, TagMapper mapper) {
        this.tagService = tagService;
        this.tagRepository = tagRepository;
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

    @GetMapping
    public ResponseEntity sortTags() {
        return null;
    }

    @GetMapping("/search")
    public ResponseEntity searchTags() {
        return null;
    }


}
