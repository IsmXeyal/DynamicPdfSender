package com.example.DynamicPdfSender.service.impl;

import com.example.DynamicPdfSender.dto.UserIdentifierDTO;
import com.example.DynamicPdfSender.exception.UserNotFoundException;
import com.example.DynamicPdfSender.model.UserDetail;
import com.example.DynamicPdfSender.repository.UserRepository;
import com.example.DynamicPdfSender.service.PdfService;
import com.example.DynamicPdfSender.util.*;
import lombok.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PdfServiceImpl implements PdfService {
    private final UserRepository userRepository;
    private final PdfGeneratorUtil pdfGeneratorUtil;
    private final EmailUtil emailUtil;

    @Override
    public void sendUserPdfByEmail(UserIdentifierDTO userIdentifierDTO) {
        String finCode = userIdentifierDTO.getFinCode();
        String email = userIdentifierDTO.getEmail();

        if (finCode == null || finCode.isEmpty() || email == null || email.isEmpty()) {
            throw new UserNotFoundException("Both finCode and email must be provided");
        }

        UserDetail userByFinCode = userRepository.findByFinCode(finCode)
                .orElseThrow(() -> new UserNotFoundException("User not found with finCode: " + finCode));

        UserDetail userByEmail = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (!userByFinCode.getId().equals(userByEmail.getId())) {
            throw new UserNotFoundException("finCode and email do not belong to the same user");
        }

        byte[] pdf = pdfGeneratorUtil.generatePdf(userByFinCode);
        emailUtil.sendEmailWithAttachment(email, pdf, "user-info.pdf");
    }
}
