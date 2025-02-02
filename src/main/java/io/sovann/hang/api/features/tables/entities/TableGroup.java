package io.sovann.hang.api.features.tables.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RedisHash("TableGroup")
@Getter
@Setter
@ToString
@Entity
@jakarta.persistence.Table(name = "table_groups")
public class TableGroup implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(columnDefinition = "UUID", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "grouped_at")
    private LocalDateTime groupedAt;

    @OneToMany(mappedBy = "tableGroup", cascade = CascadeType.ALL)
    private List<TableGroupMapping> mappings;
}
