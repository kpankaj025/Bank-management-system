package com.bank.api_routing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationDto {

    private String firstName;
    private String lastName;
    private String email;
    private String userName;

    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_WRITE)
    private String confirmPassword;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public RegistrationDto(String firstName, String lastName, String email, String userName, String password,
            String confirmPassword) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public RegistrationDto() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

}
