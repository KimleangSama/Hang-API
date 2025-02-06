package io.sovann.hang.api.features.carts.entities;

import io.sovann.hang.api.features.foods.entities.*;
import io.sovann.hang.api.features.users.entities.*;
import jakarta.persistence.*;
import java.io.*;
import lombok.*;
import org.springframework.data.redis.core.*;

@RedisHash("CartFood")
@Getter
@Setter
@ToString
@Entity
@Table(name = "cart_foods")
public class CartFood extends BaseEntityAudit {
    @Serial
    private final static long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

    private Integer quantity;
    private String specialRequests;
}
