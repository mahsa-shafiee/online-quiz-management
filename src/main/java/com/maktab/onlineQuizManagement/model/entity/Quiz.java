package com.maktab.onlineQuizManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Topic cannot be null")
    @Size(min = 2, max = 10, message = "length should be in between 2 to 10")
    private String topic;

    @NotNull(message = "Description cannot be null")
    private String description;

    @NotNull(message = "Duration cannot be null")
    private long duration;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+04:30")
    private Timestamp start;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+04:30")
    private Timestamp end;

    private boolean enabled = true;

    @ManyToOne
    private Course course;

    @ManyToOne
    private User creator;

    @OneToMany(mappedBy = "primaryKey.quiz", cascade = CascadeType.ALL)
    private Set<UserQuizzes> participants = new HashSet<>();

    @OneToMany(mappedBy = "primaryKey.quiz", cascade = CascadeType.ALL)
    private Set<QuizQuestions> quizQuestions = new HashSet<>();

    private int scoreCeiling;
}
