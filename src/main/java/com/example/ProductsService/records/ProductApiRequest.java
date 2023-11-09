package com.example.ProductsService.records;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

public record ProductApiRequest(@NotBlank(message = "Name cannot be null") @Length(min = 0, max = 30) String name, @NotBlank(message = "Description cannot be blank")  @Length(min = 0, max = 600) String description, @Length(min = 0, max = 40) String brandId,  @Length(min = 0, max = 40) String brandName, @NotBlank(message = "Category cannot be blank")  @Length(min = 0, max = 30)String category, @NotNull(message = "Price cannot be null") Long price, @NotNull(message = "Availability cannot be null") Boolean available, @NotNull(message = "Image cannot be null") MultipartFile image,@NotBlank(message = "Url cannot be null") @Length(min = 0, max = 100) String imageUrl ) {
}
