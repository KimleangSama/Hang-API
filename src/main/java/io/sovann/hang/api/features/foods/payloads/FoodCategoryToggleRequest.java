package io.sovann.hang.api.features.foods.payloads;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString
public class FoodCategoryToggleRequest {
    private UUID storeId;
    private UUID categoryId;
}
