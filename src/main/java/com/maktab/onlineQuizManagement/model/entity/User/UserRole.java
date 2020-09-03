package com.maktab.onlineQuizManagement.model.entity.User;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRole {

    TEACHER("teacher"), STUDENT("student");

    String name;
}
