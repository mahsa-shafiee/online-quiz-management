package com.maktab.onlineQuizManagement.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "question_type",
        discriminatorType = DiscriminatorType.STRING)
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Topic cannot be null")
    @Size(min = 2, max = 10, message = "length should be in between 2 to 10")
    private String title;

    @NotNull(message = "Description cannot be null")
    private String content;

    @OneToMany(mappedBy = "primaryKey.question", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<QuizQuestions> quizzes = new HashSet<>();

}
