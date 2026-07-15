package com.example.demo.dto;

import lombok.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Boolean active;

}
