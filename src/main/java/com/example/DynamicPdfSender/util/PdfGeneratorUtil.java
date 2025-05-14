package com.example.DynamicPdfSender.util;

import com.example.DynamicPdfSender.exception.PdfGenerationException;
import com.example.DynamicPdfSender.model.UserDetail;
import com.itextpdf.text.Document;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class PdfGeneratorUtil {
    public byte[] generatePdf(UserDetail user) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

//            if (user.getPhoto() != null) {
//                Image img = Image.getInstance(user.getPhoto());
//                img.scaleToFit(100, 100);
//                img.setAlignment(Image.ALIGN_CENTER);
//                document.add(new Paragraph("Photo:"));
//                document.add(img);
//            }

            document.add(new Paragraph("User Details", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Name: " + user.getFirstName() + " " + user.getLastName()));
            document.add(new Paragraph("Email: " + user.getEmail()));
            document.add(new Paragraph("FIN: " + user.getFinCode()));
            document.add(new Paragraph("Birth Date: " + user.getBirthDate()));

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new PdfGenerationException("PDF generation failed", e);
        }
    }
}
