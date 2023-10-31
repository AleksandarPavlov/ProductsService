package com.example.ProductsService.model;

import com.example.ProductsService.exceptions.CustomException;
import org.springframework.http.HttpStatus;

public enum SortDirection {
        ASC, DESC;

        public static SortDirection fromString(String value) {
            try {
                return SortDirection.valueOf(value.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new CustomException("Invalid sort direction: " + value, HttpStatus.BAD_REQUEST);
            }
        }
    }

