package com.bd.webdiary.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long categoryId;

    private long userId;

    @NotBlank(message = "Title can not be empty")
    private String title;

    @NotBlank(message = "Content can not be empty")
    private String content;

    private LocalDateTime lastUpdatedTime;

    private int statusId;

}
