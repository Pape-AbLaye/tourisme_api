package tech.laye.tourisme_api.product;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.laye.tourisme_api.common.BaseAuditingEntity;
import tech.laye.tourisme_api.order_line.OrderLine;
import tech.laye.tourisme_api.user.User;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
public class Product extends BaseAuditingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private double stock;
    @Enumerated(EnumType.STRING)
    private ProductType productType;
    private BigDecimal price;
    @Column(nullable = false)
    private boolean isHidden = false;
    //todo photo


    @OneToMany(mappedBy = "product")
    private List<OrderLine> orderLines;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
