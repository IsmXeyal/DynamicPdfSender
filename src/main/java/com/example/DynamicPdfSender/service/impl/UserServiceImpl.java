package com.example.DynamicPdfSender.service.impl;

import com.example.DynamicPdfSender.dto.UserDetailDTO;
import com.example.DynamicPdfSender.exception.*;
import com.example.DynamicPdfSender.model.UserDetail;
import com.example.DynamicPdfSender.repository.UserRepository;
import com.example.DynamicPdfSender.service.UserService;
import com.example.DynamicPdfSender.util.ImageUtil;
import lombok.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public void createUser(UserDetailDTO userDetailDTO) throws IOException {
        userRepository.findByFinCode(userDetailDTO.getFinCode())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("User with the given finCode already exists");
                });

        userRepository.findByEmail(userDetailDTO.getEmail())
                .ifPresent(user -> {
                    throw new UserAlreadyExistsException("User with the given email already exists");
                });

        UserDetail userDetail = UserDetail.builder()
                .firstName(userDetailDTO.getFirstName())
                .lastName(userDetailDTO.getLastName())
                .email(userDetailDTO.getEmail())
                .finCode(userDetailDTO.getFinCode())
                .birthDate(userDetailDTO.getBirthDate())
                .photo(ImageUtil.loadImageAsByteArray("static/images/default-img.jpg"))
                .build();

        userRepository.save(userDetail);
    }
}
