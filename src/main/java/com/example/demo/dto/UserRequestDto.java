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
    @NotBlank(message = "Full Name is requeired")
    @Size(max = 50, message = "Full Name must not exceed 50 characters")
    private String fullName;

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not in correct form")
    private String email;

    @NotBlank(message = "Phone is required")
    @Size(max = 20, message = "Phone Number is too long")
    @Pattern(regexp = "^\\d{10,20}$", message = "Phone number is either too long or too short")
    private String phone;


}


