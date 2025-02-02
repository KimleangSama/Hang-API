package io.sovann.hang.api.features.stores.entities;

import io.sovann.hang.api.features.users.entities.BaseEntityAudit;
import io.sovann.hang.api.features.users.entities.Group;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serial;
import java.util.List;

@RedisHash("Store")
@Getter
@Setter
@ToString
@Entity
@Table(name = "stores")
public class Store extends BaseEntityAudit {
    @Serial
    private final static long serialVersionUID = 1L;

    @Column(unique = true, nullable = false)
    private String name;
    private String logoUrl;
    private String color;
    private String description;
    private String address;
    private String phone;
    private String email;
    private String googleMapAddress;

    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OperatingHour> operatingHours;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderingOption> orderingOptions;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PaymentMethod> paymentMethods;
    @OneToMany(mappedBy = "store", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<io.sovann.hang.api.features.tables.entities.Table> tables;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;
}
