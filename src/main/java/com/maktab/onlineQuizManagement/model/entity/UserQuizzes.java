package com.maktab.onlineQuizManagement.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "User_Quiz")
@AssociationOverrides({
        @AssociationOverride(name = "primaryKey.user",
                joinColumns = @JoinColumn(name = "user_id")),
        @AssociationOverride(name = "primaryKey.quiz",
                joinColumns = @JoinColumn(name = "quiz_id"))})
@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserQuizzes {

    @EmbeddedId
    private UserQuizId primaryKey = new UserQuizId();

    @ElementCollection
    @MapKeyColumn(name = "question_number")
    @Column(length = 3000)
    private Map<Integer, String> answersOfQuestions = new HashMap<>();

    private double totalScore;

}
