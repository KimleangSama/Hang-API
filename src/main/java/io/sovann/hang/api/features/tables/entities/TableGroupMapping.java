package io.sovann.hang.api.features.tables.entities;

import jakarta.persistence.*;
import java.io.*;
import java.util.*;
import lombok.*;
import org.springframework.data.redis.core.*;

@RedisHash("TableGroupMapping")
@Getter
@Setter
@ToString
@Entity
@jakarta.persistence.Table(name = "table_group_mappings")
public class TableGroupMapping implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private TableGroup tableGroup;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private Table table;
}
