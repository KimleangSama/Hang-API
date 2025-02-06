package io.sovann.hang.api.features.carts.repos;

import io.sovann.hang.api.features.carts.entities.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<List<Cart>> findByTableId(UUID tableId);
    Optional<Cart> findByCreatedBy(UUID id);
}
