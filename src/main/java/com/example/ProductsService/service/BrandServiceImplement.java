package com.example.ProductsService.service;

import com.example.ProductsService.exceptions.CustomException;
import com.example.ProductsService.model.Brand;
import com.example.ProductsService.records.BrandApiRequest;
import com.example.ProductsService.repository.BrandRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BrandServiceImplement implements BrandService {
    private static final Logger log = LoggerFactory.getLogger(ProductServiceImplement.class);
    private final BrandRepository brandRepository;
    private final ImageService imageService;

    public BrandServiceImplement(BrandRepository brandRepository, ImageService imageService) {
        this.brandRepository = brandRepository;
        this.imageService = imageService;
    }

    @Override
    public List<Brand> getAllBrands() {
        log.info("Fetching all brands.");
        return brandRepository.findAll();
    }

    @Override
    public Brand createProduct(BrandApiRequest brand) throws IOException {
        log.info("Adding a new brand with name: {}", brand.name());

        if (brand.image() == null || brand.image().isEmpty()) {
            throw new IllegalArgumentException("Image cannot be empty");
        }

        Brand newBrand = new Brand();
        newBrand.setName(brand.name());
        newBrand.setBusinessActivity(brand.businessActivity());
        newBrand.setEstablishmentYear(brand.establishmentYear());
        newBrand.setLogoImageName(brand.image().getOriginalFilename());
        Brand createdBrand = brandRepository.save(newBrand);
        imageService.storeBrandImage(brand.image(), createdBrand.getId());
        return createdBrand;
    }

    @Override
    public Brand fetchBrandById(String id) {
        log.info("Attempting to retrieve brand with ID: {}", id);
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if (brandOptional.isPresent()) {
            return brandOptional.get();
        } else {
            log.error("Error retrieving brand with ID: {}", id);
            throw new CustomException("Brand with ID: {} not found" + id, HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public List<Brand> fetchBrandsByBusinessActivity(String businessActivity) {
        return brandRepository.findAllByBusinessActivity(businessActivity);
    }
}
