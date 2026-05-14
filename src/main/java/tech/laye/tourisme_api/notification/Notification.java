package tech.laye.tourisme_api.notification;

import jakarta.persistence.*;
import lombok.*;
import tech.laye.tourisme_api.order.Order;
import tech.laye.tourisme_api.payment.Payment;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class Notification {

    @Id
    @GeneratedValue
    private String id;
    private NotificationType notificationType;
    private LocalDateTime notificationDate;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "payment_id")
    private Payment payment;
}
