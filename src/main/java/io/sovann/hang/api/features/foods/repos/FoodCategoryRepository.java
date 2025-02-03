package io.sovann.hang.api.features.foods.repos;

import io.sovann.hang.api.features.foods.entities.FoodCategory;
import io.sovann.hang.api.features.stores.entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, UUID> {
    List<FoodCategory> findAllByStore(Store store);
}
