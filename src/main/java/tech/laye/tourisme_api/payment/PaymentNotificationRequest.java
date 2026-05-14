package tech.laye.tourisme_api.payment;


import java.math.BigDecimal;

public record PaymentNotificationRequest(
        Integer id,
        String orderReference,
        BigDecimal amount,
        PaymentMethod paymentMethod,
        String customerFirstname,
        String customerLastname,
        String customerEmail
) {
}
