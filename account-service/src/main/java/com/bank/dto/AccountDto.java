package com.bank.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.bank.entity.Address;
import com.bank.util.ValidGender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String firstName;
    private String lastName;
    @Email(message = "please enter a valid email address")
    private String email;
    @ValidGender(message = " please select: male| female|other")
    private String gender;
    private String addharNumber;

    @NotBlank(message = "mandatory to enter pan number")
    private String panNumber;
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateOfBirth;
    private String userName;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String accountNumber;

    private Address address;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String profileImageUrl;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime createdAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDateTime updatedAt;
}
