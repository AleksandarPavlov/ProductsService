package com.example.ProductsService.records;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public record BrandApiRequest(@NotBlank(message = "Name cannot be blank")  @Length(min = 0, max = 40) String name, @NotBlank(message = "Business activity cannot be blank")  @Length(min = 0, max = 60) String businessActivity, @NotNull(message = "Establishment year cannot be blank") LocalDate establishmentYear, @NotNull(message = "Image cannot be null") MultipartFile image) {
}
