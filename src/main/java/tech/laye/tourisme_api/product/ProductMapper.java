package tech.laye.tourisme_api.product;

import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.circuit.Circuit;
import tech.laye.tourisme_api.circuit.CircuitRequest;

@Service
public class ProductMapper {

    public ProductResponse toProductResponse(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .stock(product.getStock())
                .price(product.getPrice())
                .productType(product.getProductType())
                .description(product.getDescription())
                .build();
    }

    public Product toProduct(ProductRequest productRequest) {
        return Product.builder()
                .name(productRequest.name())
                .stock(productRequest.stock())
                .price(productRequest.price())
                .productType(productRequest.productType())
                .description(productRequest.description())
                .build();
    }
}
