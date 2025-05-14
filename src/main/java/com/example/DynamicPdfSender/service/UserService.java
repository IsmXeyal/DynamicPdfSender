package com.example.DynamicPdfSender.service;

import com.example.DynamicPdfSender.dto.UserDetailDTO;
import com.example.DynamicPdfSender.exception.UserAlreadyExistsException;
import com.example.DynamicPdfSender.exception.UserNotFoundException;
import com.example.DynamicPdfSender.model.UserDetail;
import com.example.DynamicPdfSender.repository.UserRepository;
import com.example.DynamicPdfSender.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PdfGeneratorUtil pdfGeneratorUtil;
    private final EmailUtil emailUtil;

    // Method to load the default photo
//    private byte[] loadDefaultPhoto() {
//        try {
//            ClassPathResource resource = new ClassPathResource("images/default-img.jpg");
//            return FileCopyUtils.copyToByteArray(resource.getInputStream());
//        } catch (IOException e) {
//            throw new RuntimeException("Error loading default photo", e);
//        }
//    }

    public void createUser(UserDetailDTO userDetailDTO) {
        userRepository.findByFinCode(userDetailDTO.getFinCode())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("User with the given finCode already exists");
                });

        userRepository.findByEmail(userDetailDTO.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("User with the given email already exists");
                });

        // Convert UserDetailDTO to UserDetail entity
        UserDetail userDetail = UserDetail.builder()
                .firstName(userDetailDTO.getFirstName())
                .lastName(userDetailDTO.getLastName())
                .email(userDetailDTO.getEmail())
                .finCode(userDetailDTO.getFinCode())
                .birthDate(userDetailDTO.getBirthDate())
                //.photo(loadDefaultPhoto())
                .build();

        userRepository.save(userDetail);
    }

//    public void uploadPhoto(String finCode, byte[] photo) {
//        UserDetail userDetail = userRepository.findByFinCode(finCode)
//                .orElseThrow(() -> new UserNotFoundException("User not found with finCode: " + finCode));
//        userDetail.setPhoto(photo);
//        userRepository.save(userDetail);
//    }

    public void sendUserPdfByEmail(String finCode) {
        UserDetail userDetail = userRepository.findByFinCode(finCode)
                .orElseThrow(() -> new UserNotFoundException("User not found with finCode: " + finCode));
        byte[] pdf = pdfGeneratorUtil.generatePdf(userDetail);
        emailUtil.sendEmailWithAttachment(userDetail.getEmail(), pdf, "user-info.pdf");
    }
}
