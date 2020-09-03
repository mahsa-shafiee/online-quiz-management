package com.maktab.onlineQuizManagement.model.entity.User;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maktab.onlineQuizManagement.model.entity.Course;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 10, message = "length should be in between 2 to 10")
    String name;

    @NotNull(message = "Family cannot be null")
    String family;

    @NotEmpty(message = "Email field should not be empty")
    @Email(regexp = "^(.+)@(.+)$", message = "Invalid email pattern")
    String emailAddress;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 15)
    String password;

    private String confirmationToken;

    @NotNull(message = "Role cannot be null")
    @Enumerated(EnumType.STRING)
    UserRole role;

    @Enumerated(EnumType.STRING)
    UserRegistrationStatus registrationStatus;

    @JsonIgnore
    @ManyToMany(mappedBy = "members", fetch = FetchType.EAGER)
    private List<Course> courses;

}
