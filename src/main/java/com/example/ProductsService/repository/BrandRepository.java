package com.example.ProductsService.repository;

import com.example.ProductsService.model.Brand;
import com.example.ProductsService.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BrandRepository extends MongoRepository<Brand, String> {

    List<Brand> findAllByBusinessActivity(String businessActivity);
}
