package tech.laye.tourisme_api.site;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.SuperBuilder;
import tech.laye.tourisme_api.common.BaseAuditingEntity;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@SuperBuilder
@Table(name = "tourite_sites")
public class TouristeSite extends BaseAuditingEntity {

    @Id
    @GeneratedValue
    private String id;
    private String name;
    private String description;
    private String location;
    private LocalDateTime openingHours;
    private long price;
}
