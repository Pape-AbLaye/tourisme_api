package tech.laye.tourisme_api.order;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.laye.tourisme_api.common.BaseAuditingEntity;
import tech.laye.tourisme_api.order_line.OrderLine;
import tech.laye.tourisme_api.user.User;

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
    private Long id;

    private int totalAmount;
    private LocalDateTime date;
    private String status;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
