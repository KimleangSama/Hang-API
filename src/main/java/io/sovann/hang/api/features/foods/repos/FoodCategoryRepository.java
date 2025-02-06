package io.sovann.hang.api.features.foods.repos;

import io.sovann.hang.api.features.foods.entities.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, UUID> {

}
