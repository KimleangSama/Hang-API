package io.sovann.hang.api.features.foods.payloads.requests;

import io.sovann.hang.api.features.foods.entities.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CreateFavoriteRequest {
    private UUID foodId;
    private UUID categoryId;

    public static Favorite fromRequest(Food food) {
        Favorite favorite = new Favorite();
        favorite.setFood(food);
        return favorite;
    }
}
