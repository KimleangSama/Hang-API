package io.sovann.hang.api.features.carts.repos;

import io.sovann.hang.api.features.carts.entities.*;
import java.util.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface CartFoodRepository extends JpaRepository<CartFood, UUID> {
}
