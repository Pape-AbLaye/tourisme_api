package tech.laye.tourisme_api.order;


import tech.laye.tourisme_api.common.PurchaseResponse;
import tech.laye.tourisme_api.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        Integer id,
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        String userFirstname,
        String email,
        String userLastname,
        List<PurchaseResponse> products
) {
}