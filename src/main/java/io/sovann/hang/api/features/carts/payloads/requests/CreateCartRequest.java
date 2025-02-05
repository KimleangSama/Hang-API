package io.sovann.hang.api.features.carts.payloads.requests;

import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CreateCartRequest {
    private UUID tableId;
    private List<CreateCartFoodRequest> cartFoods;
}
