package com.group5.stackoverflow.tag.service;

import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.entity.QuestionTag;
import com.group5.stackoverflow.question.repository.QuestionRepository;
import com.group5.stackoverflow.tag.entity.Tag;
import com.group5.stackoverflow.tag.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;
    private final QuestionRepository questionRepository;

    public TagService(TagRepository tagRepository, QuestionRepository questionRepository) {
        this.tagRepository = tagRepository;
        this.questionRepository = questionRepository;
    }

    // 태그 생성
    public Tag createTag(String tagName) {
        Tag tag = new Tag();
        tag.setTagName(tagName);
        return tagRepository.save(tag);
    }

    // 태그를 List로 저장
    public List<Tag> findTagsElseCreateTags(List<String> tagNames) {
        return tagNames.stream()
                .map(tagName -> findVerifiedTag(tagName))
                .collect(Collectors.toList());
    }

    // tab = popular, name , new
    // popular = askedTotal 높은 순 Desc
    // name = tagName 기준 Asc
    // new = tagId 기준 Desc
    @Transactional(readOnly = true)
    public Page<Tag> findTags(int page, int size, String keyword, String tab) {

        Pageable pageable;
        Page<Tag> tags;

        switch (tab) {
            case "new" :
                pageable = PageRequest.of(page, size, Sort.by("tagId").descending());
                break;
            case "name" :
                pageable = PageRequest.of(page, size, Sort.by("tagName").ascending());
                break;
            default : // popular
                pageable = PageRequest.of(page, size, Sort.by("questionCount").descending());
        }

        if (keyword.isEmpty()) {
            tags = tagRepository.findAll(pageable);
        } else {
            tags = tagRepository.findAllByTagNameContains(keyword, pageable);
        }
        return tags;
    }

    public void updateQuestionCount(Tag tag) {
        tag.calQuestionCount();
        tagRepository.save(tag);
    }

    // 태그 검색
    @Transactional(readOnly = true)
    public Optional<Tag> findOptionalTagByTagName(String tagName) {
        return tagRepository.findByTagName(tagName);
    }

    public Tag findVerifiedTag(String tagName) {
        Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        } else {
            return createTag(tagName);
        }
    }


//    // popular, name, new 기준별 정렬 페이지
//    public List<Tag> findTagsPopular(int page) {
//        Page<Tag> tagPage = tagRepository.findByOrderByAskedTotal(PageRequest.of(page, 36));
//        List<Tag> content = tagPage.getContent();
//        return content;
//    }
//
//    public List<Tag> findTagsName(int page) {
//        Page<Tag> tagPage = tagRepository.findByOrderByTagNameAsc(PageRequest.of(page, 36));
//        List<Tag> content = tagPage.getContent();
//        return content;
//    }
//
//    public List<Tag> findTagsNew(int page) {
//        Page<Tag> tagPage = tagRepository.findByOrderByCreatedAtDesc(PageRequest.of(page, 36));
//        List<Tag> content = tagPage.getContent();
//
//        return content;
//    }
}
