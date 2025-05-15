package com.example.DynamicPdfSender.service;

import com.example.DynamicPdfSender.dto.UserDetailDTO;
import java.io.IOException;

public interface UserService {
    void createUser(UserDetailDTO userDetailDTO) throws IOException;
}
