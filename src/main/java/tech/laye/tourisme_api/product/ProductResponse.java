package tech.laye.tourisme_api.product;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Integer id;
    private String name;
    private String description;
    private double stock;
    private ProductType productType;
    private BigDecimal price;
}
