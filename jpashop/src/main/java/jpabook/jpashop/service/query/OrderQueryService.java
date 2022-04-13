package jpabook.jpashop.service.query;

import jpabook.jpashop.api.OrderApiController;
import jpabook.jpashop.domain.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Transactional(readOnly = true)
public class OrderQueryService {

//    @GetMapping("/api/v3/orders")
//    public List<OrderApiController.OrderDto> orderV3() {
//        List<Order> orders = orderRepository.findAllWithItem();
//
//        List<OrderApiController.OrderDto> result = orders.stream()
//                .map(o -> new OrderApiController.OrderDto(o))
//                .collect(Collectors.toList());
//        return result;
//    }
}
