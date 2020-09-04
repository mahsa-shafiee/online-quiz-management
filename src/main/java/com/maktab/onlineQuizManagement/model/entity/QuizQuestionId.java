package com.maktab.onlineQuizManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class QuizQuestionId implements Serializable {
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Question question;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Quiz quiz;
}
