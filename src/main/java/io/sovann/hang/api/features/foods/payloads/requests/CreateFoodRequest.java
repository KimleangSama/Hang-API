package io.sovann.hang.api.features.foods.payloads.requests;

import io.sovann.hang.api.features.foods.entities.*;
import io.sovann.hang.api.features.foods.enums.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CreateFoodRequest {
    private String name;
    private String description;
    private double price;
    private double discount;
    private String imageUrl;
    private boolean isAvailable;
    private boolean isHidden;
    private SpicyLevel spicyLevel;
    private String ingredients;
    private String allergens;
    private String cookingTime;
    private String servingSize;
    private String calories;
    private UUID categoryId;

    public static Food fromRequest(CreateFoodRequest request) {
        Food food = new Food();
        food.setName(request.getName());
        food.setDescription(request.getDescription());
        food.setPrice(request.getPrice());
        food.setDiscount(request.getDiscount());
        food.setImageUrl(request.getImageUrl());
        food.setIsAvailable(request.isAvailable());
        food.setIsHidden(request.isHidden());
        food.setSpicyLevel(request.getSpicyLevel());
        food.setIngredients(request.getIngredients());
        food.setAllergens(request.getAllergens());
        food.setCookingTime(request.getCookingTime());
        food.setServingSize(request.getServingSize());
        food.setCalories(request.getCalories());
        return food;
    }
}
