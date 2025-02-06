package io.sovann.hang.api.features.carts.repos;

import io.sovann.hang.api.features.carts.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartRepository extends JpaRepository<Cart, UUID> {
    Optional<Cart> findByCreatedBy(UUID createdBy);

    List<Cart> findAllByTableId(UUID tableId);
}
