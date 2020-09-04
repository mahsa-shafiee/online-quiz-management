package com.maktab.onlineQuizManagement.model.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRegistrationStatus {

    CONFIRMED("confirmed"),
    WAITING_FOR_CONFIRMATION("waiting for confirmation"),
    NOT_CONFIRMED("not confirmed");

    String name;
}
