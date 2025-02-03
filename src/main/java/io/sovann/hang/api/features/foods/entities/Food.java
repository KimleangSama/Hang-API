package io.sovann.hang.api.features.foods.entities;

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

@RedisHash("Food")
@Getter
@Setter
@ToString
@Entity
@Table(name = "foods")
public class Food extends BaseEntityAudit {
    @Serial
    private final static long serialVersionUID = 1L;

    private String name;
    private String description;
    // Price
    private Double price;
    private Double discount;

    private String imageUrl;
    private Boolean isAvailable;
    private SpicyLevel spicyLevel;
    private String ingredients;
    private String allergens;
    private String cookingTime;
    private String servingSize;
    private String calories;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private FoodCategory category;
}
