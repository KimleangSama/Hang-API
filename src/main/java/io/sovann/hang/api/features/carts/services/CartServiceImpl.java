package io.sovann.hang.api.features.carts.services;

import io.sovann.hang.api.exceptions.*;
import io.sovann.hang.api.features.carts.entities.*;
import io.sovann.hang.api.features.carts.payloads.requests.*;
import io.sovann.hang.api.features.carts.payloads.responses.*;
import io.sovann.hang.api.features.carts.repos.*;
import io.sovann.hang.api.features.tables.entities.*;
import io.sovann.hang.api.features.tables.repos.*;
import io.sovann.hang.api.features.users.entities.*;
import java.util.*;
import lombok.*;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class CartServiceImpl {
    private final CartRepository cartRepository;
    private final TableRepository tableRepository;

    @Transactional
    @CacheEvict(value = "carts", key = "#user.id")
    public CartResponse createCart(User user, CreateCartRequest request) {
        Table table = tableRepository.findById(request.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table", request.getTableId().toString()));
        Cart cart = new Cart();
        cart.setTable(table);
        if (user != null) {
            cart.setCreatedBy(user.getId());
        }
        cartRepository.save(cart);
        return CartResponse.fromEntity(cart);
    }

    @Transactional
    @Cacheable(value = "carts", key = "#user.id")
    public CartResponse getCart(User user) {
        Cart cart = cartRepository.findByCreatedBy(user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart", user.getId().toString()));
        return CartResponse.fromEntity(cart);
    }

    @Transactional
    @Cacheable(value = "carts", key = "#cartId")
    public CartResponse getCartById(UUID cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", cartId.toString()));
        return CartResponse.fromEntity(cart);
    }

    @Transactional
    @Cacheable(value = "entities", key = "#cartId")
    public Optional<Cart> getCartEntityById(UUID cartId) {
        return cartRepository.findById(cartId);
    }
}
