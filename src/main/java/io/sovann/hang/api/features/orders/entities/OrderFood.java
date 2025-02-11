package io.sovann.hang.api.features.orders.entities;

import io.sovann.hang.api.features.foods.entities.*;
import io.sovann.hang.api.features.users.entities.*;
import jakarta.persistence.*;
import java.io.*;
import lombok.*;
import org.springframework.data.redis.core.*;

@RedisHash("OrderFood")
@Getter
@Setter
@ToString
@Entity
@Table(name = "order_foods")
public class OrderFood extends BaseEntityAudit {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private int quantity;
    private Double unitPrice;
    private String specialRequests;

    public OrderFood() {}

    public OrderFood(Food food, Integer quantity, Double unitPrice, String specialRequests) {
        this.food = food;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.specialRequests = specialRequests;
    }
}