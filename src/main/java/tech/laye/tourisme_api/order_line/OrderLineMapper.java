package tech.laye.tourisme_api.order_line;

import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.order.Order;
import tech.laye.tourisme_api.product.Product;

@Service
public class OrderLineMapper {
    public OrderLine toOrderLine(OrderLineRequest request) {
        return OrderLine
                .builder()
                .id(request.id())
                .order(
                        Order
                                .builder()
                                .id(request.orderId())
                                .build()
                )
                .product(
                        Product.builder()
                                .id(request.productId()).build()
                )
                .quantity(request.quantity())
                .build();
    }

    public OrderLineResponse toOrderLineResponse(OrderLine orderLine) {
        return new OrderLineResponse(orderLine.getId(),orderLine.getQuantity());
    }
}
