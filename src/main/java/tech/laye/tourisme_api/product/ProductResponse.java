package tech.laye.tourisme_api.product;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private int stock;
    private ProductType productType;
    private Long price;
}
