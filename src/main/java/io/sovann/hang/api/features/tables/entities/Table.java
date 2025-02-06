package io.sovann.hang.api.features.tables.entities;

import io.sovann.hang.api.features.tables.enums.*;
import io.sovann.hang.api.features.users.entities.*;
import jakarta.persistence.*;
import java.io.*;
import java.util.*;
import lombok.*;
import org.springframework.data.redis.core.*;

@RedisHash("Table")
@Getter
@Setter
@ToString
@Entity
@jakarta.persistence.Table(name = "tables")
public class Table extends BaseEntityAudit {
    @Serial
    private static final long serialVersionUID = 1L;

    @Column
    private String name;
    @Column(nullable = false)
    private String tableNumber;
    @Column
    private String description;
    private double basePrice;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TableStatus status;
    @Column
    private int capacity;

    @Column
    private Boolean isGrouped = false;
    @OneToMany(mappedBy = "table")
    private List<TableGroupMapping> groupMappings;
}
