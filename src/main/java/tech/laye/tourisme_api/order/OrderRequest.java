package tech.laye.tourisme_api.order;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import tech.laye.tourisme_api.common.PurchaseRequest;
import tech.laye.tourisme_api.payment.PaymentMethod;

import java.math.BigDecimal;
import java.util.List;

public record OrderRequest(
        String reference,
        @Positive(message = "Order amount should be positive")
        BigDecimal amount,
        @NotNull(message = "payment method should be precised")
        PaymentMethod paymentMethod,
        @NotEmpty(message = "you should at least purchase one product")
        List<PurchaseRequest> products

) {
}
