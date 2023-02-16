package com.group5.stackoverflow.tag.service;

import com.group5.stackoverflow.tag.entity.Tag;
import com.group5.stackoverflow.tag.repository.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Tag> findTagsPopular() {
        return null;
    }

    public List<Tag> findTagsName() {
        return null;
    }

    public List<Tag> findTagsNew() {
        return null;
    }

    public Page<Tag> searchTag() {
        return null;
    }
}
