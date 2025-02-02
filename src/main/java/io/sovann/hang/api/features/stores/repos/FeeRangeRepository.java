package io.sovann.hang.api.features.stores.repos;

import io.sovann.hang.api.features.stores.entities.FeeRange;
import io.sovann.hang.api.features.stores.entities.PaymentMethod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FeeRangeRepository extends JpaRepository<FeeRange, UUID> {
}
