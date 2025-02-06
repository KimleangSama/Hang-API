package io.sovann.hang.api.features.feedbacks.entities;

import io.sovann.hang.api.features.feedbacks.enums.*;
import io.sovann.hang.api.features.users.entities.*;
import jakarta.persistence.*;
import java.io.*;
import lombok.*;
import org.springframework.data.redis.core.*;

@RedisHash("Feedback")
@Getter
@Setter
@ToString
@Entity
@Table(name = "feedbacks")
public class Feedback extends BaseEntityAudit {
    @Serial
    private static final long serialVersionUID = 1L;

    @ManyToOne
    @JoinColumn(name = "table_id")
    private io.sovann.hang.api.features.tables.entities.Table table;

    private String fullname;
    private String phone;
    private String comment;
    @Enumerated(EnumType.STRING)
    private Rating rating;
}