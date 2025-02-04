package io.sovann.hang.api.features.foods.payloads.requests;

import io.sovann.hang.api.features.foods.entities.FoodCategory;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class CreateFoodCategoryRequest {
    private String name;
    private String description;
    private String icon;
    private boolean isHidden = false;
    private boolean isAvailable = true;
    private UUID storeId;

    public static FoodCategory fromRequest(CreateFoodCategoryRequest request) {
        FoodCategory foodCategory = new FoodCategory();
        foodCategory.setName(request.getName());
        foodCategory.setDescription(request.getDescription());
        foodCategory.setIcon(request.getIcon());
        foodCategory.setHidden(request.isHidden());
        foodCategory.setAvailable(request.isAvailable());
        return foodCategory;
    }
}
