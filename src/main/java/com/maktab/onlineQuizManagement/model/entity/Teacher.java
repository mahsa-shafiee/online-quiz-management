package com.maktab.onlineQuizManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Teacher extends User {

    @JsonIgnore
    @OneToMany(mappedBy = "creator", fetch = FetchType.LAZY)
    private List<Quiz> createdQuizzes;

}
