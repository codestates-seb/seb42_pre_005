package com.group5.stackoverflow.tag.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Tag {

    @Id
    private Long tagId;
    private String tagName;
}
