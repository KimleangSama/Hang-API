package io.sovann.hang.api.features.foods.payloads.responses;

import io.sovann.hang.api.features.foods.entities.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class FoodCategoryResponse {
    private UUID id;
    private String name;
    private String description;
    private String icon;
    private boolean isHidden = false;
    private boolean isAvailable = true;

    public static FoodCategoryResponse fromEntity(FoodCategory foodCategory) {
        FoodCategoryResponse response = new FoodCategoryResponse();
        response.setId(foodCategory.getId());
        response.setName(foodCategory.getName());
        response.setDescription(foodCategory.getDescription());
        response.setIcon(foodCategory.getIcon());
        response.setHidden(foodCategory.isHidden());
        response.setAvailable(foodCategory.isAvailable());
        return response;
    }

    public static List<FoodCategoryResponse> fromEntities(List<FoodCategory> foodCategories) {
        return foodCategories.stream().map(FoodCategoryResponse::fromEntity).toList();
    }
}
