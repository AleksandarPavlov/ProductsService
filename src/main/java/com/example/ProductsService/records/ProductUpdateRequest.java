package com.example.ProductsService.records;

import org.hibernate.validator.constraints.Length;

public record ProductUpdateRequest(@Length(min = 0, max = 50) String name ,@Length(min = 0, max = 50) String category, @Length(min = 0, max = 600)String description, long price, boolean available) {
}
