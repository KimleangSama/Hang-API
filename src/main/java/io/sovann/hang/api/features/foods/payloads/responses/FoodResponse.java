package io.sovann.hang.api.features.foods.payloads.responses;

import io.sovann.hang.api.features.foods.entities.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class FoodResponse {
    private String name;
    private String description;
    private double price;
    private double discount;
    private String imageUrl;
    private boolean isAvailable;
    private String spicyLevel;
    private String ingredients;
    private String allergens;
    private String cookingTime;
    private String servingSize;
    private String calories;

    public static FoodResponse fromEntity(Food food) {
        FoodResponse response = new FoodResponse();
        response.setName(food.getName());
        response.setDescription(food.getDescription());
        response.setPrice(food.getPrice());
        response.setDiscount(food.getDiscount());
        response.setImageUrl(food.getImageUrl());
        response.setAvailable(food.getIsAvailable());
        response.setSpicyLevel(food.getSpicyLevel().name());
        response.setIngredients(food.getIngredients());
        response.setAllergens(food.getAllergens());
        response.setCookingTime(food.getCookingTime());
        response.setServingSize(food.getServingSize());
        response.setCalories(food.getCalories());
        return response;
    }

    public static List<FoodResponse> fromEntities(List<Food> foods) {
        return foods.stream().map(FoodResponse::fromEntity).toList();
    }
}
