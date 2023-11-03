package com.example.ProductsService.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {


    String storeImage(MultipartFile file, String productId) throws IOException;

    byte[] getImage(String fileName) throws IOException;

    String storeBrandImage(MultipartFile image, String id) throws IOException;
}
