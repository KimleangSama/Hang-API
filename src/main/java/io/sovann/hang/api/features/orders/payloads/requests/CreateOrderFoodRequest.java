package io.sovann.hang.api.features.orders.payloads.requests;

import io.sovann.hang.api.features.orders.entities.OrderFood;
import io.sovann.hang.api.features.orders.enums.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
@Builder
public class CreateOrderFoodRequest {
    private UUID foodId;
    private int quantity;
    private String specialRequests;
}
