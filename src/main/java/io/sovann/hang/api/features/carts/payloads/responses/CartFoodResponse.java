package io.sovann.hang.api.features.carts.payloads.responses;

import io.sovann.hang.api.features.carts.entities.*;
import io.sovann.hang.api.features.foods.payloads.responses.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CartFoodResponse {
    private UUID id;
    private UUID cartId;
    private UUID tableId;
    private UUID foodId;
    private int quantity;
    private String specialRequests;
    private FoodResponse food;

    public static CartFoodResponse fromEntity(CartFood cartFood) {
        CartFoodResponse response = new CartFoodResponse();
        response.setId(cartFood.getId());
        response.setCartId(cartFood.getCart().getId());
        response.setTableId(cartFood.getCart().getTable().getId());
        response.setFood(FoodResponse.fromEntity(cartFood.getFood()));
        response.setFoodId(cartFood.getFood().getId());
        response.setQuantity(cartFood.getQuantity());
        response.setSpecialRequests(cartFood.getSpecialRequests());
        return response;
    }

    public static List<CartFoodResponse> fromEntities(List<CartFood> cartFoods) {
        if (cartFoods == null) {
            return Collections.emptyList();
        }
        List<CartFoodResponse> responses = new ArrayList<>();
        for (CartFood cartFood : cartFoods) {
            responses.add(fromEntity(cartFood));
        }
        return responses;
    }
}
