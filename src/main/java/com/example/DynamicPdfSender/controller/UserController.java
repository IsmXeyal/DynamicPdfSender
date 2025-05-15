package com.example.DynamicPdfSender.controller;

import com.example.DynamicPdfSender.dto.*;
import com.example.DynamicPdfSender.service.*;
import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final PdfService pdfService;

    @PostMapping("/create-user")
    public ResponseEntity<String> createUser(@RequestBody UserDetailDTO userDetailDTO) {
        try {
            userService.createUser(userDetailDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("User created successfully: " + userDetailDTO.getFirstName() + " " + userDetailDTO.getLastName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating user: " + e.getMessage());
        }
    }

    @PostMapping(value = "/upload-photo", consumes = {"multipart/form-data"})
    public ResponseEntity<String> uploadPhoto(
            @RequestPart("finCode") String finCode,
            @RequestPart("file") MultipartFile file) {
        try {
            userService.uploadPhoto(finCode, file.getBytes());
            return ResponseEntity.ok("Photo uploaded successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Failed to upload photo: " + e.getMessage());
        }
    }

    @PostMapping("/send-email")
    public ResponseEntity<String> sendEmail(@RequestBody UserIdentifierDTO request) {
        try {
            pdfService.sendUserPdfByEmail(request);
            return ResponseEntity.status(HttpStatus.OK).body("Email was sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }
}
