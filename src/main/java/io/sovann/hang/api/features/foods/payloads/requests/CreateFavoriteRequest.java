package io.sovann.hang.api.features.foods.payloads.requests;

import io.sovann.hang.api.features.foods.entities.Favorite;
import io.sovann.hang.api.features.foods.entities.Food;
import io.sovann.hang.api.features.foods.enums.SpicyLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

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
