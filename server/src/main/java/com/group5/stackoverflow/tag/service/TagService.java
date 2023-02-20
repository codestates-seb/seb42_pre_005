package com.group5.stackoverflow.tag.service;

import com.group5.stackoverflow.tag.entity.Tag;
import com.group5.stackoverflow.tag.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag createTag(Tag tag) {
        return tagRepository.save(tag);
    }

    public Tag updateTag() {
        return null;
    }

    public List<Tag> findTagsPopular(int page) {
        Page<Tag> tagPage = tagRepository.findByOrderByAskedTotal(PageRequest.of(page, 36));
        List<Tag> content = tagPage.getContent();
        return content;
    }

    public List<Tag> findTagsName(int page) {
        Page<Tag> tagPage = tagRepository.findByOrderByTagNameAsc(PageRequest.of(page, 36));
        List<Tag> content = tagPage.getContent();
        return content;
    }

    public List<Tag> findTagsNew(int page) {
        Page<Tag> tagPage = tagRepository.findByOrderByCreatedAtDesc(PageRequest.of(page, 36));
        List<Tag> content = tagPage.getContent();

        return content;
    }

    public Page<Tag> searchTag() {
        return null;
    }
}
