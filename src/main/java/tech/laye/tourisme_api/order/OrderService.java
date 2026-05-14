package tech.laye.tourisme_api.order;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.common.PurchaseRequest;
import tech.laye.tourisme_api.order_line.OrderLineRequest;
import tech.laye.tourisme_api.order_line.OrderLineService;
import tech.laye.tourisme_api.payment.PaymentRequest;
import tech.laye.tourisme_api.payment.PaymentService;
import tech.laye.tourisme_api.product.ProductService;
import tech.laye.tourisme_api.securityUtils.SecurityUtils;
import tech.laye.tourisme_api.user.User;
import tech.laye.tourisme_api.user.UserResponse;
import tech.laye.tourisme_api.user.UserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final OrderMapper mapper;
    private final UserService userService;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentService paymentService;

    public Integer createOrder( OrderRequest request , Authentication connectedUser) {
        String currentUserId = SecurityUtils.getCurrentUserId(connectedUser);
        UserResponse user = userService.findUserById(currentUserId);
        var purchasedProducts = this.productService.purchaseProduct(request.products());
        var order = mapper.toOrder(request);
        order.setUser(
                User.builder()
                        .id(currentUserId)
                        .build()
        );
        order = orderRepository.save(order);

        //persist order line
        for (PurchaseRequest purchaseRequest : request.products()){
            orderLineService.saveOrderLine(
                    new OrderLineRequest(
                            null,
                            order.getId(),
                            purchaseRequest.productId(),
                            purchaseRequest.quantity()
                    )
            );
        }

        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
        paymentService.createPayment(paymentRequest);

        orderProducer.sendOrderConfirmation(
                new OrderConfirmation(
                        order.getId(),
                        request.reference(),
                        request.amount(),
                        request.paymentMethod(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        purchasedProducts
                )
        );

        return order.getId();
    }

    public List<OrderResponse> findAll() {
        return orderRepository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(
                        () -> new EntityNotFoundException("order not found !")
                );
    }
}
