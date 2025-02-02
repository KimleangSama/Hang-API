package io.sovann.hang.api.features.foods.entities;

import io.sovann.hang.api.features.foods.enums.SpicyLevel;
import io.sovann.hang.api.features.users.entities.BaseEntityAudit;
import io.sovann.hang.api.features.users.entities.User;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;

@RedisHash("Favorite")
@Getter
@Setter
@ToString
@Entity
@Table(name = "favorites")
public class Favorite extends BaseEntityAudit {
    @Serial
    private final static long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "food_id", nullable = false)
    private Food food;
}
