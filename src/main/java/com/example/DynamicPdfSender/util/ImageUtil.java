package com.example.DynamicPdfSender.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

public class ImageUtil {
    public static byte[] loadImageAsByteArray(String pathInResources) throws IOException {
        InputStream inputStream = new ClassPathResource(pathInResources).getInputStream();
        return inputStream.readAllBytes();
    }
}
