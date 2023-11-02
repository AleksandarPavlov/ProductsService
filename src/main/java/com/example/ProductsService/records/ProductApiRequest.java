package com.example.ProductsService.records;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public record ProductApiRequest(@NotBlank(message = "Name cannot be null") @Size(min = 0, max = 30) String name, @NotBlank(message = "Description cannot be blank")  @Size(min = 0, max = 1000) String description, @NotBlank(message = "Category cannot be blank")  @Size(min = 0, max = 30)String category, @NotNull(message = "Price cannot be null") Long price, @NotNull(message = "Availability cannot be null") Boolean available, @NotNull(message = "Image cannot be null") MultipartFile image) {
}
