package com.example.ProductsService.controller;

import com.example.ProductsService.model.Brand;
import com.example.ProductsService.model.Product;
import com.example.ProductsService.records.BrandApiRequest;
import com.example.ProductsService.records.ProductApiRequest;
import com.example.ProductsService.records.ProductApiResponse;
import com.example.ProductsService.service.BrandService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/api/v1/brands")
public class BrandController {
    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }
    @GetMapping
    @Operation(summary = "Fetch all brands")
    public ResponseEntity<List<Brand>> getAllBrands(){
        return new ResponseEntity<>(brandService.getAllBrands(), HttpStatus.OK);
    }

    @PostMapping(value = "/")
    @Operation(summary = "Add a new brand")
    public ResponseEntity<Brand> createBrand(@Valid @ModelAttribute BrandApiRequest brand) throws IOException {
        return new ResponseEntity<>(brandService.createProduct(brand), HttpStatus.CREATED);
    }

    @GetMapping(value ="/{id}")
    @Operation(summary = "Fetch brand by ID")
    public ResponseEntity<Brand> getBrandById(@PathVariable String id) {
        return new ResponseEntity<>(brandService.fetchBrandById(id), HttpStatus.OK);
    }

    @GetMapping(value ="/businessActivity/{businessActivity}")
    @Operation(summary = "Fetch brands by Business Activity")
    public ResponseEntity<List<Brand>> getBrandByBusinessActivity(@PathVariable String businessActivity) {
        return new ResponseEntity<>(brandService.fetchBrandsByBusinessActivity(businessActivity), HttpStatus.OK);
    }
}
