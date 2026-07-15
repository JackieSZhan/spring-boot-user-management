package com.example.demo.dto;

import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class UserRequestDto {
    @NotBlank(message = "FirstName is requeired")
    @Size(max = 50, message = "FirstName must not exceed 50 characters")
    private String firstName;

    @NotBlank(message = "LastName is required")
    @Size(max = 50, message = "LastName must not exceed 50 characters")
    private String lastName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not in correct form")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(max = 20, message = "Phone Number is too long")
    @Pattern(regexp = "^{10,20}$", message = "Invalid Phone Number")
    private String phone;


}


