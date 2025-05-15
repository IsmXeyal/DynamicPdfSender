package com.example.DynamicPdfSender.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserIdentifierDTO {
    private String finCode;
    private String email;
}