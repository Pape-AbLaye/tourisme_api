package tech.laye.tourisme_api.product;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ProductRequest(
        @NotEmpty(message = "Title cannot be empty")
        String name,
        String description,
        @NotEmpty(message = "product's type cannot be empty")
        ProductType productType,
        @NotNull(message = "price is required")
        Long price,
        @NotNull(message = "stock is required")
        int stock

) {
}
