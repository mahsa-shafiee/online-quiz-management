package com.maktab.onlineQuizManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
public class UserQuizId implements Serializable {

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Quiz quiz;

}
