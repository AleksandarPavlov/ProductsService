package com.example.ProductsService.service;

import com.example.ProductsService.model.Brand;
import com.example.ProductsService.records.BrandApiRequest;

import java.io.IOException;
import java.util.List;

public interface BrandService {
   List<Brand> getAllBrands();

    Brand createProduct(BrandApiRequest brand) throws IOException;

    Brand fetchBrandById(String id);

    List<Brand> fetchBrandsByBusinessActivity(String businessActivity);
}
