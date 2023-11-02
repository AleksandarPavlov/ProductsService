package com.example.ProductsService.records;


import com.example.ProductsService.model.Product;

public record ProductApiResponse(String id, String name, String category, long price, boolean available, String description, String imageName) {

    public static ProductApiResponse map(Product product){
        return new ProductApiResponse(product.getId(), product.getName(), product.getCategory(), product.getPrice(), product.isAvailable(), product.getDescription(), product.getId()+"-"+product.getImageName());
    }
}
