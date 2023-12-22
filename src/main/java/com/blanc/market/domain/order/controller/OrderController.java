package com.blanc.market.domain.order.controller;


import com.blanc.market.domain.order.dto.OrderProductResponse;
import com.blanc.market.domain.order.dto.OrderRequest;
import com.blanc.market.domain.order.dto.OrderResponse;
import com.blanc.market.domain.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Order", description = "Order API Document")
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "주문 생성", description = "주문 생성 메서드입니다.")
    @PostMapping("/api/orders")
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody @Valid OrderRequest dto){
        System.out.println(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderService.order(dto));
    }

    @Operation(summary = "주문 정보 조회", description = "주문에 담긴 상품id 리스트를 반환합니다.")
    @GetMapping("api/orders/{id}")
    public ResponseEntity<List<OrderProductResponse>> getOrder(@PathVariable Long id){
        return ResponseEntity.ok(orderService.getProductForOrder(id));
    }




    //주문 상태 취소로 변경
    @Operation(summary = "주문 취소", description = "주문 취소 메서드입니다. 주문 상태를 cancle로 변경합니다.")
    @PostMapping("api/orders/{id}/cancle")
    public ResponseEntity<Void> cancleOrders(@PathVariable Long id){
        orderService.cancleOrders(id);
        return ResponseEntity.ok().build();
    }

    //완전 삭제도 필요?


    //사용자 주문 조회
    @Operation(summary = "현재 사용자 주문 조회", description = "주문 조회 메서드입니다. 현재 사용자의 주문 리스트를 조회합니다.")
    @GetMapping("api/orders/my-orders")
    public ResponseEntity<List<OrderResponse>> getMyOrders(){
        return ResponseEntity.ok(orderService.findAllMyOrders());
    }



}
