package com.example.ProductsService.service;
import com.example.ProductsService.exceptions.CustomException;
import com.example.ProductsService.model.Product;
import com.example.ProductsService.records.ProductApiRequest;
import com.example.ProductsService.records.ProductApiResponse;
import com.example.ProductsService.repository.ProductRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductServiceImplement implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImplement(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public List<ProductApiResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductApiResponse> getActiveProducts() {
        List<Product> products = productRepository.findActiveProducts();
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());
    }

    @Override
    public Product createProduct(ProductApiRequest product) {
        Product newProduct = new Product();
        newProduct.setAvailable(product.available());
        newProduct.setDescription(product.description());
        newProduct.setCategory(product.category());
        newProduct.setDeleted(false);
        newProduct.setName(product.name());
        newProduct.setPrice(product.price());
        return productRepository.save(newProduct);
    }

    @Override
    public void deleteProduct(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setDeleted(true);
            productRepository.save(product);
        } else {
            throw new CustomException("Product with ID: " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public ProductApiResponse getProductById(String id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if(optionalProduct.isPresent()){
            return ProductApiResponse.map(optionalProduct.get());
        }
        else{
            throw new CustomException("Product with ID: " + id + " not found", HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<ProductApiResponse> getProductsByCategory(String category) {
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductApiResponse> getProductsByCategoryAndSort(String category, String sortField, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        List<Product> products = productRepository.findAllByCategory(category, sort);
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());

    }

    @Override
    public List<ProductApiResponse> getAvailableProducts() {
        List<Product> products = productRepository.findAvailableProducts();
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());
    }
}
