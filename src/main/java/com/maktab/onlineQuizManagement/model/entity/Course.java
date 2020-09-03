package com.maktab.onlineQuizManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maktab.onlineQuizManagement.model.entity.User.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @ManyToOne
    private CourseClassification classification;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = {@JoinColumn(name = "course_id")},
            inverseJoinColumns = {@JoinColumn(name = "member_id")}
    )
    private List<User> members;
}
