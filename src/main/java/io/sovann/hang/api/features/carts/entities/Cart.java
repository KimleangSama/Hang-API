package io.sovann.hang.api.features.carts.entities;

import io.sovann.hang.api.features.carts.payloads.responses.*;
import io.sovann.hang.api.features.users.entities.*;
import jakarta.persistence.*;
import java.io.*;
import java.util.*;
import lombok.*;
import org.springframework.data.redis.core.*;

@RedisHash("Cart")
@Getter
@Setter
@ToString
@Entity
@Table(name = "carts", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"created_by"})
})
public class Cart extends BaseEntityAudit {
    @Serial
    private final static long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private io.sovann.hang.api.features.tables.entities.Table table;

    @OneToMany(mappedBy = "cart")
    private List<CartFood> cartFoods;
}
