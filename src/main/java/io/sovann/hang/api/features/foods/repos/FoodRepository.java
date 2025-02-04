package io.sovann.hang.api.features.foods.repos;

import io.sovann.hang.api.features.foods.entities.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface FoodRepository extends JpaRepository<Food, UUID> {
    List<Food> findAllByCategory(FoodCategory foodCategory);
}
