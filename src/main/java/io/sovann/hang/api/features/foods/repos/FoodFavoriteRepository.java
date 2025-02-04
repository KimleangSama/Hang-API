package io.sovann.hang.api.features.foods.repos;

import io.sovann.hang.api.features.foods.entities.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FoodFavoriteRepository extends JpaRepository<Favorite, UUID> {
    Optional<Favorite> findByUserIdAndFoodId(UUID id, UUID foodId);

    List<Favorite> findAllByUserId(UUID userId);
}
