package com.example.ProductsService.controller;

import com.example.ProductsService.model.Product;
import com.example.ProductsService.model.SortDirection;
import com.example.ProductsService.records.ProductApiRequest;
import com.example.ProductsService.records.ProductApiResponse;
import com.example.ProductsService.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @Operation(summary = "Fetch all products")
    public ResponseEntity<List<ProductApiResponse>> getAllProducts(){
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }
    @GetMapping("/active")
    @Operation(summary = "Fetch non-deleted products")
    public ResponseEntity<List<ProductApiResponse>> getActiveProducts(){
        return new ResponseEntity<>(productService.getActiveProducts(), HttpStatus.OK);
    }
    @GetMapping("/{id}")
    @Operation(summary = "Fetch a product by ID")
    public ResponseEntity <ProductApiResponse> getProductById(@PathVariable String id){
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }
    @GetMapping("/available")
    @Operation(summary = "Fetch all available products")
    public ResponseEntity <List<ProductApiResponse>> getAvailableProducts(){
        return new ResponseEntity<>(productService.getAvailableProducts(), HttpStatus.OK);
    }
    @GetMapping("/category/{category}")
    @Operation(summary = "Fetch products by category")
    public ResponseEntity <List<ProductApiResponse>> getProductsByCategory(@PathVariable String category){
        return new ResponseEntity<>(productService.getProductsByCategory(category), HttpStatus.OK);
    }
    @GetMapping("/sorted")
    @Operation(summary = "Sort products")
    public ResponseEntity<List<ProductApiResponse>> getProductsByCategoryAndSort(
            @RequestParam String category,
            @RequestParam(defaultValue = "price") String sortField,
            @RequestParam(defaultValue = "asc") String sortDirection) {
            SortDirection direction = SortDirection.fromString(sortDirection);
            List<ProductApiResponse> products = productService.getProductsByCategoryAndSort(category, sortField, sortDirection);
            return new ResponseEntity<>(products, HttpStatus.OK);
    }
    @PostMapping(value = "/")
    @Operation(summary = "Add a new product")
    public ResponseEntity<Product> createProduct( @Valid @RequestBody ProductApiRequest product){
        return new ResponseEntity<>(productService.createProduct(product), HttpStatus.CREATED);
    }
    @DeleteMapping(value = "/{id}")
    @Operation(summary = "Softly delete product")
    public ResponseEntity<String> deleteProduct(@PathVariable String id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product deleted successfully.", HttpStatus.OK);
    }


}
