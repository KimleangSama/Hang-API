package io.sovann.hang.api.features.orders.entities;

import io.sovann.hang.api.features.orders.enums.*;
import io.sovann.hang.api.features.users.entities.*;
import jakarta.persistence.*;
import java.io.*;
import java.time.*;
import java.util.*;
import lombok.*;
import org.springframework.data.redis.core.*;

@RedisHash("Order")
@Getter
@Setter
@ToString
@Entity
@Table(name = "orders")
public class Order extends BaseEntityAudit {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private io.sovann.hang.api.features.tables.entities.Table table;

    private Double totalAmount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.pending;

    @Column(name = "order_time")
    private LocalDateTime orderTime;

    private String specialInstructions;

    @OneToMany(mappedBy = "order")
    private List<OrderFood> orderFoods;
}