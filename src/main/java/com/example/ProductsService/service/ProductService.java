package com.example.ProductsService.service;


import com.example.ProductsService.model.Product;
import com.example.ProductsService.records.ProductApiRequest;
import com.example.ProductsService.records.ProductApiResponse;

import java.util.List;

public interface ProductService {
    public List<ProductApiResponse> getAllProducts();
    public List<ProductApiResponse> getActiveProducts();
    public Product createProduct(ProductApiRequest product);
    public void deleteProduct(String id);
    public ProductApiResponse getProductById(String id);
    public List<ProductApiResponse> getProductsByCategory(String category);

    List<ProductApiResponse> getProductsByCategoryAndSort(String category, String sortField, String sortDirection);

    List<ProductApiResponse> getAvailableProducts();
}
