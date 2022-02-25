package com.kaizen.model;

import lombok.*;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Data
public class LogInModel {
    private String usernameOrEmail;
    private String password;
}
