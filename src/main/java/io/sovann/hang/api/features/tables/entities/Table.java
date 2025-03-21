package io.sovann.hang.api.features.tables.entities;

import io.sovann.hang.api.features.stores.entities.Store;
import io.sovann.hang.api.features.tables.enums.TableStatus;
import io.sovann.hang.api.features.users.entities.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.util.List;

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
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TableStatus status;
    @Column
    private int capacity;
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @Column
    private Boolean isGrouped = false;
    @OneToMany(mappedBy = "table")
    private List<TableGroupMapping> groupMappings;
}
