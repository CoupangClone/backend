package com.blanc.market.domain.cart.service;

import com.blanc.market.domain.cart.dto.CartRequest;
import com.blanc.market.domain.cart.dto.CartResponse;
import com.blanc.market.domain.cart.entity.Cart;
import com.blanc.market.domain.cart.mapper.CartMapper;
import com.blanc.market.domain.cart.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CartService {
    private final CartMapper cartMapper;
    private final CartRepository cartRepository;

    @Transactional
    public void addItemToCart(CartRequest cartRequest) {
        cartRepository.save(cartMapper.toEntity(cartRequest));
    }

    public List<CartResponse> getCartByUserId(Long userId) {
        List<Cart> cartItems = cartRepository.findByUserId(userId).orElseThrow();
        return cartItems.stream().map(cartMapper::from).collect(Collectors.toList());
    }

    @Transactional
    public void updateCartItemQuantity(Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId).orElseThrow();
        cart.setProductQuantity(quantity);
    }

    @Transactional
    public void removeItemFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    @Transactional
    public void removeCartByUserId(Long userId){
        cartRepository.deleteByUserId(userId);
    }
}
