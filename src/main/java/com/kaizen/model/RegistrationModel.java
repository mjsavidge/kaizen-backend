package com.kaizen.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationModel {
    private final String name;
    private final String username;
    private final String password;
    public final String email;
}
