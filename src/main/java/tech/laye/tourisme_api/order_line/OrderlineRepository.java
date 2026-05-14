package tech.laye.tourisme_api.order_line;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;

public interface OrderlineRepository extends JpaRepository<OrderLine , Integer> {
    List<OrderLine> findAllByOrderId(Integer orderId);
}
