package com.blanc.market.domain.order.service;

import com.blanc.market.domain.order.dto.OrderProductRequest;
import com.blanc.market.domain.order.dto.OrderProductResponse;
import com.blanc.market.domain.order.dto.OrderRequest;
import com.blanc.market.domain.order.dto.OrderResponse;
import com.blanc.market.domain.order.entity.Order;
import com.blanc.market.domain.order.entity.OrderProduct;
import com.blanc.market.domain.order.entity.OrderStatus;
import com.blanc.market.domain.order.mapper.OrderMapper;
import com.blanc.market.domain.order.mapper.OrderProductMapper;
import com.blanc.market.domain.order.repository.OrderProductRepository;
import com.blanc.market.domain.order.repository.OrderRepository;
import com.blanc.market.domain.product.entity.Product;
import com.blanc.market.domain.product.repository.ProductRepository;
import com.blanc.market.domain.product.service.ProductService;
import com.blanc.market.domain.user.entity.User;
import com.blanc.market.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;
    private final OrderMapper orderMapper;
    private final OrderProductMapper orderProductMapper;

    //주문
    @Transactional
    public OrderResponse order(OrderRequest dto){

        //주문한 사용자 엔티티 조회
        User user = userRepository.findUserById(dto.getUserId()).orElseThrow(NoSuchElementException::new);

        double totalPrice = 0;

        //주문 생성
        Order order = Order.createOrder(user, totalPrice);
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        List<OrderProductRequest> products = dto.getProducts();
        for (OrderProductRequest item : products){
            Long productId = item.getProductId();
            int quantity = item.getQuantity();

            Product product = productRepository.findById(productId).orElseThrow(NoSuchElementException::new);
            double productPrice = product.getPrice();

            //totalprice 계산
            totalPrice += productPrice * quantity;

            //주문상품 생성
            OrderProduct orderProduct = OrderProduct.createOrderProduct(product,order,quantity);
            orderProductRepository.save(orderProduct);

        }
        //total price
        order.setTotalPrice(totalPrice);

        //주문 저장
        orderRepository.save(order);

        return orderMapper.toDto(order);

    }

    //주문 취소
    @Transactional
    public void cancleOrders(Long id){
        getEntity(id).cancelOrder();
    }


    public List<OrderProductResponse> getProductForOrder(Long OrderId){
        Order order = orderRepository.findById(OrderId).orElseThrow();
        List<OrderProduct> orderProducts = orderProductRepository.findAllByOrder(order);
        log.info("test {}",orderProducts);
        return orderProducts.stream().map(orderProductMapper::toDto).toList();
    }


    //본인 주문 조회
    public List<OrderResponse> findAllMyOrders(){

        //User_temp loginUser = authService.getLoginUser();
        Long testId = Long.valueOf(1);

        User loginUser = userRepository.findUserById(testId).orElseThrow(NoSuchElementException::new);
        return orderRepository.findAllByUser(loginUser).stream()
                .map(orderMapper::toDto).toList();
    }


    public Order getEntity(Long id){
        return orderRepository.findById(id).get();  //예외처리 추가햐야함
    }
}
