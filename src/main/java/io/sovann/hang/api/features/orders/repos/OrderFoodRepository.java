package io.sovann.hang.api.features.orders.repos;

import io.sovann.hang.api.features.orders.entities.OrderFood;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderFoodRepository extends JpaRepository<OrderFood, UUID> {
}
