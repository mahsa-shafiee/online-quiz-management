package com.maktab.onlineQuizManagement.model.entity;

import lombok.*;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("Descriptive")
public class DescriptiveQuestion extends Question {
    private int requiredAnswerLines;
}
