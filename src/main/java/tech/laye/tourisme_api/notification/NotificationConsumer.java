package tech.laye.tourisme_api.notification;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.laye.tourisme_api.email.EmailService;
import tech.laye.tourisme_api.order.Order;
import tech.laye.tourisme_api.order.OrderConfirmation;
import tech.laye.tourisme_api.payment.Payment;
import tech.laye.tourisme_api.payment.PaymentNotificationRequest;
import tech.laye.tourisme_api.payment.PaymentService;

import java.time.LocalDateTime;

import static java.lang.String.format;


@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationRepository repository;
    private final PaymentService paymentService;

    private final EmailService emailService;
    @KafkaListener(topics = "payment-topic")
    public void consumePaymentSuccessNotifications(PaymentNotificationRequest paymentConfirmation) throws RuntimeException, MessagingException {
        log.info(format("Consuming the message from payment-topic Topic:: %s", paymentConfirmation));
        repository.save(
                Notification
                        .builder()
                        .notificationType(NotificationType.PAYMENT_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .payment(Payment.builder().id(paymentConfirmation.id()).build())
                        .build()
        );

        var customerName = paymentConfirmation.customerFirstname() + " " + paymentConfirmation.customerLastname();
        emailService.sentPaymentSuccessEmail(
                paymentConfirmation.customerEmail(),
                customerName,
                paymentConfirmation.amount(),
                paymentConfirmation.orderReference()
        );
    }

    @KafkaListener(topics = "order-topic")
    public void consumeOrderConfirmationNotifications(OrderConfirmation orderConfirmation) throws MessagingException {
        log.info(format("Consuming the message from order-topic Topic:: %s", orderConfirmation));
        repository.save(
                Notification
                        .builder()
                        .notificationType(NotificationType.ORDER_CONFIRMATION)
                        .notificationDate(LocalDateTime.now())
                        .order(Order.builder().id(orderConfirmation.id()).build())
                        .build()
        );
        var customerName = orderConfirmation.userFirstname() + " " + orderConfirmation.userLastname();
        emailService.sentOrderConfirmationEmail(
                orderConfirmation.email(),
                customerName,
                orderConfirmation.totalAmount(),
                orderConfirmation.orderReference(),
                orderConfirmation.products()
        );
    }
}

