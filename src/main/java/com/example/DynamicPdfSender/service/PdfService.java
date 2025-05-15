package com.example.DynamicPdfSender.service;

import com.example.DynamicPdfSender.dto.UserIdentifierDTO;

public interface PdfService {
    void sendUserPdfByEmail(UserIdentifierDTO userIdentifierDTO);
}
