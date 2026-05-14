package tech.laye.tourisme_api.order;

import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.user.User;

@Service
public class OrderMapper {

    public Order toOrder(OrderRequest request) {
        return Order
                .builder()
                .totalAmount(request.amount())
                .reference(request.reference())
                .paymentMethod(request.paymentMethod())
                .build();
    }

    public OrderResponse fromOrder(Order order) {

        return new OrderResponse(
                order.getId(),
                order.getReference(),
                order.getTotalAmount(),
                order.getPaymentMethod(),
                order.getUser().getId()
        );
    }
}