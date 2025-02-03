package io.sovann.hang.api.features.foods.entities;

import io.sovann.hang.api.features.stores.entities.Store;
import io.sovann.hang.api.features.users.entities.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.util.List;

@RedisHash("FoodCategory")
@Getter
@Setter
@ToString
@Entity
@Table(name = "food_categories")
public class FoodCategory extends BaseEntityAudit {
    @Serial
    private final static long serialVersionUID = 1L;

    @Column(nullable = false)
    private String name;
    private String description;
    private String icon;
    private boolean isHidden = false;
    private boolean isAvailable = true;

    @OneToMany(mappedBy = "category")
    private List<Food> foods;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
}
