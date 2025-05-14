package com.example.DynamicPdfSender.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;

import java.time.LocalDate;

@Entity
@Table(name = "users_Details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private String email;
    private String finCode;
    private LocalDate birthDate;

//    @Lob
//    @Column(name = "photo", columnDefinition = "BYTEA")
//    private byte[] photo;
}
