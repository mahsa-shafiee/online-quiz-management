package com.maktab.onlineQuizManagement.model.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Student extends User {

    @OneToMany(mappedBy = "primaryKey.user", cascade = CascadeType.ALL)
    private List<UserQuizzes> participatedQuizzes = new ArrayList<>();

}
