package io.sovann.hang.api.features.stores.repos;

import io.sovann.hang.api.features.stores.entities.OperatingHour;
import io.sovann.hang.api.features.stores.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OperatingHourRepository extends JpaRepository<OperatingHour, UUID> {
}
