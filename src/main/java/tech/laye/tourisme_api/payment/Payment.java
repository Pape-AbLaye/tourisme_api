package tech.laye.tourisme_api.payment;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import tech.laye.tourisme_api.common.BaseAuditingEntity;
import tech.laye.tourisme_api.notification.Notification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Payment  extends BaseAuditingEntity {

     @Id
     @GeneratedValue
     private Integer id;
     private BigDecimal amount;
     private Integer orderId;
     @Enumerated(EnumType.STRING)
     private PaymentMethod paymentMethod;

     @OneToMany(mappedBy = "payment")
     private List<Notification> notifications;

}
