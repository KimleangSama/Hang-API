package io.sovann.hang.api.features.foods.payloads.requests;

import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class FoodCategoryToggleRequest {
    private UUID categoryId;
}
