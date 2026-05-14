package tech.laye.tourisme_api.payment;

import org.springframework.stereotype.Service;

@Service
public class PaymentMapper {

    public Payment toPayment(PaymentRequest paymentRequest) {
        return Payment.builder()
                .orderId(paymentRequest.orderId())
                .amount(paymentRequest.amount())
                .paymentMethod(paymentRequest.paymentMethod())
                .build();
    }
}
