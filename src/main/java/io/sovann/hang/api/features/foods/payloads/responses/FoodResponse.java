package io.sovann.hang.api.features.foods.payloads.responses;

import io.sovann.hang.api.features.foods.entities.*;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class FoodResponse {
    private UUID id;
    private String name;
    private String description;
    private double price;
    private double discount;
    private String imageUrl;
    private boolean isAvailable;
    private boolean isHidden;
    private String spicyLevel;
    private String ingredients;
    private String allergens;
    private String cookingTime;
    private String servingSize;
    private String calories;
    private boolean isFavorite;
    private UUID categoryId;
    private String categoryName;

    public static FoodResponse fromEntity(Food food) {
        FoodResponse response = new FoodResponse();
        response.setId(food.getId());
        response.setName(food.getName());
        response.setDescription(food.getDescription());
        response.setPrice(food.getPrice());
        response.setDiscount(food.getDiscount());
        response.setImageUrl(food.getImageUrl());
        response.setAvailable(food.getIsAvailable());
        response.setHidden(food.getIsHidden());
        response.setSpicyLevel(food.getSpicyLevel().name());
        response.setIngredients(food.getIngredients());
        response.setAllergens(food.getAllergens());
        response.setCookingTime(food.getCookingTime());
        response.setServingSize(food.getServingSize());
        response.setCalories(food.getCalories());
        if (food.getCategory() == null) {
            return response;
        }
        response.setCategoryId(food.getCategory().getId());
        response.setCategoryName(food.getCategory().getName());
        return response;
    }

    public static List<FoodResponse> fromEntities(List<Food> foods, List<FavoriteResponse> favorites) {
        List<FoodResponse> responses = new ArrayList<>();
        for (Food food : foods) {
            FoodResponse response = fromEntity(food);
            response.setFavorite(
                    favorites
                            .stream()
                            .anyMatch(favorite -> favorite.getFoodId().equals(food.getId())));
            responses.add(response);
        }
        return responses;
    }
}
