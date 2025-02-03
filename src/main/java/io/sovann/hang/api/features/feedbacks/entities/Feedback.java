package io.sovann.hang.api.features.feedbacks.entities;

import io.sovann.hang.api.features.feedbacks.enums.Rating;
import io.sovann.hang.api.features.users.entities.BaseEntityAudit;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;

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