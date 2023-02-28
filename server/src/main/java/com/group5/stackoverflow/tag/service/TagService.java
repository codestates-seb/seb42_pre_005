package com.group5.stackoverflow.tag.service;

import com.group5.stackoverflow.question.entity.Question;
import com.group5.stackoverflow.question.entity.QuestionTag;
import com.group5.stackoverflow.question.repository.QuestionRepository;
import com.group5.stackoverflow.question.repository.QuestionTagRepository;
import com.group5.stackoverflow.tag.entity.Tag;
import com.group5.stackoverflow.tag.repository.TagRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
public class TagService {

    private final TagRepository tagRepository;
    private final QuestionRepository questionRepository;
    private final QuestionTagRepository questionTagRepository;

    public TagService(TagRepository tagRepository, QuestionRepository questionRepository,
                      QuestionTagRepository questionTagRepository) {
        this.tagRepository = tagRepository;
        this.questionRepository = questionRepository;
        this.questionTagRepository = questionTagRepository;
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

    // 태그달린 questionCount
    public void updateQuestionCount(Tag tag) {
        Tag name = findVerifiedTag(tag.getTagName());

        if (name.equals(tag)) {
            int count = tag.getQuestionCount() + 1;
            tag.setQuestionCount(count);
        }
        tagRepository.save(tag);
    }

    // 태그 수정
    public List<Tag> updateQuestionTags(Question question, List<String> tagNames) {

        // 기존 태그 삭제
        questionTagRepository.findAllByQuestion(question).stream()
                .forEach(questionTag -> {
                    questionTagRepository.delete(questionTag);
                    updateQuestionCount(questionTag.getTag());
                });

        // 새로운 태그 유효성 검사
        List<Tag> findTags = findTagsElseCreateTags(tagNames);

        // 새로운 questionTag 저장
        findTags.stream()
                .forEach(tag -> {
                    QuestionTag questionTag = new QuestionTag(question, tag);
                    QuestionTag saveQuestionTag = questionTagRepository.save(questionTag);
                    updateQuestionCount(saveQuestionTag.getTag());
                });

        return findTags;
    }

    // 태그이름으로 질문조회
    public Page<Question> findQuestionByTag(Pageable pageable,String tagName){
        Tag findTag = findVerifiedTag(tagName);
        List<QuestionTag> questionTags = findTag.getQuestionTags();
        List<Long> questionIdList = new ArrayList<>();
        for(QuestionTag questionTag:questionTags){
            Long questionId = questionTag.getQuestion().getQuestionId();
            questionIdList.add(questionId);
        }
        List<Question> questions = questionRepository.findAllById(questionIdList);

        Page<Question> questionPage = new PageImpl<>(questions, pageable, questions.size());

        return questionPage;
    }

    public Tag findVerifiedTag(String tagName) {
        Optional<Tag> optionalTag = tagRepository.findByTagName(tagName);
        if (optionalTag.isPresent()) {
            return optionalTag.get();
        } else {
            return createTag(tagName);
        }
    }

}
