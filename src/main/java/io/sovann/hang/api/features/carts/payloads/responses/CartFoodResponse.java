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
    private FoodResponse food;

    public static CartFoodResponse fromEntity(CartFood cartFood) {
        CartFoodResponse response = new CartFoodResponse();
        response.setId(cartFood.getId());
        response.setCartId(cartFood.getCart().getId());
        response.setTableId(cartFood.getCart().getTable().getId());
        response.setFood(FoodResponse.fromEntity(cartFood.getFood()));
        return response;
    }

    public static List<CartFoodResponse> fromEntities(List<CartFood> cartFoods) {
        if (cartFoods == null) {
            return null;
        }
        List<CartFoodResponse> responses = new ArrayList<>();
        for (CartFood cartFood : cartFoods) {
            responses.add(fromEntity(cartFood));
        }
        return responses;
    }
}
