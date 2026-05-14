package tech.laye.tourisme_api.payment;


import java.math.BigDecimal;

public record PaymentRequest(
        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        String firstname,
        String lastname,
        String email
) {
}
