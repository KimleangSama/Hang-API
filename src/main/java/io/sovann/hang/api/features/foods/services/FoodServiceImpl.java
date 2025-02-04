package io.sovann.hang.api.features.foods.services;

import io.sovann.hang.api.features.foods.entities.*;
import io.sovann.hang.api.features.foods.payloads.requests.*;
import io.sovann.hang.api.features.foods.payloads.responses.*;
import io.sovann.hang.api.features.foods.repos.*;
import io.sovann.hang.api.features.users.entities.*;
import java.util.*;
import lombok.*;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.*;
import org.springframework.stereotype.*;
import org.springframework.transaction.annotation.*;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl {
    private final FoodRepository foodRepository;
    private final FoodCategoryRepository foodCategoryRepository;

    @Transactional
    @CacheEvict(value = "foods", allEntries = true)
    public FoodResponse createFood(User user, CreateFoodRequest request) {
        FoodCategory foodCategory = foodCategoryRepository.findById(request.getCategoryId())
                .orElse(null);
        Food food = CreateFoodRequest.fromRequest(request);
        food.setCreatedBy(user.getId());
        food.setCategory(foodCategory);
        foodRepository.save(food);
        return FoodResponse.fromEntity(food);
    }

    @Transactional
    @Cacheable(value = "foods", key = "#categoryId")
    public List<FoodResponse> listFoodByCategoryId(
            User user,
            UUID categoryId
    ) {
        FoodCategory foodCategory = foodCategoryRepository.findById(categoryId)
                .orElse(null);
        if (foodCategory == null) {
            return Collections.emptyList();
        }
        List<Food> foods = foodRepository.findAllByCategory(foodCategory);
        return FoodResponse.fromEntities(foods);
    }

    @Transactional
    @Cacheable(value = "foods")
    public List<FoodResponse> listFoods(User user, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Food> foods = foodRepository.findAll(pageable);
        return FoodResponse.fromEntities(foods.getContent());
    }

    @Transactional
    public long count() {
        return foodRepository.count();
    }
}
