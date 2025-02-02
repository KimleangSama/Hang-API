package io.sovann.hang.api.features.carts.entities;

import io.sovann.hang.api.features.users.entities.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.util.List;

@RedisHash("Cart")
@Getter
@Setter
@ToString
@Entity
@Table(name = "carts")
public class Cart extends BaseEntityAudit {
    @Serial
    private final static long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private io.sovann.hang.api.features.tables.entities.Table table;

    @OneToMany(mappedBy = "cart")
    private List<CartFood> cartFoods;
}
