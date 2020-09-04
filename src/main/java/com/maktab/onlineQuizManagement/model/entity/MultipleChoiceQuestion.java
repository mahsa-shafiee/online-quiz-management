package com.maktab.onlineQuizManagement.model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("MultipleChoice")
public class MultipleChoiceQuestion extends Question {
    @NotNull(message = "Options cannot be null")
    @ElementCollection
    @JoinTable(
            name = "MultipleChoiceQuestion_Option",
            joinColumns = {@JoinColumn(name = "question_id")}
    )
    private Set<String> options = new HashSet<>();

    private String correctOption;
}