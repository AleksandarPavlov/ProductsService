package com.example.ProductsService.records;

import jakarta.validation.constraints.Size;

public record ProductUpdateRequest(@Size(min = 0, max = 50) String name ,  @Size(min = 0, max = 50) String category,  @Size(min = 0, max = 1000)String description, long price, boolean available) {
}
