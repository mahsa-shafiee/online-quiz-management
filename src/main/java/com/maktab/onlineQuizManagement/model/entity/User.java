package com.maktab.onlineQuizManagement.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.maktab.onlineQuizManagement.model.entity.enums.UserRegistrationStatus;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "emailAddress"))
@Inheritance(strategy = InheritanceType.JOINED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull(message = "Name cannot be null")
    @Size(min = 2, max = 10, message = "length should be in between 2 to 10")
    private String name;

    @NotNull(message = "Family cannot be null")
    private String family;

    @NotEmpty(message = "Email field should not be empty")
    @Email(regexp = "^(.+)@(.+)$", message = "Invalid email pattern")
    private String emailAddress;

    @NotNull(message = "Password cannot be null")
    private String password;

    private String confirmationToken;

    @Enumerated(EnumType.STRING)
    private UserRegistrationStatus registrationStatus;

    @JsonIgnore
    @ManyToMany(mappedBy = "members", fetch = FetchType.LAZY)
    private List<Course> courses;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    private List<Role> roles;

}
