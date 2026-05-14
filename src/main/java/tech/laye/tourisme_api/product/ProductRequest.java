package tech.laye.tourisme_api.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductRequest(
        @NotEmpty(message = "Title cannot be empty")
        String name,
        String description,
        @NotEmpty(message = "product's type cannot be empty")
        ProductType productType,
        @NotNull(message = "price is required")
        BigDecimal price,
        @NotNull(message = "stock is required")
        double stock

) {
}
