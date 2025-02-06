package io.sovann.hang.api.features.carts.services;

import io.sovann.hang.api.exceptions.*;
import io.sovann.hang.api.features.carts.entities.*;
import io.sovann.hang.api.features.carts.payloads.requests.*;
import io.sovann.hang.api.features.carts.payloads.responses.*;
import io.sovann.hang.api.features.carts.repos.*;
import io.sovann.hang.api.features.foods.entities.*;
import io.sovann.hang.api.features.foods.services.*;
import io.sovann.hang.api.features.users.entities.*;
import lombok.*;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class CartFoodServiceImpl {
    private final FoodServiceImpl foodService;
    private final CartServiceImpl cartService;
    private final CartFoodRepository cartFoodRepository;

    @Transactional
    @CacheEvict(value = "carts", key = "#request.cartId")
    public CartFoodResponse createCart(User user, CreateCartFoodRequest request) {
        Cart cart = cartService.getCartById(user, request.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart", request.getCartId().toString()));
        Food food = foodService.getFoodById(request.getFoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Food", request.getFoodId().toString()));
        CartFood cartFood = new CartFood();
        cartFood.setCart(cart);
        cartFood.setFood(food);
        cartFood.setQuantity(request.getQuantity());
        cartFood.setSpecialRequests(request.getSpecialRequests());
        cartFoodRepository.save(cartFood);
        return CartFoodResponse.fromEntity(cartFood);
    }
}
