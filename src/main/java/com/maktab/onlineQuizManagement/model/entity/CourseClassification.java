package com.maktab.onlineQuizManagement.model.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CourseClassification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private int id;

    @NotNull(message = "Name cannot be null")
    private String name;

    @OneToMany(mappedBy = "classification", fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Course> courses;
}
