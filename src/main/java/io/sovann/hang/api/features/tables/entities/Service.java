package io.sovann.hang.api.features.tables.entities;

import io.sovann.hang.api.features.users.entities.*;
import jakarta.persistence.*;
import java.io.*;
import lombok.*;
import org.springframework.data.redis.core.*;

@RedisHash("Service")
@Getter
@Setter
@ToString
@Entity
@jakarta.persistence.Table(name = "services")
public class Service extends BaseEntityAudit {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private io.sovann.hang.api.features.tables.entities.Table table;

    private String name;
    private String description;
    private Boolean isResolved;
}