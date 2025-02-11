package io.sovann.hang.api.features.carts.repos;

import io.sovann.hang.api.features.carts.entities.Cart;
import io.sovann.hang.api.features.carts.entities.CartFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartFoodRepository extends JpaRepository<CartFood, UUID> {
    List<CartFood> findAllByCartId(UUID cartId);

    List<CartFood> findAllByCartIn(List<Cart> carts);

    Optional<CartFood> findByIdAndCartId(UUID id, UUID cartId);

    void deleteAllByCartId(UUID cartId);
}
