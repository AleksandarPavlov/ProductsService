package com.example.ProductsService.service;
import com.example.ProductsService.exceptions.CustomException;
import com.example.ProductsService.model.Product;
import com.example.ProductsService.records.ProductApiRequest;
import com.example.ProductsService.records.ProductApiResponse;
import com.example.ProductsService.records.ProductUpdateRequest;
import com.example.ProductsService.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductServiceImplement implements ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductServiceImplement.class);
    private final ProductRepository productRepository;
    private final ImageService imageService;

    public ProductServiceImplement(ProductRepository productRepository, ImageService imageService) {
        this.productRepository = productRepository;
        this.imageService = imageService;
    }

    @Override
    public List<ProductApiResponse> getAllProducts() {
        log.info("Fetching all products.");
        List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductApiResponse> getActiveProducts() {
        log.info("Fetching active products.");
        List<Product> products = productRepository.findActiveProducts();
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());
    }

    @Override
    public Product createProduct(ProductApiRequest product) throws IOException {

        log.info("Adding a new product with name: {}", product.name());

        if (product.image() == null || product.image().isEmpty()) {
            throw new IllegalArgumentException("Image cannot be empty");
        }

        Product newProduct = new Product();
        newProduct.setAvailable(product.available());
        newProduct.setDescription(product.description());
        newProduct.setCategory(product.category());
        newProduct.setDeleted(false);
        newProduct.setName(product.name());
        newProduct.setPrice(product.price());
        newProduct.setImageName(product.image().getOriginalFilename());
        newProduct.setImageUrl(product.imageUrl());
        newProduct.setBrandId(product.brandId());
        newProduct.setBrandName(product.brandName());
        Product createdProduct = productRepository.save(newProduct);
        imageService.storeImage(product.image(), createdProduct.getId());
        return createdProduct;
    }

    @Override
    public void deleteProduct(String id) {
        log.info("Deleting product with ID: {}", id);
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
        log.info("Fetching product with ID: {}", id);
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
        log.info("Fetching products by category: {}", category);
        List<Product> products = productRepository.findByCategory(category);
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductApiResponse> getProductsByCategoryAndSort(String category, String sortField, String sortDirection) {
        log.info("Fetching products and sorting.");
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortField);
        List<Product> products = productRepository.findAllByCategory(category, sort);
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());

    }

    @Override
    public List<ProductApiResponse> getAvailableProducts() {
        log.info("Fetching available products.");
        List<Product> products = productRepository.findAvailableProducts();
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());
    }

    @Override
    public ProductApiResponse updateProduct(String id, ProductUpdateRequest productUpdateRequest) {
        log.info("Updating product with ID: {}", id);
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {

            Product product = optionalProduct.get();

            Optional.ofNullable(productUpdateRequest.name())
                    .filter(name -> !name.isEmpty())
                    .ifPresent(product::setName);

            Optional.ofNullable(productUpdateRequest.price())
                    .filter(price -> price > 0)
                    .ifPresent(product::setPrice);

            Optional.ofNullable(productUpdateRequest.description())
                    .filter(description -> !description.isEmpty())
                    .ifPresent(product::setDescription);

            Optional.ofNullable(productUpdateRequest.category())
                    .filter(category -> !category.isEmpty())
                    .ifPresent(product::setCategory);

            Optional.ofNullable(productUpdateRequest.available())
                    .ifPresent(product::setAvailable);

           return ProductApiResponse.map(productRepository.save(product));

        } else {
            log.info("Failed to update product with ID: {}", id);
            throw new CustomException("Product with ID: " + id + " not found", HttpStatus.NOT_FOUND);
        }

    }

    @Override
    public List<ProductApiResponse> getProductsByBrandId(String brandId) {
        log.info("Fetching products by brand with ID: {}.", brandId);
        List<Product> products = productRepository.findAllByBrandIdAndDeletedIsFalse(brandId);
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductApiResponse> getProductsByBrandName(String brandName) {
        log.info("Fetching products by brand with Name: {}.", brandName);
        List<Product> products = productRepository.findAllByBrandNameAndDeletedIsFalse(brandName);
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProductApiResponse> getProductsOfBrandByCategory(String category, String brand) {
        log.info("Fetching products by brand: {} and category {}", brand, category);
        List<Product> products = productRepository.findByCategoryAndBrandId(category,brand);
        return products.stream()
                .map(ProductApiResponse::map)
                .collect(Collectors.toList());

    }
}
