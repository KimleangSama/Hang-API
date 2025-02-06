package io.sovann.hang.api.features.foods.repos;

import io.sovann.hang.api.features.foods.entities.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface FoodFavoriteRepository extends JpaRepository<Favorite, UUID> {
    Optional<Favorite> findByUserIdAndFoodId(UUID id, UUID foodId);

    List<Favorite> findAllByUserId(UUID userId);
}
