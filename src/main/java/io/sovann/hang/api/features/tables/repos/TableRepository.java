package io.sovann.hang.api.features.tables.repos;

import io.sovann.hang.api.features.tables.entities.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface TableRepository extends JpaRepository<Table, UUID> {
    List<Table> findByCreatedBy(UUID userId);
}
