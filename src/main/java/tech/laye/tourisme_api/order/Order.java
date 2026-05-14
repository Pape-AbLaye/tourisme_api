package tech.laye.tourisme_api.order;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.laye.tourisme_api.common.BaseAuditingEntity;
import tech.laye.tourisme_api.notification.Notification;
import tech.laye.tourisme_api.order_line.OrderLine;
import tech.laye.tourisme_api.payment.PaymentMethod;
import tech.laye.tourisme_api.user.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "orders")
public class Order extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private BigDecimal totalAmount;
    private String reference;
    private String status;
    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;

    @OneToMany(mappedBy = "order")
    private List<Notification> notifications;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
