package io.sovann.hang.api.features.carts.services;

import io.sovann.hang.api.configs.CartRabbitMQConfig;
import io.sovann.hang.api.exceptions.ResourceNotFoundException;
import io.sovann.hang.api.features.carts.entities.Cart;
import io.sovann.hang.api.features.carts.entities.CartFood;
import io.sovann.hang.api.features.carts.payloads.requests.CartFoodMutateRequest;
import io.sovann.hang.api.features.carts.payloads.requests.CreateCartFoodRequest;
import io.sovann.hang.api.features.carts.payloads.responses.CartFoodResponse;
import io.sovann.hang.api.features.carts.repos.CartFoodRepository;
import io.sovann.hang.api.features.carts.repos.CartRepository;
import io.sovann.hang.api.features.foods.entities.Food;
import io.sovann.hang.api.features.foods.services.FoodServiceImpl;
import io.sovann.hang.api.features.tables.entities.Table;
import io.sovann.hang.api.features.tables.services.TableServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartFoodServiceImpl {
    private final FoodServiceImpl foodService;
    private final CartServiceImpl cartService;
    private final TableServiceImpl tableService;
    private final CartFoodRepository cartFoodRepository;
    private final RabbitTemplate rabbitTemplate;
    private final CartRepository cartRepository;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "cart-foods", key = "#request.cartId"),
            @CacheEvict(value = "cart-food", key = "#request.cartId")
    })
    public CartFoodResponse createCart(CreateCartFoodRequest request) {
        try {
            rabbitTemplate.convertAndSend(
                    CartRabbitMQConfig.QUEUE_NAME, request);
            CartFoodResponse response = new CartFoodResponse();
            response.setCartId(request.getCartId());
            response.setFoodId(request.getFoodId());
            response.setQuantity(request.getQuantity());
            response.setSpecialRequests(request.getSpecialRequests());
            return response;
        } catch (Exception e) {
            log.error("Error creating cart food", e);
            return null;
        }
    }

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void createCartFood(CreateCartFoodRequest request) {
        Cart cart = cartService.getCartEntityById(request.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("Cart", request.getCartId().toString()));
        Food food = foodService.getFoodById(request.getFoodId())
                .orElseThrow(() -> new ResourceNotFoundException("Food", request.getFoodId().toString()));
        CartFood cartFood = new CartFood();
        cartFood.setCart(cart);
        cartFood.setFood(food);
        cartFood.setQuantity(request.getQuantity());
        cartFood.setSpecialRequests(request.getSpecialRequests());
        cartFoodRepository.save(cartFood);
    }

    @Cacheable(value = "cart-foods", key = "#cartId")
    public List<CartFoodResponse> getCartFoodOfCart(UUID cartId) {
        List<CartFood> cartFoods = cartFoodRepository.findAllByCartId(cartId);
        return CartFoodResponse.fromEntities(cartFoods);
    }

    public List<CartFoodResponse> getCartFoodOfTable(UUID tableId) {
        Table table = tableService.findTableEntityById(tableId)
                .orElseThrow(() -> new ResourceNotFoundException("Table", tableId.toString()));
        List<Cart> carts = cartRepository.findAllByTableId(table.getId());
        List<CartFood> foodCarts = cartFoodRepository.findAllByCartIn(carts);
        return CartFoodResponse.fromEntities(foodCarts);
    }

    @Caching(evict = {
            @CacheEvict(value = "cart-foods", key = "#request.cartId"),
            @CacheEvict(value = "cart-food", key = "#request.cartId")
    })
    public CartFoodResponse deleteCartFood(CartFoodMutateRequest request) {
        CartFood cartFood = cartFoodRepository.findByIdAndCartId(request.getCartFoodId(), request.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("CartFood and Cart", request.getCartFoodId().toString()
                        + " and " + request.getCartId().toString()));
        cartFoodRepository.delete(cartFood);
        return CartFoodResponse.fromEntity(cartFood);
    }

    @Cacheable(value = "cart-food", key = "#request.cartId")
    public CartFoodResponse getCartFoodById(CartFoodMutateRequest request) {
        CartFood cartFood = cartFoodRepository.findByIdAndCartId(request.getCartFoodId(), request.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("CartFood and Cart", request.getCartFoodId().toString()
                        + " and " + request.getCartId().toString()));
        return CartFoodResponse.fromEntity(cartFood);
    }

    @Caching(evict = {
            @CacheEvict(value = "cart-foods", key = "#request.cartId"),
            @CacheEvict(value = "cart-food", key = "#request.cartId")
    })
    public CartFoodResponse updateCartFood(CartFoodMutateRequest request) {
        CartFood cartFood = cartFoodRepository.findByIdAndCartId(request.getCartFoodId(), request.getCartId())
                .orElseThrow(() -> new ResourceNotFoundException("CartFood and Cart", request.getCartFoodId().toString()
                        + " and " + request.getCartId().toString()));
        cartFood.setQuantity(request.getQuantity());
        cartFood.setSpecialRequests(request.getSpecialRequests());
        cartFoodRepository.save(cartFood);
        return CartFoodResponse.fromEntity(cartFood);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "cart-foods", key = "#cartId"),
            @CacheEvict(value = "cart-food", key = "#cartId")
    })
    public void deleteCartFoodOfCart(UUID cartId) {
        cartFoodRepository.deleteAllByCartId(cartId);
    }
}
