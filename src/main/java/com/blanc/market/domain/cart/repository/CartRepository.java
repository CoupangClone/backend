package com.blanc.market.domain.cart.repository;

import com.blanc.market.domain.cart.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<List<Cart>> findByUserId(Long userId);
    void deleteByUserId(Long userID);
}
