package com.example.DynamicPdfSender.util;

import com.example.DynamicPdfSender.exception.EmailSendingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {
    private final JavaMailSender javaMailSender;

    public void sendEmailWithAttachment(String to, byte[] attachmentData, String fileName) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject("User Information PDF");
            helper.setText("Attached is your personal information in PDF format.");
            helper.addAttachment(fileName, new ByteArrayResource(attachmentData));
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new EmailSendingException("Failed to send email", e);
        }
    }
}
