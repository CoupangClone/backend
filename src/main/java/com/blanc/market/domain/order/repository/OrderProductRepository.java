package com.blanc.market.domain.order.repository;


import com.blanc.market.domain.order.entity.Order;
import com.blanc.market.domain.order.entity.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {
    List<OrderProduct> findAllByOrder(Order order);
}
