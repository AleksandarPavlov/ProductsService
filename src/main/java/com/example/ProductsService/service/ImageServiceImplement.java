package com.example.ProductsService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class ImageServiceImplement implements ImageService {
    private static final Logger log = LoggerFactory.getLogger(ImageServiceImplement.class);
    @Value("${image.upload.path}")
    private String uploadPath;
    @Override
    public String storeImage(MultipartFile file, String productId) throws IOException {
        log.info("Attempting to store an image for product with ID: {}", productId);
        String fileName = productId+"-"+file.getOriginalFilename();
        Path filePath = Paths.get(uploadPath + fileName);
        Files.write(filePath, file.getBytes());
        return fileName;
    }
    @Override
    public byte[] getImage(String fileName) throws IOException {
        log.info("Attempting to retrieve image with filename: {}", fileName);
        Path filePath = Paths.get(uploadPath + fileName);
        return Files.readAllBytes(filePath);
    }
}
