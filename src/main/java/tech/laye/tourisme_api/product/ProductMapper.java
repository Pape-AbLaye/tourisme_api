package tech.laye.tourisme_api.product;

import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.common.PurchaseResponse;

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

    public PurchaseResponse toProductPurchaseResponse(Product product, double quantity) {
        return  new PurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
}
