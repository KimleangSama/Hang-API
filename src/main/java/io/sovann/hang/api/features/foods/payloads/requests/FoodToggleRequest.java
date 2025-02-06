package io.sovann.hang.api.features.foods.payloads.requests;

import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class FoodToggleRequest {
    private UUID foodId;
    private UUID categoryId;
}
