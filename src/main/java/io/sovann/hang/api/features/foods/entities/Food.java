package io.sovann.hang.api.features.foods.entities;

import io.sovann.hang.api.features.foods.enums.*;
import io.sovann.hang.api.features.users.entities.*;
import jakarta.persistence.*;
import java.io.*;
import lombok.*;
import org.springframework.data.redis.core.*;

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
    private Boolean isHidden = false;
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
