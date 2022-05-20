package com.bd.webdiary.model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginInfo {

    @NotBlank
    private String userName;

    @NotBlank
    private String password;
}
