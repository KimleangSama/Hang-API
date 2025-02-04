package io.sovann.hang.api.features.foods.payloads.requests;

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
    private SpicyLevel spicyLevel;
    private String ingredients;
    private String allergens;
    private String cookingTime;
    private String servingSize;
    private String calories;
    private UUID categoryId;
}
