package com.maktab.onlineQuizManagement.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;


@Entity
@Table(name = "Quiz_Question")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.quiz",
                joinColumns = @JoinColumn(name = "quiz_id")),
        @AssociationOverride(name = "primaryKey.question",
                joinColumns = @JoinColumn(name = "question_id"))})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class QuizQuestions {
    @EmbeddedId
    private QuizQuestionId primaryKey = new QuizQuestionId();

    private int score;
}
