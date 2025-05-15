package com.example.DynamicPdfSender.util;

import com.example.DynamicPdfSender.exception.PdfGenerationException;
import com.example.DynamicPdfSender.model.UserDetail;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.draw.LineSeparator;
import com.itextpdf.text.pdf.PdfPCell;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;

@Component
public class PdfGeneratorUtil {
    public byte[] generatePdf(UserDetail user) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            // Title
            Paragraph title = new Paragraph("User Information", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            // Add photo
            if (user.getPhoto() != null && user.getPhoto().length > 0) {
                try {
                    Image img = Image.getInstance(user.getPhoto());
                    img.scaleToFit(100, 100);
                    img.setAlignment(Image.ALIGN_CENTER);
                    document.add(img);
                    document.add(Chunk.NEWLINE);
                } catch (Exception e) {
                    System.err.println("Failed to load image: " + e.getMessage());
                    document.add(new Paragraph("[Could not load image]"));
                    document.add(Chunk.NEWLINE);
                }
            }

            // Separator line
            LineSeparator ls = new LineSeparator();
            document.add(new Chunk(ls));
            document.add(Chunk.NEWLINE);

            // Table for user data
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);

            // Cell entries
            addRow(table, "FIN Code:", user.getFinCode());
            addRow(table, "First Name:", user.getFirstName());
            addRow(table, "Last Name:", user.getLastName());
            addRow(table, "Email:", user.getEmail());
            addRow(table, "Birth Date:", String.valueOf(user.getBirthDate()));

            document.add(table);

            document.close();
            return out.toByteArray();
        } catch (Exception e) {
            throw new PdfGenerationException("PDF generation failed", e);
        }
    }

    private void addRow(PdfPTable table, String key, String value) {
        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12);

        PdfPCell cellKey = new PdfPCell(new Phrase(key, boldFont));
        PdfPCell cellValue = new PdfPCell(new Phrase(value != null ? value : "", normalFont));

        // Show borders with width 1
        cellKey.setBorder(Rectangle.BOX);
        cellValue.setBorder(Rectangle.BOX);

        cellKey.setBorderWidth(1f);
        cellValue.setBorderWidth(1f);

        table.addCell(cellKey);
        table.addCell(cellValue);
    }
}
