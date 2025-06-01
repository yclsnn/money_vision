package com.moneyvision.user.model;

import lombok.Data;

@Data
public class UserQueryModel {
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private Boolean active;
} 