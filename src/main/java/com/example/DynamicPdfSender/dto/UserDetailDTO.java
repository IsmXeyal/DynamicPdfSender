package com.example.DynamicPdfSender.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String finCode;
    private LocalDate birthDate;
}
