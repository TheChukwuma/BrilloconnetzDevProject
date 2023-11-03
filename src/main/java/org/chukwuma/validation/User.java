package org.chukwuma.validation;

import lombok.Data;

import java.time.LocalDate;

@Data
public class User {

    private String username;
    private String password;
    private String email;
    private String dob;
}
