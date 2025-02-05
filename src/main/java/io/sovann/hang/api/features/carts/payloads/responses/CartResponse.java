package io.sovann.hang.api.features.carts.payloads.responses;

import io.sovann.hang.api.features.carts.entities.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CartResponse {
    private UUID id;
    private UUID tableId;
    private List<CartFoodResponse> cartFoods;

    public static CartResponse fromEntity(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setTableId(cart.getTable().getId());
        response.setCartFoods(CartFoodResponse.fromEntities(cart.getCartFoods()));
        return response;
    }
}
