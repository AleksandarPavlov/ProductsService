package com.example.ProductsService.repository;

import com.example.ProductsService.model.Product;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {
    @Query("{'deleted': false}")
    List<Product> findActiveProducts();
    @Query("{'category': {$regex: ?0, $options: 'i'}}")
    List<Product> findByCategory(String category);
    List<Product> findAllByCategory(String category, Sort sort);
    @Query("{'deleted': false, 'available': true}")
    List<Product> findAvailableProducts();
    List<Product> findAllByBrandIdAndDeletedIsFalse(String brandId);
    @Query("{'brandName': {$regex: ?0, $options: 'i'}}")
    List<Product> findAllByBrandNameAndDeletedIsFalse(String brandId);
    @Query("{'category': {$regex: ?0, $options: 'i'}, 'brandId': ?1, 'deleted': false}")
    List<Product> findByCategoryAndBrandId(String category, String brandId);

}
