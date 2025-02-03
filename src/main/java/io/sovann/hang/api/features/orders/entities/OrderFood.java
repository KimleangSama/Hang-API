package io.sovann.hang.api.features.orders.entities;

import io.sovann.hang.api.features.foods.entities.Food;
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

    private Integer quantity;
    private Double unitPrice;
    private String specialRequests;
}