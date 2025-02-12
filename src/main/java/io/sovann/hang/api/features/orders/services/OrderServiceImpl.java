package io.sovann.hang.api.features.orders.services;

import io.sovann.hang.api.exceptions.ResourceNotFoundException;
import io.sovann.hang.api.features.carts.services.CartFoodServiceImpl;
import io.sovann.hang.api.features.foods.entities.Food;
import io.sovann.hang.api.features.foods.services.FoodServiceImpl;
import io.sovann.hang.api.features.orders.entities.Order;
import io.sovann.hang.api.features.orders.entities.OrderFood;
import io.sovann.hang.api.features.orders.payloads.requests.CreateOrderFoodRequest;
import io.sovann.hang.api.features.orders.payloads.requests.CreateOrderRequest;
import io.sovann.hang.api.features.orders.payloads.responses.OrderResponse;
import io.sovann.hang.api.features.orders.repos.OrderFoodRepository;
import io.sovann.hang.api.features.orders.repos.OrderRepository;
import io.sovann.hang.api.features.tables.entities.Table;
import io.sovann.hang.api.features.tables.services.TableServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl {
    private final FoodServiceImpl foodService;
    private final TableServiceImpl tableService;
    private final CartFoodServiceImpl cartFoodService;
    private final OrderRepository orderRepository;
    private final OrderFoodRepository orderFoodRepository;

    public OrderResponse createOrder(CreateOrderRequest request) {
        List<CreateOrderFoodRequest> orderFoodsRequest = extractOrderFoodRequests(request.getCartId());
        if (orderFoodsRequest.isEmpty()) {
            throw new IllegalArgumentException("Cannot create an order without food items.");
        }
        Table table = tableService.findTableEntityById(request.getTableId())
                .orElseThrow(() -> new ResourceNotFoundException("Table", request.getTableId().toString()));
        List<OrderFood> orderFoods = createOrderFoods(orderFoodsRequest);
        if (orderFoods.isEmpty()) {
            throw new IllegalArgumentException("No valid food items found to create an order.");
        }
        double totalAmount = calculateTotalAmount(orderFoods);
        Order order = new Order();
        order.setTable(table);
        order.setStatus(request.getStatus());
        order.setOrderTime(request.getOrderTime());
        order.setTotalAmount(totalAmount);
        order.setSpecialInstructions(request.getSpecialInstructions());
        order.setOrderFoods(orderFoods); // Set foods before saving
        Order savedOrder = orderRepository.save(order);
        orderFoods.forEach(orderFood -> orderFood.setOrder(savedOrder));
        List<OrderFood> savedFoods = orderFoodRepository.saveAll(orderFoods);
        // Delete food from cart if success
        if (!savedFoods.isEmpty()) {
            cartFoodService.deleteCartFoodOfCart(request.getCartId());
        }
        return OrderResponse.fromEntity(savedOrder);
    }

    private List<CreateOrderFoodRequest> extractOrderFoodRequests(UUID cartId) {
        return cartFoodService.getCartFoodOfCart(cartId).stream()
                .map(cartFood -> CreateOrderFoodRequest.builder()
                        .foodId(cartFood.getFoodId())
                        .quantity(cartFood.getQuantity())
                        .specialRequests(cartFood.getSpecialRequests())
                        .build())
                .toList();
    }

    private List<OrderFood> createOrderFoods(List<CreateOrderFoodRequest> requests) {
        return requests.stream()
                .map(this::createOrderFood)
                .filter(Optional::isPresent) // Filter out invalid items
                .map(Optional::get)
                .toList();
    }

    private Optional<OrderFood> createOrderFood(CreateOrderFoodRequest request) {
        try {
            Food food = foodService.getFoodById(request.getFoodId())
                    .orElseThrow(() -> new ResourceNotFoundException("Food", request.getFoodId().toString()));

            return Optional.of(new OrderFood(food, request.getQuantity(), food.getPrice(), request.getSpecialRequests()));
        } catch (ResourceNotFoundException e) {
            log.warn("Skipping unavailable food item: {}", e.getMessage());
            return Optional.empty();
        } catch (Exception e) {
            log.error("Error processing order food: {}", e.getMessage(), e);
            return Optional.empty();
        }
    }

    private double calculateTotalAmount(List<OrderFood> orderFoods) {
        return orderFoods.stream()
                .mapToDouble(orderFood -> orderFood.getQuantity() * orderFood.getUnitPrice())
                .sum();
    }
}
