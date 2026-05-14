package tech.laye.tourisme_api.order_line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderLineService {

    private final OrderlineRepository orderlineRepository;
    private final OrderLineMapper mapper;

    public Integer saveOrderLine(OrderLineRequest request) {
        var order = mapper.toOrderLine(request);
        return orderlineRepository.save(order).getId();

    }

    public List<OrderLineResponse> findAllByOrderId(Integer orderId) {
        return  orderlineRepository.findAllByOrderId(orderId).stream()
                .map(mapper::toOrderLineResponse)
                .collect(Collectors.toList());
    }
}
