package tech.laye.tourisme_api.order_line;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderlineRepository extends JpaRepository<OrderLine , Long> {
}
