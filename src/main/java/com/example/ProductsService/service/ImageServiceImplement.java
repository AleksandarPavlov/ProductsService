package com.example.ProductsService.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class ImageServiceImplement implements ImageService {
    private static final Logger log = LoggerFactory.getLogger(ImageServiceImplement.class);
    @Value("${image.upload.path}")
    private String uploadPath;

    @Value("${image.upload.path.brand}")
    private String uploadBrandPath;


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

    @Override
    public String storeBrandImage(MultipartFile image, String id) throws IOException {
        log.info("Attempting to store an image for brand with ID: {}", id);
        String fileName = id+"-"+image.getOriginalFilename();
        Path filePath = Paths.get(uploadBrandPath + fileName);
        Files.write(filePath, image.getBytes());
        return fileName;
    }
}
