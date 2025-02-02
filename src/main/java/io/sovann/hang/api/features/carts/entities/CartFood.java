package io.sovann.hang.api.features.carts.entities;

import io.sovann.hang.api.features.foods.entities.Food;
import io.sovann.hang.api.features.foods.entities.FoodCategory;
import io.sovann.hang.api.features.foods.enums.SpicyLevel;
import io.sovann.hang.api.features.users.entities.BaseEntityAudit;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;

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
    private Food item;

    private Integer quantity;
    private String specialRequests;
}
