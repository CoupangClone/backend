package com.blanc.market.domain.cart.controller;

import com.blanc.market.domain.cart.dto.CartRequest;
import com.blanc.market.domain.cart.dto.CartResponse;
import com.blanc.market.domain.cart.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Void> addItemToCart(@RequestBody @Valid CartRequest cartRequest) {
        cartService.addItemToCart(cartRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<CartResponse>> getCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCartItemQuantity(@PathVariable Long id, @RequestParam int quantity) {
        cartService.updateCartItemQuantity(id, quantity);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeItemFromCart(@PathVariable Long id) {
        cartService.removeItemFromCart(id);
        return ResponseEntity.noContent().build();
    }


    //userid로 장바구니 삭제
    @DeleteMapping("delete/{userId}")
    public ResponseEntity<Void> removeCart(@PathVariable Long userId){
        cartService.removeCartByUserId(userId);
        return ResponseEntity.noContent().build();
    }

}
