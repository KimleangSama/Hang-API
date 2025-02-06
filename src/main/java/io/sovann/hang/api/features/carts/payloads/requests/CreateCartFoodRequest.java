package io.sovann.hang.api.features.carts.payloads.requests;

import java.io.Serializable;
import java.util.*;
import lombok.*;

@Getter
@Setter
@ToString
public class CreateCartFoodRequest implements Serializable {
    private UUID cartId;
    private UUID foodId;
    private Integer quantity;
    private String specialRequests;
}
